package pt.isel.ls.model.commands.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

@FunctionalInterface
public interface SqlFunction {
    void execute(Connection con) throws SQLException, ParseException;
}
