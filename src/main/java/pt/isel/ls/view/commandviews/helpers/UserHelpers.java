package pt.isel.ls.view.commandviews.helpers;

import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.model.entities.User;

public class UserHelpers {

    public static void appendEmail(User user, StringBuilder builder) {
        String email = user.getEmail();
        builder.append("Email: ");
        builder.append(email == null ? "N/A" : email);
    }

    public static void appendName(User user, StringBuilder builder) {
        String name = user.getName();
        builder.append("Name: ");
        builder.append(name == null ? "N/A" : name);
    }

    public static void appendId(User user, StringBuilder builder) {
        builder.append("User ID: ");
        builder.append(user.getUid());
    }

    public static void appendBookings(Iterable<Booking> bookings, StringBuilder builder) {
        if (bookings == null) {
            builder.append("N/A");
            return;
        }

        builder.append("User bookings:\n");

        for (Booking booking : bookings) {
            builder.append("\t");
            builder.append("Booking ID: ");
            builder.append(booking.getBid());
            builder.append("\n");
            builder.append("\t\tBegin Instant: ");
            builder.append(booking.getBeginInst());
            builder.append("\n");
            builder.append("\t\tEnd Instant: ");
            builder.append(booking.getEndInst());
        }
    }
}
