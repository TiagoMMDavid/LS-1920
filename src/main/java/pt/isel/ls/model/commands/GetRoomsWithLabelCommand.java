package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetRoomsWithLabelCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) {
        CommandResult result = new CommandResult();
        try (Connection con = commandRequest.getConnectionHandler().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT rid "
                    + "FROM ROOMLABEL WHERE lid = ?");

            int labelId = Integer.parseInt(commandRequest.getPath().getVariable("lid"));
            ps.setInt(1, labelId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.addResult("room id (rid): " + rs.getInt("rid"));
            }
            result.setTitle("List of rooms that have label " + labelId);
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
