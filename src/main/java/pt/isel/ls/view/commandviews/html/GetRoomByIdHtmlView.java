package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetRoomByIdResult;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.model.entities.Room;
import pt.isel.ls.utils.html.HtmlTableBuilder;
import pt.isel.ls.utils.html.elements.Element;

import static pt.isel.ls.utils.html.HtmlDsl.a;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.li;
import static pt.isel.ls.utils.html.HtmlDsl.p;
import static pt.isel.ls.utils.html.HtmlDsl.title;
import static pt.isel.ls.utils.html.HtmlDsl.ul;

public class GetRoomByIdHtmlView extends HtmlView {

    private GetRoomByIdResult result;

    public GetRoomByIdHtmlView(CommandResult commandResult) {
        this.result = (GetRoomByIdResult) commandResult;
    }

    @Override
    public String display() {
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
                        a("/", "Home"), a("/rooms", "Rooms"),
                        h1("Detailed Information of Room \"" + room.getName() + "\""),
                        buildHtmlRoomInfo()
                );

        if (labels != null && labels.iterator().hasNext()) {
            body.addChild(buildHtmlLabelTable());
        } else {
            body.addChild(p("No labels associated with this room"));
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

        Element bookingsListEntry = result.hasBookings()
                ? li(a("/rooms/" + room.getRid() + "/bookings", "View bookings"))
                : li("No bookings available");

        roomInfo.addChild(bookingsListEntry);
        return roomInfo;
    }

    private Element buildHtmlLabelTable() {
        return new HtmlTableBuilder<>(result.getLabels())
                .withColumn("Label ID",
                    label -> a("/labels/" + label.getLid(), "" + label.getLid()))
                .withColumn("Name", Label::getName)
                .build();
    }
}
