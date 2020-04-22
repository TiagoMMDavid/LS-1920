package pt.isel.ls.utils.html.elements;

public class Html extends Element {

    public Html(Element... elements) {
        super("<!DOCTYPE html>\n<html>", elements, "</html>");
    }
}
