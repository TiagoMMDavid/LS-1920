package pt.isel.ls.model.commands.common.exceptions;

/**
 * Abstract Class which represents an Exception that can be thrown
 * while executing a Command Handler
 */
public abstract class CommandException extends Exception {

    /**
     * Enum used to identify the type of Exception that occurred
     * This enum is helpful in order to get the specific Exception that was thrown
     */
    public enum ExceptionType {
        ExitException,
        InvalidIdException,
        MissingArgumentsException,
        OverlapException,
        ParseArgumentException,
        ServerException,
        ValidationException,

        //SQL Exceptions
        DuplicateColumnError
    }

    public CommandException(String message) {
        super("Error: " + message);
    }

    public abstract ExceptionType getExceptionType();
}
