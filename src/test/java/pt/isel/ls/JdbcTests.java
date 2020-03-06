package pt.isel.ls;

import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JdbcTests {
    // These fields describe the connection URL
    private final String url = "jdbc:postgresql://";
    private final String serverAddress = "localhost";
    private final String portNumber = "5432";
    private final String databaseName = "postgres";

    private final String user = "postgres";
    private final String password = "123macaco";

    @Test
    public void testJdbc() throws SQLException {
        Connection con = testConnection();
        assertNotNull(con);

        con.setAutoCommit(false);   // Disable automatic commit of changes
        try (con) {
            createTable(con);
            fillTable(con);
            queryTest(con);
            clearTable(con);
            dropTable(con);
            con.commit();           // Commit only if there was no exception thrown
        } catch (Exception e) {
            con.rollback();         // If there's an error, rollback the changes
            throw e;
        }
    }

    private Connection testConnection() throws SQLException {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL(getConnectionUrl());
        ds.setUser(user);
        ds.setPassword(password);

        Connection con = ds.getConnection();
        assertNotNull(con);
        return con;
    }

    private void createTable(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("CREATE TABLE STUDENT("
                                                        + "name   VARCHAR(255),"
                                                        + "age    INT,"
                                                        + "number INT PRIMARY KEY);");
        ps.executeUpdate();
        ps.close();
    }

    private void fillTable(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO STUDENT VALUES "
                                                        + "('John Doe', 20, 1234), "
                                                        + "('Luís Barreiros', 50, 4321);");
        ps.executeUpdate();
        ps.close();
    }

    private void queryTest(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT age "
                                                        + "FROM STUDENT "
                                                        + "WHERE number = 1234;");
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
        return url
                + serverAddress + ":"
                + portNumber + "/"
                + databaseName;
    }
}
