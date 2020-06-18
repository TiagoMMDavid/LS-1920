package pt.isel.ls.model.commands.common;

import pt.isel.ls.model.commands.common.exceptions.CommandException;

import java.sql.SQLException;

public interface CommandHandler {
    /**
     * Executes the command
     * @param commandRequest the CommandRequest containing this command's Parameters and Path,
     *                       aswell as the TransactionManager to be used, and the Router used to get this command.
     * @return The result of the execution
     * @throws CommandException
     * @throws SQLException
     */
    CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException;

    /**
     * @return This Handler's description
     */
    String getDescription();
}
