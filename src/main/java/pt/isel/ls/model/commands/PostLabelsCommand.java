package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.PsqlConnectionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


public class PostLabelsCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) {
        CommandResult result = new CommandResult();
        try (Connection con = PsqlConnectionHandler.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO LABEL "
                            + "(name) Values(?)",
                            Statement.RETURN_GENERATED_KEYS
            );

            String label = commandRequest.getParams().getValue("name");
            if (label != null) {
                ps.setString(1, label);
                int success = ps.executeUpdate();
                con.commit();
                result.setSuccess(success > 0);
                result.setTitle("Label <" + label + "> added successfully");
                //Get lid
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                result.addResult("LID = " + rs.getInt("lid"));
            } else {
                throw new IllegalArgumentException("No arguments found / Invalid arguments");
            }
            ps.close();

        } catch (SQLException e) {
            result.setSuccess(false);
            result.clearResults();
            result.setTitle(e.getMessage());
        }
        return result;
    }
}
