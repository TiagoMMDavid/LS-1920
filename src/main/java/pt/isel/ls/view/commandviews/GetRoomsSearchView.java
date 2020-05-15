package pt.isel.ls.view.commandviews;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetRoomsSearchResult;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.utils.html.elements.Element;
import pt.isel.ls.view.View;

import java.util.Iterator;

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

public class GetRoomsSearchView extends View {

    private GetRoomsSearchResult result;

    public GetRoomsSearchView(CommandResult commandResult) {
        this.result = (GetRoomsSearchResult) commandResult;
    }

    @Override
    public String displayText() {
        return "No textual representation available, please use the accept:text/html header";
    }

    @Override
    public String displayHtml() {
        return
                html(
                        head(
                                title("Rooms Search")
                        ),
                        body(
                                h1("Search for desired Room"),
                                form("get", "/rooms",
                                    div(
                                        label("begin", "Enter begin date: "),
                                        input(DATETIME_LOCAL, "begin", "", "begin")
                                    ),
                                    div(
                                        label("duration", "Enter duration time: "),
                                        input(TIME, "duration", "", "duration")
                                    ),
                                    div(
                                        label("capacity", "Select minimum capacity: "),
                                        input(NUMBER, "capacity", "0", "capacity")
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
                        currLid,
                        "",
                        currLid));
                div.addChild(label(currLid, label.getName()));
                div.addChild(br());
            }
        }
        return div;
    }
}

