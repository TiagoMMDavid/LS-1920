package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.exceptions.CommandException;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetHomeResult;

import java.sql.SQLException;

public class GetHomeCommand implements CommandHandler {

    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        return new GetHomeResult();
    }

    @Override
    public String getDescription() {
        return "HTTP only command to get the home page";
    }
}
