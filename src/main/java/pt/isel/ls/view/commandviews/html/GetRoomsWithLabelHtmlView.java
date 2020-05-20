package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetRoomsWithLabelResult;
import pt.isel.ls.model.entities.Room;
import pt.isel.ls.utils.html.elements.Element;

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

public class GetRoomsWithLabelHtmlView extends HtmlView {

    private GetRoomsWithLabelResult result;

    public GetRoomsWithLabelHtmlView(CommandResult commandResult) {
        this.result = (GetRoomsWithLabelResult) commandResult;
    }

    @Override
    public String display() {
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
