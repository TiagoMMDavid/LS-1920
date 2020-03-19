package pt.isel.ls.model.commands.common;

public class CommandResult {
    private boolean success;
    private String msg;

    public CommandResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return success;
    }
}
