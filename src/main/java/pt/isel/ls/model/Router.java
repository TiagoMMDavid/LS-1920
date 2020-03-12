package pt.isel.ls.model;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;

public class Router {
    public Router() {

    }

    public void addRoute(Method method, PathTemplate path, CommandHandler handler) {

    }

    public CommandHandler findRoute(Method method, Path path) {
        return null;
    }

}
