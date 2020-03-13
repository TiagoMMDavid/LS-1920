package pt.isel.ls.model.paths;

import java.util.LinkedList;

public class Path extends PathCommon {

    public Path(String path) {
        super(path);
    }

    public LinkedList<Directory> getPath() {
        return path;
    }

    @Override
    protected void addDirectory(String str) {
        super.path.add(new Directory(str,false));
    }
}
