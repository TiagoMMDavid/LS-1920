package pt.isel.ls.utils.html.elements;

public class Body extends Element {

    public Body(Element... elements) {
        super("<body>", elements, "</body>");
    }

    public Body(String font, Element... elements) {
        super("<body style=\"font-family: " + font + "\">", elements, "</body>");
    }
}
