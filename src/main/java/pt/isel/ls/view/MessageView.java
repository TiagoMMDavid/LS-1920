package pt.isel.ls.view;

import pt.isel.ls.model.entities.Entity;
import pt.isel.ls.model.entities.Message;

import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.p;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class MessageView extends View {
    protected MessageView(Iterable<Entity> entity) {
        super(entity);
    }

    @Override
    public String displayText() {
        return ((Message) entity).getMessage();
    }

    public String displayHtml() {
        return
                html(
                        head(
                                title("Message")
                        ),
                        body(
                                p(((Message) entity).getMessage())
                        )
                ).toString() + '\n';
    }
}
