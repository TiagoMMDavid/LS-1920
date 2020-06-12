package pt.isel.ls.view.commandviews.helpers;

import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.utils.DateUtils;

import java.util.Date;

public class BookingHelpers {
    public static void appendEndInst(Booking booking, StringBuilder builder) {
        Date date = booking.getEndInst();
        builder.append("End Date:\t\t");
        builder.append(date == null ? "N/A" : DateUtils.formatDate(date, "dd-MM-yyyy HH:mm (z)"));
    }

    public static void appendBeginInst(Booking booking, StringBuilder builder) {
        Date date = booking.getBeginInst();
        builder.append("Begin Date:\t\t");
        builder.append(date == null ? "N/A" : DateUtils.formatDate(date, "dd-MM-yyyy HH:mm (z)"));
    }

    public static  void appendRid(Booking booking, StringBuilder builder) {
        int rid = booking.getRid();
        builder.append("Room ID:\t\t");
        builder.append(rid < 0 ? "N/A" : rid);
    }

    public static  void appendUid(Booking booking, StringBuilder builder) {
        int uid = booking.getUid();
        builder.append("User ID:\t\t");
        builder.append(uid < 0 ? "N/A" : uid);
    }

    public static  void appendBid(Booking booking, StringBuilder builder) {
        builder.append("Booking ID:\t\t");
        builder.append(booking.getBid());
    }
}
