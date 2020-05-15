package pt.isel.ls.utils.html.elements;

public class Form extends Element {

    public Form(String method, String path, Element... elements) {
        super("<form method=\"" + method + "\" action=\"" + path + "\">",
                elements,
                "</form>");
    }
}
