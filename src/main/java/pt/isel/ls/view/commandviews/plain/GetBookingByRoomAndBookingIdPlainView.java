package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetBookingByRoomAndBookingIdResult;
import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.utils.html.elements.Element;
import pt.isel.ls.view.View;

import static pt.isel.ls.utils.html.HtmlDsl.a;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.li;
import static pt.isel.ls.utils.html.HtmlDsl.title;
import static pt.isel.ls.utils.html.HtmlDsl.ul;
import static pt.isel.ls.view.commandviews.helpers.BookingHelpers.appendBeginInst;
import static pt.isel.ls.view.commandviews.helpers.BookingHelpers.appendBid;
import static pt.isel.ls.view.commandviews.helpers.BookingHelpers.appendEndInst;
import static pt.isel.ls.view.commandviews.helpers.BookingHelpers.appendRid;
import static pt.isel.ls.view.commandviews.helpers.BookingHelpers.appendUid;

public class GetBookingByRoomAndBookingIdPlainView extends View {
    private Booking booking;

    public GetBookingByRoomAndBookingIdPlainView(CommandResult commandResult) {
        this.booking = ((GetBookingByRoomAndBookingIdResult) commandResult).getBooking();
    }

    @Override
    public String displayText() {
        StringBuilder builder = new StringBuilder();
        appendBid(booking, builder);
        builder.append('\n');
        appendRid(booking, builder);
        builder.append('\n');
        appendUid(booking, builder);
        builder.append('\n');
        appendBeginInst(booking, builder);
        builder.append('\n');
        appendEndInst(booking, builder);
        builder.append('\n');
        return builder.toString();
    }

    @Override
    public String displayHtml() {
        return
                html(
                        head(
                                title("Booking [" + booking.getBid() + "]")
                        ),
                        body(
                                a("/", "Home"),
                                h1("Detailed Information of Booking with ID [" + booking.getBid() + "]"),
                                buildHtmlBookingInfo(booking)
                        )
                ).toString();
    }

    private Element buildHtmlBookingInfo(Booking booking) {
        Element bookingInfo = ul();
        bookingInfo.addChild(li("Booking ID: " + booking.getBid()));
        bookingInfo.addChild(
                li(
                    a("/rooms/" + booking.getRid(), "Room ID: " + booking.getRid())
                )
        );

        bookingInfo.addChild(
                li(
                        a("/users/" + booking.getUid(), "User ID: " + booking.getUid())
                )
        );

        bookingInfo.addChild(li("Begin Date: " + booking.getBeginInst()));
        bookingInfo.addChild(li("End Date: " + booking.getEndInst()));
        return bookingInfo;
    }
}
