package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandException;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.entities.Time;

import java.sql.SQLException;
import java.util.Date;

public class GetTimeCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        CommandResult res = new CommandResult();
        res.addResult(new Time(new Date()));
        return res;
    }

    @Override
    public String toString() {
        return "presents the current time";
    }
}
