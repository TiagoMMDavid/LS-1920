package pt.isel.ls.utils;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;

import java.util.LinkedList;
import java.util.List;

public class MethodNode {
    private Method method;
    private List<HandlerNode> cmdhandler = new LinkedList<>();

    public MethodNode(Method method, PathTemplate template, CommandHandler cmd) {
        this.method = method;
        HandlerNode toAdd = new HandlerNode(cmd, template);
        cmdhandler.add(toAdd);
    }

    public Method getMethod() {
        return method;
    }

    public List<HandlerNode> getCmdhandler() {
        return cmdhandler;
    }

    public CommandHandler getHandlerAndApplyTemplate(Path path) {
        for (HandlerNode handlerNode : cmdhandler) {
            PathTemplate template = handlerNode.getTemplate();
            if (template.isTemplateOf(path)) {
                template.applyTemplate(path);
                return handlerNode.getCmdHandler();
            }
        }
        return null;
    }
}
