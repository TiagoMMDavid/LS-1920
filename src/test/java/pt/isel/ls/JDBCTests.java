package pt.isel.ls;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

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
        Connection con = DriverManager.getConnection(getConnectionUrl(),"postgres","123macaco");
        PreparedStatement ps = con.prepareStatement("INSERT INTO student values("+
                "'John Doe',"+
                "20,"+
                "1234"+
                ");"+
                "INSERT INTO student values("+
                "'Luís Barreiros',"+
                "50,"+
                "4321"+
                ");");
        ps.executeUpdate();
    }

    @Test
    public void queryTest() throws SQLException {
        Connection con = DriverManager.getConnection(getConnectionUrl(),"postgres","123macaco");
        PreparedStatement ps = con.prepareStatement("select age "+
                "from student "+
                "where number = 1234 ");
        ps.executeUpdate();
        assertEquals(20, ps.getResultSet().getInt(0));
    }

    @Test
    public void clearTable() throws SQLException {
        Connection con = DriverManager.getConnection(getConnectionUrl(),"postgres","123macaco");
        PreparedStatement ps = con.prepareStatement("DELETE "+
                "FROM student "+
                ";");
        ps.executeUpdate();
    }

    @Test
    public void dropTable() throws SQLException {
        Connection con = DriverManager.getConnection(getConnectionUrl(),"postgres","123macaco");
        PreparedStatement ps = con.prepareStatement("DROP TABLE if EXISTS student;");
        ps.executeUpdate();
    }

    /**
     * Uses the Strings in the class's fields to generate a connection URL
     * @return the connection URL
     */
    private String getConnectionUrl() {
        return url + serverName + ":" + portNumber + "/" + databaseName ;
    }
}
