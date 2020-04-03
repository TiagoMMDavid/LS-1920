package pt.isel.ls.model.entities;

public class Room {
    private int rid;
    private String name;
    private String description;
    private String location;
    private int capacity;

    public Room(int rid) {
        this.rid = rid;
    }

    public Room(int rid, String name) {
        this.rid = rid;
        this.name = name;
    }

    public Room(int rid, String name, String description, String location, int capacity) {
        this(rid, name);
        this.description = description;
        this.location = location;
        this.capacity = capacity;
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

    public int getCapacity() {
        return capacity;
    }
}
