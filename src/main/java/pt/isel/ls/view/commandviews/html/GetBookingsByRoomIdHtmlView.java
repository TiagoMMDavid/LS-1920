package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetBookingsByRoomIdResult;
import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.model.entities.Room;
import pt.isel.ls.utils.html.HtmlTableBuilder;
import pt.isel.ls.utils.html.elements.Element;

import static pt.isel.ls.utils.html.HtmlDsl.a;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class GetBookingsByRoomIdHtmlView extends HtmlView {
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
                        body(HTML_DEFAULT_FONT,
                                a("/", "Home"), a("/rooms/" + room.getRid(), "Return to Room [" + room.getRid() + "]"),
                                a("/rooms/" + room.getRid() + "/bookings/create", "Create a Booking"),
                                h1("Information of all Bookings from Room \"" + result.getRoom().getName() + "\""),
                                buildHtmlBookingInfo(result.getBookings(), room)
                        )
                ).toString();
    }

    private Element buildHtmlBookingInfo(Iterable<Booking> bookings, Room room) {
        return new HtmlTableBuilder<>(bookings)
                .withColumn("Booking ID",
                    booking -> a("/rooms/" + room.getRid() + "/bookings/" + booking.getBid(), "" + booking.getBid()))
                .withColumn("Begin Date", Booking::getBeginInst)
                .withColumn("End Date", Booking::getEndInst)
                .build();
    }
}
