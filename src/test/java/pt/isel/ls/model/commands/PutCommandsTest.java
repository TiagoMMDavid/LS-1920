package pt.isel.ls.model.commands;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.model.Router;
import pt.isel.ls.model.commands.common.*;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.model.entities.Entity;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;

import java.sql.PreparedStatement;
import java.util.Iterator;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PutCommandsTest {
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
                            + "INSERT INTO BOOKING VALUES (0, 0, 0, '2016-06-22 19:10:10', '2017-06-22 19:20:10');");
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
    public void putBookingInRoomTest() throws Exception {
        Router router = new Router();
        router.addRoute(Method.PUT, new PathTemplate("/rooms/{rid}/bookings/{bid}"),
                new PutBookingInRoom());
        CommandRequest cmd = new CommandRequest(new Path("/rooms/0/bookings/0"), new Parameters("begin=2020-12-20+10:20&duration=00:10&uid=0"), trans, null);
        CommandHandler handler = router.findRoute(Method.PUT, cmd.getPath());
        CommandResult result = handler.execute(cmd);
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }
}