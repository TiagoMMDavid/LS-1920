package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.ValidatedResult;

public class GetUsersCreateResult extends ValidatedResult implements CommandResult {

    private String previousName = "";
    private String previousEmail = "";

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
