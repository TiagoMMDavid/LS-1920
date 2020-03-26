package pt.isel.ls.model;

import org.junit.Test;
import pt.isel.ls.model.commands.GetRoomsCommand;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.commands.common.PsqlConnectionHandler;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;


public class RouterTest {
    private static PsqlConnectionHandler connectionHandler = new PsqlConnectionHandler(
            "jdbc:postgresql://localhost:5432/postgrestests");

    @Test
    public void addValidRouteAndFindRouteTest() {
        //Arrange
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/rooms/{rid}"), new GetRoomsCommand());
        CommandRequest cmd = new CommandRequest(Method.GET, new Path("/rooms/92"), connectionHandler);

        //Act
        CommandHandler handler = router.findRoute(cmd.getMethod(), cmd.getPath());

        //Assert
        assertNotNull(handler);
        assertTrue(handler instanceof GetRoomsCommand);
    }

    @Test
    public void findNonExistingRoute() {
        //Arrange
        Router router = new Router();
        CommandRequest cmd = new CommandRequest(Method.GET, new Path("/rooms"), connectionHandler);

        //Act
        CommandHandler handler = router.findRoute(cmd.getMethod(), cmd.getPath());

        //Assert
        assertNull(handler);
    }
}