package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.sql.TransactionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static pt.isel.ls.utils.DateUtils.parseTime;
import static pt.isel.ls.utils.DateUtils.parseTimeWithTimezone;

public class PutBookingInRoom implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {
        CommandResult result = new CommandResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        if (!trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("UPDATE BOOKING "
                            + "SET uid = ?, begin_inst = ?, end_inst = ? "
                            + "WHERE rid = ? AND bid = ?"
            );
            int uid = commandRequest.getParams().getInt("uid");
            int rid = commandRequest.getPath().getInt("rid");
            int bid = commandRequest.getPath().getInt("bid");
            String duration = commandRequest.getParams().getString("duration");
            String begin = commandRequest.getParams().getString("begin");
            if (uid >= 0 && rid >= 0 && bid >= 0 && duration != null && begin != null) {
                ps.setInt(1, uid);
                ps.setInt(4, rid);
                ps.setInt(5, bid);

                //Get beginDate and calculate end time
                Date beginDate = parseTimeWithTimezone(begin, "yyyy-MM-dd HH:mm");

                //Parsed time without timezone because the duration is independent of Timezones
                Date durationDate = parseTime(duration, "HH:mm");
                Date endDate = new Date(beginDate.getTime() + durationDate.getTime());
                if (!hasOverlaps(beginDate, endDate, rid, bid, con)) {
                    ps.setTimestamp(2, new java.sql.Timestamp(beginDate.getTime()));
                    ps.setTimestamp(3, new java.sql.Timestamp(endDate.getTime()));

                    int success = ps.executeUpdate();
                    result.setSuccess(success > 0);
                } else {
                    result.setSuccess(false);
                }
            } else {
                throw new IllegalArgumentException("No arguments found / Invalid arguments");
            }
        })) {
            result.setSuccess(false);
            result.clearResults();
        }
        return result;
    }

    private boolean hasOverlaps(Date begin, Date end, int rid, int bid, Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("select rid, begin_inst, end_inst"
                + " from booking"
                + " where (?, ?) overlaps (begin_inst, end_inst)"
                + " and ? = rid and ? != bid");
        ps.setTimestamp(1, new java.sql.Timestamp(begin.getTime()));
        ps.setTimestamp(2, new java.sql.Timestamp(end.getTime()));
        ps.setInt(3, rid);
        ps.setInt(4, bid);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}
