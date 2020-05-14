package pt.isel.ls.utils.html.elements;

public class Anchor extends ElementText {

    public Anchor(String href, String text) {
        super("<a href=" + href + ">", text, "</a>");
    }
}
