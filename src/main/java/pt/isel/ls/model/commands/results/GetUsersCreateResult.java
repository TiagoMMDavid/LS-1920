package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;

public class GetUsersCreateResult implements CommandResult {

    private boolean wasError = false;
    private String previousName = "";
    private String previousEmail = "";

    public void setError() {
        wasError = true;
    }

    public void setPreviousName(String previousName) {
        if (previousName != null) {
            this.previousName = previousName;
        }
    }

    public void setPreviousEmail(String previousEmail) {
        if (previousEmail != null) {
            this.previousEmail = previousEmail;
        }
    }

    public boolean wasError() {
        return wasError;
    }

    public String getPreviousName() {
        return previousName;
    }

    public String getPreviousEmail() {
        return previousEmail;
    }

    @Override
    public boolean hasResults() {
        return true;
    }

    @Override
    public ResultType getResultType() {
        return ResultType.GetUsersCreate;
    }
}
