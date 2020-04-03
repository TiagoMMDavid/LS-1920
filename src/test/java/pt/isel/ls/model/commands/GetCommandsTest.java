package pt.isel.ls.model.commands;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.model.Router;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.PreparedStatement;
import java.util.Iterator;

public class GetCommandsTest {
    private static TransactionManager trans = new TransactionManager(System.getenv("postgresTestUrl"));

    @BeforeClass
    public static void fillTables() throws Exception {
        trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO USERS VALUES (0, 'John Frank', 'johnfrank@company.org');"
                            + "INSERT INTO USERS VALUES (1, 'Michael Hawk', 'michaelhawk@company.org');"
                            + "INSERT INTO ROOM VALUES (0, 'Meeting Room', 'A place where meetings are held.', "
                            + "'Floor 1', 10);"
                            + "INSERT INTO ROOM VALUES (1, 'Snack Room', "
                            + "'Room filled with snacks to satisfy your belly.', "
                            + "'Floor -1', 20);"
                            + "INSERT INTO label VALUES (0, 'Has Projector');"
                            + "INSERT INTO label VALUES (1, 'Has Food');"
                            + "INSERT INTO label VALUES (2, 'Has Board');"
                            + "insert into ROOMLABEL values(0, 0);"
                            + "insert into ROOMLABEL values(1, 1);"
                            + "insert into ROOMLABEL values(2, 0);"
                            + "INSERT INTO BOOKING VALUES (0, 0, 0, '2016-06-22 19:10:10', '2017-06-22 19:20:10');"
                            + "INSERT INTO BOOKING VALUES (1, 0, 0, '2016-06-22 19:10:10', '2017-06-22 19:10:10');"
                            + "INSERT INTO BOOKING VALUES (3, 0, 0, '2016-06-22 19:10', '2017-06-22 19:30');"
                            + "INSERT INTO BOOKING VALUES (4, 0, 0, '2016-06-22 19:10:10', '2016-06-22 22:10:10');");
            ps.execute();
        });

    }

    @AfterClass
    public static void clearTables() throws Exception {
        trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("DELETE FROM BOOKING;"
                    + "DELETE FROM ROOMLABEL;"
                    + "DELETE FROM label;"
                    + "DELETE FROM ROOM;"
                    + "DELETE FROM USERS;"
                    + "ALTER SEQUENCE users_uid_seq RESTART;"
                    + "ALTER SEQUENCE booking_bid_seq RESTART;"
                    + "ALTER SEQUENCE label_lid_seq RESTART;"
                    + "ALTER SEQUENCE room_rid_seq RESTART;");
            ps.execute();
        });
    }

    @Test
    public void getBookingsByRoomAndBookingIdTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/rooms/{rid}/bookings/{bid}"),
                new GetBookingByRoomAndBookingId());
        CommandRequest cmd = new CommandRequest(new Path("/rooms/0/bookings/4"), trans);

        CommandHandler handler = router.findRoute(Method.GET, cmd.getPath());
        CommandResult result = handler.execute(cmd);
        Iterator<String> itr = result.iterator();

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("booking id (bid): 4", itr.next());
        assertEquals("reservation by user id (uid): 0", itr.next());
        assertEquals("room id (rid): 0", itr.next());
        assertEquals("begin instant: 2016-06-22 19:10:10", itr.next());
        assertEquals("end instant: 2016-06-22 22:10:10", itr.next());
    }

    @Test
    public void getBookingsByUserIdTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/users/{uid}/bookings"), new GetBookingsByUserIdCommand());
        CommandRequest cmd = new CommandRequest(new Path("/users/0/bookings"), trans);

        CommandHandler handler = router.findRoute(Method.GET, cmd.getPath());
        CommandResult result = handler.execute(cmd);
        Iterator<String> itr = result.iterator();

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("booking id (bid): 0", itr.next());
        assertEquals("booking id (bid): 1", itr.next());
        assertEquals("booking id (bid): 3", itr.next());
        assertEquals("booking id (bid): 4", itr.next());
    }

    @Test
    public void getLabelsTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/labels"), new GetLabelsCommand());
        CommandRequest cmd = new CommandRequest(new Path("/labels"), trans);

        CommandHandler handler = router.findRoute(Method.GET, cmd.getPath());
        CommandResult result = handler.execute(cmd);
        Iterator<String> itr = result.iterator();

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("Has Projector (lid: 0)", itr.next());
        assertEquals("Has Food (lid: 1)", itr.next());
        assertEquals("Has Board (lid: 2)", itr.next());

    }

    @Test
    public void getRoomsByIdTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/rooms/{rid}"), new GetRoomByIdCommand());
        CommandRequest cmd = new CommandRequest(new Path("/rooms/0"), trans);

        CommandHandler handler = router.findRoute(Method.GET, cmd.getPath());
        CommandResult result = handler.execute(cmd);
        Iterator<String> itr = result.iterator();

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("room id (rid): 0", itr.next());
        assertEquals("name: Meeting Room", itr.next());
    }

    @Test
    public void getRoomsTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/rooms"), new GetRoomsCommand());
        CommandRequest cmd = new CommandRequest(new Path("/rooms"), trans);

        CommandHandler handler = router.findRoute(Method.GET, cmd.getPath());
        CommandResult result = handler.execute(cmd);
        Iterator<String> itr = result.iterator();

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("Meeting Room (rid: 0)", itr.next());
        assertEquals("Snack Room (rid: 1)", itr.next());
    }

    @Test
    public void getRoomsWithLabelTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/labels/{lid}/rooms"), new GetRoomsWithLabelCommand());
        CommandRequest cmd = new CommandRequest(new Path("/labels/1/rooms"), trans);

        CommandHandler handler = router.findRoute(Method.GET, cmd.getPath());
        CommandResult result = handler.execute(cmd);
        Iterator<String> itr = result.iterator();

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("room id (rid): 1", itr.next());
    }

    @Test
    public void getUsersByIdTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/users/{uid}"), new GetUserByIdCommand());
        CommandRequest cmd = new CommandRequest(new Path("/users/0"), trans);

        CommandHandler handler = router.findRoute(Method.GET, cmd.getPath());
        CommandResult result = handler.execute(cmd);
        Iterator<String> itr = result.iterator();

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("user id (uid): 0", itr.next());
        assertEquals("name: John Frank", itr.next());
    }
}
