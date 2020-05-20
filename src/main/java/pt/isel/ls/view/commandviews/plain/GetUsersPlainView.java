package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetUsersResult;
import pt.isel.ls.model.entities.User;

import java.util.Iterator;

import static pt.isel.ls.view.commandviews.helpers.UserHelpers.appendEmail;
import static pt.isel.ls.view.commandviews.helpers.UserHelpers.appendId;
import static pt.isel.ls.view.commandviews.helpers.UserHelpers.appendName;

public class GetUsersPlainView extends PlainView {

    private GetUsersResult result;

    public GetUsersPlainView(CommandResult commandResult) {
        result = (GetUsersResult) commandResult;
    }

    @Override
    public String display() {
        StringBuilder builder = new StringBuilder();
        Iterator<User> iter = result.getUsers().iterator();
        while (iter.hasNext()) {
            User user = iter.next();

            appendId(user, builder);
            builder.append('\n');
            appendName(user, builder);
            builder.append('\n');
            appendEmail(user, builder);

            if (iter.hasNext()) {
                builder.append("\n=============\n");
            }
        }
        return builder.toString();
    }
}
