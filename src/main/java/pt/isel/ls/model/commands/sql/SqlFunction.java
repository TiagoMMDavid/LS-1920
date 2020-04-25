package pt.isel.ls.model.commands.sql;

import pt.isel.ls.model.commands.common.CommandException;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlFunction {
    void execute(Connection con) throws CommandException, SQLException;
}
