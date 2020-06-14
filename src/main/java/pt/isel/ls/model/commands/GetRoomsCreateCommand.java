package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.Parameters;
import pt.isel.ls.model.commands.common.exceptions.CommandException;
import pt.isel.ls.model.commands.results.GetRoomsCreateResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Label;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetRoomsCreateCommand implements CommandHandler {

    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        GetRoomsCreateResult result = new GetRoomsCreateResult();
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
        Parameters params = commandRequest.getParams();
        if (params != null) {
            result.setError(
                    params.getString("errorType"),
                    params.getString("errorMessage"),
                    params.getString("validatedString"));

            result.setPreviousName(params.getString("name"));
            result.setPreviousCapacity(params.getString("capacity"));
            result.setPreviousDescription(params.getString("description"));
            result.setPreviousLocation(params.getString("location"));
            result.setPreviousLabels(params.getValuesAsList("label"));
        }
        return result;
    }

    @Override
    public String getDescription() {
        return "HTTP only command to get the room create page";
    }
}