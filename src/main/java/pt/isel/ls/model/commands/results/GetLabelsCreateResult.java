package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;

public class GetLabelsCreateResult implements CommandResult {

    private boolean wasError = false;
    private String previousName = "";

    public void setError() {
        wasError = true;
    }

    public void setPreviousName(String previousName) {
        if (previousName != null) {
            this.previousName = previousName;
        }
    }

    public boolean wasError() {
        return wasError;
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
