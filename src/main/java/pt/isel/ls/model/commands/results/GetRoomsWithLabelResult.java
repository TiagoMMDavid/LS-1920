package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.model.entities.Room;

import java.util.LinkedList;

public class GetRoomsWithLabelResult implements CommandResult {

    private boolean hasResult = false;
    private LinkedList<Room> rooms;
    private Label label;

    public void addRoom(Room room) {
        if (rooms == null) {
            rooms = new LinkedList<>();
            hasResult = true;
        }
        rooms.add(room);
    }

    public void setLabel(Label label) {
        this.label = label;
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
        return ResultType.GetRoomsWithLabel;
    }

    public Label getLabel() {
        return label;
    }
}
