package pt.isel.ls.model.entities;

public class Label implements Entity {
    private int lid;
    private String name;

    public Label(int lid, String name) {
        this.lid = lid;
        this.name = name;
    }

    public int getLid() {
        return lid;
    }

    public String getName() {
        return name;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.LABEL;
    }
}
