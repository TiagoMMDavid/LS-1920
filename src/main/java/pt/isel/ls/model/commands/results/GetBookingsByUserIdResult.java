package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.entities.Booking;

import java.util.LinkedList;

public class GetBookingsByUserIdResult implements CommandResult {

    private boolean hasResult = false;
    private LinkedList<Booking> bookings;
    private int uid;

    public void addBooking(Booking booking) {
        if (bookings == null) {
            bookings = new LinkedList<>();
            hasResult = true;
        }
        bookings.add(booking);
    }


    public void setUid(int uid) {
        this.uid = uid;
    }

    public Iterable<Booking> getBookings() {
        return bookings;
    }

    public int getUid() {
        return uid;
    }

    @Override
    public boolean hasResults() {
        return hasResult;
    }

    @Override
    public CommandResult.ResultType getResultType() {
        return CommandResult.ResultType.GetBookingsByUserId;
    }

}
