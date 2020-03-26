package pt.isel.ls.utils;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.paths.PathTemplate;

import java.util.HashMap;

public class NTree {

    HashMap<Method,MethodNode> methods = new HashMap<>();

    //Adds a route to the HashMap
    public void add(Method method, PathTemplate template, CommandHandler cmd) {
        MethodNode node = getMethodNode(method);
        if (node == null) {
            node = new MethodNode(template, cmd);
        } else {
            node.addHandler(template, cmd);
        }
        methods.put(method, node);
    }

    public MethodNode getMethodNode(Method method) {
        return methods.get(method);
    }
}
