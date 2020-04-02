package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.sql.TransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetBookingsByUserIdCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {
        CommandResult result = new CommandResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        if(!trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("SELECT bid "
                    + "FROM BOOKING WHERE uid = ?");

            int userId = commandRequest.getPath().getInt("uid");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                result.addResult("No results found");
            } else {
                do {
                    result.addResult("booking id (bid): " + rs.getInt("bid"));
                } while (rs.next());
            }
            result.setTitle("All bookings of user " + userId);
            result.setSuccess(true);

            rs.close();
            ps.close();
        })) {
            result.setSuccess(false);
            result.clearResults();
        }
        return result;
    }
}
