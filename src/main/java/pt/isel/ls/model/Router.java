package pt.isel.ls.model;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;
import pt.isel.ls.utils.NTree;

import java.util.Iterator;

public class Router {
    /**
     * A NTree is used to store the routes.
     * Composed of an HashMap with a Method as a key, and MethodNode as value.
     * This way, repetition is avoided, while also minimizing the amount of time to execute.
     */
    private NTree routes = new NTree();

    public void addRoute(Method method, PathTemplate path, CommandHandler handler) {
        routes.add(method, path, handler);
    }

    //Searches the NTree for a valid route
    public CommandHandler findRoute(Method method, Path path) {
        return routes.getHandlerAndApplyTemplate(method, path);
    }

    public Iterator<Object> getCommands() {
        return routes.iterator();
    }
}