package pt.isel.ls.model.commands;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.model.Router;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.commands.common.Parameters;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.results.PostBookingInRoomResult;
import pt.isel.ls.model.commands.results.PostLabelResult;
import pt.isel.ls.model.commands.results.PostRoomResult;
import pt.isel.ls.model.commands.results.PostUserResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;

import java.sql.PreparedStatement;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PostCommandsTest {
    private static TransactionManager trans = new TransactionManager(System.getenv("postgresTestUrl"));

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
    public void postBookingInRoomCommandTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.POST, new PathTemplate("/rooms/{rid}/bookings"), new PostBookingInRoomCommand());
        CommandRequest cmd = new CommandRequest(new Path("/rooms/0/bookings"),
                new Parameters("begin=2020-12-20T10:20&duration=00:10&uid=0"),
                trans, null);

        CommandHandler handler = router.findRoute(Method.POST, cmd.getPath());
        PostBookingInRoomResult result = (PostBookingInRoomResult) handler.execute(cmd);

        assertNotNull(result);
        assertTrue(result.hasResults());
        assertEquals(1, result.getBooking().getBid());
    }

    @Test
    public void postLabelCommandTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.POST, new PathTemplate("/labels"), new PostLabelCommand());
        CommandRequest cmd = new CommandRequest(new Path("/labels"),
                new Parameters("name=projector"),
                trans, null);

        CommandHandler handler = router.findRoute(Method.POST, cmd.getPath());
        PostLabelResult result = (PostLabelResult) handler.execute(cmd);

        assertNotNull(result);
        assertTrue(result.hasResults());
        assertEquals(2, result.getLabel().getLid());
    }

    @Test
    public void postRoomCommandTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.POST, new PathTemplate("/rooms"), new PostRoomCommand());
        CommandRequest cmd = new CommandRequest(new Path("/rooms"),
                new Parameters("name=LS3&location=Building+F+floor+-1&label=monitors&label=windows"),
                trans, null);

        CommandHandler handler = router.findRoute(Method.POST, cmd.getPath());
        PostRoomResult result = (PostRoomResult) handler.execute(cmd);

        assertNotNull(result);
        assertTrue(result.hasResults());
        assertEquals(1, result.getRoom().getRid());
    }

    @Test
    public void postUserCommandTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.POST, new PathTemplate("/users"), new PostUserCommand());
        CommandRequest cmd = new CommandRequest(new Path("/users"),
                new Parameters("name=David&email=davidp@email.org"),
                trans, null);

        CommandHandler handler = router.findRoute(Method.POST, cmd.getPath());
        PostUserResult result = (PostUserResult) handler.execute(cmd);

        assertNotNull(result);
        assertTrue(result.hasResults());
        assertEquals(1, result.getUser().getUid());
    }
}
