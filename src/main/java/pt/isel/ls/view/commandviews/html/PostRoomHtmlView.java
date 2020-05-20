package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.PostRoomResult;
import pt.isel.ls.model.entities.Room;
import pt.isel.ls.view.View;

import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class PostRoomHtmlView extends View {
    private Room room;

    public PostRoomHtmlView(CommandResult commandResult) {
        this.room = ((PostRoomResult) commandResult).getRoom();
    }

    @Override
    public String display() {
        return
                html(
                        head(
                                title("Post Room")
                        ),
                        body(
                                h1("Created Room \"" + room.getName() + "\" with ID " + room.getRid())
                        )
                ).toString();
    }
}
