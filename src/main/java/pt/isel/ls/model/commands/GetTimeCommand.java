package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandException;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetTimeResult;

import java.sql.SQLException;
import java.util.Date;

public class GetTimeCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        GetTimeResult res = new GetTimeResult();
        res.setTime(new Date());
        return res;
    }

    @Override
    public String getDescription() {
        return "presents the current time";
    }
}
