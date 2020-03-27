package pt.isel.ls.model.paths;

class Directory {

    private String name;
    private boolean isVariable;

    public Directory(String name, boolean isVariable) {
        if (isVariable) {
            name = name.substring(1, name.length() - 1);
        }
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
