package pt.isel.ls.model.commands.common.exceptions;

public class ValidationException extends CommandException {

    private String validatedParam;
    private String message;

    public ValidationException(String validatedParam, String paramType, String message) {
        super(paramType + " " + message);
        this.validatedParam = validatedParam;
        this.message = message;
    }

    public String getValidatedParam() {
        return validatedParam;
    }

    public String getErrorMessage() {
        return message;
    }

    @Override
    public ExceptionType getExceptionType() {
        return ExceptionType.ValidationException;
    }
}
