package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.entities.Command;

import java.util.Iterator;

public class OptionCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {
        CommandResult result = new CommandResult();
        result.setSuccess(commandRequest.getCommands() != null);
        for (Iterator<Object> it = commandRequest.getCommands(); it.hasNext(); ) {
            Object command = it.next();
            result.addResult(new Command(command.toString()));
        }
        return result;
    }

    @Override
    public String toString() {
        return "presents a list of available commands and their description";
    }
}
