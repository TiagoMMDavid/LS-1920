package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.exceptions.CommandException;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.OptionResult;
import pt.isel.ls.model.entities.Command;
import pt.isel.ls.utils.Pair;

import java.sql.SQLException;
import java.util.Iterator;

public class OptionCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        OptionResult result = new OptionResult();
        for (Iterator<Pair<String,String>> it = commandRequest.getCommands(); it.hasNext(); ) {
            Pair<String,String> command = it.next();
            result.addCommand(new Command(command));
        }
        return result;
    }

    @Override
    public String getDescription() {
        return "presents a list of available commands and their description";
    }
}
