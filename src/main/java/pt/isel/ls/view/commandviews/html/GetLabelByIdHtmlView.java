package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetLabelByIdResult;
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

public class GetLabelByIdHtmlView extends HtmlView {
    private GetLabelByIdResult result;

    public GetLabelByIdHtmlView(CommandResult commandResult) {
        this.result = (GetLabelByIdResult) commandResult;
    }

    @Override
    public String display() {
        Label label = result.getLabel();
        Iterable<Room> rooms = result.getRooms();

        return
                html(
                        head(
                                title("Label [" + label.getLid() + "]")
                        ),
                        buildBody(label, rooms)
                ).toString();
    }

    private Element buildBody(Label label, Iterable<Room> rooms) {
        Element body =
                body(HTML_DEFAULT_FONT,
                        a("/", "Home"), a("/labels", "View Existing Labels"),
                        h1("Detailed Information for Label \"" + label.getName() + "\""),
                        buildLabelInfo(label)
                );

        if (rooms != null && rooms.iterator().hasNext()) {
            body.addChild(buildRoomsWithLabelTable(rooms));
        } else {
            body.addChild(p("No rooms associated with this label"));
        }

        return body;
    }

    private Element buildLabelInfo(Label label) {
        Element labelInfo = ul();
        labelInfo.addChild(li("Label ID: " + label.getLid()));
        labelInfo.addChild(li("Name: " + label.getName()));
        return labelInfo;
    }

    private Element buildRoomsWithLabelTable(Iterable<Room> rooms) {
        return new HtmlTableBuilder<>(rooms)
                .withColumn("Room ID",
                    room -> a("/rooms/" + room.getRid(), "" + room.getRid()))
                .withColumn("Name", Room::getName)
                .build();
    }
}
