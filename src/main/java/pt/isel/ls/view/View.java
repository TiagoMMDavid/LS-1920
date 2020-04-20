package pt.isel.ls.view;

import pt.isel.ls.model.entities.Entity;

import java.io.IOException;
import java.io.OutputStream;

public abstract class View {
    protected Entity entity;

    public static View getInstance(Entity ent) {
        switch (ent.getEntityType()) {
            case BOOKING:
                return new BookingView(ent);
            case USER:
                return new UserView(ent);
            case LABEL:
                return new LabelView(ent);
            case ROOM:
                return new RoomView(ent);
            case COMMAND:
                return new CommandView(ent);
            case TIME:
                return new TimeView(ent);
            default:
                return null;
        }
    }

    protected View(Entity entity) {
        this.entity = entity;
    }

    public void display(OutputStream out, String viewFormat) throws IOException {
        if (viewFormat == null || viewFormat.equals("text/plain")) {
            displayText(out);
        } else if (viewFormat.equals("text/html")) {
            displayHtml(out);
        }
    }

    public abstract void displayText(OutputStream out) throws IOException;


    public abstract void displayHtml(OutputStream out) throws IOException;
}
