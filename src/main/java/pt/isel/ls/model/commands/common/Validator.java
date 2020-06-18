package pt.isel.ls.model.commands.common;

import pt.isel.ls.model.commands.common.exceptions.ValidationException;
import java.util.Date;

import static pt.isel.ls.utils.DateUtils.isDateMinutesMultipleOf;

public class Validator {

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

    // Checks if the given date is multiple of x
    public static boolean validateDateMultipleOf(Date date, String name, int x) throws ValidationException {
        if (!isDateMinutesMultipleOf(date, x)) {
            throw new ValidationException(name, "Date", "minutes is not multiple of " + x);
        }
        return true;
    }
}
