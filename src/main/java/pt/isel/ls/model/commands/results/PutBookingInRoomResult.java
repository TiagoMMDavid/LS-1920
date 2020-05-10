package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.entities.Booking;

public class PutBookingInRoomResult implements CommandResult {

    private boolean hasResult = false;
    private Booking booking;

    public void setBooking(Booking booking) {
        this.booking = booking;
        hasResult = true;
    }

    public Booking getBooking() {
        return booking;
    }

    @Override
    public boolean hasResults() {
        return hasResult;
    }

    @Override
    public ResultType getResultType() {
        return ResultType.PutBookingInRoom;
    }
}
