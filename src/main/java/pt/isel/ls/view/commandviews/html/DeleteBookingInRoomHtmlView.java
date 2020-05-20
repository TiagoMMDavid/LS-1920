package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.DeleteBookingInRoomResult;
import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.view.View;

import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class DeleteBookingInRoomHtmlView extends View {
    private Booking booking;

    public DeleteBookingInRoomHtmlView(CommandResult commandResult) {
        this.booking = ((DeleteBookingInRoomResult) commandResult).getBooking();
    }

    @Override
    public String displayText() {
        return "Deleted Booking with ID " + booking.getBid();
    }

    @Override
    public String displayHtml() {
        return
                html(
                        head(
                                title("Deleted Booking")
                        ),
                        body(
                                h1("Deleted Booking with ID " + booking.getBid())
                        )
                ).toString();
    }
}
