package pt.isel.ls.view.commandviews;

import pt.isel.ls.view.View;

import static pt.isel.ls.utils.html.HtmlDsl.a;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.br;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class GetHomeView extends View {

    @Override
    public String displayText() {
        return "Home";
    }

    @Override
    public String displayHtml() {
        return
                html(
                        head(
                                title("Home")
                        ),
                        body(
                                h1("Welcome to the homepage of Group 8's Project!"),
                                a("/time", "View Current Time"),
                                br(),
                                a("/users", "View Existing Users"),
                                br(),
                                a("/rooms/search", "Search for Rooms"),
                                br(),
                                a("/labels", "View Existing Labels")
                        )
                ).toString();
    }
}
