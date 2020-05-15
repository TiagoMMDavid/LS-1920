package pt.isel.ls.utils.html.elements;

public class ListElement extends Element {

    public ListElement(Element... elements) {
        super("<li>", elements, "</li>");
    }
}
