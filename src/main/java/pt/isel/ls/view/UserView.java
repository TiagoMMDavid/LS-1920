package pt.isel.ls.view;

import pt.isel.ls.model.entities.Entity;
import pt.isel.ls.model.entities.User;
import pt.isel.ls.utils.html.elements.Element;

import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.li;
import static pt.isel.ls.utils.html.HtmlDsl.table;
import static pt.isel.ls.utils.html.HtmlDsl.td;
import static pt.isel.ls.utils.html.HtmlDsl.th;
import static pt.isel.ls.utils.html.HtmlDsl.tr;
import static pt.isel.ls.utils.html.HtmlDsl.title;
import static pt.isel.ls.utils.html.HtmlDsl.ul;

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
            if (!user.isPost()) {
                appendName(user, builder);
                if (user.isDetailed()) {
                    appendEmail(user, builder);
                }
            }
            builder.append("\n\n");
        }
        return builder.toString();
    }

    @Override
    public String displayHtml() {
        User user = (User) entity;
        String header = user.isDetailed() ? "Detailed Information for User:" : "List of Users:";
        Element html =
                html(
                        head(
                                title("Users")
                        ),
                        body(
                                h1(header),
                                buildUserInfo(user)
                        )
                );
        return html.toString();
    }

    private Element buildUserInfo(User user) {
        Element userInfo;
        if (user.isDetailed() || user.isPost()) {
            userInfo = ul();
            userInfo.addChild(li("User ID: " + user.getUid()));
            if (user.isDetailed()) {
                userInfo.addChild(li("User Name: " + user.getName()));
                userInfo.addChild(li("User Email: " + (user.getEmail() == null ? "N/A" : user.getEmail())));
            }
        } else {
            userInfo = table();
            userInfo.addChild(th("User ID"));
            userInfo.addChild(th("Name"));
            for (Entity entity : entities) {
                addHtmlTableRow(userInfo, (User) entity);
            }

        }
        return userInfo;
    }

    private void addHtmlTableRow(Element table, User user) {
        Element tableRowData = tr();
        tableRowData.addChild(td(user.getUid()));
        tableRowData.addChild(td(user.getName() == null ? "N/A" : user.getName()));
        table.addChild(tableRowData);
    }

    private void appendEmail(User user, StringBuilder builder) {
        String email = user.getEmail();
        builder.append("\nEmail: ");
        builder.append(email == null ? "N/A" : email);
    }

    private void appendName(User user, StringBuilder builder) {
        String name = user.getName();
        builder.append("\nName: ");
        builder.append(name == null ? "N/A" : name);
    }

    private void appendId(User user, StringBuilder builder) {
        builder.append("User ID: ");
        builder.append(user.getUid());
    }
}
