package pt.isel.ls.model.paths;

import java.util.LinkedList;

abstract class BasePath {

    protected LinkedList<Directory> path;
    protected String pathString;

    protected BasePath(String path) {
        this.path = new LinkedList<>();
        parsePath(path);
    }

    /**
     * Splits the string and parses it accordingly, adding each valid string to the list of Directories.
     * @param path the path to be processed
     * @throws IllegalArgumentException in case the given path is invalid
     */
    protected void parsePath(String path) throws IllegalArgumentException {
        StringBuilder pathStringBuilder = new StringBuilder();
        if (!isValid(path)) {
            throw new IllegalArgumentException("Wrong path format");
        }
        path = path.substring(1);
        String[] paths = path.split("/");
        if (paths.length == 0) {
            throw new IllegalArgumentException("Wrong path format");
        }
        if (!paths[0].isEmpty()) {
            for (String str : paths) {
                if (str.isEmpty()) {
                    throw new IllegalArgumentException("Wrong path format");
                }
                addDirectory(str);
                pathStringBuilder.append("/");
                pathStringBuilder.append(str);
            }
        } else {
            pathStringBuilder.append("/");
        }
        pathString = pathStringBuilder.toString();
    }

    /**
     * Abstract method to add a directory to the list of directories
     * @param dir the directory's name
     */
    protected abstract void addDirectory(String dir);

    /**
     * Checks if a given path string is valid
     * @param path the path to be validated
     * @return whether the path is valid or not
     */
    protected boolean isValid(String path) {
        return path != null && !path.isEmpty() && path.charAt(0) == '/';
    }

    @Override
    public String toString() {
        return pathString;
    }
}
