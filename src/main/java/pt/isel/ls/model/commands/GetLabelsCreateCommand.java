package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.Parameters;
import pt.isel.ls.model.commands.common.exceptions.CommandException;
import pt.isel.ls.model.commands.results.GetLabelsCreateResult;

import java.sql.SQLException;

public class GetLabelsCreateCommand implements CommandHandler {

    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        GetLabelsCreateResult result = new GetLabelsCreateResult();
        Parameters params = commandRequest.getParams();
        if (params != null) {
            result.setError();
            result.setPreviousName(params.getString("name"));
        }
        return result;
    }

    @Override
    public String getDescription() {
        return "HTTP only command to get the label create page";
    }
}