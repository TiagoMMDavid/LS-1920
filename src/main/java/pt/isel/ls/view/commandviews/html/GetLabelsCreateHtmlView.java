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
import static pt.isel.ls.view.commandviews.helpers.ErrorHelper.getResultError;

public class GetLabelsCreateHtmlView extends HtmlView {

    private GetLabelsCreateResult result;

    public GetLabelsCreateHtmlView(CommandResult commandResult) {
        this.result = (GetLabelsCreateResult) commandResult;
    }

    @Override
    public String display() {
        String regex = "^(?! )[^<>]*(?<! )$";
        return
                html(
                        head(
                                title("Label Creation")
                        ),
                        body(HTML_DEFAULT_FONT,
                                a("/", "Home"), a("/labels", "View Existing Labels"),
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
                                                p(
                                                        70, "red",
                                                        getResultError(result,
                                                                "name", "Name", result.getPreviousName(), false)
                                                )
                                        ),
                                        input(SUBMIT, attrib("name","submit"), attrib("value","Create"))
                                )
                        )
                ).toString();
    }
}

