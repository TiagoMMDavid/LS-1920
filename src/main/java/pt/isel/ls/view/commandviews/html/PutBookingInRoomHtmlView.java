package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.PutBookingInRoomResult;
import pt.isel.ls.model.entities.Booking;

import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class PutBookingInRoomHtmlView extends HtmlView {
    private Booking booking;

    public PutBookingInRoomHtmlView(CommandResult commandResult) {
        this.booking = ((PutBookingInRoomResult) commandResult).getBooking();
    }

    @Override
    public String display() {
        return
                html(
                        head(
                                title("Put Booking")
                        ),
                        body(
                                h1(true, "Changed Booking with ID " + booking.getBid())
                        )
                ).toString();
    }
}
