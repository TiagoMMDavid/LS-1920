package pt.isel.ls;

import org.junit.Assert;
import org.junit.Test;


import java.sql.*;

import static org.junit.Assert.assertEquals;

public class JDBCTests {
    // These fields describe the connection URL
    private final String URL = "jdbc:postgresql://";
    private final String SERVER_NAME = "localhost";
    private final String PORT_NUMBER = "5432";
    private final String DATABASE_NAME = "postgres";
    private final String USER = "postgres";
    private final String PASSWORD = "123macaco";

    @Test
    public void testJDBC() throws SQLException {
        Connection con = testConnection();
        Assert.assertNotNull(con);

        createTable(con);
        fillTable(con);
        queryTest(con);
        clearTable(con);
        dropTable(con);

        con.close();
    }

    private Connection testConnection() throws SQLException {
        return DriverManager.getConnection(getConnectionUrl(),USER, PASSWORD);
    }

    private void createTable(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("CREATE TABLE STUDENT(" +
                                                        "name   VARCHAR(255)," +
                                                        "age    INT," +
                                                        "number INT PRIMARY KEY);");
        ps.executeUpdate();
        ps.close();
    }

    private void fillTable(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO STUDENT VALUES " +
                                                        "('John Doe', 20, 1234), "+
                                                        "('Luís Barreiros', 50, 4321);");
        ps.executeUpdate();
        ps.close();
    }

    private void queryTest(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT age " +
                                                        "FROM STUDENT " +
                                                        "WHERE number = 1234;");
        ResultSet rs = ps.executeQuery();
        rs.next();
        assertEquals(20, rs.getInt(1));

        rs.close();
        ps.close();
    }

    private void clearTable(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("DELETE FROM STUDENT;");
        ps.executeUpdate();
        ps.close();
    }

    private void dropTable(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("DROP TABLE STUDENT;");
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Uses the Strings in the class's fields to generate a connection URL
     * @return the connection URL
     */
    private String getConnectionUrl() {
        return URL + SERVER_NAME + ":" + PORT_NUMBER + "/" + DATABASE_NAME;
    }
}
