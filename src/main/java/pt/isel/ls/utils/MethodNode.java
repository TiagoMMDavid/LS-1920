package pt.isel.ls.utils;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;

import java.util.HashSet;

public class MethodNode {
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
