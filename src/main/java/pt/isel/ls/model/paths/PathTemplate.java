package pt.isel.ls.model.paths;

import java.util.Iterator;

public class PathTemplate extends BasePath {

    public PathTemplate(String template) {
        super(template);
    }

    /**
     * Used to check if the given Path is adhering to this PathTemplate.
     * @param path The Path to be checked
     * @return if this template is a template of the given Path
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
     * Applies the Template to a Path, allowing it to add its variables in the correct places.
     * @param path The Path to alter
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

    private boolean isVariable(String dir) {
        if (dir.charAt(0) == '{') {
            return dir.charAt(dir.length() - 1) == '}';
        }
        return false;
    }

    @Override
    protected void addDirectory(String dir) {
        super.path.add(new Directory(dir,isVariable(dir)));
    }
}
