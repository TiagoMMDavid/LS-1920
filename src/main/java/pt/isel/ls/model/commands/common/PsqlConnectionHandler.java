package pt.isel.ls.model.commands.common;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class PsqlConnectionHandler {
    private String connectionUrl;

    public PsqlConnectionHandler(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public Connection getConnection() throws SQLException {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL(connectionUrl);
        ds.setUser("postgres");
        ds.setPassword("123macaco");

        Connection con = ds.getConnection();
        con.setAutoCommit(false);
        return con;
    }
}
