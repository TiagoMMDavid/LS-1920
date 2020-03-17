package pt.isel.ls.model.commands.common;

public interface CommandHandler {
    CommandResult execute(CommandRequest commandRequest);
}
