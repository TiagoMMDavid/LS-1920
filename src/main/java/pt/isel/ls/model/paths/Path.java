package pt.isel.ls.model.paths;

import java.util.HashMap;
import java.util.LinkedList;

public class Path extends PathCommon {

    /**
     * An Hashmap of Strings to represent the each variable's name.
     */
    private HashMap<String, String> variables;

    public void addVariable(String varName, String var) {
        if (variables == null) {
            variables = new HashMap<>();
        }
        variables.put(varName, var);
    }

    public Path(String path) {
        super(path);
    }

    public String getVariable(String varName) {
        return variables.get(varName);
    }

    public LinkedList<Directory> getPath() {
        return path;
    }

    @Override
    protected void addDirectory(String str) {
        super.path.add(new Directory(str,false));
    }
}
