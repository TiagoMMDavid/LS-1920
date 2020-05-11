package pt.isel.ls.utils;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class NTree implements Iterable<Pair<String,String>> {

    static class HandlerNode {
        private PathTemplate template;
        private CommandHandler cmdHandler;

        public HandlerNode(CommandHandler cmd, PathTemplate path) {
            this.template = path;
            this.cmdHandler = cmd;
        }

        public boolean checkTemplateAndApply(Path path) {
            if (template.isTemplateOf(path)) {
                template.applyTemplate(path);
                return true;
            } else {
                return false;
            }
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
                if (handlerNode.checkTemplateAndApply(path)) {
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

    @Override
    public Iterator<Pair<String,String>> iterator() {
        return new Iterator<>() {

            Iterator<Method> method = methods.keySet().iterator();
            Method currMethod = method.next();

            Iterator<HandlerNode> handlers = methods.get(currMethod).cmdhandlers.iterator();

            Pair<Method,HandlerNode> curr = null;

            @Override
            public boolean hasNext() {
                if (curr != null) {
                    return true;
                }

                while (!handlers.hasNext() && method.hasNext()) {
                    currMethod = method.next();
                    handlers = methods.get(currMethod).cmdhandlers.iterator();
                }

                if (handlers.hasNext()) {
                    HandlerNode handler = handlers.next();
                    curr = new Pair<>(currMethod, handler);
                }

                return curr != null;
            }

            @Override
            public Pair<String,String> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Pair<String,String> aux = new Pair<>(curr.first.toString() + " " + curr.second.template.toString(),
                        curr.second.cmdHandler.getDescription());
                curr = null;
                return aux;
            }
        };
    }
}
