package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetLabelsResult;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.utils.html.HtmlTableBuilder;
import pt.isel.ls.utils.html.elements.Element;

import static pt.isel.ls.utils.html.HtmlDsl.a;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class GetLabelsHtmlView extends HtmlView {

    private GetLabelsResult result;

    public GetLabelsHtmlView(CommandResult commandResult) {
        this.result = (GetLabelsResult) commandResult;
    }

    @Override
    public String display() {
        Element html =
                html(
                        head(
                                title("Labels")
                        ),
                        body(
                                a("/", "Home"), a("/labels/create", "Create a Label"),
                                h1("Information of all Labels"),
                                buildLabelInfo()
                        )
                );
        return html.toString();
    }

    private Element buildLabelInfo() {
        return new HtmlTableBuilder<>(result.getLabels())
                .withColumn("Label ID",
                    label -> a("/labels/" + label.getLid(), "" + label.getLid()))
                .withColumn("Name", Label::getName)
                .build();
    }
}
