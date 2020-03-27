package pt.isel.ls.model.paths;

import java.util.Iterator;

public class PathTemplate extends BasePath {

    public PathTemplate(String template) {
        super(template);
    }

    /**
     * Compares the Directories of both the Path and PathTemplate, in case they all have the same name,
     * or correspond to a variable, returns true.
     */

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

    /**
     * Applies the Template to a path, allowing it to add the variables to the allocated place.
     */
    public void applyTemplate(Path path) {
        Iterator<Directory> templateItr = this.path.iterator();
        Iterator<Directory> pathItr = path.getPath().iterator();
        while (pathItr.hasNext() && templateItr.hasNext()) {
            Directory fromPath = pathItr.next();
            Directory fromTemplate = templateItr.next();
            if (fromTemplate.isVariable()) {
                path.addVariable(fromTemplate.getName(), fromPath.getName());
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
