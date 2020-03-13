package pt.isel.ls.model.paths;

import java.util.LinkedList;

public class Path {
    private LinkedList<Directory> path = new LinkedList<>();

    public Path(String path) {
        String[] paths = path.split("/");
        for (String str: paths) {
            this.path.add(new Directory(str, false));
        }
    }

    public LinkedList<Directory> getPath() {
        return path;
    }
}
