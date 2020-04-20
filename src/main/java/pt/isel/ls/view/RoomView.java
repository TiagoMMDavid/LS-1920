package pt.isel.ls.view;

import pt.isel.ls.model.entities.Entity;
import pt.isel.ls.model.entities.Room;

import java.io.IOException;
import java.io.OutputStream;

public class RoomView extends View {

    protected RoomView(Entity entity) {
        super(entity);
    }

    @Override
    public void displayText(OutputStream out) throws IOException {
        Room room = (Room) entity;
        StringBuilder builder = new StringBuilder();

        appendId(room, builder);
        appendName(room, builder);
        appendDescription(room, builder);
        appendLocation(room, builder);
        appendCapacity(room, builder);
        builder.append('\n');

        out.write(builder.toString().getBytes());
    }

    @Override
    public void displayHtml(OutputStream out) throws IOException {
        // TODO
    }

    private void appendId(Room room, StringBuilder builder) {
        builder.append("Room ID: ");
        builder.append(room.getRid());
    }

    public void appendName(Room room, StringBuilder builder) {
        String name = room.getName();
        if (name != null) {
            builder.append("\nName: ");
            builder.append(name);
        }
    }

    public void appendDescription(Room room, StringBuilder builder) {
        String desc = room.getDescription();
        if (desc != null) {
            builder.append("\nDescription: ");
            builder.append(desc);
        }
    }

    public void appendLocation(Room room, StringBuilder builder) {
        String location = room.getLocation();
        if (location != null) {
            builder.append("\nLocation: ");
            builder.append(location);
        }
    }

    public void appendCapacity(Room room, StringBuilder builder) {
        int capacity = room.getCapacity();
        if (capacity > 0) {
            builder.append("\nCapacity: ");
            builder.append(capacity);
        }
    }
}
