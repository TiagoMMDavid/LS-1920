package pt.isel.ls.model;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;
import pt.isel.ls.utils.NTree;
import pt.isel.ls.utils.Pair;

import java.util.Iterator;

public class Router {

    private NTree routes = new NTree();

    /**
     * Adds a new route to a Command Handler
     * @param method Handler's Method
     * @param path Handler's Path
     * @param handler The Handler itself
     */
    public void addRoute(Method method, PathTemplate path, CommandHandler handler) {
        routes.add(method, path, handler);
    }

    /**
     * Searches the NTree for the correct Handler based on the parameters given
     * @param method Handler's Method
     * @param path Handler's Path
     * @return the Handler associated with the given parameters
     */
    public CommandHandler findRoute(Method method, Path path) {
        return routes.getHandlerAndApplyTemplate(method, path);
    }

    /**
     * @return an iterator of all the existing commands in this Router. This iterator only
     * contains String representations of said commands.
     */
    public Iterator<Pair<String,String>> getCommands() {
        return routes.iterator();
    }
}