package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.PostResult;
import pt.isel.ls.model.entities.Room;

public class PostRoomResult implements CommandResult, PostResult {

    private boolean hasResult = false;
    private Room room;

    public void setRoom(Room room) {
        this.room = room;
        hasResult = true;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public boolean hasResults() {
        return hasResult;
    }

    @Override
    public CommandResult.ResultType getResultType() {
        return ResultType.PostRoom;
    }

    @Override
    public String getCreatedId() {
        return "" + room.getRid();
    }
}
