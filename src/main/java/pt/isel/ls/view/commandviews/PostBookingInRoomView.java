package pt.isel.ls.view.commandviews;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.PostBookingInRoomResult;
import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.view.View;

import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class PostBookingInRoomView extends View {
    private Booking booking;

    public PostBookingInRoomView(CommandResult commandResult) {
        this.booking = ((PostBookingInRoomResult) commandResult).getBooking();
    }

    @Override
    public String displayText() {
        return "Created Booking with ID " + booking.getBid();
    }

    @Override
    public String displayHtml() {
        return
                html(
                        head(
                                title("Post Booking")
                        ),
                        body(
                                h1("Created Booking with ID " + booking.getBid())
                        )
                ).toString();
    }
}
