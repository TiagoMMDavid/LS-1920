package pt.isel.ls.view.commandviews;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetLabelByIdResult;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.utils.html.elements.Element;
import pt.isel.ls.view.View;

import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.li;
import static pt.isel.ls.utils.html.HtmlDsl.title;
import static pt.isel.ls.utils.html.HtmlDsl.ul;
import static pt.isel.ls.view.commandviews.helpers.LabelHelper.appendId;
import static pt.isel.ls.view.commandviews.helpers.LabelHelper.appendName;
import static pt.isel.ls.view.commandviews.helpers.LabelHelper.appendRooms;
import static pt.isel.ls.view.commandviews.helpers.LabelHelper.appendRoomsWithCommas;

public class GetLabelByIdView extends View {
    private GetLabelByIdResult result;

    public GetLabelByIdView(CommandResult commandResult) {
        this.result = (GetLabelByIdResult) commandResult;
    }

    @Override
    public String displayText() {
        StringBuilder builder = new StringBuilder();
        Label label = result.getLabel();
        appendId(label, builder);
        builder.append('\n');
        appendName(label, builder);
        builder.append('\n');
        appendRooms(result, builder);
        return builder.toString();
    }

    @Override
    public String displayHtml() {
        Label label = result.getLabel();
        return
                html(
                        head(
                                title("Label [" + label.getLid() + "]")
                        ),
                        body(
                                h1("Detailed Information for Label:"),
                                buildLabelInfo(label)
                        )
                ).toString();
    }

    private Element buildLabelInfo(Label label) {
        Element labelInfo = ul();
        labelInfo.addChild(li("Label ID: " + label.getLid()));
        labelInfo.addChild(li("Label Name: " + label.getName()));
        labelInfo.addChild(li("Rooms with Label: " + appendRoomsWithCommas(result, new StringBuilder()).toString()));
        // TODO: CREATE TABLE WITH ROOM INFO
        return labelInfo;
    }
}
