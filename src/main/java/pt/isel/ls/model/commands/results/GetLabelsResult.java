package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.entities.Label;

import java.util.LinkedList;

public class GetLabelsResult implements CommandResult {

    private boolean hasResult = false;
    private LinkedList<Label> labels;

    public void addLabel(Label label) {
        if (labels == null) {
            labels = new LinkedList<>();
            hasResult = true;
        }
        labels.add(label);
    }

    public Iterable<Label> getLabels() {
        return labels;
    }

    @Override
    public boolean hasResults() {
        return hasResult;
    }

    @Override
    public CommandResult.ResultType getResultType() {
        return ResultType.GetLabels;
    }
}
