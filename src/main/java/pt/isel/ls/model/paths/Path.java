package pt.isel.ls.model.paths;

import java.util.HashMap;
import java.util.LinkedList;

public class Path extends BasePath {

    /**
     * An Hashmap of Strings to represent the each variable's name.
     */
    private HashMap<String, String> variables;

    void addVariable(String varName, String var) {
        if (variables == null) {
            variables = new HashMap<>();
        }
        variables.put(varName, var);
    }

    public Path(String path) {
        super(path);
    }

    public int getInt(String varName) {
        String res = variables.get(varName);
        return res == null ? -1 : Integer.parseInt(res);
    }

    public String getString(String varName) {
        return variables.get(varName);
    }

    LinkedList<Directory> getPath() {
        return path;
    }

    @Override
    protected void addDirectory(String dir) {
        super.path.add(new Directory(dir,false));
    }
}
