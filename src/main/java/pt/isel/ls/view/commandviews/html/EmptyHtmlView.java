package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.utils.html.elements.Element;

import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class EmptyHtmlView extends HtmlView {

    public EmptyHtmlView() {
        super();
    }

    @Override
    public String display() {
        Element html =
                html(
                        head(
                                title("No results")
                        ),
                        body(
                                h1(true, "No results found.")
                        )
                );
        return html.toString();
    }
}
