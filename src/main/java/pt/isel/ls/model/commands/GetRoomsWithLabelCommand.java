package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetRoomsWithLabelCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {
        CommandResult result = new CommandResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        if (!trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("SELECT rid "
                    + "FROM ROOMLABEL WHERE lid = ?");

            int labelId = commandRequest.getPath().getInt("lid");
            ps.setInt(1, labelId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                do {
                    result.addResult(new Room(rs.getInt("rid")));
                } while (rs.next());
            }
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
