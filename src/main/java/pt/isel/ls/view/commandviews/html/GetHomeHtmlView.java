package pt.isel.ls.view.commandviews.html;

import static pt.isel.ls.utils.html.HtmlDsl.a;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.br;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.hr;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class GetHomeHtmlView extends HtmlView {

    @Override
    public String display() {
        return
                html(
                        head(
                                title("Home")
                        ),
                        body(
                                h1(true, "Welcome to the homepage of Group 8's Project!"),
                                hr(),
                                a("/time", "View Current Time"),
                                br(), br(),
                                a("/users", "View Existing Users"),
                                br(), br(),
                                a("/rooms/search", "Search for Rooms"),
                                br(), br(),
                                a("/labels", "View Existing Labels")
                        )
                ).toString();
    }
}
