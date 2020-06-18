package pt.isel.ls.view.commandviews.helpers;

import pt.isel.ls.model.commands.common.ValidatedResult;
import pt.isel.ls.model.commands.common.exceptions.CommandException;

public class ErrorHelper {

    /**
     * This method is responsible for returning a String corresponding to the error that's present in the result.
     * @param result the result containing the error
     * @param valueId identification of the value to verify
     * @param valueName used to represent the value name
     * @param previousValue the previously entered value
     * @param canBeDuplicatedOrOverlapped whether the value can be duplicated or overlapped with an existing one
     */

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
                    if (result.getValidatedStringName().equals(valueId)) {
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
