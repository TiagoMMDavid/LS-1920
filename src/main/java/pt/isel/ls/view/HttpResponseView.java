package pt.isel.ls.view;

import org.eclipse.jetty.http.HttpStatus;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.HttpResponseResult;

import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class HttpResponseView extends View {
    private int statusCode;

    public HttpResponseView(CommandResult commandResult) {
        this.statusCode = ((HttpResponseResult) commandResult).getStatus();
    }


    // TODO: TO BE REMOVED WITH ROUTER IMPLEMENTATION
    @Override
    public String displayText() {
        return "No representation available";
    }


    @Override
    public String displayHtml() {
        return
                html(
                        head(
                                title("Error " + statusCode)
                        ),
                        body(
                                h1("Error " + statusCode + " : " + HttpStatus.getMessage(statusCode))
                        )
                ).toString();
    }
}
