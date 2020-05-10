package pt.isel.ls.view.commandviews;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.view.View;

public class GetLabelsView extends View {

    public GetLabelsView(CommandResult commandResult) {
        super(commandResult);
    }

    @Override
    public String displayText() {
        return null;
    }

    @Override
    public String displayHtml() {
        return null;
    }
}
