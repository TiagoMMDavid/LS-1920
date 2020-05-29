package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.entities.Label;

public class GetRoomsCreateResult implements CommandResult {

    private Iterable<Label> labels;

    public Iterable<Label> getLabels() {
        return labels;
    }

    public void setLabels(Iterable<Label> labels) {
        this.labels = labels;
    }

    @Override
    public boolean hasResults() {
        return true;
    }

    @Override
    public ResultType getResultType() {
        return ResultType.GetRoomsCreate;
    }
}
