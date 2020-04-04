package pt.isel.ls.view;

import pt.isel.ls.model.entities.Entity;

import java.io.IOException;
import java.io.OutputStream;

public abstract class View {
    protected Entity context;

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
            default:
                return null;
        }
    }

    protected View(Entity entity) {
        context = entity;
    }

    public abstract void displayText(OutputStream out) throws IOException;
}
