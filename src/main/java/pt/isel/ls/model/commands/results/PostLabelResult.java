package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.PostResult;
import pt.isel.ls.model.entities.Label;

public class PostLabelResult implements CommandResult, PostResult {

    private boolean hasResult = false;
    private Label label;

    public void setLabel(Label label) {
        this.label = label;
        hasResult = true;
    }

    public Label getLabel() {
        return label;
    }

    @Override
    public boolean hasResults() {
        return hasResult;
    }

    @Override
    public CommandResult.ResultType getResultType() {
        return CommandResult.ResultType.PostLabel;
    }

    @Override
    public String getCreatedId() {
        return "" + label.getLid();
    }
}
