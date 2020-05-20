package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetRoomsSearchResult;
import pt.isel.ls.view.View;

public class GetRoomsSearchPlainView extends View {

    private GetRoomsSearchResult result;

    public GetRoomsSearchPlainView(CommandResult commandResult) {
        this.result = (GetRoomsSearchResult) commandResult;
    }

    @Override
    public String display() {
        return "No textual representation available, please use the accept:text/html header";
    }
}

