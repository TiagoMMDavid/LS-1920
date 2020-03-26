package pt.isel.ls.model.commands.common;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JdbcTests {
    private static PsqlConnectionHandler connectionHandler = new PsqlConnectionHandler(
            "jdbc:postgresql://localhost:5432/postgrestests");
    
    @Test
    public void connectionToDatabaseTest() throws SQLException {
        Connection con = connectionHandler.getConnection();
        assertNotNull(con);
        con.close();
    }

    @Test
    public void createTableTest() throws SQLException {
        Connection con = connectionHandler.getConnection();
        try {
            createTableAux(con);
        } finally {
            con.rollback();
            con.close();
        }
    }


    private void createTableAux(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("CREATE TABLE STUDENT("
                + "name   VARCHAR(255),"
                + "age    INT,"
                + "number INT PRIMARY KEY);");
        ps.executeUpdate();
        ps.close();
    }

    @Test
    public void fillTableWithDataTest() throws SQLException {
        Connection con = connectionHandler.getConnection();
        createTableAux(con);
        try {
            fillTableAux(con);
        } finally {
            con.rollback();
            con.close();
        }
    }

    private void fillTableAux(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO STUDENT VALUES "
                + "('John Doe', 20, 1234), "
                + "('Luís Barreiros', 50, 4321);");
        ps.executeUpdate();
        ps.close();
    }

    @Test
    public void selectQueryTest() throws SQLException {
        Connection con = connectionHandler.getConnection();
        createTableAux(con);
        fillTableAux(con);
        try {
            queryTestAux(con);
        } finally {
            con.rollback();
            con.close();
        }
    }

    private void queryTestAux(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT age "
                + "FROM STUDENT "
                + "WHERE number = 1234;");
        ResultSet rs = ps.executeQuery();
        rs.next();
        assertEquals(20, rs.getInt(1));

        rs.close();
        ps.close();
    }

    @Test
    public void clearTableTest() throws SQLException {
        Connection con = connectionHandler.getConnection();
        createTableAux(con);
        fillTableAux(con);

        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM STUDENT;");
            ps.executeUpdate();
            ps.close();
        } finally {
            con.rollback();
            con.close();
        }
    }

    @Test
    public void dropTableTest() throws SQLException {
        Connection con = connectionHandler.getConnection();
        createTableAux(con);
        fillTableAux(con);

        try {
            PreparedStatement ps = con.prepareStatement("DROP TABLE STUDENT;");
            ps.executeUpdate();
            ps.close();
        } finally {
            con.rollback();
            con.close();
        }
    }
}
