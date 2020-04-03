package pt.isel.ls.model.commands.common;

import pt.isel.ls.model.entities.Entity;

import java.util.Iterator;
import java.util.LinkedList;

public class CommandResult implements Iterable<Entity> {
    private boolean success;
    private LinkedList<Entity> results = new LinkedList<>();

    public CommandResult() {
        this.success = true;
    }

    public CommandResult(boolean success) {
        this.success = success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void addResult(Entity result) {
        results.add(result);
    }

    public void clearResults() {
        results.clear();
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public Iterator<Entity> iterator() {
        return results.iterator();
    }
}
