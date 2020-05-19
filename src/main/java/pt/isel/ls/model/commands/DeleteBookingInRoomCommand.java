package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.exceptions.CommandException;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.exceptions.InvalidIdException;
import pt.isel.ls.model.commands.results.DeleteBookingInRoomResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.model.paths.Path;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteBookingInRoomCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        DeleteBookingInRoomResult result = new DeleteBookingInRoomResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("DELETE "
                    + "FROM BOOKING WHERE rid = ? AND bid = ?");
            Path path = commandRequest.getPath();
            int roomId;
            int bookingId;
            try {
                roomId = path.getInt("rid");
                bookingId = path.getInt("bid");
            } catch (NumberFormatException e) {
                throw new InvalidIdException("Invalid Room or Booking ID");
            }

            ps.setInt(1, roomId);
            ps.setInt(2, bookingId);
            if (ps.executeUpdate() != 0) {
                result.setBooking(new Booking(bookingId));
            } else {
                throw new InvalidIdException("Booking does not exist");
            }
        });
        return result;
    }

    @Override
    public String getDescription() {
        return "removes the identified booking";
    }
}
