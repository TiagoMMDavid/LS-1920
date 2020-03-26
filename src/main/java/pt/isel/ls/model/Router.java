package pt.isel.ls.model;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;
import pt.isel.ls.utils.MethodNode;
import pt.isel.ls.utils.NTree;

public class Router {
    private NTree routes = new NTree();

    public void addRoute(Method method, PathTemplate path, CommandHandler handler) {
        routes.add(method, path, handler);
    }

    public CommandHandler findRoute(Method method, Path path) {
        MethodNode node = routes.getMethodNode(method);
        return node == null ? null : node.getHandlerAndApplyTemplate(path);
    }
}