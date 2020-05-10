package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.entities.User;

import java.util.LinkedList;

public class GetUsersResult implements CommandResult {

    private boolean hasResult = false;
    private LinkedList<User> users;

    public void addUser(User user) {
        if (users == null) {
            users = new LinkedList<>();
            hasResult = true;
        }
        users.add(user);
    }

    public Iterable<User> getUsers() {
        return users;
    }

    @Override
    public boolean hasResults() {
        return hasResult;
    }

    @Override
    public CommandResult.ResultType getResultType() {
        return CommandResult.ResultType.GetUsers;
    }
}
