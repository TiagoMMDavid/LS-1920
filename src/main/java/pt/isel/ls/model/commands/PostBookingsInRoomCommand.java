package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Booking;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import static pt.isel.ls.utils.DateUtils.parseTime;
import static pt.isel.ls.utils.DateUtils.parseUniversalTime;

public class PostBookingsInRoomCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {
        CommandResult result = new CommandResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        if (!trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO BOOKING "
                            + "(uid, rid, begin_inst, end_inst) Values(?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            String uid = commandRequest.getParams().getValue("uid");
            String rid = commandRequest.getPath().getString("rid");
            String duration = commandRequest.getParams().getValue("duration");
            String begin = commandRequest.getParams().getValue("begin");
            if (uid != null && rid != null && duration != null && begin != null) {
                ps.setInt(1, Integer.parseInt(uid));
                ps.setInt(2, Integer.parseInt(rid));

                //Calculate end time
                Date beginDate = parseTime(begin, "yyyy-MM-dd HH:mm");
                //Parsed Universal time because the duration is independent of Timezones
                Date durationDate = parseUniversalTime(duration, "HH:mm");
                Date endDate = new Date(beginDate.getTime() + durationDate.getTime());
                ps.setTimestamp(3, new java.sql.Timestamp(beginDate.getTime()));
                ps.setTimestamp(4, new java.sql.Timestamp(endDate.getTime()));

                int success = ps.executeUpdate();
                result.setSuccess(success > 0);
                //Get bid
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                result.addResult(new Booking(rs.getInt("bid")));
                con.commit();
            } else {
                throw new IllegalArgumentException("No arguments found / Invalid arguments");
            }
            ps.close();

        })) {
            result.setSuccess(false);
            result.clearResults();
        }
        return result;
    }
}
