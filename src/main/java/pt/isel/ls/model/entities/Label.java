package pt.isel.ls.model.entities;

public class Label {
    private int lid;
    private String name;

    public Label(int lid, String name) {
        this.lid = lid;
        this.name = name;
    }

    public Label(int lid) {
        this.lid = lid;
    }

    public int getLid() {
        return lid;
    }

    public String getName() {
        return name;
    }
}
