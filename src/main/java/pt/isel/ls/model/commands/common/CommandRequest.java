package pt.isel.ls.model.commands.common;

import pt.isel.ls.model.paths.Path;

public class CommandRequest {
    private Method method;
    private Path path;
    private Parameters params;
    private PsqlConnectionHandler connectionHandler;

    public CommandRequest(Method method, Path path, PsqlConnectionHandler connectionHandler) {
        this.method = method;
        this.path = path;
        this.connectionHandler = connectionHandler;
    }

    public CommandRequest(Method method, Path path, Parameters params, PsqlConnectionHandler connectionHandler) {
        this(method, path, connectionHandler);
        this.params = params;
    }

    public Method getMethod() {
        return method;
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
