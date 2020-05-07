package pt.isel.ls.model.entities;

public class User implements Entity {
    private int uid;
    private String name;
    private String email;

    private boolean isPost = false;
    private boolean isDetailed = false;

    public User(int uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.isDetailed = true;
    }

    public User(int uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public User(int uid) {
        this.uid = uid;
        this.isPost = true;
    }

    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.USER;
    }

    public boolean isPost() {
        return isPost;
    }

    public boolean isDetailed() {
        return isDetailed;
    }
}
