package pt.isel.ls.utils.html.elements;

public class UnorderedList extends Element {
    public UnorderedList(Element... elements) {
        super("<ul>", elements, "</ul>");
    }
}
