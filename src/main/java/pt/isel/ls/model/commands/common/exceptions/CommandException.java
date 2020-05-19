package pt.isel.ls.model.commands.common.exceptions;

public class CommandException extends Exception {

    public CommandException(String message) {
        super("Error: " + message);
    }
}
