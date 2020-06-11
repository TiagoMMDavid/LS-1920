package pt.isel.ls.model.commands.common.exceptions;

public class ParseArgumentException extends CommandException {

    public ParseArgumentException(String message) {
        super(message);
    }

    @Override
    public ExceptionType getExceptionType() {
        return ExceptionType.ParseArgumentException;
    }
}
