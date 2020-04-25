package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandException;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetRoomsWithLabelCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        CommandResult result = new CommandResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("SELECT rid "
                    + "FROM ROOMLABEL WHERE lid = ?");

            int labelId = commandRequest.getPath().getInt("lid");
            ps.setInt(1, labelId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                do {
                    result.addResult(new Room(rs.getInt("rid")));
                } while (rs.next());
            }

            rs.close();
            ps.close();
        });
        return result;
    }

    @Override
    public String toString() {
        return "returns a list with all the rooms having label lid";
    }
}
