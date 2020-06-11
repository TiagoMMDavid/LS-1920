package pt.isel.ls.utils.html.elements;

public class Option extends ElementText {

    public Option(String value, String text) {
        super("<option value=\"" + value + "\">", text, "</option>");
    }

    public Option(String value, String text, boolean isSelected) {
        super("<option value=\"" + value + "\"" + (isSelected ? "selected>" : ">"),
                text, "</option>");
    }
}
