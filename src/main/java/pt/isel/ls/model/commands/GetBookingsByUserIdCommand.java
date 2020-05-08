package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandException;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Booking;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetBookingsByUserIdCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        CommandResult result = new CommandResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("SELECT bid, rid "
                    + "FROM BOOKING WHERE uid = ?");
            int userId;
            try {
                userId = commandRequest.getPath().getInt("uid");
            }  catch (NumberFormatException e) {
                throw new CommandException("Invalid User ID");
            }
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                do {
                    result.addResult(new Booking(rs.getInt("bid"), rs.getInt("rid"), userId));
                } while (rs.next());
            }
            rs.close();
            ps.close();
        });
        return result;
    }

    @Override
    public String toString() {
        return "returns the list of all bookings owned by the uid user";
    }
}
