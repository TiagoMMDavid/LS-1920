package pt.isel.ls.model.commands.common.exceptions;

public class ServerException extends CommandException {

    public ServerException(String message) {
        super(message);
    }

    @Override
    public ExceptionType getExceptionType() {
        return ExceptionType.ServerException;
    }
}
