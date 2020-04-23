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
            appendUid(booking, builder);
            appendRid(booking, builder);
            appendBeginInst(booking, builder);
            appendEndInst(booking, builder);
            builder.append("\n\n");
        }
        return builder.toString();
    }

    @Override
    public String displayHtml() {
        Element html =
                html(
                        head(
                                title("Bookings")
                        ),
                        body(
                                h1("List of Bookings:"),
                                buildHtmlTable()
                        )
                );
        return html.toString();
    }

    private Element buildHtmlTable() {
        Element tableRow = tr();
        tableRow.addChild(th("BID"));
        tableRow.addChild(th("UID"));
        tableRow.addChild(th("RID"));
        tableRow.addChild(th("Begin Instant"));
        tableRow.addChild(th("End Instant"));

        Element table = table();
        table.addChild(tableRow);
        for (Entity entity : entities) {
            addHtmlTableRow(table, (Booking) entity);
        }
        return table;
    }

    private void addHtmlTableRow(Element table, Booking booking) {
        Element tableRowData = tr();
        tableRowData.addChild(td(booking.getBid()));
        if (booking.getUid() <= 0) {
            tableRowData.addChild(td("N/A"));
        } else {
            tableRowData.addChild(td(booking.getUid()));
        }
        if (booking.getRid() <= 0) {
            tableRowData.addChild(td("N/A"));
        } else {
            tableRowData.addChild(td(booking.getRid()));
        }
        tableRowData.addChild(td(booking.getBeginInst() == null ? "N/A" : booking.getBeginInst().toString()));
        tableRowData.addChild(td(booking.getEndInst() == null ? "N/A" : booking.getEndInst().toString()));
        table.addChild(tableRowData);
    }

    private void appendEndInst(Booking booking, StringBuilder builder) {
        Date date = booking.getEndInst();
        if (date != null) {
            builder.append("\nEnd Date: ");
            builder.append(date);
        }
    }

    private void appendBeginInst(Booking booking, StringBuilder builder) {
        Date date = booking.getBeginInst();
        if (date != null) {
            builder.append("\nBegin Date: ");
            builder.append(date);
        }
    }

    private void appendRid(Booking booking, StringBuilder builder) {
        int rid = booking.getRid();
        if (rid >= 0) {
            builder.append("\nRoom ID: ");
            builder.append(rid);
        }
    }

    private void appendUid(Booking booking, StringBuilder builder) {
        int uid = booking.getUid();
        if (uid >= 0) {
            builder.append("\nUser ID: ");
            builder.append(uid);
        }
    }

    private void appendBid(Booking booking, StringBuilder builder) {
        builder.append("Booking ID: ");
        builder.append(booking.getBid());
    }
}
