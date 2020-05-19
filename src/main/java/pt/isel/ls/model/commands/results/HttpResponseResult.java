package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;

import static pt.isel.ls.model.commands.common.CommandResult.ResultType.HttpResponse;

public class HttpResponseResult implements CommandResult {
    private int status;

    public HttpResponseResult(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public boolean hasResults() {
        return true;
    }

    @Override
    public ResultType getResultType() {
        return HttpResponse;
    }
}
