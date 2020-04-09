package pt.isel.ls.model.commands;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.model.Router;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;

import java.sql.PreparedStatement;

import static org.junit.Assert.assertTrue;

public class DeleteCommandsTest {
    private static TransactionManager trans = new TransactionManager(System.getenv("postgresTestUrl"));

    @BeforeClass
    public static void fillTables() throws Exception {
        trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO USERS VALUES (0, 'John Frank', 'johnfrank@company.org');"
                            + "INSERT INTO ROOM VALUES (0, 'Meeting Room', 'A place where meetings are held.', "
                            + "'Floor 1', 10);"
                            + "INSERT INTO label VALUES (0, 'Has Projector');"
                            + "insert into ROOMLABEL values(0, 0);"
                            + "INSERT INTO BOOKING VALUES (0, 0, 0, '2016-06-22 19:10:10', '2017-06-22 19:20:10');"
                            + "INSERT INTO BOOKING VALUES (1, 0, 0, '2016-06-22 19:10:10', '2017-06-22 19:10:10');");
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
    public void deleteBookingInRoomTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.DELETE, new PathTemplate("/rooms/{rid}/bookings/{bid}"),
                new DeleteBookingInRoom());
        CommandRequest cmd = new CommandRequest(new Path("/rooms/0/bookings/0"), trans, null);

        CommandHandler handler = router.findRoute(Method.DELETE, cmd.getPath());
        CommandResult result = handler.execute(cmd);
        assertTrue(result.isSuccess());
    }
}
