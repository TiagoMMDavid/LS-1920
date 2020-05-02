package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandException;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;

import static pt.isel.ls.model.commands.helpers.LabelsHelper.getRidsWithLabels;
import static pt.isel.ls.utils.DateUtils.parseTime;
import static pt.isel.ls.utils.DateUtils.parseTimeWithTimezone;

public class GetRoomsCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws CommandException, SQLException {
        CommandResult result = new CommandResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        trans.executeTransaction(con -> {
            Integer capacity = null;
            String begin = null;
            String dur = null;
            Iterable<String> labels;
            LinkedList<Integer> rids = null;
            if (commandRequest.getParams() != null) {
                capacity = commandRequest.getParams().getInt("capacity");
                begin = commandRequest.getParams().getString("begin");
                dur = commandRequest.getParams().getString("duration");
                labels = commandRequest.getParams().getValues("label");
                rids = getRidsWithLabels(con, labels);
            }
            if (rids == null || !rids.isEmpty()) {
                PreparedStatement ps = con.prepareStatement(getQueryString(capacity, begin, dur, rids));
                fillStatementWithParameters(ps, capacity, begin, dur, rids);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    do {
                        capacity = rs.getInt("capacity");
                        if (rs.wasNull()) {
                            capacity = null;
                        }
                        result.addResult(new Room(rs.getInt("rid"),
                                rs.getString("name"),
                                rs.getString("location"),
                                capacity));
                    } while (rs.next());
                }
                rs.close();
                ps.close();
            }
        });
        return result;
    }

    private String getQueryString(Integer capacity, String begin, String dur, LinkedList<Integer> rids) {
        StringBuilder query = new StringBuilder("SELECT rid, name, location, capacity FROM ROOM");
        boolean hasParameters = false;

        if (capacity != null) {
            query.append(" WHERE CAPACITY = ?");
            hasParameters = true;
        }
        if (rids != null && rids.size() > 0) {
            query.append(hasParameters ? " AND" : " WHERE");
            query.append(" rid IN (?");
            for (int i = 0; i < rids.size() - 1; i++) {
                query.append(",?");
            }
            query.append(")");
        }

        if (begin != null && dur != null) {
            query.append(" EXCEPT SELECT BOOKING.rid, name, location, capacity "
                    + "FROM BOOKING JOIN ROOM ON BOOKING.rid = ROOM.rid "
                    + "WHERE (?, ?) OVERLAPS (begin_inst, end_inst)");
        }

        return query.toString();
    }

    private void fillStatementWithParameters(PreparedStatement ps, Integer capacity, String begin, String dur,
                                                 LinkedList<Integer> rids) throws SQLException, CommandException {
        int currIdx = 1;

        if (capacity != null) {
            ps.setInt(currIdx++, capacity);
        }
        if (rids != null && rids.size() > 0) {
            for (Integer rid: rids) {
                ps.setInt(currIdx++, rid);
            }
        }
        if (begin != null && dur != null) {
            Date beginDate;
            Date durationDate;
            try {
                beginDate = parseTimeWithTimezone(begin, "yyyy-MM-dd HH:mm");
                durationDate = parseTime(dur, "HH:mm");
            } catch (ParseException e) {
                throw new CommandException("Failed to parse dates");
            }
            Date endDate = new Date(beginDate.getTime() + durationDate.getTime());
            ps.setTimestamp(currIdx++, new java.sql.Timestamp(beginDate.getTime()));
            ps.setTimestamp(currIdx++, new java.sql.Timestamp(endDate.getTime()));
        }
    }

    @Override
    public String toString() {
        return "returns a list with all existing rooms. Can accept the following parameters\n"
                + "- begin - the begin instant for the booking period.\n"
                + "- duration - the booking duration.\n"
                + "- capacity - the room's person capacity.\n"
                + "- label - the set of labels for the room.";
    }
}
