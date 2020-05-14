package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;

public class GetHomeResult implements CommandResult {

    @Override
    public boolean hasResults() {
        return true;
    }

    @Override
    public ResultType getResultType() {
        return ResultType.GetHome;
    }
}
