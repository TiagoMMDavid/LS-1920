package pt.isel.ls.model.entities;

public class User {
    private int uid;
    private String name;
    private String email;

    public User(int uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    public User(int uid) {
        this.uid = uid;
    }
}
