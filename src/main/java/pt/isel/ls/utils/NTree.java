package pt.isel.ls.utils;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.paths.PathTemplate;

import java.util.LinkedList;

public class NTree {
    LinkedList<MethodNode> methods = new LinkedList<>();

    public void add(Method method, PathTemplate template, CommandHandler cmd) {
        methods.add(new MethodNode(method, template, cmd));
    }

    public MethodNode getMethodNode(Method method) {
        for (MethodNode node: methods) {
            if (node.getMethod().equals(method)) {
                return node;
            }
        }
        return null;
    }
}
