package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.PostBookingInRoomResult;
import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.view.View;
public class PostBookingInRoomPlainView extends View {
    private Booking booking;

    public PostBookingInRoomPlainView(CommandResult commandResult) {
        this.booking = ((PostBookingInRoomResult) commandResult).getBooking();
    }

    @Override
    public String display() {
        return "Created Booking with ID " + booking.getBid();
    }
}
