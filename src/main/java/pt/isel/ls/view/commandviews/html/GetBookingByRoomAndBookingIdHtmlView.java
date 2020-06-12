package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetBookingByRoomAndBookingIdResult;
import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.utils.DateUtils;
import pt.isel.ls.utils.html.elements.Element;

import static pt.isel.ls.utils.html.HtmlDsl.a;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.li;
import static pt.isel.ls.utils.html.HtmlDsl.title;
import static pt.isel.ls.utils.html.HtmlDsl.ul;

public class GetBookingByRoomAndBookingIdHtmlView extends HtmlView {
    private Booking booking;

    public GetBookingByRoomAndBookingIdHtmlView(CommandResult commandResult) {
        this.booking = ((GetBookingByRoomAndBookingIdResult) commandResult).getBooking();
    }

    @Override
    public String display() {
        return
                html(
                        head(
                                title("Booking [" + booking.getBid() + "]")
                        ),
                        body(HTML_DEFAULT_FONT,
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

        bookingInfo.addChild(li("Begin Date: " + DateUtils.formatDate(booking.getBeginInst(), "dd-MM-yyyy HH:mm (z)")));
        bookingInfo.addChild(li("End Date: " + DateUtils.formatDate(booking.getEndInst(), "dd-MM-yyyy HH:mm (z)")));
        return bookingInfo;
    }
}
