package pt.isel.ls.model.paths;

import java.util.HashMap;
import java.util.LinkedList;

public class Path extends BasePath {

    private HashMap<String, String> variables;

    public Path(String path) {
        super(path);
    }

    /**
     * Adds a variable to the variable Map
     * @param name The variable's name
     * @param value The variable's value
     */
    void addVariable(String name, String value) {
        if (variables == null) {
            variables = new HashMap<>();
        }
        variables.put(name, value);
    }

    public Integer getInt(String varName) throws NumberFormatException {
        String res = variables.get(varName);
        return res == null ? null : Integer.parseInt(res);
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
