package pt.isel.ls.model.commands.common;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class PsqlConnectionHandler {
    private final String connectionUrl;
    private final String user;
    private final String password;

    public PsqlConnectionHandler(String ip, int port, String dbName, String user, String password) {
        this.connectionUrl = "jdbc:postgresql://" + ip + ":" + port + "/" + dbName;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL(connectionUrl);
        ds.setUser(user);
        ds.setPassword(password);

        Connection con = ds.getConnection();
        con.setAutoCommit(false);
        return con;
    }
}
