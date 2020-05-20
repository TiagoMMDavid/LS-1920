package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.ListenResult;

public class ListenPlainView extends PlainView {

    private ListenResult result;

    public ListenPlainView(CommandResult commandResult) {
        result = (ListenResult) commandResult;
    }

    @Override
    public String display() {
        return "Servlet now listening on Port: " + result.getPort();
    }
}
