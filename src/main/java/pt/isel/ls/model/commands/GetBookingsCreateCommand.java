package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.Parameters;
import pt.isel.ls.model.commands.common.exceptions.CommandException;
import pt.isel.ls.model.commands.results.GetBookingsCreateResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetBookingsCreateCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        GetBookingsCreateResult result = new GetBookingsCreateResult();
        Parameters params = commandRequest.getParams();
        TransactionManager trans = commandRequest.getTransactionHandler();
        trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("SELECT * "
                    + "FROM USERS ORDER BY uid");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.addUser(new User(
                        rs.getInt("uid"),
                        rs.getString("name"),
                        rs.getString("email")
                ));
            }
            rs.close();
            ps.close();
        });
        String rid = commandRequest.getPath().getString("rid");
        result.setRoomId(rid);

        if (params != null) {
            result.setError(params.getString("errorType"));
            result.setPreviousBeginInst(params.getString("begin"));
            result.setPreviousDuration(params.getString("duration"));
            result.setPreviousUserId(params.getString("uid"));
        }
        return result;
    }

    @Override
    public String getDescription() {
        return "HTTP only command to get the booking create page";
    }
}
