package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetLabelsCreateResult;

import static pt.isel.ls.utils.html.HtmlDsl.a;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.div;
import static pt.isel.ls.utils.html.HtmlDsl.form;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.input;
import static pt.isel.ls.utils.html.HtmlDsl.label;
import static pt.isel.ls.utils.html.HtmlDsl.p;
import static pt.isel.ls.utils.html.HtmlDsl.title;
import static pt.isel.ls.utils.html.elements.Input.InputType.SUBMIT;
import static pt.isel.ls.utils.html.elements.Input.InputType.TEXT;
import static pt.isel.ls.utils.html.elements.Input.attrib;

public class GetLabelsCreateHtmlView extends HtmlView {

    private GetLabelsCreateResult result;

    public GetLabelsCreateHtmlView(CommandResult commandResult) {
        this.result = (GetLabelsCreateResult) commandResult;
    }

    @Override
    public String display() {
        String regex = "^([A-Za-z0-9-_]+ )+[A-Za-z0-9-_]+$|^[A-Za-z0-9-_]+$";
        return
                html(
                        head(
                                title("Label Creation")
                        ),
                        body(HTML_DEFAULT_FONT,
                                a("/", "Home"),
                                h1("Create a Label"),
                                form("post", "/labels/create",
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
                                                p(result.wasError()
                                                        ? "Name '" + result.getPreviousName() + "' already exists!"
                                                        : "")
                                        ),
                                        input(SUBMIT, attrib("name","submit"), attrib("value","Create"))
                                )
                        )
                ).toString();
    }
}

