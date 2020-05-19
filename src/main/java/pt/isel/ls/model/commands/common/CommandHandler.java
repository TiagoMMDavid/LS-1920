package pt.isel.ls.model.commands.common;

import pt.isel.ls.model.commands.common.exceptions.CommandException;

import java.sql.SQLException;

public interface CommandHandler {
    CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException;

    String getDescription();
}
