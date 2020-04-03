package pt.isel.ls.view;

import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.model.entities.Entity;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class BookingView extends View {
    public BookingView(Entity obj) {
        super(obj);
    }

    @Override
    public void displayText(OutputStream out) throws IOException {
        Booking booking = (Booking) context;
        StringBuilder builder = new StringBuilder();

        appendBid(booking, builder);
        appendUid(booking, builder);
        appendRid(booking, builder);
        appendBeginInst(booking, builder);
        appendEndInst(booking, builder);
        builder.append('\n');

        out.write(builder.toString().getBytes());
    }

    private void appendEndInst(Booking booking, StringBuilder builder) {
        Date date = booking.getEndInst();
        if (date != null) {
            builder.append("\nEnd Date: ");
            builder.append(date);
        }
    }

    private void appendBeginInst(Booking booking, StringBuilder builder) {
        Date date = booking.getBeginInst();
        if (date != null) {
            builder.append("\nBegin Date: ");
            builder.append(date);
        }
    }

    private void appendRid(Booking booking, StringBuilder builder) {
        int rid = booking.getRid();
        if (rid >= 0) {
            builder.append("\nRoom ID: ");
            builder.append(rid);
        }
    }

    private void appendUid(Booking booking, StringBuilder builder) {
        int uid = booking.getUid();
        if (uid >= 0) {
            builder.append("\nUser ID: ");
            builder.append(uid);
        }
    }

    private void appendBid(Booking booking, StringBuilder builder) {
        builder.append("Booking ID: ");
        builder.append(booking.getBid());
    }
}
