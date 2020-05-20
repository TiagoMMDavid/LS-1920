package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetBookingByRoomAndBookingIdResult;
import pt.isel.ls.model.entities.Booking;

import static pt.isel.ls.view.commandviews.helpers.BookingHelpers.appendBeginInst;
import static pt.isel.ls.view.commandviews.helpers.BookingHelpers.appendBid;
import static pt.isel.ls.view.commandviews.helpers.BookingHelpers.appendEndInst;
import static pt.isel.ls.view.commandviews.helpers.BookingHelpers.appendRid;
import static pt.isel.ls.view.commandviews.helpers.BookingHelpers.appendUid;

public class GetBookingByRoomAndBookingIdPlainView extends PlainView {

    private Booking booking;

    public GetBookingByRoomAndBookingIdPlainView(CommandResult commandResult) {
        this.booking = ((GetBookingByRoomAndBookingIdResult) commandResult).getBooking();
    }

    @Override
    public String display() {
        StringBuilder builder = new StringBuilder();
        appendBid(booking, builder);
        builder.append('\n');
        appendRid(booking, builder);
        builder.append('\n');
        appendUid(booking, builder);
        builder.append('\n');
        appendBeginInst(booking, builder);
        builder.append('\n');
        appendEndInst(booking, builder);
        return builder.toString();
    }
}
