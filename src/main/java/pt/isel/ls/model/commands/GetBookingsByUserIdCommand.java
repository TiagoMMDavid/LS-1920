package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.exceptions.CommandException;
import pt.isel.ls.model.commands.common.exceptions.InvalidIdException;
import pt.isel.ls.model.commands.common.exceptions.ParseArgumentException;
import pt.isel.ls.model.commands.results.GetBookingsByUserIdResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Booking;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import static pt.isel.ls.utils.DateUtils.parseTimeWithTimezone;

public class GetBookingsByUserIdCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        GetBookingsByUserIdResult result = new GetBookingsByUserIdResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("SELECT * "
                    + "FROM BOOKING WHERE uid = ? ORDER BY bid");
            int userId;
            try {
                userId = commandRequest.getPath().getInt("uid");
            }  catch (NumberFormatException e) {
                throw new InvalidIdException("Invalid User ID");
            }
            result.setUid(userId);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Date beginInst;
                Date endInst;
                try {
                    beginInst = parseTimeWithTimezone(rs.getString("begin_inst"), "yyyy-MM-dd HH:mm:ss");
                    endInst = parseTimeWithTimezone(rs.getString("end_inst"),"yyyy-MM-dd HH:mm:ss");
                } catch (ParseException e) {
                    throw new ParseArgumentException("Failed to parse dates");
                }
                result.addBooking(new Booking(rs.getInt("bid"), rs.getInt("rid"), beginInst, endInst));
            }
            rs.close();
            ps.close();
        });
        return result;
    }

    @Override
    public String getDescription() {
        return "returns the list of all bookings owned by the uid user";
    }
}
