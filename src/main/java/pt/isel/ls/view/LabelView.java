package pt.isel.ls.view;

import pt.isel.ls.model.entities.Entity;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.utils.html.elements.Element;

import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.table;
import static pt.isel.ls.utils.html.HtmlDsl.td;
import static pt.isel.ls.utils.html.HtmlDsl.th;
import static pt.isel.ls.utils.html.HtmlDsl.tr;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class LabelView extends View {
    protected LabelView(Iterable<Entity> entity) {
        super(entity);
    }


    @Override
    public String displayText() {
        StringBuilder builder = new StringBuilder();
        for (Entity entity : entities) {
            Label label = (Label) entity;
            appendId(label, builder);
            appendName(label, builder);
            builder.append("\n\n");
        }
        return builder.toString();
    }

    @Override
    public String displayHtml() {
        Element html =
                html(
                        head(
                                title("Labels")
                        ),
                        body(
                                h1("List of Labels:"),
                                buildHtmlTable()
                        )
                );
        return html.toString();
    }

    private Element buildHtmlTable() {
        Element table = table();
        Element tableRow = tr();
        tableRow.addChild(th("LID"));
        tableRow.addChild(th("Name"));
        table.addChild(tableRow);

        for (Entity entity : entities) {
            addHtmlTableRow(table, (Label) entity);
        }
        return table;
    }

    private void addHtmlTableRow(Element table, Label label) {
        Element tableRowData = tr();
        tableRowData.addChild(td(label.getLid()));
        tableRowData.addChild(td(label.getName() == null ? "N/A" : label.getName()));
        table.addChild(tableRowData);
    }

    private void appendName(Label label, StringBuilder builder) {
        String name = label.getName();
        if (name != null) {
            builder.append("\nName: ");
            builder.append(name);
        }
    }

    private void appendId(Label label, StringBuilder builder) {
        builder.append("Label ID: ");
        builder.append(label.getLid());
    }
}
