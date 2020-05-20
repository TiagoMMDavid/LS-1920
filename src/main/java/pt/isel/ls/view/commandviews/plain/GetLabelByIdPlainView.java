package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetLabelByIdResult;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.view.View;

import static pt.isel.ls.view.commandviews.helpers.LabelHelper.appendId;
import static pt.isel.ls.view.commandviews.helpers.LabelHelper.appendName;
import static pt.isel.ls.view.commandviews.helpers.LabelHelper.appendRooms;

public class GetLabelByIdPlainView extends View {
    private GetLabelByIdResult result;

    public GetLabelByIdPlainView(CommandResult commandResult) {
        this.result = (GetLabelByIdResult) commandResult;
    }

    @Override
    public String display() {
        StringBuilder builder = new StringBuilder();
        Label label = result.getLabel();
        appendId(label, builder);
        builder.append('\n');
        appendName(label, builder);
        builder.append('\n');
        appendRooms(result, builder);
        return builder.toString();
    }
}
