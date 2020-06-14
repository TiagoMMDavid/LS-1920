package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetRoomsCreateResult;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.utils.html.elements.Element;

import java.util.Iterator;
import java.util.List;

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
import static pt.isel.ls.utils.html.elements.Input.InputType.NUMBER;
import static pt.isel.ls.utils.html.elements.Input.InputType.SUBMIT;
import static pt.isel.ls.utils.html.elements.Input.InputType.TEXT;
import static pt.isel.ls.utils.html.elements.Input.attrib;
import static pt.isel.ls.view.commandviews.helpers.ErrorHelper.getResultError;

public class GetRoomsCreateHtmlView extends HtmlView {

    private GetRoomsCreateResult result;

    public GetRoomsCreateHtmlView(CommandResult commandResult) {
        this.result = (GetRoomsCreateResult) commandResult;
    }

    @Override
    public String display() {
        String regex = "^(?! )[^<>]*(?<! )$";
        return
                html(
                        head(
                                title("Room Creation")
                        ),
                        body(HTML_DEFAULT_FONT,
                                a("/", "Home"), a("/rooms/search", "Search for Rooms"),
                                h1("Create a Room"),
                                form("post", "/rooms/create",
                                        div(
                                                label("name", "Enter name: "),
                                                input(TEXT,
                                                        attrib("value", result.getPreviousName()),
                                                        attrib("name", "name"),
                                                        attrib("id", "name"),
                                                        attrib("placeholder", "Name"),
                                                        attrib("required", "true"),
                                                        attrib("pattern", regex),
                                                        attrib("maxlength", "30")
                                                ),
                                                p(
                                                        70, "red",
                                                        getResultError(result,
                                                                "name", "Name", result.getPreviousName(), false)
                                                )
                                        ),
                                        div(
                                                label("description", "Enter description: "),
                                                input(TEXT,
                                                        attrib("value", result.getPreviousDescription()),
                                                        attrib("name", "description"),
                                                        attrib("id", "description"),
                                                        attrib("placeholder", "Description"),
                                                        attrib("pattern", regex),
                                                        attrib("maxlength", "50")
                                                ),
                                                p(
                                                        70, "red",
                                                        getResultError(result, "description",
                                                                "Description", result.getPreviousDescription(), true)
                                                )
                                        ),
                                        div(
                                                label("location", "Enter location: "),
                                                input(TEXT,
                                                        attrib("value", result.getPreviousLocation()),
                                                        attrib("name", "location"),
                                                        attrib("id", "location"),
                                                        attrib("placeholder", "Location"),
                                                        attrib("required", "true"),
                                                        attrib("pattern", regex),
                                                        attrib("maxlength", "50")
                                                ),
                                                p(
                                                        70, "red",
                                                        getResultError(result, "location",
                                                                "Location", result.getPreviousLocation(), true)
                                                )
                                        ),
                                        div(
                                                label("capacity", "Enter capacity: "),
                                                input(NUMBER,
                                                        attrib("name", "capacity"),
                                                        attrib("id", "capacity"),
                                                        attrib("placeholder", "Capacity"),
                                                        attrib("value", result.getPreviousCapacity()),
                                                        attrib("min", "1")
                                                ),
                                                br(), br()
                                        ),
                                        hr(),
                                        buildLabelCheckboxes(),
                                        br(),
                                        input(SUBMIT, attrib("name","submit"), attrib("value","Create"))
                                )
                        )
                ).toString();
    }

    private Element buildLabelCheckboxes() {
        Element div = div();
        Iterator<Label> iter = result.getLabels().iterator();
        List<String> previousLabelNames = result.getPreviousLabels();
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
                                attrib("value", label.getName()),
                                attrib(previousLabelNames != null && previousLabelNames.contains(label.getName())
                                        ? "checked"
                                        : "", "")
                        )
                );
                div.addChild(label(currLid, label.getName()));
                div.addChild(br());
            }
        }
        return div;
    }
}

