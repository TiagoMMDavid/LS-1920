package pt.isel.ls;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

import pt.isel.ls.model.Router;

import pt.isel.ls.model.commands.DeleteBookingInRoomCommand;
import pt.isel.ls.model.commands.ExitCommand;
import pt.isel.ls.model.commands.GetBookingByRoomAndBookingIdCommand;
import pt.isel.ls.model.commands.GetBookingsByRoomIdCommand;
import pt.isel.ls.model.commands.GetBookingsByUserIdCommand;
import pt.isel.ls.model.commands.GetLabelByIdCommand;
import pt.isel.ls.model.commands.GetLabelsCommand;
import pt.isel.ls.model.commands.GetRoomByIdCommand;
import pt.isel.ls.model.commands.GetRoomsCommand;
import pt.isel.ls.model.commands.GetRoomsWithLabelCommand;
import pt.isel.ls.model.commands.GetTimeCommand;
import pt.isel.ls.model.commands.GetUserByIdCommand;
import pt.isel.ls.model.commands.GetUsersCommand;
import pt.isel.ls.model.commands.ListenCommand;
import pt.isel.ls.model.commands.OptionCommand;
import pt.isel.ls.model.commands.PostBookingInRoomCommand;
import pt.isel.ls.model.commands.PostLabelCommand;
import pt.isel.ls.model.commands.PostRoomCommand;
import pt.isel.ls.model.commands.PostUserCommand;
import pt.isel.ls.model.commands.PutBookingInRoomCommand;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.Headers;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.commands.common.Parameters;
import pt.isel.ls.model.commands.sql.TransactionManager;

import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;

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
                System.out.println("Wrong format. " + VALID_COMMAND_FORMAT);
            } else {
                running = executeCommand(commands);
            }
        }
    }

    private static boolean isCommandValid(String[] commands) {
        return commands.length > 1 && commands.length <= 4;
    }

    private static final String VALID_COMMAND_FORMAT =
              "Please either use:"
            + "\n{method} {path}"
            + "\n{method} {path} {headers}"
            + "\n{method} {path} {parameters}"
            + "\n{method} {path} {headers} {parameters}";

    //Returned value determines if the app should continue running or not
    private static boolean executeCommand(String[] commands) {
        Method method;
        try {
            method = Method.valueOf(commands[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Method \"" + commands[0] + "\" does not exist");
            System.out.println(VALID_COMMAND_FORMAT);
            return true;
        }

        Headers headers = null;
        Parameters params = null;
        Path path;

        try {
            switch (commands.length) {
                case 4:
                    headers = new Headers(commands[2]);
                    params = new Parameters(commands[3]);
                    break;
                case 3:
                    if (isHeader(commands[2])) {
                        headers = new Headers(commands[2]);
                    } else {
                        params = new Parameters(commands[2]);
                    }
                    break;
                default:
            }
            path = new Path(commands[1]);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + VALID_COMMAND_FORMAT);
            return true;
        }

        CommandRequest cmd = new CommandRequest(path,
                params,
                trans,
                router);

        CommandHandler handler = router.findRoute(method, cmd.getPath());
        if (handler == null) {
            System.out.println("Command not found");
            return true;
        }

        CommandResult result;
        try {
            result = handler.execute(cmd);
            if (result != null) {
                displayResult(result, headers);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n");
            return true;
        }

        return result != null;
    }

    private static boolean isHeader(String command) {
        return command.contains(":") && !command.contains("=") && !command.contains("&");
    }

    private static void displayResult(CommandResult result, Headers headers) {
        try {
            String filename = null;
            String viewFormat = null;

            // Get headers
            if (headers != null) {
                filename = headers.getValue("file-name");
                viewFormat = headers.getValue("accept");
            }
            // Get output stream
            OutputStream out = filename == null ? System.out : getFileStream(filename);

            // Present each entity
            View view = View.getInstance(result);
            if (view != null) {
                view.display(out, viewFormat);
            }

            // Close output stream only if it isn't System.out
            if (out != System.out) {
                out.close();
            }
        } catch (IOException e) {
            System.out.println("Failed to open output stream");
        }
    }

    private static OutputStream getFileStream(String fileName) throws FileNotFoundException {
        return new FileOutputStream(fileName);
    }

    private static void addCommands() {
        //Path Templates (shared between different commands)
        PathTemplate roomsTemplate = new PathTemplate("/rooms");
        PathTemplate labelsTemplate = new PathTemplate("/labels");

        //GET commands
        router.addRoute(Method.GET, roomsTemplate, new GetRoomsCommand());
        router.addRoute(Method.GET, new PathTemplate("/rooms/{rid}"), new GetRoomByIdCommand());
        router.addRoute(Method.GET, new PathTemplate("/rooms/{rid}/bookings"), new GetBookingsByRoomIdCommand());
        router.addRoute(Method.GET, new PathTemplate("/rooms/{rid}/bookings/{bid}"),
                new GetBookingByRoomAndBookingIdCommand());
        router.addRoute(Method.GET, new PathTemplate("/users"), new GetUsersCommand());
        router.addRoute(Method.GET, new PathTemplate("/users/{uid}"), new GetUserByIdCommand());
        router.addRoute(Method.GET, new PathTemplate("/users/{uid}/bookings"), new GetBookingsByUserIdCommand());
        router.addRoute(Method.GET, labelsTemplate, new GetLabelsCommand());
        router.addRoute(Method.GET, new PathTemplate("/labels/{lid}"), new GetLabelByIdCommand());
        router.addRoute(Method.GET, new PathTemplate("/labels/{lid}/rooms"), new GetRoomsWithLabelCommand());
        router.addRoute(Method.GET, new PathTemplate("/time"), new GetTimeCommand());

        //POST commands
        router.addRoute(Method.POST, roomsTemplate, new PostRoomCommand());
        router.addRoute(Method.POST, new PathTemplate("/rooms/{rid}/bookings"), new PostBookingInRoomCommand());
        router.addRoute(Method.POST, new PathTemplate("/users"), new PostUserCommand());
        router.addRoute(Method.POST, labelsTemplate, new PostLabelCommand());

        //DELETE command
        router.addRoute(Method.DELETE, new PathTemplate("/rooms/{rid}/bookings/{bid}"),
                new DeleteBookingInRoomCommand());

        //PUT command
        router.addRoute(Method.PUT, new PathTemplate("/rooms/{rid}/bookings/{bid}"),
                new PutBookingInRoomCommand());

        //EXIT command
        router.addRoute(Method.EXIT, new PathTemplate("/"), new ExitCommand());

        // OPTION command
        router.addRoute(Method.OPTION, new PathTemplate("/"), new OptionCommand());

        // LISTEN command
        router.addRoute(Method.LISTEN, new PathTemplate("/"), new ListenCommand());
    }
}
