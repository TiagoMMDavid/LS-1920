package pt.isel.ls.utils.html.elements;

public class TableHeader extends ElementText {
    public TableHeader(String header) {
        super("<th>", header, "</th>");
    }
}
