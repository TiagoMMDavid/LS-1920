package pt.isel.ls.view;

import pt.isel.ls.model.entities.Entity;
import pt.isel.ls.model.entities.Room;
import pt.isel.ls.utils.html.elements.Element;

import java.util.Iterator;

import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.table;
import static pt.isel.ls.utils.html.HtmlDsl.td;
import static pt.isel.ls.utils.html.HtmlDsl.th;
import static pt.isel.ls.utils.html.HtmlDsl.tr;
import static pt.isel.ls.utils.html.HtmlDsl.title;
import static pt.isel.ls.utils.html.HtmlDsl.ul;
import static pt.isel.ls.utils.html.HtmlDsl.li;


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
                appendLocation(room, builder);
                appendCapacity(room, builder);
                if (room.isDetailed()) {
                    appendDescription(room, builder);
                    appendLabels(room, builder);
                }
            }

            builder.append("\n\n");
        }

        return builder.toString();
    }

    @Override
    public String displayHtml() {
        Room room = (Room) entity;
        String header = room.isDetailed() ? "Detailed Information for Room:" : "List of Rooms:";
        Element html =
                html(
                        head(
                                title("Rooms")
                        ),
                        body(
                                h1(header),
                                buildHtmlRoomInfo(room)
                        )
                );
        return html.toString();
    }

    private Element buildHtmlRoomInfo(Room room) {
        Element roomInfo;
        if (room.isDetailed() || room.isPost()) {
            roomInfo = ul();
            roomInfo.addChild(li("Room ID: " + room.getRid()));
            if (room.isDetailed()) {
                roomInfo.addChild(li("Name: " + room.getName()));
                roomInfo.addChild(li("Location: " + room.getLocation()));
                roomInfo.addChild(li("Capacity: "
                        + (room.getCapacity() == null ? "N/A" : room.getCapacity())));
                roomInfo.addChild(li("Description: "
                        + (room.getDescription() == null ? "N/A" : room.getDescription())));
                roomInfo.addChild(li(appendLabelsWithComma(new StringBuilder("Labels: "), room.getLabels())));
            }

        } else {
            Element tableRow = tr();
            roomInfo = table();
            roomInfo.addChild(tableRow);
            tableRow.addChild(th("Room ID"));
            tableRow.addChild(th("Name"));
            tableRow.addChild(th("Location"));
            tableRow.addChild(th("Capacity"));
            for (Entity entity : entities) {
                addHtmlTableRow(roomInfo, (Room) entity);
            }
        }

        return roomInfo;
    }

    private void addHtmlTableRow(Element table, Room room) {
        Element tableRowData = tr();
        tableRowData.addChild(td(room.getRid()));
        tableRowData.addChild(td(room.getName()));
        tableRowData.addChild(td(room.getLocation()));
        tableRowData.addChild(td(room.getCapacity() == null ? "N/A" : room.getCapacity()));
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
        Integer capacity = room.getCapacity();
        builder.append("\nCapacity: ");
        builder.append(capacity == null ? "N/A" : capacity);
    }

    private void appendLabels(Room room, StringBuilder builder) {
        builder.append("\nLabels: ");
        appendLabelsWithComma(builder, room.getLabels());
    }

    private String appendLabelsWithComma(StringBuilder builder, Iterable<String> iter) {
        Iterator<String> labels = iter.iterator();
        int size = 0;
        while (labels.hasNext()) {
            ++size;
            builder.append(labels.next());
            if (labels.hasNext()) {
                builder.append(", ");
            }
        }
        if (size == 0) {
            builder.append("N/A");
        }
        return builder.toString();
    }


}
