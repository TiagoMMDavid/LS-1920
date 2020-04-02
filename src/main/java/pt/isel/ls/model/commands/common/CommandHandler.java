package pt.isel.ls.model.commands.common;

@FunctionalInterface
public interface CommandHandler {
    CommandResult execute(CommandRequest commandRequest) throws Exception;
}
