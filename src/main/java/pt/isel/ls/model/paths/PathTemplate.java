package pt.isel.ls.model.paths;

import java.util.LinkedList;

public class PathTemplate {
    LinkedList<Directory> path = new LinkedList<>();

    public PathTemplate (String template) throws IllegalArgumentException {
        String[] path = template.split("/");
        for (String str: path) {
            if (!isValid(str)) throw new IllegalArgumentException("Invalid template");
            this.path.add(new Directory(str,isVariable(str)));
        }
    }

    private boolean isValid(String dir) {
        if (dir.charAt(0) == '{') {
            return (dir.charAt(dir.length() - 1) == '}' && dir.indexOf('/') < 0);   // If it's a variable, check if it's closed on both sides.
        } else {
            return (dir.indexOf('/') < 0);                                          // If it's a constant, check it doesn't have a slash.
        }
    }

    public boolean isTemplateOf(Path o) {
        // TODO
        return false;
    }

    private boolean isVariable(String dir) {
        return (dir.charAt(0) == '{' && dir.charAt(dir.length()-1) == '}');
    }
}
