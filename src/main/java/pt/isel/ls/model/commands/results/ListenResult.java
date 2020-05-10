package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;

public class ListenResult implements CommandResult {

    private boolean hasResult = false;
    private int port;

    public void setPort(int port) {
        this.port = port;
        hasResult = true;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean hasResults() {
        return hasResult;
    }

    @Override
    public ResultType getResultType() {
        return ResultType.Listen;
    }
}
