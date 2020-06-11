package pt.isel.ls.model.commands.common.exceptions;

public class MissingArgumentsException extends CommandException {

    public MissingArgumentsException(String message) {
        super(message);
    }

    public MissingArgumentsException() {
        super("No arguments found / Invalid arguments");
    }

    @Override
    public ExceptionType getExceptionType() {
        return ExceptionType.MissingArgumentsException;
    }
}
