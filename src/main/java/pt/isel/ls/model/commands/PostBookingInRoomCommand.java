package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.Parameters;
import pt.isel.ls.model.commands.common.exceptions.CommandException;
import pt.isel.ls.model.commands.common.exceptions.InvalidIdException;
import pt.isel.ls.model.commands.common.exceptions.MissingArgumentsException;
import pt.isel.ls.model.commands.common.exceptions.OverlapException;
import pt.isel.ls.model.commands.common.exceptions.ParseArgumentException;
import pt.isel.ls.model.commands.results.PostBookingInRoomResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Booking;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;

import static pt.isel.ls.model.commands.helpers.DatabaseDataHelper.dateOverlaps;
import static pt.isel.ls.utils.DateUtils.parseTime;
import static pt.isel.ls.utils.DateUtils.parseTimeWithTimezone;

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
                throw new MissingArgumentsException("No parameters specified");
            }

            Integer uid;
            Integer rid;
            try {
                uid = params.getInt("uid");
                rid = commandRequest.getPath().getInt("rid");
            } catch (NumberFormatException e) {
                throw new InvalidIdException("Invalid User ID or Room ID");
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
                    beginDate = parseTimeWithTimezone(begin, "yyyy-MM-dd'T'HH:mm");
                    //Parsed time without timezone because the duration is independent of Timezones
                    durationDate = parseTime(duration, "HH:mm");
                } catch (ParseException e) {
                    throw new ParseArgumentException("Failed to parse dates");
                }
                Date endDate = new Date(beginDate.getTime() + durationDate.getTime());

                if (!dateOverlaps(beginDate, endDate, rid, con)) {
                    ps.setTimestamp(3, new java.sql.Timestamp(beginDate.getTime()));
                    ps.setTimestamp(4, new java.sql.Timestamp(endDate.getTime()));

                    ps.executeUpdate();

                    //Get bid
                    ResultSet rs = ps.getGeneratedKeys();
                    rs.next();
                    result.setBooking(new Booking(rs.getInt("bid")));
                } else {
                    throw new OverlapException("Could not insert Booking, as it overlaps with an existing one");
                }
            } else {
                throw new MissingArgumentsException();
            }
            ps.close();
        });
        return result;
    }

    @Override
    public String getDescription() {
        return "creates a new booking, given the following additional parameters\n"
                + "- begin - the begin instant for the booking period.\n"
                + "- duration - the booking duration.\n"
                + "- uid - the identifier of the user making the booking.";
    }
}
