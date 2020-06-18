package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetBookingsCreateResult;
import pt.isel.ls.model.entities.User;
import pt.isel.ls.utils.DateUtils;
import pt.isel.ls.utils.html.elements.Element;

import java.text.SimpleDateFormat;
import java.util.Date;

import static pt.isel.ls.utils.html.HtmlDsl.a;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.br;
import static pt.isel.ls.utils.html.HtmlDsl.div;
import static pt.isel.ls.utils.html.HtmlDsl.form;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.input;
import static pt.isel.ls.utils.html.HtmlDsl.label;
import static pt.isel.ls.utils.html.HtmlDsl.option;
import static pt.isel.ls.utils.html.HtmlDsl.p;
import static pt.isel.ls.utils.html.HtmlDsl.select;
import static pt.isel.ls.utils.html.HtmlDsl.title;
import static pt.isel.ls.utils.html.elements.Input.InputType.DATETIME_LOCAL;
import static pt.isel.ls.utils.html.elements.Input.InputType.SUBMIT;
import static pt.isel.ls.utils.html.elements.Input.InputType.TIME;
import static pt.isel.ls.utils.html.elements.Input.attrib;
import static pt.isel.ls.view.commandviews.helpers.ErrorHelper.getResultError;

public class GetBookingsCreateHtmlView extends HtmlView {

    private GetBookingsCreateResult result;

    public GetBookingsCreateHtmlView(CommandResult commandResult) {
        this.result = (GetBookingsCreateResult) commandResult;
    }

    @Override
    public String display() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String beginDate = result.getPreviousBeginInst().isEmpty()
                ? formatter.format(DateUtils.roundDateToTenMin(new Date())).replace(' ', 'T')
                : result.getPreviousBeginInst();

        String duration = result.getPreviousDuration().isEmpty() ? "00:10" : result.getPreviousDuration();

        String roomId = result.getRoomId();

        return
                html(
                        head(
                                title("Booking Creation")
                        ),
                        body(HTML_DEFAULT_FONT,
                                a("/", "Home"), a("/rooms/" + roomId, "Return to Room [" + roomId + "]"),
                                a("/rooms/" + roomId + "/bookings", "Bookings of Room [" + roomId + "]"),
                                h1("Create a Booking for Room [" + roomId + "]"),
                                form("post", "/rooms/" + roomId + "/bookings/create",
                                        div(
                                                label("uid", "Select user: "),
                                                buildUsersDropdown(),
                                                br(), br()
                                        ),
                                        div(
                                                label("begin", "Enter begin date: "),
                                                input(DATETIME_LOCAL,
                                                        attrib("name", "begin"),
                                                        attrib("id", "begin"),
                                                        attrib("value", beginDate),
                                                        attrib("required", "true"),
                                                        attrib("step", "600") // 600 seconds = 10 min
                                                ),
                                                p(
                                                        70, "red",
                                                        getResultError(result,
                                                                "begin", "Date", result.getPreviousBeginInst(), false))
                                        ),
                                        div(
                                                label("duration", "Enter duration time: "),
                                                input(TIME,
                                                        attrib("name", "duration"),
                                                        attrib("id", "duration"),
                                                        attrib("value", duration),
                                                        attrib("required", "true"),
                                                        attrib("step", "600")
                                                ),
                                                p(
                                                        70, "red",
                                                        getResultError(result,
                                                                "duration", "Duration",
                                                                result.getPreviousDuration(), true))
                                        ),
                                        br(),
                                        input(SUBMIT, attrib("name","submit"), attrib("value","Create"))
                                )
                        )
                ).toString();
    }

    private Element buildUsersDropdown() {
        Element select = select("uid", "uid");
        for (User user : result.getUsers()) {
            String userId = "" + user.getUid();
            select.addChild(
                    option(userId, user.getName() + " (" + user.getEmail() + ")",
                            userId.equals(result.getPreviousUserId()))
            );
        }
        return select;
    }
}
