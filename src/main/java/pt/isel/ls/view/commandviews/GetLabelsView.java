package pt.isel.ls.view.commandviews;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetLabelsResult;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.utils.html.elements.Element;
import pt.isel.ls.view.View;

import java.util.Iterator;

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
import static pt.isel.ls.view.commandviews.helpers.LabelHelper.appendId;
import static pt.isel.ls.view.commandviews.helpers.LabelHelper.appendName;

public class GetLabelsView extends View {

    private GetLabelsResult result;

    public GetLabelsView(CommandResult commandResult) {
        this.result = (GetLabelsResult) commandResult;
    }

    @Override
    public String displayText() {
        StringBuilder builder = new StringBuilder();
        Iterator<Label> iter = result.getLabels().iterator();
        while (iter.hasNext()) {
            Label label = iter.next();
            appendId(label, builder);
            builder.append('\n');
            appendName(label, builder);
            builder.append('\n');
            if (iter.hasNext()) {
                builder.append("============================================\n");
            }
        }
        return builder.toString();
    }

    @Override
    public String displayHtml() {
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
