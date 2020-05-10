package pt.isel.ls.model.commands.results;

import pt.isel.ls.model.commands.common.CommandResult;

import java.util.Date;

public class GetTimeResult implements CommandResult {

    private boolean hasResult = false;
    private Date date;

    public void setTime(Date date) {
        this.date = date;
        hasResult = true;
    }

    public Date getTime() {
        return date;
    }

    @Override
    public boolean hasResults() {
        return hasResult;
    }

    @Override
    public CommandResult.ResultType getResultType() {
        return ResultType.GetTime;
    }
}
