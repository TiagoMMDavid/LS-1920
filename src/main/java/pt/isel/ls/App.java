package pt.isel.ls;

import pt.isel.ls.model.Router;
import pt.isel.ls.model.commands.GetRoomsCommand;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.paths.PathTemplate;

public class App {
    public static void main(String[] args) {
        Router router = new Router();
        addCommands(router);
        //TODO: read user input
        //CommandHandler cmd = router.find(args[0], args[1]);
        //Result res = cmd.execute();
        //System.out.println(res);
    }

    private static void addCommands(Router router) {
        //TODO: Add more commands
        router.addRoute(Method.GET, new PathTemplate("/rooms"), new GetRoomsCommand());
    }
}
