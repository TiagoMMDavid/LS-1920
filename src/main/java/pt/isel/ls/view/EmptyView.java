package pt.isel.ls.view;

import pt.isel.ls.utils.html.elements.Element;

import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class EmptyView extends View {

    protected EmptyView() {
        super();
    }

    @Override
    public String displayText() {
        return "No results found\n";
    }

    @Override
    public String displayHtml() {
        Element html =
                html(
                        head(
                                title("No results")
                        ),
                        body(
                                h1("No results found.")
                        )
                );
        return html.toString();
    }
}
