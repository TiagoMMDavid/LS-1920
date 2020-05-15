package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandException;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetRoomsSearchResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Label;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class GetRoomsSearchCommand implements CommandHandler {

    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        GetRoomsSearchResult result = new GetRoomsSearchResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("SELECT * "
                    + "FROM LABEL");
            ResultSet rs = ps.executeQuery();
            LinkedList<Label> labels = new LinkedList<>();
            while (rs.next()) {
                labels.add(new Label(rs.getInt("lid"), rs.getString("name")));
            }
            result.setLabels(labels);
            rs.close();
            ps.close();
        });
        return result;
    }

    @Override
    public String getDescription() {
        return "HTTP only command to get the room search page";
    }
}