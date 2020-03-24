package pt.isel.ls.model.commands;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static pt.isel.ls.model.commands.common.PsqlConnectionHandler.getConnection;

//TODO:
public class PostCommandsTest {

    @Test
    public void postBookingsInRoomCommandTest() {

    }

    @Test
    public void postLabelsCommandTest() {

    }

    @Test
    public void postRoomsCommandTest() {

    }

    @Test
    public void PostUsersCommandTest() {

    }

    @BeforeClass
    public static void insertTestValues() throws SQLException {
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO USERS VALUES (0, 'John Frank', 'johnfrank@company.org');"
                            + "INSERT INTO USERS VALUES (1, 'Michael Hawk', 'michaelhawk@company.org');"
                            + "INSERT INTO ROOM VALUES (0, 'Meeting Room', 'A place where meetings are held.', "
                            + "'Floor 1', 10);"
                            + "INSERT INTO label VALUES (0, 'Has Projector');"
                            + "INSERT INTO label VALUES (1, 'Has Food');"
            );
            ps.execute();
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            throw e;
        } finally {
            con.close();
        }
    }

    @AfterClass
    public static void clearTables() throws SQLException {
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM label WHERE lid IN (0,1);"
                    + "DELETE FROM ROOM where rid = 0;"
                    + "DELETE FROM USERS WHERE uid IN (0,1);");
            ps.execute();
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            throw e;
        } finally {
            con.close();
        }
    }
}
