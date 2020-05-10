package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandException;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.Parameters;
import pt.isel.ls.model.commands.results.PostBookingInRoomResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.ParseException;
import java.util.Date;

import static pt.isel.ls.utils.DateUtils.parseTimeWithTimezone;
import static pt.isel.ls.utils.DateUtils.parseTime;

public class PostBookingInRoomCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        PostBookingInRoomResult result = new PostBookingInRoomResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO BOOKING "
                            + "(uid, rid, begin_inst, end_inst) Values(?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            Parameters params = commandRequest.getParams();
            if (params == null) {
                throw new CommandException("No parameters specified");
            }
            Integer uid;
            Integer rid;
            try {
                uid = params.getInt("uid");
                rid = commandRequest.getPath().getInt("rid");
            } catch (NumberFormatException e) {
                throw new CommandException("Invalid User ID or Room ID");
            }
            String duration = params.getString("duration");
            String begin = params.getString("begin");
            if (uid != null && rid != null && duration != null && begin != null) {
                ps.setInt(1, uid);
                ps.setInt(2, rid);

                //Get beginDate and calculate end time
                Date beginDate;
                Date durationDate;
                try {
                    beginDate = parseTimeWithTimezone(begin, "yyyy-MM-dd HH:mm");
                    //Parsed time without timezone because the duration is independent of Timezones
                    durationDate = parseTime(duration, "HH:mm");
                } catch (ParseException e) {
                    throw new CommandException("Failed to parse dates");
                }
                Date endDate = new Date(beginDate.getTime() + durationDate.getTime());

                if (!hasOverlaps(beginDate, endDate, rid, con)) {
                    ps.setTimestamp(3, new java.sql.Timestamp(beginDate.getTime()));
                    ps.setTimestamp(4, new java.sql.Timestamp(endDate.getTime()));

                    ps.executeUpdate();

                    //Get bid
                    ResultSet rs = ps.getGeneratedKeys();
                    rs.next();
                    result.setBooking(new Booking(rs.getInt("bid")));
                } else {
                    throw new CommandException("Could not insert Booking, as it overlaps with an existing one");
                }
            } else {
                throw new CommandException("No arguments found / Invalid arguments");
            }
            ps.close();
        });
        return result;
    }

    private boolean hasOverlaps(Date begin, Date end, int rid, Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("select rid, begin_inst, end_inst"
                + " from booking"
                + " where (?, ?) overlaps (begin_inst, end_inst)"
                + " and ? = rid");
        ps.setTimestamp(1, new java.sql.Timestamp(begin.getTime()));
        ps.setTimestamp(2, new java.sql.Timestamp(end.getTime()));
        ps.setInt(3, rid);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    @Override
    public String toString() {
        return "creates a new booking, given the following additional parameters\n"
                + "- begin - the begin instant for the booking period.\n"
                + "- duration - the booking duration.\n"
                + "- uid - the identifier of the user making the booking.";
    }
}
