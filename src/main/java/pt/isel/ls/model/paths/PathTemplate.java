package pt.isel.ls.model.paths;

import java.util.Iterator;

public class PathTemplate extends PathCommon {

    public PathTemplate(String template) {
        super(template);
    }

    public boolean isTemplateOf(Path path) {
        Iterator<Directory> templateItr = this.path.iterator();
        Iterator<Directory> pathItr = path.getPath().iterator();
        boolean templateSuccess = true;

        while (pathItr.hasNext() && templateItr.hasNext()) {
            Directory fromPath = pathItr.next();
            Directory fromTemplate = templateItr.next();
            if (!fromTemplate.isVariable() && !fromTemplate.getName().equals(fromPath.getName())) {
                templateSuccess = false;
                break;
            }
        }

        if (pathItr.hasNext() || templateItr.hasNext()) {
            templateSuccess = false;
        }

        return templateSuccess;
    }

    public void applyTemplate(Path path) {
        Iterator<Directory> templateItr = this.path.iterator();
        Iterator<Directory> pathItr = path.getPath().iterator();
        while (pathItr.hasNext() && templateItr.hasNext()) {
            Directory fromPath = pathItr.next();
            Directory fromTemplate = templateItr.next();
            if (fromTemplate.isVariable()) {
                path.addVariable(fromPath.getName());
            }
        }
    }

    @Override
    protected void addDirectory(String str) {
        super.path.add(new Directory(str,isVariable(str)));
    }

    private boolean isVariable(String dir) {
        if (dir.charAt(0) == '{') {
            return dir.charAt(dir.length() - 1) == '}';
        }
        return false;
    }
}
