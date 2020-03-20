package pt.isel.ls.model.commands.common;

import java.util.Iterator;
import java.util.LinkedList;

public class CommandResult implements Iterable<String> {
    private boolean success;
    private String title;
    private LinkedList<String> results;

    public CommandResult() {
        this.success = true;
    }

    public CommandResult(boolean success, String msg) {
        this.success = success;
        this.title = msg;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addResult(String result) {
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

    public String getTitle() {
        return title;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public Iterator<String> iterator() {
        return results != null ? results.iterator() : null;
    }
}
