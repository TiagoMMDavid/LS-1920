package pt.isel.ls.utils.html.elements;

public class TableRow extends Element {
    public TableRow(Element... elements) {
        super("<tr>", elements, "</tr>");
    }
}
