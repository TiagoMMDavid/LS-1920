package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.model.entities.User;

public class GetUserByIdResult implements CommandResult {

    private boolean hasResult = false;
    private User user;
    private Iterable<Booking> bookings;

    public void setUser(User user) {
        this.user = user;
        hasResult = true;
    }

    public void setBookings(Iterable<Booking> bookings) {
        this.bookings = bookings;
    }

    public User getUser() {
        return user;
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
        return ResultType.GetUserById;
    }
}
