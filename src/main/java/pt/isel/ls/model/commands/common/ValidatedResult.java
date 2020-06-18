package pt.isel.ls.model.commands.common;

import pt.isel.ls.model.commands.common.exceptions.CommandException;

public abstract class ValidatedResult {

    private CommandException.ExceptionType errorType;
    private String errorMessage;
    private String validatedParamName;

    /**
     * Sets the validation error type
     * @param errorType the error type
     * @param errorMessage the message representing the error
     * @param validatedParam the name of the validated parameter
     */
    public void setError(String errorType, String errorMessage, String validatedParam) {
        this.errorType = CommandException.ExceptionType.valueOf(errorType);
        this.errorMessage = errorMessage;
        this.validatedParamName = validatedParam;
    }

    public CommandException.ExceptionType getErrorType() {
        return errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getValidatedParamName() {
        return validatedParamName;
    }
}
