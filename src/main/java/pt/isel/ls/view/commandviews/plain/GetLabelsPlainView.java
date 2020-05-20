package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetLabelsResult;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.view.View;

import java.util.Iterator;

import static pt.isel.ls.view.commandviews.helpers.LabelHelper.appendId;
import static pt.isel.ls.view.commandviews.helpers.LabelHelper.appendName;

public class GetLabelsPlainView extends View {

    private GetLabelsResult result;

    public GetLabelsPlainView(CommandResult commandResult) {
        this.result = (GetLabelsResult) commandResult;
    }

    @Override
    public String display() {
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
}
