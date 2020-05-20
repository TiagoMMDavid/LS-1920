package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.PostRoomResult;
import pt.isel.ls.model.entities.Room;
import pt.isel.ls.view.View;

public class PostRoomPlainView extends View {
    private Room room;

    public PostRoomPlainView(CommandResult commandResult) {
        this.room = ((PostRoomResult) commandResult).getRoom();
    }

    @Override
    public String display() {
        return "Created Room \"" + room.getName() + "\" with ID " + room.getRid();
    }
}
