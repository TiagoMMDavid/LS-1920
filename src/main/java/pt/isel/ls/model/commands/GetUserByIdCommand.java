package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandException;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.helpers.DatabaseDataHelper;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.results.GetUserByIdResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetUserByIdCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        GetUserByIdResult result = new GetUserByIdResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("SELECT * "
                    + "FROM USERS WHERE uid = ?");
            int userId;
            try {
                userId = commandRequest.getPath().getInt("uid");
            }  catch (NumberFormatException e) {
                throw new CommandException("Invalid User ID");
            }
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result.setUser(new User(
                        rs.getInt("uid"),
                        rs.getString("name"),
                        rs.getString("email")
                ));
                result.setBookings(DatabaseDataHelper.getBookingsFromUid(userId, con));
            }
            rs.close();
            ps.close();
        });
        return result;
    }

    @Override
    public String getDescription() {
        return "returns the detailed information for the uid user";
    }
}
