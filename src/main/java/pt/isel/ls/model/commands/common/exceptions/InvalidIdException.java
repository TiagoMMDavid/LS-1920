package pt.isel.ls.model.commands.common.exceptions;

public class InvalidIdException extends CommandException {

    public InvalidIdException(String message) {
        super(message);
    }

    @Override
    public ExceptionType getExceptionType() {
        return ExceptionType.InvalidIdException;
    }
}
