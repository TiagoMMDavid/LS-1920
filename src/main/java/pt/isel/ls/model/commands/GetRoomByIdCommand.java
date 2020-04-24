package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.helpers.LabelsHelper;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Room;

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
            if (rs.next()) {
                result.addResult(new Room(
                        rs.getInt("rid"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getInt("capacity"),
                        LabelsHelper.getLabelsFromRid(con, roomId)
                ));
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

    @Override
    public String toString() {
        return "returns the detailed information for the room identified by rid";
    }
}
