package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandException;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Label;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


public class PostLabelsCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        CommandResult result = new CommandResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO LABEL "
                            + "(name) Values(?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            String label = commandRequest.getParams().getString("name");
            if (label != null) {
                ps.setString(1, label);
                ps.executeUpdate();

                //Get lid
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                result.addResult(new Label(rs.getInt("lid"), label));
            } else {
                throw new CommandException("No arguments found / Invalid arguments");
            }
            ps.close();

        });
        return result;
    }

    @Override
    public String toString() {
        return "creates a new label, given the following parameters\n"
                + "- name - the label's name";
    }
}