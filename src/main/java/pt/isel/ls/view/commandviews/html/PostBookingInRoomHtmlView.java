package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.PostBookingInRoomResult;
import pt.isel.ls.model.entities.Booking;

import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class PostBookingInRoomHtmlView extends HtmlView {
    private Booking booking;

    public PostBookingInRoomHtmlView(CommandResult commandResult) {
        this.booking = ((PostBookingInRoomResult) commandResult).getBooking();
    }

    @Override
    public String display() {
        return
                html(
                        head(
                                title("Post Booking")
                        ),
                        body(
                                h1(true, "Created Booking with ID " + booking.getBid())
                        )
                ).toString();
    }
}
