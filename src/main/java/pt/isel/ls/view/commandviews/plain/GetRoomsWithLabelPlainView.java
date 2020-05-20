package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetRoomsWithLabelResult;
import pt.isel.ls.model.entities.Room;
import pt.isel.ls.utils.html.elements.Element;
import pt.isel.ls.view.View;

import java.util.Iterator;

import static pt.isel.ls.utils.html.HtmlDsl.a;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.table;
import static pt.isel.ls.utils.html.HtmlDsl.td;
import static pt.isel.ls.utils.html.HtmlDsl.th;
import static pt.isel.ls.utils.html.HtmlDsl.title;
import static pt.isel.ls.utils.html.HtmlDsl.tr;
import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendCapacity;
import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendId;
import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendLocation;
import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendName;

public class GetRoomsWithLabelPlainView extends View {

    private GetRoomsWithLabelResult result;

    public GetRoomsWithLabelPlainView(CommandResult commandResult) {
        this.result = (GetRoomsWithLabelResult) commandResult;
    }

    @Override
    public String displayText() {
        StringBuilder builder = new StringBuilder();
        Iterator<Room> iter = result.getRooms().iterator();
        builder.append("Room with label \"")
                .append(result.getLabel().getName())
                .append("\":")
                .append("\n\n");
        while (iter.hasNext()) {
            Room room = iter.next();
            appendId(room, builder);
            builder.append('\n');
            appendName(room, builder);
            builder.append('\n');
            appendLocation(room, builder);
            builder.append('\n');
            appendCapacity(room, builder);
            builder.append('\n');
            if (iter.hasNext()) {
                builder.append("============================================\n");
            }
        }
        return builder.toString();
    }

    @Override
    public String displayHtml() {
        Element html =
                html(
                        head(
                                title("Rooms with Label [" + result.getLabel().getLid() + "]")
                        ),
                        body(
                                a("/", "Home"),
                                h1("List of Rooms with Label \"" + result.getLabel().getName() + "\""),
                                buildLabelInfo()
                        )
                );
        return html.toString();
    }

    private Element buildLabelInfo() {
        Element tableRow = tr();
        tableRow.addChild(th("Room ID"));
        tableRow.addChild(th("Name"));
        tableRow.addChild(th("Location"));
        tableRow.addChild(th("Capacity"));
        Element roomInfo = table();
        roomInfo.addChild(tableRow);
        for (Room room : result.getRooms()) {
            addHtmlTableRow(roomInfo, room);
        }
        return roomInfo;
    }

    private void addHtmlTableRow(Element table, Room room) {
        Element tableRowData = tr();
        tableRowData.addChild(td(a("/rooms/" + room.getRid(), "" + room.getRid())));
        tableRowData.addChild(td(room.getName()));
        tableRowData.addChild(td(room.getLocation()));
        tableRowData.addChild(td(room.getCapacity() == null ? "N/A" : room.getCapacity().toString()));
        table.addChild(tableRowData);
    }
}
