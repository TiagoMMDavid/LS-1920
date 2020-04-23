package pt.isel.ls.view;

import pt.isel.ls.model.entities.Entity;
import pt.isel.ls.model.entities.Time;

import java.text.SimpleDateFormat;

import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.title;
import static pt.isel.ls.utils.html.HtmlDsl.p;

public class TimeView extends View {

    public TimeView(Iterable<Entity> ent) {
        super(ent);
    }

    @Override
    public String displayText() {
        return getTime() + "\n\n";
    }

    @Override
    public String displayHtml() {
        return
                html(
                        head(
                                title("Current Time")
                        ),
                        body(
                                h1("Current Time"),
                                p(getTime())
                        )
                ).toString() + '\n';
    }

    private String getTime() {
        Time time = (Time) entity;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(time.getTime());
    }
}
