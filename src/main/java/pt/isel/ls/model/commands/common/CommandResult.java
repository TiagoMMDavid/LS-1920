package pt.isel.ls.model.commands.common;

import pt.isel.ls.model.entities.Entity;

import java.util.Iterator;
import java.util.LinkedList;

public class CommandResult implements Iterable<Entity> {

    private LinkedList<Entity> results = new LinkedList<>();

    public void addResult(Entity result) {
        results.add(result);
    }

    public void clearResults() {
        results.clear();
    }

    @Override
    public Iterator<Entity> iterator() {
        return results.iterator();
    }
}
