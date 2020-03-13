package pt.isel.ls.model.paths;

import java.util.Iterator;
import java.util.LinkedList;

public class PathTemplate {
    private LinkedList<Directory> path = new LinkedList<>();

    public PathTemplate (String template) {
        String[] path = template.split("/");
        for (String str: path) {
            this.path.add(new Directory(str,isVariable(str)));
        }
    }

    public boolean isTemplateOf(Path o) {
        Iterator<Directory> template = path.iterator();
        Iterator<Directory> path = o.getPath().iterator();
        boolean templateSuccess = true;

        while(path.hasNext() && template.hasNext()) {
            Directory fromPath = path.next();
            Directory fromTemplate = template.next();
            if (!fromTemplate.isVariable() && !fromTemplate.getName().equals(fromPath.getName())) {
                templateSuccess = false;
                break;
            }
        }
        if(path.hasNext() || template.hasNext()) templateSuccess = false;

        return templateSuccess;
    }

    private boolean isVariable(String dir) {
        if (dir.charAt(0) == '{')
            return dir.charAt(dir.length()-1) == '}';
        return false;
    }
}
