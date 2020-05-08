package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandException;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.helpers.LabelsHelper;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Label;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class GetLabelByIdCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        CommandResult result = new CommandResult();
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
                LinkedList<Integer> rids = LabelsHelper.getRidsWithLabel(con, lid);
                result.addResult(new Label(lid, rs.getString("name"), rids));
            }
            rs.close();
            ps.close();
        });
        return result;
    }
}
