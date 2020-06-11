package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetRoomsWithLabelResult;
import pt.isel.ls.model.entities.Room;
import pt.isel.ls.utils.html.HtmlTableBuilder;
import pt.isel.ls.utils.html.elements.Element;

import static pt.isel.ls.utils.html.HtmlDsl.a;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.title;

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
                        body(HTML_DEFAULT_FONT,
                                a("/", "Home"),
                                h1("List of Rooms with Label \"" + result.getLabel().getName() + "\""),
                                buildLabelInfo()
                        )
                );
        return html.toString();
    }

    private Element buildLabelInfo() {
        return new HtmlTableBuilder<>(result.getRooms())
                .withColumn("Room ID",
                    room -> a("/rooms/" + room.getRid(), "" + room.getRid()))
                .withColumn("Name", Room::getName)
                .withColumn("Location", Room::getLocation)
                .withColumn("Capacity",
                    room -> room.getCapacity() == null ? "N/A" : room.getCapacity().toString())
                .build();
    }
}
