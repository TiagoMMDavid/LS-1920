package pt.isel.ls.utils.html.elements;

public class Label extends ElementText {

    public Label(String forId, String text) {
        super("<label for=\"" + forId + "\">", text, "</label>");
    }
}
