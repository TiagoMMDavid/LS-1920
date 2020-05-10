package pt.isel.ls.view.commandviews;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.OptionResult;
import pt.isel.ls.model.entities.Command;
import pt.isel.ls.utils.html.elements.Element;
import pt.isel.ls.view.View;

import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.h2;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.title;
import static pt.isel.ls.utils.html.HtmlDsl.p;

public class OptionView extends View {

    public OptionView(CommandResult result) {
        super(result);
    }

    @Override
    public String displayText() {
        StringBuilder builder = new StringBuilder();
        for (Command cmd : ((OptionResult) commandResult).getCommands()) {
            builder.append(cmd.getName());
            builder.append(" - ");
            builder.append(cmd.getDescription());
            builder.append('\n');
        }
        return builder.toString();
    }

    @Override
    public String displayHtml() {
        Element html =
                html(
                        head(
                                title("Commands")
                        ),
                        buildHtmlBody()
                );
        return html.toString();
    }

    private Element buildHtmlBody() {
        Element body = body(
                h1("List of Commands:")
        );
        for (Command cmd : ((OptionResult) commandResult).getCommands()) {
            body.addChild(h2(cmd.getName()));
            body.addChild(p(cmd.getDescription().replace("\n", "<br>")));
        }
        return body;
    }
}
