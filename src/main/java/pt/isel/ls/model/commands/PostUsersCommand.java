package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.sql.TransactionManager;

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

            String name = commandRequest.getParams().getValue("name");
            String email = commandRequest.getParams().getValue("email");
            if (name != null && email != null) {
                ps.setString(1, name);
                ps.setString(2, email);
                int success = ps.executeUpdate();
                result.setSuccess(success > 0);
                result.setTitle("User <" + name + "> added successfully");
                //Get uid
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                result.addResult("UID = " + rs.getInt("uid"));
                con.commit();
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
}
