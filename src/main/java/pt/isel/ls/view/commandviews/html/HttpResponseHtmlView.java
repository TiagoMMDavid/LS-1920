package pt.isel.ls.view.commandviews.html;

import org.eclipse.jetty.http.HttpStatus;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.HttpResponseResult;

import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class HttpResponseHtmlView extends HtmlView {

    private int statusCode;

    public HttpResponseHtmlView(CommandResult commandResult) {
        this.statusCode = ((HttpResponseResult) commandResult).getStatus();
    }

    @Override
    public String display() {
        return
                html(
                        head(
                                title("Error " + statusCode)
                        ),
                        body(HTML_DEFAULT_FONT,
                                h1(true, "Error " + statusCode + " : " + HttpStatus.getMessage(statusCode))
                        )
                ).toString();
    }
}
