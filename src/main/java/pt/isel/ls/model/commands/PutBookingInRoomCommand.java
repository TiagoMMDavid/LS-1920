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
import pt.isel.ls.model.commands.results.PutBookingInRoomResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.model.paths.Path;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import static pt.isel.ls.model.commands.helpers.DatabaseDataHelper.dateOverlaps;
import static pt.isel.ls.utils.DateUtils.parseTime;
import static pt.isel.ls.utils.DateUtils.parseTimeWithTimezone;

public class PutBookingInRoomCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        PutBookingInRoomResult result = new PutBookingInRoomResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("UPDATE BOOKING "
                            + "SET uid = ?, begin_inst = ?, end_inst = ? "
                            + "WHERE rid = ? AND bid = ?"
            );

            Parameters params = commandRequest.getParams();
            Path path = commandRequest.getPath();
            if (params == null) {
                throw new MissingArgumentsException("No parameters specified");
            }

            Integer uid;
            Integer rid;
            Integer bid;
            try {
                uid = params.getInt("uid");
                rid = path.getInt("rid");
                bid = path.getInt("bid");
            } catch (NumberFormatException e) {
                throw new InvalidIdException("Invalid Room, Booking or User ID");
            }

            String duration = params.getString("duration");
            String begin = params.getString("begin");
            if (uid != null && rid != null && bid != null && duration != null && begin != null) {
                ps.setInt(1, uid);
                ps.setInt(4, rid);
                ps.setInt(5, bid);

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

                if (!dateOverlaps(beginDate, endDate, rid, bid, con)) {
                    ps.setTimestamp(2, new java.sql.Timestamp(beginDate.getTime()));
                    ps.setTimestamp(3, new java.sql.Timestamp(endDate.getTime()));

                    if (ps.executeUpdate() != 0) {
                        result.setBooking(new Booking(bid));
                    } else {
                        throw new InvalidIdException("Booking does not exist");
                    }
                } else {
                    throw new OverlapException("Could not modify Booking, as it overlaps with an existing one");
                }
            } else {
                throw new MissingArgumentsException();
            }
        });
        return result;
    }

    @Override
    public String getDescription() {
        return "Changes the identified booking, given the following additional parameters\n"
                + "- begin - the begin instant for the booking period.\n"
                + "- duration - the booking duration.\n"
                + "- uid - the identifier of the user making the booking.";
    }
}
