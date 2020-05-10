package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.entities.User;

public class PostUserResult implements CommandResult {

    private boolean hasResult = false;
    private User user;

    public void setUser(User user) {
        this.user = user;
        hasResult = true;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean hasResults() {
        return hasResult;
    }

    @Override
    public CommandResult.ResultType getResultType() {
        return ResultType.PostUser;
    }
}
