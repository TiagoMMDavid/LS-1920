package pt.isel.ls.utils.html.elements;

public class Select extends Element {

    public Select(String name, String id, Element... options) {
        super("<select name=\"" + name + "\" id=\"" + id + "\">", options, "</select>");
    }
}
