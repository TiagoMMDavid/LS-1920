package pt.isel.ls.model.paths;

import java.util.Iterator;

public class PathTemplate extends PathCommon {

    public PathTemplate (String template) {
        super(template);
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

    @Override
    protected void addDirectory(String str) {
        super.path.add(new Directory(str,isVariable(str)));
    }

    private boolean isVariable(String dir) {
        if (dir.charAt(0) == '{')
            return dir.charAt(dir.length()-1) == '}';
        return false;
    }
}
