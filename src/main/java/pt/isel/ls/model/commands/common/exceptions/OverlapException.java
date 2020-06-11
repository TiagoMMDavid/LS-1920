package pt.isel.ls.model.commands.common.exceptions;

public class OverlapException extends CommandException {

    public OverlapException(String message) {
        super(message);
    }

    public ExceptionType getExceptionType() {
        return ExceptionType.OverlapException;
    }
}
