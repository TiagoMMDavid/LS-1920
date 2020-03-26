package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class PostBookingsInRoomCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) {
        CommandResult result = new CommandResult();
        try (Connection con = commandRequest.getConnectionHandler().getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO BOOKING "
                            + "(uid, rid, begin_inst, end_inst) Values(?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS
            );
            String uid = commandRequest.getParams().getValue("uid");
            String rid = commandRequest.getPath().getVariable(0);
            String duration = commandRequest.getParams().getValue("duration");
            String begin = commandRequest.getParams().getValue("begin");
            if (uid != null && rid != null && duration != null && begin != null) {
                ps.setInt(1, Integer.parseInt(uid));
                ps.setInt(2, Integer.parseInt(rid));

                //Calculate end time
                Date beginDate = parseTime(begin, "yyyy-MM-dd HH:mm");
                Date durationDate = parseTime(duration, "HH:mm");
                Date endDate = new Date(beginDate.getTime() + durationDate.getTime());
                ps.setTimestamp(3, new java.sql.Timestamp(beginDate.getTime()));
                ps.setTimestamp(4, new java.sql.Timestamp(endDate.getTime()));

                int success = ps.executeUpdate();
                result.setSuccess(success > 0);
                result.setTitle("Booking in room <" + rid + "> added successfully");
                //Get bid
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                result.addResult("BID = " + rs.getInt("bid"));
                con.commit();
            } else {
                throw new IllegalArgumentException("No arguments found / Invalid arguments");
            }
            ps.close();

        } catch (Exception e) {
            result.setSuccess(false);
            result.clearResults();
            result.setTitle(e.getMessage());
        }
        return result;
    }

    private Date parseTime(String date, String pattern) throws ParseException {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
        formatDate.setTimeZone(tz);
        return formatDate.parse(date);
    }
}
