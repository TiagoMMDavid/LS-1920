package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.sql.TransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetRoomByIdCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {
        CommandResult result = new CommandResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        if (!trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("SELECT * "
                    + "FROM ROOM WHERE rid = ?");

            int roomId = commandRequest.getPath().getInt("rid");
            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                result.addResult("No results found");
            } else {
                result.addResult("room id (rid): " + rs.getInt("rid"));
                result.addResult("name: " + rs.getString("name"));
                result.addResult("description: " + rs.getString("description"));
                result.addResult("location: " + rs.getString("location"));
                result.addResult("capacity: " + rs.getInt("capacity"));
            }
            result.setTitle("Information about room " + roomId);
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
