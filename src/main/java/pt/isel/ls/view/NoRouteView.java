package pt.isel.ls.view;

import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class NoRouteView extends View {
    @Override
    public String displayText() {
        return "Resource not found\n";
    }

    @Override
    public String displayHtml() {
        return
                html(
                        head(
                                title("No resource found")
                        ),
                        body(
                                h1("The requested resource was not found.")
                        )
                ).toString() + '\n';
    }
}
