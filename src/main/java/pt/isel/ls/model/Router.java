package pt.isel.ls.model;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;
import pt.isel.ls.utils.Pair;

import java.util.HashMap;

public class Router {
    private HashMap<Pair<Method, PathTemplate>, CommandHandler> routes = new HashMap<>();

    public void addRoute(Method method, PathTemplate path, CommandHandler handler) {
        routes.put(new Pair<>(method, path), handler);
    }

    public CommandHandler findRoute(Method method, Path path) {
        //TODO: Improve path finding
        Iterable<Pair<Method, PathTemplate>> itr = routes.keySet();
        for (Pair<Method, PathTemplate> key: itr) {
            if (key.first.equals(method) && key.second.isTemplateOf(path)) {
                return routes.get(key);
            }
        }
        return null;
    }
}