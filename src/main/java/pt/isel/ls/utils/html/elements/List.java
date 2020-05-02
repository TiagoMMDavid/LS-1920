package pt.isel.ls.utils.html.elements;

public class List extends ElementText {

    public List(String text) {
        super("<li>", text, "</li>");
    }
}
