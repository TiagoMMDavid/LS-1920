package pt.isel.ls.utils.html.elements;

public class Input extends ElementText {

    public enum InputType {
        TEXT,
        DATETIME_LOCAL,
        TIME,
        SUBMIT,
        CHECKBOX,
        NUMBER,
    }

    public Input(InputType type, String name, String value) {
        super("<input type=\"" + getTypeName(type) + "\" "
                + "name=\"" + name + "\" "
                + "value=\"" + value + "\"",
                "", "/>");
    }

    public Input(InputType type, String name, String value, String id) {
        super("<input type=\"" + getTypeName(type) + "\" "
                + "name=\"" + name + "\" "
                + "value=\"" + value + "\" "
                + "id=\"" + id + "\"",
                "", "/>");
    }

    private static String getTypeName(InputType type) {
        return type.toString().toLowerCase().replace('_', '-');
    }
}
