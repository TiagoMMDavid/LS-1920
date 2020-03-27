package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetLabelsCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) {
        CommandResult result = new CommandResult();
        try (Connection con = commandRequest.getConnectionHandler().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT lid, name "
                    + "FROM LABEL");
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                result.addResult("No results found");
            } else {
                do {
                    result.addResult(rs.getString("name") + " (lid: " + rs.getInt("lid") + ")");
                } while (rs.next());
            }
            result.setTitle("ID and name of all the labels");
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
