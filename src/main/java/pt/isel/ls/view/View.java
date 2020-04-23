package pt.isel.ls.view;

import pt.isel.ls.model.entities.Entity;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

public abstract class View {

    protected Iterable<Entity> entities;

    // Used for commands with a single entity, for example, GET /time
    protected Entity entity;

    public static View getInstance(Iterable<Entity> entities) {
        Iterator<Entity> iter = entities.iterator();
        if (!iter.hasNext()) {
            return null;
        }
        // We know all entities in the result have the same type
        switch (iter.next().getEntityType()) {
            case BOOKING:
                return new BookingView(entities);
            case USER:
                return new UserView(entities);
            case LABEL:
                return new LabelView(entities);
            case ROOM:
                return new RoomView(entities);
            case COMMAND:
                return new CommandView(entities);
            case TIME:
                return new TimeView(entities);
            default:
                return null;
        }
    }

    protected View(Iterable<Entity> entities) {
        this.entities = entities;
        this.entity = entities.iterator().next();
    }

    public void display(OutputStream out, String viewFormat) throws IOException {
        String text = "";
        if (viewFormat == null || viewFormat.equals("text/plain")) {
            text = displayText();

        } else if (viewFormat.equals("text/html")) {
            text = displayHtml() + '\n';
        }
        out.write((text).getBytes());
    }


    public abstract String displayText();


    public abstract String displayHtml();
}
