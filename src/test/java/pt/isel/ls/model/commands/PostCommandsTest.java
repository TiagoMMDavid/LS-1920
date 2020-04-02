package pt.isel.ls.model.commands;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.model.Router;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.commands.common.Parameters;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;

import java.sql.PreparedStatement;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PostCommandsTest {
    private static TransactionManager trans = new TransactionManager("localhost", 5432,
            "postgrestests", System.getenv("jdbcUser"),System.getenv("jdbcPass"));

    @BeforeClass
    public static void insertTestValues() throws Exception {
        trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO USERS VALUES (0, 'John Frank', 'johnfrank@company.org');"
                            + "INSERT INTO ROOM VALUES (0, 'Meeting Room', 'A place where meetings are held.', "
                            + "'Floor 1', 10);"
                            + "INSERT INTO label VALUES (0, 'monitors');"
                            + "INSERT INTO label VALUES (1, 'windows');"
                            + "ALTER SEQUENCE label_lid_seq RESTART WITH 2;"
            );
            ps.execute();
        });
    }

    @AfterClass
    public static void clearTables() throws Exception {
        trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("DELETE FROM ROOMLABEL;"
                    + "DELETE FROM booking;"
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
    public void postBookingsInRoomCommandTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.POST, new PathTemplate("/rooms/{rid}/bookings"), new PostBookingsInRoomCommand());
        CommandRequest cmd = new CommandRequest(new Path("/rooms/0/bookings"),
                new Parameters("begin=2020-12-20+10:20&duration=00:10&uid=0"),
                trans);

        CommandHandler handler = router.findRoute(Method.POST, cmd.getPath());
        CommandResult result = handler.execute(cmd);

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("Booking in room <0> added successfully", result.getTitle());
        assertEquals("BID = 1", result.iterator().next());
    }

    @Test
    public void postLabelsCommandTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.POST, new PathTemplate("/labels"), new PostLabelsCommand());
        CommandRequest cmd = new CommandRequest(new Path("/labels"),
                new Parameters("name=projector"),
                trans);

        CommandHandler handler = router.findRoute(Method.POST, cmd.getPath());
        CommandResult result = handler.execute(cmd);

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("Label <projector> added successfully", result.getTitle());
        assertEquals("LID = 2", result.iterator().next());
    }

    @Test
    public void postRoomsCommandTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.POST, new PathTemplate("/rooms"), new PostRoomsCommand());
        CommandRequest cmd = new CommandRequest(new Path("/rooms"),
                new Parameters("name=LS3&location=Building+F+floor+-1&label=monitors&label=windows"),
                trans);

        CommandHandler handler = router.findRoute(Method.POST, cmd.getPath());
        CommandResult result = handler.execute(cmd);

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("Room <LS3> added successfully", result.getTitle());
        assertEquals("RID = 1", result.iterator().next());
    }

    @Test
    public void postUsersCommandTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.POST, new PathTemplate("/users"), new PostUsersCommand());
        CommandRequest cmd = new CommandRequest(new Path("/users"),
                new Parameters("name=David&email=davidp@email.org"),
                trans);

        CommandHandler handler = router.findRoute(Method.POST, cmd.getPath());
        CommandResult result = handler.execute(cmd);

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("User <David> added successfully", result.getTitle());
        assertEquals("UID = 1", result.iterator().next());
    }
}
