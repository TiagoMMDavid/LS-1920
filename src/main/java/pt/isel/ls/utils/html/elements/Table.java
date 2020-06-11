package pt.isel.ls.utils.html.elements;

public class Table extends Element {

    public Table(int borderSize, Element... elements) {
        super("<table border=" + borderSize + ">", elements, "</table>");
    }
}
