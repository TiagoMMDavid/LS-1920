package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.PostUserResult;
import pt.isel.ls.model.entities.User;

import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class PostUserHtmlView extends HtmlView {
    private User user;

    public PostUserHtmlView(CommandResult commandResult) {
        this.user = ((PostUserResult) commandResult).getUser();
    }

    @Override
    public String display() {
        return
                html(
                        head(
                                title("Post User")
                        ),
                        body(
                                h1(true, "Created User \"" + user.getName() + "\" with ID " + user.getUid())
                        )
                ).toString();
    }
}
