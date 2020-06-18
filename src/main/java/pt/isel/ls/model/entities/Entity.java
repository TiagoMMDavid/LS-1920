package pt.isel.ls.model.entities;

public interface Entity {

    /**
     * Enum containing every type of entity available. New entities should be added here.
     */
    enum EntityType {
        BOOKING,
        LABEL,
        ROOM,
        USER,
        COMMAND
    }

    /**
     * Method used in order for other classes to recognize the entity's type
     * @return the entity's type
     */
    EntityType getEntityType();
}
