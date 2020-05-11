package pt.isel.ls.view.commandviews.helpers;

import pt.isel.ls.model.commands.results.GetLabelByIdResult;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.model.entities.Room;

import java.util.Iterator;

public class LabelHelper {
    public static void appendName(Label label, StringBuilder builder) {
        String name = label.getName();
        if (name != null) {
            builder.append("Name:\t\t\t");
            builder.append(name);
        }
    }

    public static void appendId(Label label, StringBuilder builder) {
        builder.append("Label ID:\t\t");
        builder.append(label.getLid());
    }

    public static void appendRooms(GetLabelByIdResult res, StringBuilder builder) {
        builder.append("Room IDs:\t\t");
        appendRoomsWithCommas(res, builder);
    }

    public static StringBuilder appendRoomsWithCommas(GetLabelByIdResult res, StringBuilder builder) {
        Iterable<Room> rooms = res.getRooms();
        if (rooms == null) {
            builder.append("N/A");
            return builder;
        }

        Iterator<Room> iter = rooms.iterator();
        while (iter.hasNext()) {
            builder.append(iter.next().getRid());
            if (iter.hasNext()) {
                builder.append(", ");
            }
        }
        return builder;
    }
}
