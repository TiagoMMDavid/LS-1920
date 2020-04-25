package pt.isel.ls.model.commands.common;

public class CommandException extends Exception {
    public CommandException() {
        super("Error while processing command!");
    }

    public CommandException(String message) {
        super("Error: " + message);
    }
}
