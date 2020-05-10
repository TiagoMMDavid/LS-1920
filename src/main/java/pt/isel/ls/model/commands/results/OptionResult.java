package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.entities.Command;

import java.util.LinkedList;

public class OptionResult implements CommandResult {

    private boolean hasResult = false;
    private LinkedList<Command> commands;

    public void addCommand(Command command) {
        if (commands == null) {
            commands = new LinkedList<>();
            hasResult = true;
        }
        commands.add(command);
    }

    public Iterable<Command> getCommands() {
        return commands;
    }

    @Override
    public boolean hasResults() {
        return hasResult;
    }

    @Override
    public CommandResult.ResultType getResultType() {
        return ResultType.Option;
    }
}
