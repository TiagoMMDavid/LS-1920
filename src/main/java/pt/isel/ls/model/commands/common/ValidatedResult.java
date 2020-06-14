package pt.isel.ls.model.commands.common;

import pt.isel.ls.model.commands.common.exceptions.CommandException;

public abstract class ValidatedResult {

    private CommandException.ExceptionType errorType;
    private String errorMessage;
    private String validatedStringName;

    public void setError(String errorType, String errorMessage, String validatedString) {
        this.errorType = CommandException.ExceptionType.valueOf(errorType);
        this.errorMessage = errorMessage;
        this.validatedStringName = validatedString;
    }

    public CommandException.ExceptionType getErrorType() {
        return errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getValidatedStringName() {
        return validatedStringName;
    }
}
