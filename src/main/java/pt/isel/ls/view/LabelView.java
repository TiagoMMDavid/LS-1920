package pt.isel.ls.view;

import pt.isel.ls.model.entities.Entity;
import pt.isel.ls.model.entities.Label;

import java.io.IOException;
import java.io.OutputStream;

public class LabelView extends View {
    protected LabelView(Entity entity) {
        super(entity);
    }


    @Override
    public void displayText(OutputStream out) throws IOException {
        Label label = (Label) entity;
        StringBuilder builder = new StringBuilder();

        appendId(label, builder);
        appendName(label, builder);
        builder.append('\n');

        out.write(builder.toString().getBytes());
    }

    @Override
    public void displayHtml(OutputStream out) throws IOException {
        // TODO
    }

    private void appendName(Label label, StringBuilder builder) {
        String name = label.getName();
        if (name != null) {
            builder.append("\nName: ");
            builder.append(name);
        }
    }

    private void appendId(Label label, StringBuilder builder) {
        builder.append("Label ID: ");
        builder.append(label.getLid());
    }
}
