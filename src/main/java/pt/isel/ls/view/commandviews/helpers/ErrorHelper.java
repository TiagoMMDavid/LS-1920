package pt.isel.ls.view.commandviews.helpers;

import pt.isel.ls.model.commands.common.ValidatedResult;
import pt.isel.ls.model.commands.common.exceptions.CommandException;

public class ErrorHelper {

    public static String getResultError(ValidatedResult result,
                                        String valueId, String valueName, String previousValue) {

        CommandException.ExceptionType errorType = result.getErrorType();
        if (errorType != null) {
            switch (errorType) {
                case DuplicateColumnError:
                    return valueName + "'" + previousValue + "' already exists!";
                case ValidationException:
                    if (result.getValidatedStringName().equals(valueId)) {
                        return valueName + " " + result.getErrorMessage();
                    }
                    break;
                default:
            }
        }
        return "";
    }
}
