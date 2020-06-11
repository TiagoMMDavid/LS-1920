package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.OptionResult;
import pt.isel.ls.model.entities.Command;
import pt.isel.ls.utils.html.HtmlTableBuilder;
import pt.isel.ls.utils.html.elements.Element;

import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class OptionHtmlView extends HtmlView {
    private OptionResult result;

    public OptionHtmlView(CommandResult commandResult) {
        this.result = (OptionResult) commandResult;
    }


    @Override
    public String display() {
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
        Element body = body(HTML_DEFAULT_FONT,
                h1(true, "List of Commands:")
        );

        Element table = new HtmlTableBuilder<>(result.getCommands())
                .withColumn("Command Name", Command::getName)
                .withColumn("Description", cmd -> cmd.getDescription().replace("\n", "<br>"))
                .build();

        return body.addChild(table);
    }
}
