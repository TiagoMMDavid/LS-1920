package pt.isel.ls.view;

import pt.isel.ls.model.entities.Command;
import pt.isel.ls.model.entities.Entity;

import java.io.IOException;
import java.io.OutputStream;

public class CommandView extends View {
    public CommandView(Entity ent) {
        super(ent);
    }

    @Override
    public void displayText(OutputStream out) throws IOException {
        Command cmd = (Command) entity;
        String output = cmd.getCommandDescription() + '\n';
        out.write(output.getBytes());
    }

    @Override
    public void displayHtml(OutputStream out) throws IOException {
        // TODO
    }
}
