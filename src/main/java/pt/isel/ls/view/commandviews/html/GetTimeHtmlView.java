package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetTimeResult;

import java.text.SimpleDateFormat;
import java.util.Date;

import static pt.isel.ls.utils.html.HtmlDsl.a;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.p;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class GetTimeHtmlView extends HtmlView {

    private GetTimeResult result;

    public GetTimeHtmlView(CommandResult commandResult) {
        result = (GetTimeResult)commandResult;
    }

    @Override
    public String display() {
        return
                html(
                        head(
                                title("Current Time")
                        ),
                        body(HTML_DEFAULT_FONT,
                                a("/", "Home"),
                                h1("Current Time"),
                                p(getTime(result.getTime()))
                        )
                ).toString() + '\n';
    }

    private String getTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

}
