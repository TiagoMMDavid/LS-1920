package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Booking;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static pt.isel.ls.utils.DateUtils.parseTime;

public class GetBookingByRoomAndBookingId implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {
        CommandResult result = new CommandResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        if (!trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("SELECT * "
                    + "FROM BOOKING WHERE rid = ? AND bid = ?");

            int roomId = commandRequest.getPath().getInt("rid");
            int bookingId = commandRequest.getPath().getInt("bid");
            ps.setInt(1, roomId);
            ps.setInt(2, bookingId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result.addResult(new Booking(
                        rs.getInt("bid"),
                        rs.getInt("uid"),
                        rs.getInt("rid"),
                        parseTime(rs.getString("begin_inst"), "yyyy-MM-dd HH:mm:ss"),
                        parseTime(rs.getString("end_inst"),"yyyy-MM-dd HH:mm:ss" )
                ));

            }
            result.setSuccess(true);

            rs.close();
            ps.close();
        })) {
            result.setSuccess(false);
            result.clearResults();
        }
        return result;
    }
}
