package pt.isel.ls.model.commands;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static pt.isel.ls.model.commands.common.PsqlConnectionHandler.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetCommandsTest {

    @BeforeClass
    public static void fillTables() throws SQLException {
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO USERS VALUES (0, 'John Frank', 'johnfrank@company.org');\n" +
                    "INSERT INTO USERS VALUES (1, 'Michael Hawk', 'michaelhawk@company.org');\n" +
                    "\n" +
                    "INSERT INTO ROOM VALUES (0, 'Meeting Room', 'A place where meetings are held.', 'Floor 1', 10);\n" +
                    "INSERT INTO ROOM VALUES (1, 'Snack Room', 'Room filled with snacks to satisfy your belly.', 'Floor -1', 20);\n" +
                    "\n" +
                    "INSERT INTO label VALUES (0, 'Has Projector');\n" +
                    "INSERT INTO label VALUES (1, 'Has Food');\n" +
                    "INSERT INTO label VALUES (2, 'Has Board');\n" +
                    "\n" +
                    "insert into ROOMLABEL values(0, 0);\n" +
                    "insert into ROOMLABEL values(1, 1);\n" +
                    "insert into ROOMLABEL values(2, 0);\n" +
                    "\t\n" +
                    "INSERT INTO BOOKING VALUES (0, 0, 0, '2016-06-22 19:10:10', '2017-06-22 19:20:10');\n" +
                    "INSERT INTO BOOKING VALUES (1, 0, 0, '2016-06-22 19:10:10', '2017-06-22 19:10:10');\n" +
                    "INSERT INTO BOOKING VALUES (3, 0, 0, '2016-06-22 19:10', '2017-06-22 19:30');\n" +
                    "INSERT INTO BOOKING VALUES (4, 0, 0, '2016-06-22 19:10:10', '2016-06-22 22:10:10');");
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
            PreparedStatement ps = con.prepareStatement("DELETE FROM BOOKING " +
                    "WHERE bid IN (0,1,3,4); " +
                    "DELETE FROM ROOMLABEL;" +
                    "DELETE FROM label WHERE lid IN (0,1,2);" +
                    "DELETE FROM ROOM where rid in (0,1);" +
                    "DELETE FROM USERS WHERE uid IN (0,1);");
            ps.execute();
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            throw e;
        } finally {
            con.close();
        }
    }

    @Test
    public void GetBookingsByRoomAndBookingIdTest() {
        //TODO:
    }
}
