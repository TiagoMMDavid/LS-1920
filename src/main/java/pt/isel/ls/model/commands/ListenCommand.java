package pt.isel.ls.model.commands;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import pt.isel.ls.http.CommandServlet;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.Parameters;
import pt.isel.ls.model.commands.common.exceptions.CommandException;
import pt.isel.ls.model.commands.common.exceptions.MissingArgumentsException;
import pt.isel.ls.model.commands.common.exceptions.ParseArgumentException;
import pt.isel.ls.model.commands.common.exceptions.ServerException;
import pt.isel.ls.model.commands.results.ListenResult;

import java.sql.SQLException;
import java.util.HashSet;

public class ListenCommand implements CommandHandler {

    private static HashSet<Integer> runningServerPorts = new HashSet<>();

    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        Parameters params = commandRequest.getParams();
        Integer port = null;
        if (params != null) {
            try {
                port = params.getInt("port");
            } catch (NumberFormatException e) {
                throw new ParseArgumentException("Invalid port number");
            }
        }

        if (port == null) {
            String envPort = System.getenv("PORT");
            if (envPort == null) {
                throw new MissingArgumentsException("No port specified");
            }
            try {
                port = Integer.parseInt(envPort);
            } catch (NumberFormatException e) {
                throw new ParseArgumentException("Invalid port number");
            }
        } else if (runningServerPorts.contains(port)) {
            throw new ServerException("Failed to start server. Server is already running on the same port");
        }

        Server server = new Server(port);
        ServletHandler handler = new ServletHandler();
        CommandServlet servlet = new CommandServlet(commandRequest.getRouter(), commandRequest.getTransactionHandler());

        handler.addServletWithMapping(new ServletHolder(servlet), "/*");
        server.setHandler(handler);
        try {
            server.start();
            runningServerPorts.add(port);
        } catch (Exception e) {
            throw new ServerException("Failed to start server");
        }

        return new ListenResult(server, port);
    }

    @Override
    public String getDescription() {
        return "Starts the HTTP server. "
                + "This command receives a port parameter containing the TCP port "
                + "where the server should listen for requests.";
    }
}
