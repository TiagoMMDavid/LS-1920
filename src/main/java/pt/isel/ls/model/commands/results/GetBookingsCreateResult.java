package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.entities.User;

import java.util.LinkedList;

public class GetBookingsCreateResult implements CommandResult {

    private String roomId;
    private LinkedList<User> users;

    private boolean wasError = false;
    private String previousDuration = "";
    private String previousBeginInst = "";
    private String previousUserId = "";

    public void addUser(User user) {
        if (users == null) {
            users = new LinkedList<>();
        }
        users.add(user);
    }

    public Iterable<User> getUsers() {
        return users;
    }

    public String getPreviousDuration() {
        return previousDuration;
    }

    public void setPreviousDuration(String previousDuration) {
        if (previousDuration != null) {
            this.previousDuration = previousDuration;
        }
    }

    public String getPreviousBeginInst() {
        return previousBeginInst;
    }

    public void setPreviousBeginInst(String previousBeginInst) {
        if (previousBeginInst != null) {
            this.previousBeginInst = previousBeginInst;
        }
    }

    public String getPreviousUserId() {
        return previousUserId;
    }

    public void setPreviousUserId(String previousUserId) {
        if (previousUserId != null) {
            this.previousUserId = previousUserId;
        }
    }

    public boolean wasError() {
        return wasError;
    }

    public void setError() {
        this.wasError = true;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public boolean hasResults() {
        return true;
    }

    @Override
    public ResultType getResultType() {
        return ResultType.GetBookingsCreate;
    }
}
