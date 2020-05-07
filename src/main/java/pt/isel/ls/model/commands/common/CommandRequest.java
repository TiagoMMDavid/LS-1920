package pt.isel.ls.model.commands.common;

import pt.isel.ls.model.Router;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.utils.Pair;

import java.util.Iterator;

public class CommandRequest {
    private Path path;
    private Parameters params;
    private TransactionManager trans;
    private Router router;

    public CommandRequest(Path path, Parameters params, TransactionManager trans,
                          Router router) {
        this.path = path;
        this.trans = trans;
        this.router = router;
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
        return router.getCommands();
    }

    public Router getRouter() {
        return router;
    }
}
