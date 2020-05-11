package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.entities.Booking;

import java.util.LinkedList;

public class GetBookingsByRoomIdResult implements CommandResult {

    private boolean hasResult = false;
    private LinkedList<Booking> bookings;
    private int rid;

    public void addBooking(Booking booking) {
        if (bookings == null) {
            bookings = new LinkedList<>();
            hasResult = true;
        }
        bookings.add(booking);
    }

    public void setRoom(int rid) {
        this.rid = rid;
    }

    public Iterable<Booking> getBookings() {
        return bookings;
    }

    @Override
    public boolean hasResults() {
        return hasResult;
    }

    @Override
    public CommandResult.ResultType getResultType() {
        return ResultType.GetBookingsByRoomId;
    }

    public int getRid() {
        return rid;
    }
}
