package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetBookingsByRoomIdResult;
import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.model.entities.Room;
import pt.isel.ls.utils.html.elements.Element;
import pt.isel.ls.view.View;

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

public class GetBookingsByRoomIdHtmlView extends View {
    private GetBookingsByRoomIdResult result;

    public GetBookingsByRoomIdHtmlView(CommandResult commandResult) {
        this.result = (GetBookingsByRoomIdResult) commandResult;
    }

    @Override
    public String display() {
        Room room = result.getRoom();
        return
                html(
                        head(
                                title("Bookings from Room with ID [" + room.getRid() + "]")
                        ),
                        body(
                                a("/", "Home"), a("/rooms/" + room.getRid(), "Room [" + room.getRid() + "]"),
                                h1("Information of all Bookings from Room \"" + result.getRoom().getName() + "\""),
                                buildHtmlBookingInfo(result.getBookings(), room)
                        )
                ).toString();
    }

    private Element buildHtmlBookingInfo(Iterable<Booking> bookings, Room room) {
        Element bookingInfo = table();
        bookingInfo.addChild(th("Booking ID"));
        bookingInfo.addChild(th("Begin Date"));
        bookingInfo.addChild(th("End Date"));
        for (Booking booking : bookings) {
            addHtmlTableRow(bookingInfo, booking, room);
        }
        return bookingInfo;
    }

    private void addHtmlTableRow(Element table, Booking booking, Room room) {
        Element tableRowData = tr();
        tableRowData.addChild(
                td(
                        a("/rooms/" + room.getRid() + "/bookings/" + booking.getBid(), "" + booking.getBid())
                )
        );
        tableRowData.addChild(td(booking.getBeginInst().toString()));
        tableRowData.addChild(td(booking.getEndInst().toString()));
        table.addChild(tableRowData);
    }
}
