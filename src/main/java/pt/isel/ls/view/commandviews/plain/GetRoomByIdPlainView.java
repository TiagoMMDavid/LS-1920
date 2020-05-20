package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetRoomByIdResult;
import pt.isel.ls.model.entities.Room;

import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendCapacity;
import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendDescription;
import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendId;
import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendLabels;
import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendLocation;
import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendName;

public class GetRoomByIdPlainView extends PlainView {

    private GetRoomByIdResult result;

    public GetRoomByIdPlainView(CommandResult commandResult) {
        this.result = (GetRoomByIdResult) commandResult;
    }

    @Override
    public String display() {
        StringBuilder builder = new StringBuilder();
        Room room = result.getRoom();
        appendId(room, builder);
        builder.append('\n');
        appendName(room, builder);
        builder.append('\n');
        appendLocation(room, builder);
        builder.append('\n');
        appendCapacity(room, builder);
        builder.append('\n');
        appendDescription(room, builder);
        builder.append('\n');
        appendLabels(result, builder);;
        return builder.toString();
    }
}
