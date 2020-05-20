package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetLabelByIdResult;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.model.entities.Room;
import pt.isel.ls.utils.html.elements.Element;

import static pt.isel.ls.utils.html.HtmlDsl.a;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.li;
import static pt.isel.ls.utils.html.HtmlDsl.p;
import static pt.isel.ls.utils.html.HtmlDsl.table;
import static pt.isel.ls.utils.html.HtmlDsl.td;
import static pt.isel.ls.utils.html.HtmlDsl.th;
import static pt.isel.ls.utils.html.HtmlDsl.title;
import static pt.isel.ls.utils.html.HtmlDsl.tr;
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
                body(
                        a("/", "Home"), a("/labels", "Labels"),
                        h1("Detailed Information for Label [" + label.getLid() + "]"),
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
        labelInfo.addChild(li("Label Name: " + label.getName()));
        return labelInfo;
    }

    private Element buildRoomsWithLabelTable(Iterable<Room> rooms) {
        Element table = table();
        table.addChild(th("Room ID"));
        table.addChild(th("Room name"));
        for (Room room: rooms) {
            Element tableRowData = tr();
            tableRowData.addChild(td(a("/rooms/" + room.getRid(), "" + room.getRid())));
            tableRowData.addChild(td(room.getName()));
            table.addChild(tableRowData);
        }
        return table;
    }
}
