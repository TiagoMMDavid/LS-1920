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
            if (!booking.isPost()) {
                appendRid(booking, builder);
                if (booking.isDetailed()) {
                    appendUid(booking, builder);
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
        Booking booking = (Booking) entity;
        tableRow.addChild(th("BID"));

        if (!booking.isPost()) {
            tableRow.addChild(th("RID"));
            if (booking.isDetailed()) {
                tableRow.addChild(th("UID"));
                tableRow.addChild(th("Begin Instant"));
                tableRow.addChild(th("End Instant"));
            }
        }

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
        if (!booking.isPost()) {
            tableRowData.addChild(td(booking.getRid() < 0 ? "N/A" : booking.getRid()));
            if (booking.isDetailed()) {
                tableRowData.addChild(td(booking.getUid() < 0 ? "N/A" : booking.getUid()));
                tableRowData.addChild(td(booking.getBeginInst() == null ? "N/A" : booking.getBeginInst().toString()));
                tableRowData.addChild(td(booking.getEndInst() == null ? "N/A" : booking.getEndInst().toString()));
            }
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
