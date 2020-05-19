package pt.isel.ls.model.commands;

import org.eclipse.jetty.plus.servlet.ServletHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import pt.isel.ls.model.commands.common.exceptions.CommandException;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.Parameters;
import pt.isel.ls.model.commands.common.exceptions.MissingArgumentsException;
import pt.isel.ls.model.commands.common.exceptions.ParseArgumentException;
import pt.isel.ls.model.commands.results.ListenResult;
import pt.isel.ls.http.CommandServlet;

import java.sql.SQLException;

public class ListenCommand implements CommandHandler {

    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        Parameters params = commandRequest.getParams();
        if (params == null) {
            throw new MissingArgumentsException("No parameters specified");
        }
        Integer port;
        try {
            port = params.getInt("port");
        } catch (NumberFormatException e) {
            throw new ParseArgumentException("Invalid port number");
        }
        if (port == null) {
            throw new MissingArgumentsException("No port specified");
        }
        Server server = new Server(port);
        ServletHandler handler = new ServletHandler();
        CommandServlet servlet = new CommandServlet(commandRequest.getRouter(), commandRequest.getTransactionHandler());

        handler.addServletWithMapping(new ServletHolder(servlet), "/*");

        server.setHandler(handler);
        try {
            server.start();
        } catch (Exception e) {
            throw new CommandException("Failed to start server");
        }

        ListenResult result = new ListenResult();
        result.setPort(port);
        return result;
    }

    @Override
    public String getDescription() {
        return "Starts the HTTP server. "
                + "This command receives a port parameter containing the TCP port "
                + "where the server should listen for requests.";
    }
}
