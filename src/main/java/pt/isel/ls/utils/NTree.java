package pt.isel.ls.utils;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;

import java.util.HashMap;
import java.util.HashSet;

public class NTree {

    static class HandlerNode {
        private PathTemplate template;
        private CommandHandler cmdHandler;

        public HandlerNode(CommandHandler cmd, PathTemplate path) {
            this.template = path;
            this.cmdHandler = cmd;
        }

        public PathTemplate getTemplate() {
            return template;
        }

        public CommandHandler getCmdHandler() {
            return cmdHandler;
        }
    }

    static class MethodNode {
        private HashSet<HandlerNode> cmdhandlers = new HashSet<>();

        public MethodNode(PathTemplate template, CommandHandler cmd) {
            HandlerNode toAdd = new HandlerNode(cmd, template);
            cmdhandlers.add(toAdd);
        }

        public void addHandler(PathTemplate template, CommandHandler cmd) {
            cmdhandlers.add(new HandlerNode(cmd, template));
        }

        public CommandHandler getHandlerAndApplyTemplate(Path path) {
            for (HandlerNode handlerNode : cmdhandlers) {
                PathTemplate template = handlerNode.getTemplate();
                if (template.isTemplateOf(path)) {
                    template.applyTemplate(path);
                    return handlerNode.getCmdHandler();
                }
            }
            return null;
        }
    }

    private HashMap<Method,MethodNode> methods = new HashMap<>();

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

    public CommandHandler getHandlerAndApplyTemplate(Method method, Path path) {
        MethodNode node = getMethodNode(method);
        return node == null ? null : node.getHandlerAndApplyTemplate(path);
    }
}
