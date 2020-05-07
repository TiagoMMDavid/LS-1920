package pt.isel.ls.model.entities;

import java.util.LinkedList;

public class Label implements Entity {
    private int lid;
    private String name;

    private boolean hasRooms = false;
    private LinkedList<Integer> rids;

    public Label(int lid, String name) {
        this.lid = lid;
        this.name = name;
    }

    public Label(int lid, String name, LinkedList<Integer> rids) {
        this.lid = lid;
        this.name = name;
        this.rids = rids;
        this.hasRooms = true;
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

    public LinkedList<Integer> getRids() {
        return rids;
    }

    public boolean hasRooms() {
        return hasRooms;
    }
}
