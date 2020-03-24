package pt.isel.ls.model.commands;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.model.Router;
import pt.isel.ls.model.commands.common.Parameters;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static pt.isel.ls.model.commands.common.PsqlConnectionHandler.getConnection;

//TODO:
public class PostCommandsTest {

    @Test
    public void postBookingsInRoomCommandTest() {
        Router router = new Router();
        router.addRoute(Method.POST, new PathTemplate("/rooms/{rid}/bookings"), new PostBookingsInRoomCommand());
        CommandRequest cmd = new CommandRequest(Method.POST, new Path("/rooms/0/bookings"),
                new Parameters("begin=2020-12-20+10:20&duration=10&uid=0"));

        CommandHandler handler = router.findRoute(cmd.getMethod(), cmd.getPath());
        CommandResult result = handler.execute(cmd);

        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    public void postLabelsCommandTest() {
        Router router = new Router();
        router.addRoute(Method.POST, new PathTemplate("/labels"), new PostLabelsCommand());
        CommandRequest cmd = new CommandRequest(Method.POST, new Path("/labels"), new Parameters("name=projector"));

        CommandHandler handler = router.findRoute(cmd.getMethod(), cmd.getPath());
        CommandResult result = handler.execute(cmd);

        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    public void postRoomsCommandTest() {
    }

    @Test
    public void postUsersCommandTest() {
        Router router = new Router();
        router.addRoute(Method.POST, new PathTemplate("/users"), new PostUsersCommand());
        CommandRequest cmd = new CommandRequest(Method.POST, new Path("/users"),
                new Parameters("name=David&email=davidp@email.org"));

        CommandHandler handler = router.findRoute(cmd.getMethod(), cmd.getPath());
        CommandResult result = handler.execute(cmd);

        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @BeforeClass
    public static void insertTestValues() throws SQLException {
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO USERS VALUES (0, 'John Frank', 'johnfrank@company.org');"
                            + "INSERT INTO ROOM VALUES (0, 'Meeting Room', 'A place where meetings are held.', "
                            + "'Floor 1', 10);"
                            + "INSERT INTO label VALUES (0, 'Has Projector');"
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
            PreparedStatement ps = con.prepareStatement("DELETE FROM booking WHERE uid = 0;"
                    + "DELETE FROM label WHERE lid IN (0,1);"
                    + "DELETE FROM ROOM where rid = 0;"
                    + "DELETE FROM USERS WHERE uid IN (0,1);"
                    + "ALTER SEQUENCE users_uid_seq RESTART;"
                    + "ALTER SEQUENCE booking_bid_seq RESTART;"
                    + "ALTER SEQUENCE label_lid_seq RESTART;"
                    + "ALTER SEQUENCE room_rid_seq RESTART;");
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
