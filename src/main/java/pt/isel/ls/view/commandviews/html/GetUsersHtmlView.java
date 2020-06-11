package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetUsersResult;
import pt.isel.ls.model.entities.User;
import pt.isel.ls.utils.html.HtmlTableBuilder;
import pt.isel.ls.utils.html.elements.Element;

import static pt.isel.ls.utils.html.HtmlDsl.a;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.p;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class GetUsersHtmlView extends HtmlView {

    private GetUsersResult result;

    public GetUsersHtmlView(CommandResult commandResult) {
        result = (GetUsersResult) commandResult;
    }

    @Override
    public String display() {
        Iterable<User> users = result.getUsers();
        Element html =
                html(
                        head(
                                title("Information of all Users")
                        ),
                        body(HTML_DEFAULT_FONT,
                                a("/", "Home"), a("/users/create", "Create a User"),
                                h1("Information of all Users"),
                                buildUsersTable(users)
                        )
                );
        return html.toString();
    }

    private Element buildUsersTable(Iterable<User> users) {
        if (!users.iterator().hasNext()) {
            return p("No Users found in database.");
        }

        return new HtmlTableBuilder<>(users)
            .withColumn("User ID", user -> a("/users/" + user.getUid(), String.valueOf(user.getUid())))
            .withColumn("Name", User::getName)
            .withColumn("Email", User::getEmail)
            .build();
    }
}
