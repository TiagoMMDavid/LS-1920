package pt.isel.ls.view.commandviews.helpers;

import pt.isel.ls.model.commands.common.ValidatedResult;
import pt.isel.ls.model.commands.common.exceptions.CommandException;

public class ErrorHelper {

    public static String getResultError(ValidatedResult result, String valueId, String valueName,
                                        String previousValue, boolean canBeDuplicatedOrOverlapped) {

        CommandException.ExceptionType errorType = result.getErrorType();
        if (errorType != null) {
            switch (errorType) {
                case DuplicateColumnError:
                    if (!canBeDuplicatedOrOverlapped) {
                        return valueName + " '" + previousValue + "' already exists!";
                    }
                    break;
                case ValidationException:
                    if (result.getValidatedParamName().equals(valueId)) {
                        return valueName + " " + result.getErrorMessage();
                    }
                    break;
                case OverlapException:
                    if (!canBeDuplicatedOrOverlapped) {
                        return valueName + " overlaps with an existing booking!";
                    }
                    break;
                default:
            }
        }
        return "";
    }
}
