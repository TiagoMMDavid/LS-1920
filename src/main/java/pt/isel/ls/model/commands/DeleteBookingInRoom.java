package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.sql.TransactionManager;

import java.sql.PreparedStatement;

public class DeleteBookingInRoom implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {
        CommandResult result = new CommandResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        if (!trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("DELETE "
                    + "FROM BOOKING WHERE rid = ? AND bid = ?");

            int roomId = commandRequest.getPath().getInt("rid");
            int bookingId = commandRequest.getPath().getInt("bid");
            ps.setInt(1, roomId);
            ps.setInt(2, bookingId);
            int changes = ps.executeUpdate();
            result.setSuccess(changes >= 1);
        })) {
            result.setSuccess(false);
            result.clearResults();
        }
        return result;
    }

    @Override
    public String toString() {
        return "removes the identified booking";
    }
}
