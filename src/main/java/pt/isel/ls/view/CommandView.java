package pt.isel.ls.view;

import pt.isel.ls.model.entities.Command;
import pt.isel.ls.model.entities.Entity;
import pt.isel.ls.utils.html.elements.Element;

import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.h2;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.title;
import static pt.isel.ls.utils.html.HtmlDsl.p;

public class CommandView extends View {
    public CommandView(Iterable<Entity> ent) {
        super(ent);
    }

    @Override
    public String displayText() {
        StringBuilder builder = new StringBuilder();
        for (Entity entity : entities) {
            Command cmd = (Command) entity;
            builder.append(cmd.getName());
            builder.append(" - ");
            builder.append(cmd.getDescription());
            builder.append('\n');
        }
        return builder.toString();
    }

    @Override
    public String displayHtml() {
        Element html =
                html(
                        head(
                                title("Commands")
                        ),
                        buildHtmlBody()
                );
        return html.toString();
    }

    private Element buildHtmlBody() {
        Element body = body(
                h1("List of Commands:")
        );
        for (Entity entity : entities) {
            Command command = (Command) entity;
            body.addChild(h2(command.getName()));
            body.addChild(p(command.getDescription().replace("\n", "<br>")));
        }
        return body;
    }
}
