package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetRoomsResult;
import pt.isel.ls.model.entities.Room;

import java.util.Iterator;

import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendCapacity;
import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendId;
import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendLocation;
import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendName;

public class GetRoomsPlainView extends PlainView {

    private GetRoomsResult result;

    public GetRoomsPlainView(CommandResult commandResult) {
        this.result = (GetRoomsResult) commandResult;
    }

    @Override
    public String display() {
        StringBuilder builder = new StringBuilder();
        Iterator<Room> iter = result.getRooms().iterator();
        while (iter.hasNext()) {
            Room room = iter.next();
            appendId(room, builder);
            builder.append('\n');
            appendName(room, builder);
            builder.append('\n');
            appendLocation(room, builder);
            builder.append('\n');
            appendCapacity(room, builder);
            if (iter.hasNext()) {
                builder.append("\n============================================\n");
            }
        }
        return builder.toString();
    }
}
