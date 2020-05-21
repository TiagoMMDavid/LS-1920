package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.exceptions.CommandException;
import pt.isel.ls.model.commands.common.exceptions.InvalidIdException;
import pt.isel.ls.model.commands.helpers.DatabaseDataHelper;
import pt.isel.ls.model.commands.results.GetRoomByIdResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetRoomByIdCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        GetRoomByIdResult result = new GetRoomByIdResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("SELECT * "
                    + "FROM ROOM WHERE rid = ?");
            int roomId;
            try {
                roomId = commandRequest.getPath().getInt("rid");
            } catch (NumberFormatException e) {
                throw new InvalidIdException("Invalid Room ID");
            }
            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Integer capacity = rs.getInt("capacity");
                if (rs.wasNull()) {
                    capacity = null;
                }
                result.setRoom(new Room(
                        rs.getInt("rid"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("location"),
                        capacity
                ));
                result.setLabels(DatabaseDataHelper.getLabelsFromRid(roomId, con));
                result.setHasBookings(DatabaseDataHelper.hasBookings(roomId, con));
            } else {
                throw new InvalidIdException("Room does not exist");
            }

            rs.close();
            ps.close();
        });
        return result;
    }

    @Override
    public String getDescription() {
        return "returns the detailed information for the room identified by rid";
    }
}
