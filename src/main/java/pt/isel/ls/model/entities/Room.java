package pt.isel.ls.model.entities;

public class Room implements Entity {

    private int rid;
    private String name;
    private String description;
    private String location;
    private Integer capacity;

    public Room(int rid) {
        this.rid = rid;
    }

    public Room(int rid, String name, String location, Integer capacity) {
        this.rid = rid;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
    }

    public Room(int rid, String name, String description, String location, Integer capacity) {
        this(rid, name, location, capacity);
        this.description = description;
    }

    public int getRid() {
        return rid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ROOM;
    }
}
