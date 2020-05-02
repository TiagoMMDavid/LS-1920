package pt.isel.ls.model.entities;

public class Room implements Entity {
    private boolean isPost = false;
    private boolean isDetailed = false;

    private int rid;
    private String name;
    private String description;
    private String location;
    private Integer capacity;
    private Iterable<String> labels;

    public Room(int rid) {
        this.rid = rid;
        this.isPost = true;
    }

    public Room(int rid, String name, String location, Integer capacity) {
        this.rid = rid;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
    }

    public Room(int rid, String name, String description, String location, Integer capacity, Iterable<String> labels) {
        this(rid, name, location, capacity);
        this.description = description;
        this.labels = labels;
        this.isDetailed = true;
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

    public Iterable<String> getLabels() {
        return labels;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ROOM;
    }

    public boolean isPost() {
        return isPost;
    }

    public boolean isDetailed() {
        return isDetailed;
    }
}
