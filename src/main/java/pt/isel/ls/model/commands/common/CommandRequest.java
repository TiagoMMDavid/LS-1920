package pt.isel.ls.model.commands.common;

import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.paths.Path;

public class CommandRequest {
    private Path path;
    private Parameters params;
    private TransactionManager trans;

    public CommandRequest(Path path, TransactionManager trans) {
        this.path = path;
        this.trans = trans;
    }

    public CommandRequest(Path path, Parameters params, TransactionManager trans) {
        this(path, trans);
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
}
