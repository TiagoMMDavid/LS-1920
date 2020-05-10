package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.model.entities.Room;

public class GetLabelByIdResult implements CommandResult {

    private boolean hasResult = false;
    private Label label;
    private Iterable<Room> rooms;

    public void setLabel(Label label) {
        this.label = label;
        hasResult = true;
    }

    public void setRooms(Iterable<Room> rooms) {
        this.rooms = rooms;
    }

    public Label getLabel() {
        return label;
    }

    public Iterable<Room> getRooms() {
        return rooms;
    }

    @Override
    public boolean hasResults() {
        return hasResult;
    }

    @Override
    public CommandResult.ResultType getResultType() {
        return ResultType.GetLabelById;
    }
}
