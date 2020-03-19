package pt.isel.ls.model.paths;

import java.util.LinkedList;

abstract class PathCommon {
    protected LinkedList<Directory> path;

    protected PathCommon(String path) {
        this.path = new LinkedList<>();
        parsePath(path);
    }

    protected void parsePath(String path) throws IllegalArgumentException {
        if (path == null || !isValid(path)) {
            throw new IllegalArgumentException("Wrong template format");
        }
        path = path.substring(1);
        String[] paths = path.split("/");
        if (paths.length == 0) {
            throw new IllegalArgumentException("Wrong format");
        }
        if (!paths[0].isEmpty()) {
            for (String str : paths) {
                if (str.isEmpty()) {
                    throw new IllegalArgumentException("Wrong format");
                }
                addDirectory(str);
            }
        }
    }

    protected abstract void addDirectory(String str);

    protected boolean isValid(String path) {
        return !path.isEmpty() && path.charAt(0) == '/';
    }
}
