package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetLabelsResult;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.utils.html.elements.Element;

import static pt.isel.ls.utils.html.HtmlDsl.a;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.table;
import static pt.isel.ls.utils.html.HtmlDsl.td;
import static pt.isel.ls.utils.html.HtmlDsl.th;
import static pt.isel.ls.utils.html.HtmlDsl.title;
import static pt.isel.ls.utils.html.HtmlDsl.tr;

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
                                a("/", "Home"),
                                h1("Information of all Labels"),
                                buildLabelInfo()
                        )
                );
        return html.toString();
    }

    private Element buildLabelInfo() {
        Element labelInfo = table();
        Element tableRow = tr();
        tableRow.addChild(th("Label ID"));
        tableRow.addChild(th("Name"));
        labelInfo.addChild(tableRow);
        for (Label label : result.getLabels()) {
            addHtmlTableRow(labelInfo, label);
        }
        return labelInfo;
    }

    private void addHtmlTableRow(Element table, Label label) {
        Element tableRowData = tr();
        tableRowData.addChild(td(a("/labels/" + label.getLid(), "" + label.getLid())));
        tableRowData.addChild(td(label.getName()));
        table.addChild(tableRowData);
    }
}
