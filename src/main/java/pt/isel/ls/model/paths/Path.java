package pt.isel.ls.model.paths;

import java.util.ArrayList;
import java.util.LinkedList;

public class Path extends PathCommon {

    private ArrayList<String> variables;

    public void addVariable(String var) {
        if (variables == null) {
            variables = new ArrayList<>();
        }
        variables.add(var);
    }

    public Path(String path) {
        super(path);
    }

    public String getVariable(int index) {
        return variables.get(index);
    }

    public LinkedList<Directory> getPath() {
        return path;
    }

    @Override
    protected void addDirectory(String str) {
        super.path.add(new Directory(str,false));
    }
}
