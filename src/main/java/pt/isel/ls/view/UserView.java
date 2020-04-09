package pt.isel.ls.view;

import pt.isel.ls.model.entities.Entity;
import pt.isel.ls.model.entities.User;

import java.io.IOException;
import java.io.OutputStream;

public class UserView extends View {

    protected UserView(Entity entity) {
        super(entity);
    }

    @Override
    public void displayText(OutputStream out) throws IOException {
        User user = (User) entity;
        StringBuilder builder = new StringBuilder();

        appendId(user, builder);
        appendName(user, builder);
        appendEmail(user, builder);

        builder.append('\n');
        out.write(builder.toString().getBytes());
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
