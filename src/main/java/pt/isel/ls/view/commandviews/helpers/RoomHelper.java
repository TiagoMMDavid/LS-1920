package pt.isel.ls.view.commandviews.helpers;

import pt.isel.ls.model.commands.results.GetRoomByIdResult;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.model.entities.Room;

import java.util.Iterator;

public class RoomHelper {

    /**
     * These methods are responsible for appending specific Room information to the
     * StringBuilder that's passed as a parameter
     */

    public static void appendId(Room room, StringBuilder builder) {
        builder.append("Room ID:\t\t");
        builder.append(room.getRid());
    }

    public static void appendName(Room room, StringBuilder builder) {
        String name = room.getName();
        builder.append("Name:\t\t\t");
        builder.append(name);
    }

    public static void appendDescription(Room room, StringBuilder builder) {
        String desc = room.getDescription();
        builder.append("Description:\t");
        builder.append(desc == null ? "N/A" : desc);
    }

    public static void appendLocation(Room room, StringBuilder builder) {
        String location = room.getLocation();
        builder.append("Location:\t\t");
        builder.append(location);
    }

    public static void appendCapacity(Room room, StringBuilder builder) {
        Integer capacity = room.getCapacity();
        builder.append("Capacity:\t\t");
        builder.append(capacity == null ? "N/A" : capacity);
    }

    public static void appendLabels(GetRoomByIdResult result, StringBuilder builder) {
        builder.append("Labels:\t\t\t");
        appendLabelsWithComma(builder, result.getLabels());
    }

    public static String appendLabelsWithComma(StringBuilder builder, Iterable<Label> iter) {
        Iterator<Label> labels = iter.iterator();
        int size = 0;
        while (labels.hasNext()) {
            ++size;
            builder.append(labels.next().getName());
            if (labels.hasNext()) {
                builder.append(", ");
            }
        }
        if (size == 0) {
            builder.append("N/A");
        }
        return builder.toString();
    }

}
