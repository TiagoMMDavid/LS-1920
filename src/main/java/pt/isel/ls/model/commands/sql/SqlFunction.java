package pt.isel.ls.model.commands.sql;

import pt.isel.ls.model.commands.common.exceptions.CommandException;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlFunction {
    void execute(Connection con) throws CommandException, SQLException;
}
