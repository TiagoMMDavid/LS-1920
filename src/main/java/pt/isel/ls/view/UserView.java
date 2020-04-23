package pt.isel.ls.view;

import pt.isel.ls.model.entities.Entity;
import pt.isel.ls.model.entities.User;
import pt.isel.ls.utils.html.elements.Element;

import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.table;
import static pt.isel.ls.utils.html.HtmlDsl.td;
import static pt.isel.ls.utils.html.HtmlDsl.th;
import static pt.isel.ls.utils.html.HtmlDsl.tr;
import static pt.isel.ls.utils.html.HtmlDsl.title;

public class UserView extends View {

    protected UserView(Iterable<Entity> entity) {
        super(entity);
    }

    @Override
    public String displayText() {
        StringBuilder builder = new StringBuilder();
        for (Entity entity : entities) {
            User user = (User) entity;
            appendId(user, builder);
            appendName(user, builder);
            appendEmail(user, builder);
            builder.append("\n\n");
        }
        return builder.toString();
    }

    @Override
    public String displayHtml() {
        Element html =
                html(
                        head(
                                title("Users")
                        ),
                        body(
                                h1("List of Users:"),
                                buildHtmlTable()
                        )
                );
        return html.toString();
    }

    private Element buildHtmlTable() {
        Element tableRow = tr();
        tableRow.addChild(th("UID"));
        tableRow.addChild(th("Name"));
        tableRow.addChild(th("E-mail"));

        Element table = table();
        table.addChild(tableRow);
        for (Entity entity : entities) {
            addHtmlTableRow(table, (User) entity);
        }
        return table;
    }

    private void addHtmlTableRow(Element table, User user) {
        Element tableRowData = tr();
        tableRowData.addChild(td(user.getUid()));
        tableRowData.addChild(td(user.getName() == null ? "N/A" : user.getName()));
        tableRowData.addChild(td(user.getEmail() == null ? "N/A" : user.getEmail()));
        table.addChild(tableRowData);
    }

    private void appendEmail(User user, StringBuilder builder) {
        String email = user.getEmail();
        if (email != null) {
            builder.append("\nEmail: ");
            builder.append(email);
        }
    }

    private void appendName(User user, StringBuilder builder) {
        String name = user.getName();
        if (name != null) {
            builder.append("\nName: ");
            builder.append(name);
        }
    }

    private void appendId(User user, StringBuilder builder) {
        builder.append("User ID: ");
        builder.append(user.getUid());
    }
}
