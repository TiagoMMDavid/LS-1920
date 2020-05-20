package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.DeleteBookingInRoomResult;
import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.view.View;

public class DeleteBookingInRoomPlainView extends View {
    private Booking booking;

    public DeleteBookingInRoomPlainView(CommandResult commandResult) {
        this.booking = ((DeleteBookingInRoomResult) commandResult).getBooking();
    }

    @Override
    public String display() {
        return "Deleted Booking with ID " + booking.getBid();
    }
}
