package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.PostUserResult;
import pt.isel.ls.model.entities.User;
import pt.isel.ls.view.View;

import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class PostUserPlainView extends View {
    private User user;

    public PostUserPlainView(CommandResult commandResult) {
        this.user = ((PostUserResult) commandResult).getUser();
    }

    @Override
    public String displayText() {
        return "Created User \"" + user.getName() + "\" with ID " + user.getUid();
    }

    @Override
    public String displayHtml() {
        return
                html(
                        head(
                                title("Post User")
                        ),
                        body(
                                h1("Created User \"" + user.getName() + "\" with ID " + user.getUid())
                        )
                ).toString();
    }
}
