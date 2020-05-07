package pt.isel.ls.model.entities;

public class Message implements Entity {
    private String message;

    public Message(String message) {
        this.message = message;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.MESSAGE;
    }

    public String getMessage() {
        return message;
    }
}
