package pt.isel.ls.view;

import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.model.entities.Entity;
import pt.isel.ls.utils.html.elements.Element;

import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.table;
import static pt.isel.ls.utils.html.HtmlDsl.td;
import static pt.isel.ls.utils.html.HtmlDsl.th;
import static pt.isel.ls.utils.html.HtmlDsl.tr;
import static pt.isel.ls.utils.html.HtmlDsl.title;
import static pt.isel.ls.utils.html.HtmlDsl.li;
import static pt.isel.ls.utils.html.HtmlDsl.ul;

import java.util.Date;

public class BookingView extends View {
    protected BookingView(Iterable<Entity> entity) {
        super(entity);
    }

    @Override
    public String displayText() {
        StringBuilder builder = new StringBuilder();
        for (Entity entity : entities) {
            Booking booking = (Booking) entity;
            appendBid(booking, builder);
            if (!booking.isPost()) {
                appendRid(booking, builder);
                if (booking.hasUserInfo()) {
                    appendUid(booking, builder);
                }
                if (booking.isDetailed() || !booking.hasUserInfo()) {
                    appendBeginInst(booking, builder);
                    appendEndInst(booking, builder);
                }
            }
            builder.append("\n\n");
        }
        return builder.toString();
    }

    @Override
    public String displayHtml() {
        Booking booking = (Booking) entity;
        String header = booking.isDetailed() ? "Detailed information for Booking:" : "List of Bookings:";
        Element html =
                html(
                        head(
                                title("Bookings")
                        ),
                        body(
                                h1(header),
                                buildHtmlBookingInfo(booking)
                        )
                );
        return html.toString();
    }

    private Element buildHtmlBookingInfo(Booking booking) {
        Element bookingInfo;
        if (booking.isDetailed() || booking.isPost()) {
            bookingInfo = ul();
            bookingInfo.addChild(li("Booking ID: " + booking.getBid()));
            if (booking.isDetailed()) {
                bookingInfo.addChild(li("Room ID: " + booking.getRid()));
                bookingInfo.addChild(li("User ID: " + booking.getUid()));
                bookingInfo.addChild(li("Begin Instant: " + booking.getBeginInst()));
                bookingInfo.addChild(li("End Instant: " + booking.getEndInst()));
            }
        } else {
            bookingInfo = table();
            bookingInfo.addChild(th("Booking ID"));
            bookingInfo.addChild(th("Room ID"));
            if (booking.hasUserInfo()) {
                bookingInfo.addChild(th("User ID"));
            } else {
                bookingInfo.addChild(th("Begin Instant"));
                bookingInfo.addChild(th("End Instant"));
            }
            for (Entity entity : entities) {
                addHtmlTableRow(bookingInfo, (Booking) entity);
            }
        }
        return bookingInfo;
    }

    private void addHtmlTableRow(Element table, Booking booking) {
        Element tableRowData = tr();
        tableRowData.addChild(td(booking.getBid()));
        tableRowData.addChild(td(booking.getRid() < 0 ? "N/A" : booking.getRid()));
        if (booking.hasUserInfo()) {
            tableRowData.addChild(td(booking.getUid() < 0 ? "N/A" : booking.getUid()));
        } else {
            tableRowData.addChild(td(booking.getBeginInst().toString()));
            tableRowData.addChild(td(booking.getEndInst().toString()));
        }
        table.addChild(tableRowData);
    }

    private void appendEndInst(Booking booking, StringBuilder builder) {
        Date date = booking.getEndInst();
        builder.append("\nEnd Date: ");
        builder.append(date == null ? "N/A" : date);
    }

    private void appendBeginInst(Booking booking, StringBuilder builder) {
        Date date = booking.getBeginInst();
        builder.append("\nBegin Date: ");
        builder.append(date == null ? "N/A" : date);
    }

    private void appendRid(Booking booking, StringBuilder builder) {
        int rid = booking.getRid();
        builder.append("\nRoom ID: ");
        builder.append(rid < 0 ? "N/A" : rid);
    }

    private void appendUid(Booking booking, StringBuilder builder) {
        int uid = booking.getUid();
        builder.append("\nUser ID: ");
        builder.append(uid < 0 ? "N/A" : uid);
    }

    private void appendBid(Booking booking, StringBuilder builder) {
        builder.append("Booking ID: ");
        builder.append(booking.getBid());
    }
}
