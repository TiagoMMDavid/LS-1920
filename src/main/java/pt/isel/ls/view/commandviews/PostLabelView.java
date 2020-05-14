package pt.isel.ls.view.commandviews;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.PostLabelResult;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.view.View;

import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class PostLabelView extends View {
    Label label;

    public PostLabelView(CommandResult commandResult) {
        this.label = ((PostLabelResult) commandResult).getLabel();
    }

    @Override
    public String displayText() {
        return "Created Label \"" + label.getName() + "\" with ID " + label.getLid();
    }

    @Override
    public String displayHtml() {
        return
                html(
                        head(
                                title("Post Label")
                        ),
                        body(
                                h1("Created Label \"" + label.getName() + "\" with ID " + label.getLid())
                        )
                ).toString();
    }
}
