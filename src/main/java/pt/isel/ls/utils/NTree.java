package pt.isel.ls.utils;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Data Structure to store the Command Handlers
 * The first nodes of the Tree Contain the possible Methods from the handlers
 * Each Method Node contains the Command Handlers that exist in a given method
 */
public class NTree implements Iterable<Pair<String,String>> {

    /**
     * Inner class that represents a method Node, as in,
     * the first level from the NTree
     */
    static class MethodNode {
        private LinkedList<HandlerNode> cmdhandlers = new LinkedList<>();

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

    /**
     * Inner class that represents a Handler Node, as in,
     * the second level from the NTree (below MethodNode)
     */
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

    private HashMap<Method,MethodNode> methods = new HashMap<>();

    /**
     * Adds a new route to the data structure
     * @param method The method in which the Command is in
     * @param template The Path template used for the Command
     * @param cmd Command Handler to be stored
     */
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

    /**
     * Gets an Iterator for the NTree
     * @return an Iterator which contains a Pair of Strings containing the Handler's Method, PathTemplate,
     *         and its description.
     *         The Method's name and Path Template are combined into the Pair's first element
     */
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
