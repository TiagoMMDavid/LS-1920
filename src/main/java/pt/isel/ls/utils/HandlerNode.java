package pt.isel.ls.utils;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.paths.PathTemplate;

class HandlerNode {
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