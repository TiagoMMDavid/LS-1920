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
import static pt.isel.ls.utils.html.HtmlDsl.h2;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.hr;
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
import static pt.isel.ls.utils.html.elements.Input.attrib;

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
                        body(HTML_DEFAULT_FONT,
                                a("/", "Home"), a("/rooms/create", "Create a Room"),
                                h1("Search for desired Room"),
                                form("get", "/rooms",
                                        div(
                                                label("begin", "Enter begin date: "),
                                                input(DATETIME_LOCAL,
                                                        attrib("name", "begin"),
                                                        attrib("id", "begin"),
                                                        attrib("value", currDate)
                                                ),
                                                br(), br()
                                        ),
                                        div(
                                                label("duration", "Enter duration time: "),
                                                input(TIME,
                                                        attrib("name", "duration"),
                                                        attrib("id", "duration"),
                                                        attrib("value", "00:10")
                                                ),
                                                br(), br()
                                        ),
                                        div(
                                                label("capacity", "Select minimum capacity: "),
                                                input(NUMBER,
                                                        attrib("name", "capacity"),
                                                        attrib("id", "capacity"),
                                                        attrib("placeholder", "Capacity"),
                                                        attrib("min", "1")
                                                ),
                                                br(), br()
                                        ),
                                        hr(),
                                        buildLabelCheckboxes(),
                                        br(),
                                        input(SUBMIT, attrib("name","submit"), attrib("value","Search"))
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
            div.addChild(h2("Select Labels"));
            for (Label label : result.getLabels()) {
                String currLid = "lid" + label.getLid();
                div.addChild(
                        input(CHECKBOX,
                                attrib("name", "label"),
                                attrib("id", currLid),
                                attrib("value", label.getName())
                        )
                );
                div.addChild(label(currLid, label.getName()));
                div.addChild(br());
            }
        }
        return div;
    }
}

