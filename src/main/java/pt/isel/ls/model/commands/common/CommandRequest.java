package pt.isel.ls.model.commands.common;

import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.utils.Pair;

import java.util.Iterator;

public class CommandRequest {
    private Path path;
    private Parameters params;
    private TransactionManager trans;
    private Iterator<Pair<String,String>> commands;

    public CommandRequest(Path path, Parameters params, TransactionManager trans,
                          Iterator<Pair<String,String>> commands) {
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

    public Iterator<Pair<String,String>> getCommands() {
        return commands;
    }
}
