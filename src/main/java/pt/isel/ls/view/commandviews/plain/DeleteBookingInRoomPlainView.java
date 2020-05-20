package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.DeleteBookingInRoomResult;
import pt.isel.ls.model.entities.Booking;

public class DeleteBookingInRoomPlainView extends PlainView {
    private Booking booking;

    public DeleteBookingInRoomPlainView(CommandResult commandResult) {
        this.booking = ((DeleteBookingInRoomResult) commandResult).getBooking();
    }

    @Override
    public String display() {
        return "Deleted Booking with ID " + booking.getBid();
    }
}
