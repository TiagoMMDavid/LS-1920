package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.PsqlConnectionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetBookingsByUserIdCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) {
        CommandResult result = new CommandResult();
        try (Connection con = PsqlConnectionHandler.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT bid "
                    + "FROM BOOKING WHERE uid = ?");

            int bookingId = Integer.parseInt(commandRequest.getPath().getVariable(0));
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.addResult("booking id (bid): " + rs.getInt("bid"));
            }
            result.setTitle("All bookings of user " + bookingId);
            result.setSuccess(true);

            rs.close();
            ps.close();
        } catch (SQLException e) {
            result.setSuccess(false);
            result.clearResults();
            result.setTitle(e.getMessage());
        }
        return result;
    }
}
