package pt.isel.ls.model.entities;

public interface Entity {

    enum EntityType {
        BOOKING,
        LABEL,
        ROOM,
        USER,
        COMMAND,
        TIME,
        MESSAGE
    }

    EntityType getEntityType();
}
