package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.PostBookingInRoomResult;
import pt.isel.ls.model.entities.Booking;

public class PostBookingInRoomPlainView extends PlainView {
    
    private Booking booking;

    public PostBookingInRoomPlainView(CommandResult commandResult) {
        this.booking = ((PostBookingInRoomResult) commandResult).getBooking();
    }

    @Override
    public String display() {
        return "Created Booking with ID " + booking.getBid();
    }
}