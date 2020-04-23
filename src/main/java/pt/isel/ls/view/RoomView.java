package pt.isel.ls.view;

import pt.isel.ls.model.entities.Entity;
import pt.isel.ls.model.entities.Room;
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

public class RoomView extends View {

    protected RoomView(Iterable<Entity> entity) {
        super(entity);
    }

    @Override
    public String displayText() {
        StringBuilder builder = new StringBuilder();

        for (Entity entity : entities) {
            Room room = (Room) entity;
            appendId(room, builder);
            appendName(room, builder);
            appendDescription(room, builder);
            appendLocation(room, builder);
            appendCapacity(room, builder);
            builder.append("\n\n");
        }

        return builder.toString();
    }

    @Override
    public String displayHtml() {
        Element html =
                html(
                        head(
                                title("Rooms")
                        ),
                        body(
                                h1("List of Rooms:"),
                                buildHtmlTable()
                        )
                );
        return html.toString();
    }

    private Element buildHtmlTable() {
        Element tableRow = tr();
        tableRow.addChild(th("RID"));
        tableRow.addChild(th("Name"));
        tableRow.addChild(th("Description"));
        tableRow.addChild(th("Location"));
        tableRow.addChild(th("Capacity"));

        Element table = table();
        table.addChild(tableRow);
        for (Entity entity : entities) {
            addHtmlTableRow(table, (Room) entity);
        }
        return table;
    }

    private void addHtmlTableRow(Element table, Room room) {
        Element tableRowData = tr();
        tableRowData.addChild(td(room.getRid()));
        tableRowData.addChild(td(room.getName() == null ? "N/A" : room.getName()));
        tableRowData.addChild(td(room.getDescription() == null ? "N/A" : room.getDescription()));
        tableRowData.addChild(td(room.getLocation() == null ? "N/A" : room.getLocation()));
        if (room.getCapacity() <= 0) {
            tableRowData.addChild(td("N/A"));
        } else {
            tableRowData.addChild(td(room.getCapacity()));
        }
        table.addChild(tableRowData);
    }

    private void appendId(Room room, StringBuilder builder) {
        builder.append("Room ID: ");
        builder.append(room.getRid());
    }

    public void appendName(Room room, StringBuilder builder) {
        String name = room.getName();
        if (name != null) {
            builder.append("\nName: ");
            builder.append(name);
        }
    }

    public void appendDescription(Room room, StringBuilder builder) {
        String desc = room.getDescription();
        if (desc != null) {
            builder.append("\nDescription: ");
            builder.append(desc);
        }
    }

    public void appendLocation(Room room, StringBuilder builder) {
        String location = room.getLocation();
        if (location != null) {
            builder.append("\nLocation: ");
            builder.append(location);
        }
    }

    public void appendCapacity(Room room, StringBuilder builder) {
        int capacity = room.getCapacity();
        if (capacity > 0) {
            builder.append("\nCapacity: ");
            builder.append(capacity);
        }
    }
}
