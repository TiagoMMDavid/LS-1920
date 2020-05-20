package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.ListenResult;
import pt.isel.ls.utils.html.elements.Element;
import pt.isel.ls.view.View;

import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class ListenPlainView extends View {

    private ListenResult result;

    public ListenPlainView(CommandResult commandResult) {
        result = (ListenResult) commandResult;
    }

    @Override
    public String displayText() {
        return "Servlet now listening on Port: " + result.getPort();
    }

    @Override
    public String displayHtml() {
        Element html =
                html(
                        head(
                                title("Listening on Port: " + result.getPort())
                        ),
                        body(
                                h1("Servlet now listening on Port: " + result.getPort())
                        )
                );
        return html.toString();
    }

}
