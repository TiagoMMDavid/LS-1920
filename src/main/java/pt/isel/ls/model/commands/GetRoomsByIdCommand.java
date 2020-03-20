package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.PsqlConnectionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetRoomsByIdCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) {
        CommandResult result = new CommandResult();
        try (Connection con = PsqlConnectionHandler.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * "
                    + "FROM ROOM WHERE rid = ?");

            int roomId = Integer.parseInt(commandRequest.getPath().getVariable(0));
            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.addResult("rid: " + rs.getInt("rid"));
                result.addResult("name: " + rs.getString("name"));
                result.addResult("description: " + rs.getString("description"));
                result.addResult("location: " + rs.getString("location"));
                result.addResult("capacity: " + rs.getInt("capacity"));
            }
            result.setTitle("Information about room " + roomId);
            result.setSuccess(true);

            rs.close();
            ps.close();
        } catch (SQLException e) {
            result.setSuccess(false);
            result.clearResults();
            result.setTitle(e.getMessage());
        }
        return result;
    }
}
