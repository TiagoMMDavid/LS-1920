package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandException;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetBookingByRoomAndBookingIdResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.model.paths.Path;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import static pt.isel.ls.utils.DateUtils.parseTimeWithTimezone;

public class GetBookingByRoomAndBookingIdCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        GetBookingByRoomAndBookingIdResult result = new GetBookingByRoomAndBookingIdResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("SELECT * "
                    + "FROM BOOKING WHERE rid = ? AND bid = ?");
            Path path = commandRequest.getPath();
            int roomId;
            int bookingId;
            try {
                roomId = path.getInt("rid");
                bookingId = path.getInt("bid");
            } catch (NumberFormatException e) {
                throw new CommandException("Invalid Room or Booking ID");
            }
            ps.setInt(1, roomId);
            ps.setInt(2, bookingId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Date beginInst;
                Date endInst;
                try {
                    beginInst = parseTimeWithTimezone(rs.getString("begin_inst"), "yyyy-MM-dd HH:mm:ss");
                    endInst = parseTimeWithTimezone(rs.getString("end_inst"),"yyyy-MM-dd HH:mm:ss");
                } catch (ParseException e) {
                    throw new CommandException("Failed to parse dates");
                }
                result.setBooking(new Booking(
                        rs.getInt("bid"),
                        rs.getInt("uid"),
                        rs.getInt("rid"),
                        beginInst, endInst
                ));

            }
            rs.close();
            ps.close();
        });

        return result;
    }

    @Override
    public String toString() {
        return "returns the detailed information for the bid booking";
    }
}
