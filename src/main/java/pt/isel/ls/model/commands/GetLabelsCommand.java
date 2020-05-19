package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.exceptions.CommandException;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetLabelsResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Label;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetLabelsCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        GetLabelsResult result = new GetLabelsResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("SELECT * "
                    + "FROM LABEL");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.addLabel(new Label(rs.getInt("lid"), rs.getString("name")));
            }
            rs.close();
            ps.close();
        });
        return result;
    }

    @Override
    public String getDescription() {
        return "returns a list with all the labels";
    }
}
