package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.PostLabelResult;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.view.View;

public class PostLabelPlainView extends View {
    private Label label;

    public PostLabelPlainView(CommandResult commandResult) {
        this.label = ((PostLabelResult) commandResult).getLabel();
    }

    @Override
    public String display() {
        return "Created Label \"" + label.getName() + "\" with ID " + label.getLid();
    }

}
