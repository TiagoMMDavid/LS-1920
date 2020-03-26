package pt.isel.ls.model.paths;

class Directory {

    private String name;
    private boolean isVariable;

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
