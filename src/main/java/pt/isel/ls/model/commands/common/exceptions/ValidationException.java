package pt.isel.ls.model.commands.common.exceptions;

public class ValidationException extends CommandException {

    private String validatedString;
    private String message;

    public ValidationException(String validatedString, String message) {
        super("String " + message);
        this.validatedString = validatedString;
        this.message = message;
    }

    public String getValidatedString() {
        return validatedString;
    }

    public String getErrorMessage() {
        return message;
    }

    @Override
    public ExceptionType getExceptionType() {
        return ExceptionType.ValidationException;
    }
}
