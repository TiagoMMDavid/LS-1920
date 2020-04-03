package pt.isel.ls.model.commands.common;

import java.util.Iterator;
import java.util.LinkedList;

public class CommandResult<T> implements Iterable<T>{
    private boolean success;
    private LinkedList<T> results = new LinkedList<>();

    public CommandResult() {
        this.success = true;
    }

    public CommandResult(boolean success) {
        this.success = success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void addResult(T result) {
        if (results == null) {
            results = new LinkedList<>();
        }
        results.add(result);
    }

    public void clearResults() {
        if (results != null) {
            results.clear();
            results = null;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public Iterator<T> iterator() {
        return results.iterator();
    }
}
