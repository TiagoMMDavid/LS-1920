package pt.isel.ls;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCTests {
    // These fields describe the connection url
    private final String url = "jdbc:postgresql://";
    private final String serverName = "localhost";
    private final String portNumber = "5432";
    private final String databaseName = "postgres";

    @Test
    public void testConnection() throws SQLException {
        Connection con = DriverManager.getConnection(getConnectionUrl(),"postgres","123macaco");
        Assert.assertNotNull(con);
    }

    @Test
    public void createTable() throws SQLException {
        Connection con = DriverManager.getConnection(getConnectionUrl(),"postgres","123macaco");
        PreparedStatement ps = con.prepareStatement("create table student(" +
                "name   VARCHAR(255)," +
                "age    INT," +
                "number INT PRIMARY KEY" +
                ");");
        ps.executeUpdate();
    }

    @Test
    public void fillTable() throws SQLException {
        // TODO
    }

    @Test
    public void queryTest() throws SQLException {
        // TODO
    }

    @Test
    public void clearTable() throws SQLException {
        // TODO
    }

    @Test
    public void dropTable() throws SQLException {
        // TODO
    }

    /**
     * Uses the Strings in the class's fields to generate a connection URL
     * @return the connection URL
     */
    private String getConnectionUrl() {
        return url + serverName + ":" + portNumber + "/" + databaseName ;
    }
}
