package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.Parameters;
import pt.isel.ls.model.commands.common.exceptions.CommandException;
import pt.isel.ls.model.commands.results.GetUsersCreateResult;

import java.sql.SQLException;

public class GetUsersCreateCommand implements CommandHandler {

    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        GetUsersCreateResult result = new GetUsersCreateResult();
        Parameters params = commandRequest.getParams();
        if (params != null) {
            result.setError(
                    params.getString("errorType"),
                    params.getString("errorMessage"),
                    params.getString("validatedString"));

            result.setPreviousName(params.getString("name"));
            result.setPreviousEmail(params.getString("email"));
        }
        return result;
    }

    @Override
    public String getDescription() {
        return "HTTP only command to get the user create page";
    }
}