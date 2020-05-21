package pt.isel.ls.utils.html.elements;

public class Input extends ElementText {

    public enum InputType {
        TEXT,
        DATETIME_LOCAL,
        TIME,
        SUBMIT,
        CHECKBOX,
        NUMBER
    }


    public Input(InputType type, String name, String value) {
        super(getCommonTags(type, name, value), "", "/>");
    }

    public Input(InputType type, String name, String value, String id) {
        super(getCommonTags(type, name, value)
                + "id=\"" + id + "\"",
                "", "/>");
    }

    public Input(InputType type, String name, String value, String id, String min) {
        super(getCommonTags(type, name, value)
                + "id=\"" + id + "\""
                + "min=\"" + min + "\"",
                "",
                "/>");
    }

    private static String getTypeName(InputType type) {
        return type.toString().toLowerCase().replace('_', '-');
    }

    private static String getCommonTags(InputType type, String name, String value) {
        return "<input type=\"" + getTypeName(type) + "\" "
                + "name=\"" + name + "\" "
                + "value=\"" + value + "\" ";
    }
}
