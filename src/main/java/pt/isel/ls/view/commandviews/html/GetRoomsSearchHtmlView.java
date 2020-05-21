package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetRoomsSearchResult;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.utils.html.elements.Element;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

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
import static pt.isel.ls.utils.html.HtmlDsl.p;
import static pt.isel.ls.utils.html.HtmlDsl.title;
import static pt.isel.ls.utils.html.elements.Input.InputType.CHECKBOX;
import static pt.isel.ls.utils.html.elements.Input.InputType.DATETIME_LOCAL;
import static pt.isel.ls.utils.html.elements.Input.InputType.NUMBER;
import static pt.isel.ls.utils.html.elements.Input.InputType.SUBMIT;
import static pt.isel.ls.utils.html.elements.Input.InputType.TIME;

public class GetRoomsSearchHtmlView extends HtmlView {

    private GetRoomsSearchResult result;

    public GetRoomsSearchHtmlView(CommandResult commandResult) {
        this.result = (GetRoomsSearchResult) commandResult;
    }

    @Override
    public String display() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String currDate = formatter.format(new Date()).replace(' ', 'T');

        return
                html(
                        head(
                                title("Rooms Search")
                        ),
                        body(
                                a("/", "Home"),
                                h1("Search for desired Room"),
                                form("get", "/rooms",
                                    div(
                                        label("begin", "Enter begin date: "),
                                        input(DATETIME_LOCAL, "begin", currDate, "begin")
                                    ),
                                    div(
                                        label("duration", "Enter duration time: "),
                                        input(TIME, "duration", "00:10", "duration")
                                    ),
                                    div(
                                        label("capacity", "Select minimum capacity: "),
                                        input(NUMBER, "capacity", "1", "capacity", "1")
                                    ),
                                    buildLabelCheckboxes(),
                                    input(SUBMIT, "submit", "Search")
                                )
                        )
                ).toString();
    }

    private Element buildLabelCheckboxes() {
        Element div = div();
        Iterator<Label> iter = result.getLabels().iterator();
        if (!iter.hasNext()) {
            div.addChild(p("No labels available."));
        } else {
            for (Label label : result.getLabels()) {
                String currLid = "lid" + label.getLid();
                div.addChild(input(CHECKBOX,
                        "label",
                        label.getName(),
                        currLid));
                div.addChild(label(currLid, label.getName()));
                div.addChild(br());
            }
        }
        return div;
    }
}

