package pt.isel.ls.view.commandviews.plain;

import org.eclipse.jetty.http.HttpStatus;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.HttpResponseResult;
import pt.isel.ls.view.commandviews.html.HtmlView;

public class HttpResponsePlainView extends HtmlView {

    private int statusCode;

    public HttpResponsePlainView(CommandResult commandResult) {
        this.statusCode = ((HttpResponseResult) commandResult).getStatus();
    }

    @Override
    public String display() {
        return "Error " + statusCode + " : " + HttpStatus.getMessage(statusCode);
    }
}
