package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.ValidatedResult;

public class GetLabelsCreateResult extends ValidatedResult implements CommandResult {

    private String previousName = "";

    public void setPreviousName(String previousName) {
        if (previousName != null) {
            this.previousName = previousName;
        }
    }

    public String getPreviousName() {
        return previousName;
    }

    @Override
    public boolean hasResults() {
        return true;
    }

    @Override
    public ResultType getResultType() {
        return ResultType.GetLabelsCreate;
    }
}
