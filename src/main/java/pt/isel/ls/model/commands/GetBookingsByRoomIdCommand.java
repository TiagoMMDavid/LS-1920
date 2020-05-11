package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandException;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetBookingsByRoomIdResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Booking;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import static pt.isel.ls.utils.DateUtils.parseTimeWithTimezone;

public class GetBookingsByRoomIdCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        GetBookingsByRoomIdResult result = new GetBookingsByRoomIdResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("SELECT * "
                    + "FROM BOOKING "
                    + "WHERE rid = ?");
            int roomId;
            try {
                roomId = commandRequest.getPath().getInt("rid");
            } catch (NumberFormatException e) {
                throw new CommandException("Invalid Room ID");
            }
            result.setRoom(roomId);
            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Date beginInst;
                Date endInst;
                try {
                    beginInst = parseTimeWithTimezone(rs.getString("begin_inst"), "yyyy-MM-dd HH:mm:ss");
                    endInst = parseTimeWithTimezone(rs.getString("end_inst"),"yyyy-MM-dd HH:mm:ss");
                } catch (ParseException e) {
                    throw new CommandException("Failed to parse dates");
                }
                result.addBooking(new Booking(
                        rs.getInt("bid"),
                        beginInst, endInst
                ));
            }
            rs.close();
            ps.close();
        });

        return result;
    }

    @Override
    public String getDescription() {
        return "returns all bookings from a specific Room ID";
    }
}
