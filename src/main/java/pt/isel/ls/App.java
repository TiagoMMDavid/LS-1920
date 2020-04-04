package pt.isel.ls;

import java.io.IOException;
import java.util.Scanner;
import pt.isel.ls.model.Router;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.commands.common.Parameters;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.sql.TransactionManager;

import pt.isel.ls.model.entities.Entity;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;

import pt.isel.ls.model.commands.GetRoomsCommand;
import pt.isel.ls.model.commands.GetRoomByIdCommand;
import pt.isel.ls.model.commands.GetBookingByRoomAndBookingId;
import pt.isel.ls.model.commands.GetUserByIdCommand;
import pt.isel.ls.model.commands.GetBookingsByUserIdCommand;
import pt.isel.ls.model.commands.GetLabelsCommand;
import pt.isel.ls.model.commands.GetRoomsWithLabelCommand;
import pt.isel.ls.model.commands.PostRoomsCommand;
import pt.isel.ls.model.commands.PostBookingsInRoomCommand;
import pt.isel.ls.model.commands.PostUsersCommand;
import pt.isel.ls.model.commands.PostLabelsCommand;
import pt.isel.ls.model.commands.ExitCommand;
import pt.isel.ls.view.View;

public class App {
    private static TransactionManager trans = new TransactionManager(System.getenv("postgresUrl"));
    private static Router router = new Router();

    public static void main(String[] args) {
        addCommands();

        if (args.length > 0) {
            executeCommand(args);
        } else {
            run();
        }
    }

    private static void run() {
        Scanner in = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.print("> ");
            String[] commands = in.nextLine().split(" ");

            if (!isCommandValid(commands)) {
                System.out.println("> Wrong format.");
            } else {
                running = executeCommand(commands);
            }
        }
    }

    private static boolean isCommandValid(String[] commands) {
        return commands.length > 1 && commands.length <= 3;
    }

    //Returned value determines if the app should continue running or not
    private static boolean executeCommand(String[] commands) {
        CommandRequest cmd;
        Method method = Method.valueOf(commands[0].toUpperCase());
        if (commands.length == 3) {
            cmd = new CommandRequest(new Path(commands[1]),
                    new Parameters(commands[2]),
                    trans);
        } else {
            cmd = new CommandRequest(new Path(commands[1]),
                    trans);
        }
        CommandHandler handler = router.findRoute(method, cmd.getPath());
        if (handler == null) {
            return true;
        }

        CommandResult result = null;
        try {
            result = handler.execute(cmd);
            if (result != null) {
                displayResult(result);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result != null;
    }

    private static void displayResult(CommandResult result) {
        if (!result.isSuccess()) {
            System.out.println("Error while executing command");
        } else {
            for (Entity ent: result) {
                View view = View.getInstance(ent);
                if (view != null) {
                    try {
                        view.displayText(System.out);
                    } catch (IOException e) {
                        System.out.println("Failed to open output stream");
                        return;
                    }
                }
            }
        }
    }

    private static void addCommands() {
        //Path Templates (shared between different commands)
        PathTemplate roomsTemplate = new PathTemplate("/rooms");
        PathTemplate labelsTemplate = new PathTemplate("/labels");

        //GET commands
        router.addRoute(Method.GET, roomsTemplate, new GetRoomsCommand());
        router.addRoute(Method.GET, new PathTemplate("/rooms/{rid}"), new GetRoomByIdCommand());
        router.addRoute(Method.GET, new PathTemplate("/rooms/{rid}/bookings/{bid}"),
                new GetBookingByRoomAndBookingId());
        router.addRoute(Method.GET, new PathTemplate("/users/{uid}"), new GetUserByIdCommand());
        router.addRoute(Method.GET, new PathTemplate("/users/{uid}/bookings"), new GetBookingsByUserIdCommand());
        router.addRoute(Method.GET, labelsTemplate, new GetLabelsCommand());
        router.addRoute(Method.GET, new PathTemplate("/labels/{lid}/rooms"), new GetRoomsWithLabelCommand());

        //POST commands
        router.addRoute(Method.POST, roomsTemplate, new PostRoomsCommand());
        router.addRoute(Method.POST, new PathTemplate("/rooms/{rid}/bookings"), new PostBookingsInRoomCommand());
        router.addRoute(Method.POST, new PathTemplate("/users"), new PostUsersCommand());
        router.addRoute(Method.POST, labelsTemplate, new PostLabelsCommand());

        //EXIT command
        router.addRoute(Method.EXIT, new PathTemplate("/"), new ExitCommand());
    }
}
