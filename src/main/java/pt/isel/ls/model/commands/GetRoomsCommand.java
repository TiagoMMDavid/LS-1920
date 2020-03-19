package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.PsqlHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetRoomsCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) {
        CommandResult result = new CommandResult();
        try (Connection con = PsqlHandler.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT rid "
                    + "FROM ROOM ");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.addResult(Integer.toString(rs.getInt(1)));
            }
            result.setTitle("Rooms ids");
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
