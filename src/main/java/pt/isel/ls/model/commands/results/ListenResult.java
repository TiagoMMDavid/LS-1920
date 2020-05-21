package pt.isel.ls.model.commands.results;

import org.eclipse.jetty.server.Server;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.exceptions.ExitException;
import pt.isel.ls.utils.ExitRoutine;

public class ListenResult implements CommandResult {

    private boolean hasResult = false;
    private int port;
    private Server server;

    public ListenResult(Server server, int port) {
        this.server = server;
        this.port = port;
        hasResult = true;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean hasResults() {
        return hasResult;
    }

    @Override
    public ResultType getResultType() {
        return ResultType.Listen;
    }

    @Override
    public ExitRoutine getExitRoutine() {
        return () -> {
            try {
                server.stop();
                server.join();
            } catch (Exception e) {
                throw new ExitException("Failed to stop server");
            }
        };
    }
}
