package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.PostLabelResult;
import pt.isel.ls.model.entities.Label;

import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class PostLabelHtmlView extends HtmlView {
    private Label label;

    public PostLabelHtmlView(CommandResult commandResult) {
        this.label = ((PostLabelResult) commandResult).getLabel();
    }

    @Override
    public String display() {
        return
                html(
                        head(
                                title("Post Label")
                        ),
                        body(
                                h1(true, "Created Label \"" + label.getName() + "\" with ID " + label.getLid())
                        )
                ).toString();
    }
}
