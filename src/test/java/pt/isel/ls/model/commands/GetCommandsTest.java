package pt.isel.ls.model.commands;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.model.Router;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static pt.isel.ls.model.commands.common.PsqlConnectionHandler.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

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

    @Test
    public void GetBookingsByUserIdTest() {
        //TODO:
    }

    @Test
    public void GetLabelsTest() {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/labels"), new GetLabelsCommand());
        CommandRequest cmd = new CommandRequest(Method.GET, new Path("/labels"));

        CommandHandler handler = router.findRoute(cmd.getMethod(), cmd.getPath());
        CommandResult result = handler.execute(cmd);
        Iterator<String> itr = result.iterator();

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(itr.next(), "Has Projector (lid: 0)");
        assertEquals(itr.next(), "Has Food (lid: 1)");
        assertEquals(itr.next(), "Has Board (lid: 2)");

    }

    @Test
    public void GetRoomsByIdTest() {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/rooms/{rid}"), new GetRoomsByIdCommand());
        CommandRequest cmd = new CommandRequest(Method.GET, new Path("/rooms/0"));

        CommandHandler handler = router.findRoute(cmd.getMethod(), cmd.getPath());
        CommandResult result = handler.execute(cmd);
        Iterator<String> itr = result.iterator();

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(itr.next(), "room id (rid): 0");
        assertEquals(itr.next(), "name: Meeting Room");
    }

    @Test
    public void GetRoomsTest() {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/rooms"), new GetRoomsCommand());
        CommandRequest cmd = new CommandRequest(Method.GET, new Path("/rooms"));

        CommandHandler handler = router.findRoute(cmd.getMethod(), cmd.getPath());
        CommandResult result = handler.execute(cmd);
        Iterator<String> itr = result.iterator();

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(itr.next(), "Meeting Room (rid: 0)");
        assertEquals(itr.next(), "Snack Room (rid: 1)");
    }

    @Test
    public void GetRoomsWithLabelTest() {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/labels/{lid}/rooms"), new GetRoomsWithLabelCommand());
        CommandRequest cmd = new CommandRequest(Method.GET, new Path("/labels/1/rooms"));

        CommandHandler handler = router.findRoute(cmd.getMethod(), cmd.getPath());
        CommandResult result = handler.execute(cmd);
        Iterator<String> itr = result.iterator();

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(itr.next(), "room id (rid): 1");
    }

    @Test
    public void GetUsersByIdTest() {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/users/{uid}"), new GetUsersByIdCommand());
        CommandRequest cmd = new CommandRequest(Method.GET, new Path("/users/0"));

        CommandHandler handler = router.findRoute(cmd.getMethod(), cmd.getPath());
        CommandResult result = handler.execute(cmd);
        Iterator<String> itr = result.iterator();

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(itr.next(), "user id (uid): 0");
        assertEquals(itr.next(), "name: John Frank");
    }
}
