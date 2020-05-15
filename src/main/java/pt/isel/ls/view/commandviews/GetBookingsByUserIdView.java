package pt.isel.ls.view.commandviews;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetBookingsByUserIdResult;
import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.utils.html.elements.Element;
import pt.isel.ls.view.View;

import java.util.Iterator;

import static pt.isel.ls.utils.html.HtmlDsl.a;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.table;
import static pt.isel.ls.utils.html.HtmlDsl.td;
import static pt.isel.ls.utils.html.HtmlDsl.th;
import static pt.isel.ls.utils.html.HtmlDsl.title;
import static pt.isel.ls.utils.html.HtmlDsl.tr;
import static pt.isel.ls.view.commandviews.helpers.BookingHelpers.appendBeginInst;
import static pt.isel.ls.view.commandviews.helpers.BookingHelpers.appendBid;
import static pt.isel.ls.view.commandviews.helpers.BookingHelpers.appendEndInst;

public class GetBookingsByUserIdView extends View {
    private GetBookingsByUserIdResult result;

    public GetBookingsByUserIdView(CommandResult commandResult) {
        this.result = (GetBookingsByUserIdResult) commandResult;
    }

    @Override
    public String displayText() {
        StringBuilder builder = new StringBuilder();
        Iterator<Booking> iter = result.getBookings().iterator();
        builder.append("Bookings from User with ID [")
                .append(result.getUid())
                .append("]:")
                .append("\n\n");
        while (iter.hasNext()) {
            Booking booking = iter.next();
            appendBid(booking, builder);
            builder.append('\n');
            appendBeginInst(booking, builder);
            appendEndInst(booking, builder);
            builder.append('\n');
            if (iter.hasNext()) {
                builder.append("============================================\n");
            }
        }
        return builder.toString();
    }

    @Override
    public String displayHtml() {
        int uid = result.getUid();
        return
                html(
                        head(
                                title("Bookings from User with ID [" + uid + "]")
                        ),
                        body(
                                a("/", "Home"), a("/users/" + uid, "User [" + uid + "]"),
                                h1("Information of all Bookings from User with ID [" + uid + "]"),
                                buildHtmlBookingInfo(result.getBookings())
                        )
                ).toString();
    }

    private Element buildHtmlBookingInfo(Iterable<Booking> bookings) {
        Element bookingInfo = table();
        bookingInfo.addChild(th("Booking ID"));
        bookingInfo.addChild(th("Begin Instant"));
        bookingInfo.addChild(th("End Instant"));
        for (Booking booking : bookings) {
            addHtmlTableRow(bookingInfo, booking);
        }
        return bookingInfo;
    }

    private void addHtmlTableRow(Element table, Booking booking) {
        Element tableRowData = tr();
        tableRowData.addChild(
                td(
                        a("/rooms/" + booking.getRid() + "/bookings/" + booking.getBid(), "" + booking.getBid())
                )
        );
        tableRowData.addChild(td(booking.getBeginInst().toString()));
        tableRowData.addChild(td(booking.getEndInst().toString()));
        table.addChild(tableRowData);
    }
}
