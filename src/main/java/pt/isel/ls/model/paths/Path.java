package pt.isel.ls.model.paths;

import java.util.LinkedList;

public class Path {
    private LinkedList<Directory> path = new LinkedList<>();

    public Path(String path) {
        if(path != null || path.charAt(0) != '/'){
            throw new IllegalArgumentException("Wrong Path format.");
        }
        path = path.substring(1);
        String[] paths = path.split("/");
        for (String str: paths) {
            this.path.add(new Directory(str, false));
        }
    }

    public LinkedList<Directory> getPath() {
        return path;
    }
}
