package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetBookingsByRoomIdResult;
import pt.isel.ls.model.entities.Booking;

import java.util.Iterator;

import static pt.isel.ls.view.commandviews.helpers.BookingHelpers.appendBeginInst;
import static pt.isel.ls.view.commandviews.helpers.BookingHelpers.appendBid;
import static pt.isel.ls.view.commandviews.helpers.BookingHelpers.appendEndInst;

public class GetBookingsByRoomIdPlainView extends PlainView {

    private GetBookingsByRoomIdResult result;

    public GetBookingsByRoomIdPlainView(CommandResult commandResult) {
        this.result = (GetBookingsByRoomIdResult) commandResult;
    }

    @Override
    public String display() {
        StringBuilder builder = new StringBuilder();
        Iterator<Booking> iter = result.getBookings().iterator();
        builder.append("Bookings from Room with ID [")
                .append(result.getRoom().getRid())
                .append("]:")
                .append("\n\n");
        while (iter.hasNext()) {
            Booking booking = iter.next();
            appendBid(booking, builder);
            builder.append('\n');
            appendBeginInst(booking, builder);
            builder.append('\n');
            appendEndInst(booking, builder);
            if (iter.hasNext()) {
                builder.append("\n============================================\n");
            }
        }
        return builder.toString();
    }
}
