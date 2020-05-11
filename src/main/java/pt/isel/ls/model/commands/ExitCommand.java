package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;

public class ExitCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) {
        return null;
    }

    @Override
    public String getDescription() {
        return "terminates the application";
    }
}
