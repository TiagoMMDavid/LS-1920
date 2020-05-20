package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.OptionResult;
import pt.isel.ls.model.entities.Command;
import pt.isel.ls.view.View;

public class OptionPlainView extends View {
    private OptionResult result;

    public OptionPlainView(CommandResult commandResult) {
        this.result = (OptionResult) commandResult;
    }

    @Override
    public String display() {
        StringBuilder builder = new StringBuilder();
        for (Command cmd : result.getCommands()) {
            builder.append(cmd.getName());
            builder.append(" - ");
            builder.append(cmd.getDescription());
            builder.append('\n');
        }
        return builder.toString();
    }
}
