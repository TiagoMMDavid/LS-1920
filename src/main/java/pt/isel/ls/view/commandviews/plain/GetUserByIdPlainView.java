package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetUserByIdResult;
import pt.isel.ls.model.entities.User;

import static pt.isel.ls.view.commandviews.helpers.UserHelpers.appendBookings;
import static pt.isel.ls.view.commandviews.helpers.UserHelpers.appendEmail;
import static pt.isel.ls.view.commandviews.helpers.UserHelpers.appendId;
import static pt.isel.ls.view.commandviews.helpers.UserHelpers.appendName;

public class GetUserByIdPlainView extends PlainView {

    private GetUserByIdResult result;

    public GetUserByIdPlainView(CommandResult commandResult) {
        result = (GetUserByIdResult) commandResult;
    }

    @Override
    public String display() {
        StringBuilder builder = new StringBuilder();
        User user = result.getUser();

        appendId(user, builder);
        builder.append('\n');
        appendName(user, builder);
        builder.append('\n');
        appendEmail(user, builder);
        builder.append('\n');
        appendBookings(result.getBookings(), builder);

        return builder.toString();
    }
}
