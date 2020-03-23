package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.PsqlConnectionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostUsersCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) {
        CommandResult result = new CommandResult();
        try (Connection con = PsqlConnectionHandler.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO USERS "
                            + "(name, email) Values(?,?)",
                            Statement.RETURN_GENERATED_KEYS
            );

            String name = commandRequest.getParams().getValue("name");
            String email = commandRequest.getParams().getValue("email");
            if (name != null && email != null) {
                ps.setString(1, name);
                ps.setString(2, email);
                int success = ps.executeUpdate();
                con.commit();
                result.setSuccess(success > 0);
                result.setTitle("User <" + name + "> added successfully");
                //Get uid
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                result.addResult("UID = " + rs.getInt("uid"));
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
