package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.PutBookingInRoomResult;
import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.view.View;

import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class PutBookingInRoomPlainView extends View {
    private Booking booking;

    public PutBookingInRoomPlainView(CommandResult commandResult) {
        this.booking = ((PutBookingInRoomResult) commandResult).getBooking();
    }

    @Override
    public String displayText() {
        return "Changed Booking with ID " + booking.getBid();
    }

    @Override
    public String displayHtml() {
        return
                html(
                        head(
                                title("Put Booking")
                        ),
                        body(
                                h1("Changed Booking with ID " + booking.getBid())
                        )
                ).toString();
    }
}
