package pt.isel.ls.model.entities;

public class Command implements Entity {
    private String commandDescription;

    public Command(String commandDescription) {
        this.commandDescription = commandDescription;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.COMMAND;
    }

    public String getCommandDescription() {
        return commandDescription;
    }
}
