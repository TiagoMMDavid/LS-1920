package pt.isel.ls.model.commands.common;

import pt.isel.ls.model.commands.common.exceptions.ValidationException;
import java.util.Date;

import static pt.isel.ls.utils.DateUtils.isDateMinutesMultipleOf;

public class Validator {

    /**
     * Validates a String by checking if it doesn't have unnecessary white spaces,
     * doesn't have illegal characters, nor does it exceed the given max length.
     * @param string the String to validate
     * @param name the name of the parameter
     * @param maxLength the maximum String length
     * @return if the validation was successful
     * @throws ValidationException if the validation fails
     */
    public static boolean validateString(String string, String name, int maxLength) throws ValidationException {
        if (string != null) {
            if (string.charAt(0) == ' ' || string.charAt(string.length() - 1) == ' ') {
                throw new ValidationException(name, "String", "contains whitespace at the beginning/end");
            }

            if (string.length() > maxLength) {
                throw new ValidationException(name, "String", "exceeds maximum length of " + maxLength);
            }

            // Try to avoid XSS
            if (string.matches(".*[<>].*")) {
                throw new ValidationException(name, "String", "contains illegal characters");
            }
        }

        return true;
    }

    /**
     * Checks if a Date's minutes are multiple of X
     * @param date the Date to validate
     * @param name the name of the parameter
     * @param x the X value that the Date's minutes must be multiple of
     * @return if the validation was successful
     * @throws ValidationException if the validation fails
     */
    public static boolean validateDateMultipleOf(Date date, String name, int x) throws ValidationException {
        if (!isDateMinutesMultipleOf(date, x)) {
            throw new ValidationException(name, "Date", "minutes is not multiple of " + x);
        }
        return true;
    }
}
