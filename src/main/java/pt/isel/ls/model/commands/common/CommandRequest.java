package pt.isel.ls.model.commands.common;

import pt.isel.ls.model.paths.Path;

public class CommandRequest {
    private Method method;
    private Path path;
    private Parameters params;

    public CommandRequest(Method method, Path path) {
        this.method = method;
        this.path = path;
    }

    public CommandRequest(Method method, Path path, Parameters params) {
        this(method, path);
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
}
