package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetUserByIdResult;
import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.model.entities.User;
import pt.isel.ls.utils.html.elements.Element;

import static pt.isel.ls.utils.html.HtmlDsl.a;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.li;
import static pt.isel.ls.utils.html.HtmlDsl.p;
import static pt.isel.ls.utils.html.HtmlDsl.table;
import static pt.isel.ls.utils.html.HtmlDsl.td;
import static pt.isel.ls.utils.html.HtmlDsl.th;
import static pt.isel.ls.utils.html.HtmlDsl.title;
import static pt.isel.ls.utils.html.HtmlDsl.tr;
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
                        body(
                                a("/", "Home"), a("/users", "Users"),
                                h1("Detailed information of User \"" + user.getName() + "\""),
                                buildUserInfo(user),
                                buildBookingInfo(bookings)
                        )
                );
        return html.toString();
    }

    private Element buildBookingInfo(Iterable<Booking> bookings) {
        if (!bookings.iterator().hasNext()) {
            return p("No bookings associated with this user.");
        }

        Element bookingHeaders = tr();
        bookingHeaders.addChild(th("Booking ID"));
        bookingHeaders.addChild(th("Begin"));
        bookingHeaders.addChild(th("End"));
        Element bookingInfo = table();
        bookingInfo.addChild(bookingHeaders);

        for (Booking booking : bookings) {
            bookingInfo.addChild(buildBookingRow(booking));
        }

        return bookingInfo;
    }

    private Element buildBookingRow(Booking booking) {
        Element tableRowData = tr();
        tableRowData.addChild(td(a("/rooms/" + booking.getRid()
                + "/bookings/" + booking.getBid(),
                String.valueOf(booking.getBid()))));
        tableRowData.addChild(td(booking.getBeginInst().toString()));
        tableRowData.addChild(td(booking.getEndInst().toString()));
        return tableRowData;
    }

    private Element buildUserInfo(User user) {
        Element list = ul();
        list.addChild(li("Name: " + user.getName()));
        list.addChild(li("Email: " + user.getEmail()));
        return list;
    }


}
