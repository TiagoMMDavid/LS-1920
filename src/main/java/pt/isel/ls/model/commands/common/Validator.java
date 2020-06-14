package pt.isel.ls.model.commands.common;

import pt.isel.ls.model.commands.common.exceptions.ValidationException;

public class Validator {

    public static boolean validateString(String string, String name, int maxLength) throws ValidationException {
        if (string != null) {
            if (string.charAt(0) == ' ' || string.charAt(string.length() - 1) == ' ') {
                throw new ValidationException(name, "contains whitespace at the beginning/end");
            }

            if (string.length() > maxLength) {
                throw new ValidationException(name, "exceeds maximum length of " + maxLength);
            }

            // Try to avoid XSS
            if (string.matches(".*[<>].*")) {
                throw new ValidationException(name, "contains illegal characters");
            }
        }

        return true;
    }
}
