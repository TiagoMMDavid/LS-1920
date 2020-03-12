package pt.isel.ls.model.paths;

public class Directory {
    private String name;
    private boolean isVariable = false;

    public Directory(String name, boolean isVariable) {
        this.name = name;
        this.isVariable = isVariable;
    }

    public String getName() {
        return name;
    }

    public boolean isVariable() {
        return isVariable;
    }
}
