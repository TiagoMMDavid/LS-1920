package pt.isel.ls.utils.html.elements;

public class TableDataElement extends Element {

    public TableDataElement(Element... elems) {
        super("<td>", elems, "</td>");
    }
}