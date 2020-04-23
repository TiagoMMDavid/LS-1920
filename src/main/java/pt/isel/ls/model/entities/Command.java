package pt.isel.ls.model.entities;

import pt.isel.ls.utils.Pair;

public class Command implements Entity {
    private Pair<String,String> commandInfo;

    public Command(String commandName, String commandDescription) {
        this.commandInfo = new Pair<>(commandName, commandDescription);
    }

    public Command(Pair<String,String> commandInfo) {
        this.commandInfo = commandInfo;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.COMMAND;
    }

    public String getName() {
        return commandInfo.first;
    }

    public String getDescription() {
        return commandInfo.second;
    }
}
