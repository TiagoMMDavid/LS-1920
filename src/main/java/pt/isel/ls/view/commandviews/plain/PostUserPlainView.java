package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.PostUserResult;
import pt.isel.ls.model.entities.User;

public class PostUserPlainView extends PlainView {

    private User user;

    public PostUserPlainView(CommandResult commandResult) {
        this.user = ((PostUserResult) commandResult).getUser();
    }

    @Override
    public String display() {
        return "Created User \"" + user.getName() + "\" with ID " + user.getUid();
    }
}
