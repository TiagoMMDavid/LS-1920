package pt.isel.ls.view;

import pt.isel.ls.model.entities.Entity;
import pt.isel.ls.model.entities.Time;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

public class TimeView extends View {

    public TimeView(Entity ent) {
        super(ent);
    }

    @Override
    public void displayText(OutputStream out) throws IOException {
        Time time = (Time) entity;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        out.write((formatter.format(time.getTime()) + '\n').getBytes());
    }

    @Override
    public void displayHtml(OutputStream out) throws IOException {
        // TODO
    }
}
