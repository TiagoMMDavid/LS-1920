package pt.isel.ls.model.commands.common;

import pt.isel.ls.model.paths.Path;

public class CommandRequest {
    private Path path;
    private Parameters params;
    private PsqlConnectionHandler connectionHandler;

    public CommandRequest(Path path, PsqlConnectionHandler connectionHandler) {
        this.path = path;
        this.connectionHandler = connectionHandler;
    }

    public CommandRequest(Path path, Parameters params, PsqlConnectionHandler connectionHandler) {
        this(path, connectionHandler);
        this.params = params;
    }

    public Path getPath() {
        return path;
    }

    public Parameters getParams() {
        return params;
    }

    public PsqlConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }
}
