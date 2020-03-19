package pt.isel.ls;

import java.util.Scanner;
import pt.isel.ls.model.Router;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.commands.common.Parameters;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;

import pt.isel.ls.model.commands.GetRoomsCommand;
import pt.isel.ls.model.commands.GetRoomsByIdCommand;
import pt.isel.ls.model.commands.GetBookingsByIdCommand;
import pt.isel.ls.model.commands.GetUsersByIdCommand;
import pt.isel.ls.model.commands.GetBookingsByUserIdCommand;
import pt.isel.ls.model.commands.GetLabelsCommand;
import pt.isel.ls.model.commands.GetRoomsWithLabelCommand;
import pt.isel.ls.model.commands.PostRoomsCommand;
import pt.isel.ls.model.commands.PostBookingsInRoomCommand;
import pt.isel.ls.model.commands.PostUsersCommand;
import pt.isel.ls.model.commands.PostLabelsCommand;
import pt.isel.ls.model.commands.ExitCommand;

public class App {
    public static void main(String[] args) {
        Router router = new Router();
        addCommands(router);

        if (args.length > 0) {
            executeCommand(args, router);
        } else {
            run(router);
        }
    }

    private static void run(Router router) {
        Scanner in = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.print("> ");
            String[] commands = in.nextLine().split(" ");

            if (!isCommandValid(commands)) {
                System.out.println("> Wrong format.");
            } else {
                running = executeCommand(commands, router);
            }
        }
    }

    private static boolean isCommandValid(String[] commands) {
        return commands.length > 1 && commands.length <= 3;
    }

    private static boolean executeCommand(String[] commands, Router router) {
        CommandRequest cmd;
        if (commands.length == 3) {
            cmd = new CommandRequest(Method.valueOf(commands[0].toUpperCase()),
                    new Path(commands[1]),
                    new Parameters(commands[2]));
        } else {
            cmd = new CommandRequest(Method.valueOf(commands[0].toUpperCase()),
                    new Path(commands[1]));
        }
        CommandHandler handler = router.findRoute(cmd.getMethod(), cmd.getPath());
        CommandResult result = handler.execute(cmd);
        if (result != null && result.isSuccess()) {
            displayResult(result);
        }
        return result != null;
    }

    private static void displayResult(CommandResult result) {
        //TODO:
    }

    private static void addCommands(Router router) {
        //GET commands
        router.addRoute(Method.GET, new PathTemplate("/rooms"), new GetRoomsCommand());
        router.addRoute(Method.GET, new PathTemplate("/rooms/{rid}"), new GetRoomsByIdCommand());
        router.addRoute(Method.GET, new PathTemplate("/rooms/{rid}/bookings/{bid}"), new GetBookingsByIdCommand());
        router.addRoute(Method.GET, new PathTemplate("/users/{uid}"), new GetUsersByIdCommand());
        router.addRoute(Method.GET, new PathTemplate("/users/{uid}/bookings"), new GetBookingsByUserIdCommand());
        router.addRoute(Method.GET, new PathTemplate("/labels"), new GetLabelsCommand());
        router.addRoute(Method.GET, new PathTemplate("/labels/{lid}/rooms"), new GetRoomsWithLabelCommand());
        //POST commands
        router.addRoute(Method.POST, new PathTemplate("/rooms"), new PostRoomsCommand());
        router.addRoute(Method.POST, new PathTemplate("/rooms/{rid}/bookings"), new PostBookingsInRoomCommand());
        router.addRoute(Method.POST, new PathTemplate("/users"), new PostUsersCommand());
        router.addRoute(Method.POST, new PathTemplate("/labels"), new PostLabelsCommand());
        //EXIT command
        router.addRoute(Method.EXIT, new PathTemplate("/"), new ExitCommand());





    }
}
