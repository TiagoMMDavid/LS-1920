package pt.isel.ls.model.commands.sql;

import pt.isel.ls.model.commands.common.exceptions.CommandException;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlFunction {

    /**
     * Executes an SQL function, as in, executes a function that requires a set of SQL instructions.
     * This method's implementation is required by the TransactionManager.
     *
     * @param con the SQL Connection to be used
     * @throws CommandException
     * @throws SQLException
     */
    void execute(Connection con) throws CommandException, SQLException;
}
