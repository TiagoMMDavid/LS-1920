package pt.isel.ls.view.commandviews;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetBookingsByRoomIdResult;
import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.utils.html.elements.Element;
import pt.isel.ls.view.View;

import java.util.Iterator;

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

public class GetBookingsByRoomIdView extends View {
    GetBookingsByRoomIdResult result;

    public GetBookingsByRoomIdView(CommandResult commandResult) {
        this.result = (GetBookingsByRoomIdResult) commandResult;
    }

    @Override
    public String displayText() {
        StringBuilder builder = new StringBuilder();
        Iterator<Booking> iter = result.getBookings().iterator();
        builder.append("Bookings from Room with ID [")
                .append(result.getRid())
                .append("]:")
                .append('\n');
        while (iter.hasNext()) {
            Booking booking = iter.next();
            appendBid(booking, builder);
            builder.append('\n');
            appendBeginInst(booking, builder);
            builder.append('\n');
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
        return
                html(
                        head(
                                title("Bookings from Room with ID [" + result.getRid() + "]")
                        ),
                        body(
                                h1("List of Bookings from Room with ID [" + result.getRid() + "]"),
                                buildHtmlBookingInfo(result.getBookings())
                        )
                ).toString();
    }

    private Element buildHtmlBookingInfo(Iterable<Booking> bookings) {
        Element bookingInfo = table();
        bookingInfo.addChild(th("Booking ID"));
        bookingInfo.addChild(th("Begin Date"));
        bookingInfo.addChild(th("End Date"));
        for (Booking booking : bookings) {
            addHtmlTableRow(bookingInfo, booking);
        }
        return bookingInfo;
    }

    private void addHtmlTableRow(Element table, Booking booking) {
        Element tableRowData = tr();
        tableRowData.addChild(td(booking.getBid()));
        tableRowData.addChild(td(booking.getBeginInst().toString()));
        tableRowData.addChild(td(booking.getEndInst().toString()));
        table.addChild(tableRowData);
    }
}
