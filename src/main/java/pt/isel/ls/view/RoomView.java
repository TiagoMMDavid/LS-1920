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

            if (!room.isPost()) {
                appendName(room, builder);
                if (room.isDetailed()) {
                    appendDescription(room, builder);
                    appendLocation(room, builder);
                    appendCapacity(room, builder);
                }
            }

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
        Element table = table();
        table.addChild(tableRow);
        Room room = (Room) entity;
        tableRow.addChild(th("RID"));

        if (!room.isPost()) {
            tableRow.addChild(th("Name"));
            if (room.isDetailed()) {
                tableRow.addChild(th("Description"));
                tableRow.addChild(th("Location"));
                tableRow.addChild(th("Capacity"));
            }
        }

        for (Entity entity : entities) {
            addHtmlTableRow(table, (Room) entity);
        }

        return table;
    }

    private void addHtmlTableRow(Element table, Room room) {
        Element tableRowData = tr();
        tableRowData.addChild(td(room.getRid()));

        if (!room.isPost()) {
            tableRowData.addChild(td(room.getName()));
            if (room.isDetailed()) {
                tableRowData.addChild(td(room.getDescription() == null ? "N/A" : room.getDescription()));
                tableRowData.addChild(td(room.getLocation() == null ? "N/A" : room.getLocation()));
                tableRowData.addChild(td(room.getCapacity() < 0 ? "N/A" : room.getCapacity()));
            }
        }
        table.addChild(tableRowData);
    }

    private void appendId(Room room, StringBuilder builder) {
        builder.append("Room ID: ");
        builder.append(room.getRid());
    }

    public void appendName(Room room, StringBuilder builder) {
        String name = room.getName();
        builder.append("\nName: ");
        builder.append(name);
    }

    public void appendDescription(Room room, StringBuilder builder) {
        String desc = room.getDescription();
        builder.append("\nDescription: ");
        builder.append(desc == null ? "N/A" : desc);
    }

    public void appendLocation(Room room, StringBuilder builder) {
        String location = room.getLocation();
        builder.append("\nLocation: ");
        builder.append(location == null ? "N/A" : location);
    }

    public void appendCapacity(Room room, StringBuilder builder) {
        int capacity = room.getCapacity();
        builder.append("\nCapacity: ");
        builder.append(capacity < 0 ? "N/A" : capacity);
    }
}
