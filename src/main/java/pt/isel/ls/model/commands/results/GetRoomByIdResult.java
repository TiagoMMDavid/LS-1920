package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.model.entities.Room;

public class GetRoomByIdResult implements CommandResult {

    private boolean hasResult = false;
    private Room room;
    private Iterable<Label> labels;

    public void setRoom(Room room) {
        this.room = room;
        hasResult = true;
    }

    public void setLabels(Iterable<Label> labels) {
        this.labels = labels;
    }

    public Room getRoom() {
        return room;
    }

    public Iterable<Label> getLabels() {
        return labels;
    }

    @Override
    public boolean hasResults() {
        return hasResult;
    }

    @Override
    public CommandResult.ResultType getResultType() {
        return ResultType.GetRoomById;
    }
}
