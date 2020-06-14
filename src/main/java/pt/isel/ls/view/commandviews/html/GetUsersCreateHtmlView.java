package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetUsersCreateResult;

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
import static pt.isel.ls.utils.html.elements.Input.InputType.SUBMIT;
import static pt.isel.ls.utils.html.elements.Input.InputType.TEXT;
import static pt.isel.ls.utils.html.elements.Input.attrib;
import static pt.isel.ls.view.commandviews.helpers.ErrorHelper.getResultError;

public class GetUsersCreateHtmlView extends HtmlView {

    private GetUsersCreateResult result;

    public GetUsersCreateHtmlView(CommandResult commandResult) {
        this.result = (GetUsersCreateResult) commandResult;
    }

    @Override
    public String display() {
        String regex = "^(?! )[^<>]*(?<! )$";
        return
                html(
                        head(
                                title("User Creation")
                        ),
                        body(HTML_DEFAULT_FONT,
                                a("/", "Home"), a("/users", "View Existing Users"),
                                h1("Create a User"),
                                form("post", "/users/create",
                                        div(
                                                label("name", "Enter name: "),
                                                input(TEXT,
                                                        attrib("value", result.getPreviousName()),
                                                        attrib("name", "name"),
                                                        attrib("id", "name"),
                                                        attrib("placeholder", "Name"),
                                                        attrib("required", "true"),
                                                        attrib("pattern", regex),
                                                        attrib("maxlength", "50")
                                                ),
                                                p(
                                                        70, "red",
                                                        getResultError(result,
                                                                "name", "Name", result.getPreviousName(), true)
                                                )
                                        ),
                                        div(
                                                label("email", "Enter email: "),
                                                input(TEXT,
                                                        attrib("value", result.getPreviousEmail()),
                                                        attrib("name", "email"),
                                                        attrib("id", "email"),
                                                        attrib("placeholder", "Email"),
                                                        attrib("maxlength", "50")
                                                ),
                                                p(
                                                        70, "red",
                                                        getResultError(result,
                                                                "email", "Email", result.getPreviousEmail(), false)
                                                )
                                        ),
                                        br(),
                                        input(SUBMIT, attrib("name","submit"), attrib("value","Create"))
                                )
                        )
                ).toString();
    }
}

