package pt.isel.ls.view.commandviews.html;

import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class NoRouteHtmlView extends HtmlView {

    public NoRouteHtmlView() {
        this.foundRoute = false;
    }

    @Override
    public String display() {
        return
                html(
                        head(
                                title("No representation")
                        ),
                        body(HTML_DEFAULT_FONT,
                                h1(true, "Error: No html representation available!")
                        )
                ).toString();
    }
}
