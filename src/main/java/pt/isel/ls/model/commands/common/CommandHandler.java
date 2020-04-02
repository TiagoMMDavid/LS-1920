package pt.isel.ls.model.commands.common;

import java.sql.SQLException;

public interface CommandHandler {
    CommandResult execute(CommandRequest commandRequest) throws Exception;
}
