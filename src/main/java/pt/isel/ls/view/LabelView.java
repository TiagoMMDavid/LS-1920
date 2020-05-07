package pt.isel.ls.view;

import pt.isel.ls.model.entities.Entity;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.utils.html.elements.Element;

import java.util.Iterator;

import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.li;
import static pt.isel.ls.utils.html.HtmlDsl.table;
import static pt.isel.ls.utils.html.HtmlDsl.td;
import static pt.isel.ls.utils.html.HtmlDsl.th;
import static pt.isel.ls.utils.html.HtmlDsl.tr;
import static pt.isel.ls.utils.html.HtmlDsl.title;
import static pt.isel.ls.utils.html.HtmlDsl.ul;

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
            if (label.hasRooms()) {
                appendRooms(label, builder);
            }
            builder.append("\n\n");
        }
        return builder.toString();
    }

    @Override
    public String displayHtml() {
        Label label = (Label) entity;
        String header = label.hasRooms() ? "Detailed Information for Label" : "List of Labels";
        Element html =
                html(
                        head(
                                title("Labels")
                        ),
                        body(
                                h1(header),
                                buildLabelInfo(label)
                        )
                );
        return html.toString();
    }

    private Element buildLabelInfo(Label label) {
        Element labelInfo;
        if (label.hasRooms()) {
            labelInfo = ul();
            labelInfo.addChild(li("Label ID: " + label.getLid()));
            labelInfo.addChild(li("Label Name: " + label.getName()));
            labelInfo.addChild(li("Rooms with Label: " + appendRoomsWithCommas(label, new StringBuilder()).toString()));
        } else {
            labelInfo = table();
            Element tableRow = tr();
            tableRow.addChild(th("Label ID"));
            tableRow.addChild(th("Name"));
            labelInfo.addChild(tableRow);
            for (Entity entity : entities) {
                addHtmlTableRow(labelInfo, (Label) entity);
            }
        }
        return labelInfo;
    }

    private void addHtmlTableRow(Element table, Label label) {
        Element tableRowData = tr();
        tableRowData.addChild(td(label.getLid()));
        tableRowData.addChild(td(label.getName()));
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

    private void appendRooms(Label label, StringBuilder builder) {
        builder.append("\nRoom IDs: ");
        appendRoomsWithCommas(label, builder);
    }

    private StringBuilder appendRoomsWithCommas(Label label, StringBuilder builder) {
        Iterator<Integer> iter = label.getRids().iterator();
        while (iter.hasNext()) {
            builder.append(iter.next());
            if (iter.hasNext()) {
                builder.append(", ");
            }
        }
        return builder;
    }
}
