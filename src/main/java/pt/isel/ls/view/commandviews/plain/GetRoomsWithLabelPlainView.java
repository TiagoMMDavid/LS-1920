package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetRoomsWithLabelResult;
import pt.isel.ls.model.entities.Room;

import java.util.Iterator;

import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendCapacity;
import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendId;
import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendLocation;
import static pt.isel.ls.view.commandviews.helpers.RoomHelper.appendName;

public class GetRoomsWithLabelPlainView extends PlainView {

    private GetRoomsWithLabelResult result;

    public GetRoomsWithLabelPlainView(CommandResult commandResult) {
        this.result = (GetRoomsWithLabelResult) commandResult;
    }

    @Override
    public String display() {
        StringBuilder builder = new StringBuilder();
        Iterator<Room> iter = result.getRooms().iterator();
        builder.append("Room with label \"")
                .append(result.getLabel().getName())
                .append("\":\n\n");
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
