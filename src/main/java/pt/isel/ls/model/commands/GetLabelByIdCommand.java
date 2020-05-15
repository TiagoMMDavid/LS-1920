package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandException;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.helpers.DatabaseDataHelper;
import pt.isel.ls.model.commands.results.GetLabelByIdResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Label;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetLabelByIdCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        GetLabelByIdResult result = new GetLabelByIdResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        trans.executeTransaction(con -> {
            Integer lid;
            try {
                lid = commandRequest.getPath().getInt("lid");
            }  catch (NumberFormatException e) {
                throw new CommandException("Invalid Label ID");
            }
            PreparedStatement ps = con.prepareStatement("SELECT lid, name "
                    + "FROM LABEL WHERE lid = ?");
            ps.setInt(1, lid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result.setLabel(new Label(lid, rs.getString("name")));
                result.setRooms(DatabaseDataHelper.getRoomsWithLabel(lid, con));
            }
            rs.close();
            ps.close();
        });
        return result;
    }

    @Override
    public String getDescription() {
        return "returns the detailed information for the label";
    }
}
