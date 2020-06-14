package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetUserByIdResult;
import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.model.entities.User;
import pt.isel.ls.utils.DateUtils;
import pt.isel.ls.utils.html.HtmlTableBuilder;
import pt.isel.ls.utils.html.elements.Element;

import static pt.isel.ls.utils.html.HtmlDsl.a;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.li;
import static pt.isel.ls.utils.html.HtmlDsl.p;
import static pt.isel.ls.utils.html.HtmlDsl.title;
import static pt.isel.ls.utils.html.HtmlDsl.ul;

public class GetUserByIdHtmlView extends HtmlView {

    private GetUserByIdResult result;

    public GetUserByIdHtmlView(CommandResult commandResult) {
        result = (GetUserByIdResult) commandResult;
    }

    @Override
    public String display() {
        User user = result.getUser();
        Iterable<Booking> bookings = result.getBookings();
        Element html =
                html(
                        head(
                                title("User [" + user.getUid() + "]")
                        ),
                        body(HTML_DEFAULT_FONT,
                                a("/", "Home"), a("/users", "View Existing Users"),
                                h1("Detailed information of User \"" + user.getName() + "\""),
                                buildUserInfo(user),
                                buildBookingInfo(bookings)
                        )
                );
        return html.toString();
    }

    private Element buildUserInfo(User user) {
        Element list = ul();
        list.addChild(li("User ID: " + user.getUid()));
        list.addChild(li("Name: " + user.getName()));
        list.addChild(li("Email: " + user.getEmail()));
        return list;
    }

    private Element buildBookingInfo(Iterable<Booking> bookings) {
        if (!bookings.iterator().hasNext()) {
            return p("No bookings associated with this user.");
        }

        return new HtmlTableBuilder<>(bookings)
                .withColumn("Booking ID",
                    booking -> a("/rooms/" + booking.getRid() + "/bookings/" + booking.getBid(), "" + booking.getBid()))
                .withColumn("Begin Date",
                    booking -> DateUtils.formatDate(booking.getBeginInst(), "dd-MM-yyyy HH:mm (z)"))
                .withColumn("End Date",
                    booking -> DateUtils.formatDate(booking.getEndInst(), "dd-MM-yyyy HH:mm (z)"))
                .build();
    }
}
