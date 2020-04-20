package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostUsersCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {
        CommandResult result = new CommandResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        if (!trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO USERS "
                            + "(name, email) Values(?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            String name = commandRequest.getParams().getString("name");
            String email = commandRequest.getParams().getString("email");
            if (name != null && email != null) {
                ps.setString(1, name);
                ps.setString(2, email);
                int success = ps.executeUpdate();
                result.setSuccess(success > 0);
                //Get uid
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                result.addResult(new User(rs.getInt("uid")));
            } else {
                throw new IllegalArgumentException("No arguments found / Invalid arguments");
            }
            ps.close();

        })) {
            result.setSuccess(false);
            result.clearResults();
        }
        return result;
    }

    @Override
    public String toString() {
        return "creates a new user, given the following parameters\n"
                + "- name - the user's name.\n"
                + "- email - the user's email.";
    }
}