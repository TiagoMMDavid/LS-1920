package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.ListenResult;
import pt.isel.ls.utils.html.elements.Element;

import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class ListenHtmlView extends HtmlView {

    private ListenResult result;

    public ListenHtmlView(CommandResult commandResult) {
        result = (ListenResult) commandResult;
    }

    @Override
    public String display() {
        Element html =
                html(
                        head(
                                title("Listening on Port: " + result.getPort())
                        ),
                        body(
                                h1(true, "Servlet now listening on Port: " + result.getPort())
                        )
                );
        return html.toString();
    }

}
