package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetTimeResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTimePlainView extends PlainView {

    private GetTimeResult result;

    public GetTimePlainView(CommandResult commandResult) {
        result = (GetTimeResult)commandResult;
    }

    @Override
    public String display() {
        return getTime(result.getTime());
    }

    private String getTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

}
