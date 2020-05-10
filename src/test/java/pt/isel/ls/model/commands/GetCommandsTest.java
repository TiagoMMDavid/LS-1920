package pt.isel.ls.model.commands;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.model.Router;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.Parameters;
import pt.isel.ls.model.commands.results.GetBookingByRoomAndBookingIdResult;
import pt.isel.ls.model.commands.results.GetBookingsByUserIdResult;
import pt.isel.ls.model.commands.results.GetLabelsResult;
import pt.isel.ls.model.commands.results.GetRoomByIdResult;
import pt.isel.ls.model.commands.results.GetRoomsResult;
import pt.isel.ls.model.commands.results.GetRoomsWithLabelResult;
import pt.isel.ls.model.commands.results.GetTimeResult;
import pt.isel.ls.model.commands.results.GetUserByIdResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.model.entities.User;
import pt.isel.ls.model.entities.Room;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;
import pt.isel.ls.utils.DateUtils;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class GetCommandsTest {
    private static TransactionManager trans = new TransactionManager(System.getenv("postgresTestUrl"));
    private String pattern = "yyyy-MM-dd HH:mm";

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
                            + "INSERT INTO BOOKING VALUES (0, 0, 1, '2016-06-22 19:40', '2016-06-22 19:50');"
                            + "INSERT INTO BOOKING VALUES (1, 0, 1, '2016-06-22 19:30', '2016-06-22 19:40');"
                            + "INSERT INTO BOOKING VALUES (3, 0, 1, '2016-06-22 19:20', '2016-06-22 19:30');"
                            + "INSERT INTO BOOKING VALUES (4, 0, 0, '2016-06-22 19:10', '2016-06-22 19:20');");
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
    public void getBookingByRoomAndBookingIdTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/rooms/{rid}/bookings/{bid}"),
                new GetBookingByRoomAndBookingIdCommand());
        CommandRequest cmd = new CommandRequest(new Path("/rooms/0/bookings/4"), null, trans, null);

        CommandHandler handler = router.findRoute(Method.GET, cmd.getPath());
        GetBookingByRoomAndBookingIdResult result = (GetBookingByRoomAndBookingIdResult) handler.execute(cmd);

        assertNotNull(result);
        assertTrue(result.hasResults());
        Booking booking = result.getBooking();
        assertEquals(4, booking.getBid());
        assertEquals(0, booking.getUid());
        assertEquals(0, booking.getRid());
        assertEquals(DateUtils.parseTimeWithTimezone("2016-06-22 19:10", pattern), booking.getBeginInst());
        assertEquals(DateUtils.parseTimeWithTimezone("2016-06-22 19:20", pattern), booking.getEndInst());
    }

    @Test
    public void getBookingsByUserIdTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/users/{uid}/bookings"), new GetBookingsByUserIdCommand());
        CommandRequest cmd = new CommandRequest(new Path("/users/0/bookings"), null, trans, null);

        CommandHandler handler = router.findRoute(Method.GET, cmd.getPath());
        GetBookingsByUserIdResult result = (GetBookingsByUserIdResult) handler.execute(cmd);
        Iterator<Booking> itr = result.getBookings().iterator();

        assertNotNull(result);
        assertTrue(result.hasResults());
        assertEquals(0, itr.next().getBid());
        assertEquals(1, itr.next().getBid());
        assertEquals(3, itr.next().getBid());
        assertEquals(4, itr.next().getBid());
    }

    @Test
    public void getLabelsTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/labels"), new GetLabelsCommand());
        CommandRequest cmd = new CommandRequest(new Path("/labels"), null, trans, null);

        CommandHandler handler = router.findRoute(Method.GET, cmd.getPath());
        GetLabelsResult result = (GetLabelsResult) handler.execute(cmd);
        Iterator<Label> itr = result.getLabels().iterator();

        assertNotNull(result);
        assertTrue(result.hasResults());
        assertEquals(0, itr.next().getLid());
        assertEquals(1, itr.next().getLid());
        assertEquals(2, itr.next().getLid());

    }

    @Test
    public void getRoomByIdTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/rooms/{rid}"), new GetRoomByIdCommand());
        CommandRequest cmd = new CommandRequest(new Path("/rooms/0"), null, trans, null);

        CommandHandler handler = router.findRoute(Method.GET, cmd.getPath());
        GetRoomByIdResult result = (GetRoomByIdResult) handler.execute(cmd);

        assertNotNull(result);
        assertTrue(result.hasResults());
        Room room = result.getRoom();
        assertEquals(0, room.getRid());
        assertEquals("Meeting Room", room.getName());
    }

    @Test
    public void getRoomsTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/rooms"), new GetRoomsCommand());
        CommandRequest cmd = new CommandRequest(new Path("/rooms"), null, trans, null);

        CommandHandler handler = router.findRoute(Method.GET, cmd.getPath());
        GetRoomsResult result = (GetRoomsResult) handler.execute(cmd);
        Iterator<Room> itr = result.getRooms().iterator();

        assertNotNull(result);
        assertTrue(result.hasResults());
        Room room1 = itr.next();
        assertEquals(0, room1.getRid());
        assertEquals("Meeting Room", room1.getName());
        Room room2 = itr.next();
        assertEquals(1, room2.getRid());
        assertEquals("Snack Room", room2.getName());
    }

    @Test
    public void getRoomsWithLabelParametersTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/rooms"), new GetRoomsCommand());
        CommandRequest cmd = new CommandRequest(new Path("/rooms"),
                new Parameters("label=Has+Board&label=Has+Projector"), trans, null);

        CommandHandler handler = router.findRoute(Method.GET, cmd.getPath());
        GetRoomsResult result = (GetRoomsResult) handler.execute(cmd);
        Iterator<Room> itr = result.getRooms().iterator();

        assertNotNull(result);
        assertTrue(result.hasResults());
        Room room1 = itr.next();
        assertEquals(0, room1.getRid());
        assertEquals("Meeting Room", room1.getName());
    }

    @Test
    public void getRoomsWithCapacityParametersTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/rooms"), new GetRoomsCommand());
        CommandRequest cmd = new CommandRequest(new Path("/rooms"),
                new Parameters("capacity=10"), trans, null);

        CommandHandler handler = router.findRoute(Method.GET, cmd.getPath());
        GetRoomsResult result = (GetRoomsResult) handler.execute(cmd);
        Iterator<Room> itr = result.getRooms().iterator();

        assertNotNull(result);
        assertTrue(result.hasResults());
        Room room1 = itr.next();
        assertEquals(0, room1.getRid());
        assertEquals("Meeting Room", room1.getName());
    }

    @Test
    public void getAvailableRoomsInDurationTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/rooms"), new GetRoomsCommand());
        CommandRequest cmd = new CommandRequest(new Path("/rooms"),
                new Parameters("begin=2016-06-22+19:20&duration=00:30"), trans, null);

        CommandHandler handler = router.findRoute(Method.GET, cmd.getPath());
        GetRoomsResult result = (GetRoomsResult) handler.execute(cmd);
        Iterator<Room> itr = result.getRooms().iterator();

        assertNotNull(result);
        assertTrue(result.hasResults());
        Room room1 = itr.next();
        assertEquals(0, room1.getRid());
        assertEquals("Meeting Room", room1.getName());
    }

    @Test
    public void getRoomsWithLabelTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/labels/{lid}/rooms"), new GetRoomsWithLabelCommand());
        CommandRequest cmd = new CommandRequest(new Path("/labels/1/rooms"), null, trans, null);

        CommandHandler handler = router.findRoute(Method.GET, cmd.getPath());
        GetRoomsWithLabelResult result = (GetRoomsWithLabelResult) handler.execute(cmd);
        Iterator<Room> itr = result.getRooms().iterator();

        assertNotNull(result);
        assertTrue(result.hasResults());
        assertEquals(1, itr.next().getRid());
    }

    @Test
    public void getUserByIdTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/users/{uid}"), new GetUserByIdCommand());
        CommandRequest cmd = new CommandRequest(new Path("/users/0"), null, trans, null);

        CommandHandler handler = router.findRoute(Method.GET, cmd.getPath());
        GetUserByIdResult result = (GetUserByIdResult) handler.execute(cmd);

        assertNotNull(result);
        assertTrue(result.hasResults());
        User user = result.getUser();
        assertEquals(0, user.getUid());
        assertEquals("John Frank", user.getName());
    }

    @Test
    public void getTimeTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/time"), new GetTimeCommand());
        CommandRequest cmd = new CommandRequest(new Path("/time"), null, trans, null);

        CommandHandler handler = router.findRoute(Method.GET, cmd.getPath());
        GetTimeResult result = (GetTimeResult) handler.execute(cmd);

        assertNotNull(result);
        assertTrue(result.hasResults());
        Date expected = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date actual = result.getTime();

        assertEquals(formatter.format(expected.getTime()), formatter.format(actual.getTime()));
    }
}
