package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.PutBookingInRoomResult;
import pt.isel.ls.model.entities.Booking;

public class PutBookingInRoomPlainView extends PlainView {

    private Booking booking;

    public PutBookingInRoomPlainView(CommandResult commandResult) {
        this.booking = ((PutBookingInRoomResult) commandResult).getBooking();
    }

    @Override
    public String display() {
        return "Changed Booking with ID " + booking.getBid();
    }
}
