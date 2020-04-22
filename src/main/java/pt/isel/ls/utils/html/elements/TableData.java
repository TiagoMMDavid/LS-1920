package pt.isel.ls.utils.html.elements;

public class TableData extends ElementText {
    public TableData(String data) {
        super("<td>", data, "</td>");
    }
}
