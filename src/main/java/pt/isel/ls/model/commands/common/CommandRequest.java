package pt.isel.ls.model.commands.common;

import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.paths.Path;

import java.util.Iterator;

public class CommandRequest {
    private Path path;
    private Parameters params;
    private TransactionManager trans;
    private Iterator<Object> commands;

    public CommandRequest(Path path, Parameters params, TransactionManager trans, Iterator<Object> commands) {
        this.path = path;
        this.trans = trans;
        this.commands = commands;
        this.params = params;
    }

    public Path getPath() {
        return path;
    }

    public Parameters getParams() {
        return params;
    }

    public TransactionManager getTransactionHandler() {
        return trans;
    }

    public Iterator<Object> getCommands() {
        return commands;
    }
}
