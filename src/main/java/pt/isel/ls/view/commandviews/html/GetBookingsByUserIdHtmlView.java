package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetBookingsByUserIdResult;
import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.utils.DateUtils;
import pt.isel.ls.utils.html.HtmlTableBuilder;
import pt.isel.ls.utils.html.elements.Element;

import static pt.isel.ls.utils.html.HtmlDsl.a;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class GetBookingsByUserIdHtmlView extends HtmlView {

    private GetBookingsByUserIdResult result;

    public GetBookingsByUserIdHtmlView(CommandResult commandResult) {
        this.result = (GetBookingsByUserIdResult) commandResult;
    }

    @Override
    public String display() {
        int uid = result.getUid();
        return
                html(
                        head(
                                title("Bookings from User with ID [" + uid + "]")
                        ),
                        body(HTML_DEFAULT_FONT,
                                a("/", "Home"), a("/users/" + uid, "User [" + uid + "]"),
                                h1("Information of all Bookings from User with ID [" + uid + "]"),
                                buildHtmlBookingInfo(result.getBookings())
                        )
                ).toString();
    }

    private Element buildHtmlBookingInfo(Iterable<Booking> bookings) {
        return new HtmlTableBuilder<>(bookings)
                .withColumn("Booking ID",
                    booking -> a("/rooms/" + booking.getRid() + "/bookings/" + booking.getBid(),
                            "" + booking.getBid()))
                .withColumn("Begin Date",
                    booking -> DateUtils.formatDate(booking.getBeginInst(), "dd-MM-yyyy HH:mm (z)"))
                .withColumn("End Date",
                    booking -> DateUtils.formatDate(booking.getEndInst(), "dd-MM-yyyy HH:mm (z)"))
                .build();
    }
}
