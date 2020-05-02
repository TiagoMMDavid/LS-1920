package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandException;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.helpers.LabelsHelper;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetRoomByIdCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        CommandResult result = new CommandResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("SELECT * "
                    + "FROM ROOM WHERE rid = ?");

            int roomId = commandRequest.getPath().getInt("rid");
            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Integer capacity = rs.getInt("capacity");
                if (rs.wasNull()) {
                    capacity = null;
                }
                result.addResult(new Room(
                        rs.getInt("rid"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("location"),
                        capacity,
                        LabelsHelper.getLabelsFromRid(con, roomId)
                ));
            }

            rs.close();
            ps.close();
        });
        return result;
    }

    @Override
    public String toString() {
        return "returns the detailed information for the room identified by rid";
    }
}
