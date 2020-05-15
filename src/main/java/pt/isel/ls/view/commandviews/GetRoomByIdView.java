package pt.isel.ls.view.commandviews;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetRoomByIdResult;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.model.entities.Room;
import pt.isel.ls.utils.html.elements.Element;
import pt.isel.ls.view.View;

import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.li;
import static pt.isel.ls.utils.html.HtmlDsl.table;
import static pt.isel.ls.utils.html.HtmlDsl.td;
import static pt.isel.ls.utils.html.HtmlDsl.th;
import static pt.isel.ls.utils.html.HtmlDsl.title;
import static pt.isel.ls.utils.html.HtmlDsl.tr;
import static pt.isel.ls.utils.html.HtmlDsl.ul;
import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendCapacity;
import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendDescription;
import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendId;
import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendLabels;
import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendLocation;
import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendName;

public class GetRoomByIdView extends View {

    private GetRoomByIdResult result;

    public GetRoomByIdView(CommandResult commandResult) {
        this.result = (GetRoomByIdResult) commandResult;
    }

    @Override
    public String displayText() {
        StringBuilder builder = new StringBuilder();
        Room room = result.getRoom();
        appendId(room, builder);
        builder.append('\n');
        appendName(room, builder);
        builder.append('\n');
        appendLocation(room, builder);
        builder.append('\n');
        appendCapacity(room, builder);
        builder.append('\n');
        appendDescription(room, builder);
        builder.append('\n');
        appendLabels(result, builder);
        builder.append('\n');
        return builder.toString();
    }

    @Override
    public String displayHtml() {
        Room room = result.getRoom();
        Element html =
                html(
                        head(
                                title("Room [" + room.getRid() + "]")
                        ),
                        buildBody(room, result.getLabels())
                );
        return html.toString();
    }

    private Element buildBody(Room room, Iterable<Label> labels) {
        Element body =
                body(
                        h1("Detailed Information of Room \"" + room.getName() + "\""),
                        buildHtmlRoomInfo()
                );

        if (labels != null && labels.iterator().hasNext()) {
            body.addChild(buildHtmlLabelTable());
        }

        return body;
    }

    public Element buildHtmlRoomInfo() {
        Element roomInfo = ul();
        Room room = result.getRoom();
        roomInfo.addChild(li("Room ID: " + room.getRid()));
        roomInfo.addChild(li("Name: " + room.getName()));
        roomInfo.addChild(li("Location: " + room.getLocation()));
        roomInfo.addChild(li("Capacity: "
                + (room.getCapacity() == null ? "N/A" : room.getCapacity())));
        roomInfo.addChild(li("Description: "
                + (room.getDescription() == null ? "N/A" : room.getDescription())));
        return roomInfo;
    }

    private Element buildHtmlLabelTable() {
        Element labelInfo = table();
        Element tableRow = tr();
        tableRow.addChild(th("Label ID"));
        tableRow.addChild(th("Name"));
        labelInfo.addChild(tableRow);
        for (Label label : result.getLabels()) {
            addHtmlTableRow(labelInfo, label);
        }
        return labelInfo;
    }

    private void addHtmlTableRow(Element table, Label label) {
        Element tableRowData = tr();
        tableRowData.addChild(td(label.getLid()));
        tableRowData.addChild(td(label.getName()));
        table.addChild(tableRowData);
    }
}
