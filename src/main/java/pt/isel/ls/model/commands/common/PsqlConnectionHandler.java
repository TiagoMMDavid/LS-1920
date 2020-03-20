package pt.isel.ls.model.commands.common;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class PsqlConnectionHandler {

    public static Connection getConnection() throws SQLException {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL(getConnectionUrl());
        ds.setUser("postgres");
        ds.setPassword("123macaco");

        Connection con = ds.getConnection();
        con.setAutoCommit(false);
        return con;
    }

    private static String getConnectionUrl() {
        return "jdbc:postgresql://localhost:5432/postgres";
    }
}
