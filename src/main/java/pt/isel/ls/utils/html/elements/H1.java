package pt.isel.ls.utils.html.elements;

public class H1 extends ElementText {

    public H1(String text) {
        super("<h1>", text, "</h1>");
    }

    public H1(boolean isCentered, String text) {
        super("<h1" + (isCentered ? " style=text-align:center>" : ">"),
                text,
                "</h1>");
    }
}
