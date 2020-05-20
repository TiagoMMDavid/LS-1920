package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.OptionResult;
import pt.isel.ls.model.entities.Command;

import java.util.Iterator;

public class OptionPlainView extends PlainView {
    private OptionResult result;

    public OptionPlainView(CommandResult commandResult) {
        this.result = (OptionResult) commandResult;
    }

    @Override
    public String display() {
        StringBuilder builder = new StringBuilder();
        Iterator<Command> commands = result.getCommands().iterator();
        while (commands.hasNext()) {
            Command cmd = commands.next();
            builder.append(cmd.getName());
            builder.append(" - ");
            builder.append(cmd.getDescription());
            if (commands.hasNext()) {
                builder.append('\n');
            }
        }
        return builder.toString();
    }
}
