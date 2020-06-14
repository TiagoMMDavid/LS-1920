package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.ValidatedResult;
import pt.isel.ls.model.entities.User;

import java.util.LinkedList;

public class GetBookingsCreateResult extends ValidatedResult implements CommandResult {

    private String roomId;
    private LinkedList<User> users;

    private String previousDuration = "";
    private String previousBeginInst = "";
    private String previousUserId = "";

    public void addUser(User user) {
        if (users == null) {
            users = new LinkedList<>();
        }
        users.add(user);
    }

    public void setPreviousUserId(String previousUserId) {
        if (previousUserId != null) {
            this.previousUserId = previousUserId;
        }
    }

    public void setPreviousDuration(String previousDuration) {
        if (previousDuration != null) {
            this.previousDuration = previousDuration;
        }
    }

    public void setPreviousBeginInst(String previousBeginInst) {
        if (previousBeginInst != null) {
            this.previousBeginInst = previousBeginInst;
        }
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Iterable<User> getUsers() {
        return users;
    }

    public String getPreviousDuration() {
        return previousDuration;
    }

    public String getPreviousBeginInst() {
        return previousBeginInst;
    }

    public String getPreviousUserId() {
        return previousUserId;
    }

    public String getRoomId() {
        return roomId;
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
