package pt.isel.ls.model.commands.helpers;

import pt.isel.ls.model.commands.common.exceptions.CommandException;
import pt.isel.ls.model.commands.common.exceptions.ParseArgumentException;
import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.model.entities.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import static pt.isel.ls.utils.DateUtils.parseTimeWithTimezone;

public class DatabaseDataHelper {

    public static LinkedList<Integer> getLids(Iterable<String> labels, Connection con) throws SQLException {
        if (labels == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder("SELECT lid FROM LABEL WHERE name in (?");
        Iterator<String> iter = labels.iterator();
        // We are assured that there's at least one label in the iterable "labels", so we skip the first one.
        iter.next();

        while (iter.hasNext()) {
            builder.append(",?");
            iter.next();
        }
        builder.append(')');
        PreparedStatement ps = con.prepareStatement(builder.toString());

        int i = 1;
        for (String label : labels) {
            ps.setString(i++, label);
        }

        ResultSet rs = ps.executeQuery();

        LinkedList<Integer> toReturn = new LinkedList<>();
        while (rs.next()) {
            toReturn.add(rs.getInt("lid"));
        }
        rs.close();

        return toReturn;
    }

    public static LinkedList<Integer> getRidsWithLabels(Iterable<String> labels, Connection con) throws SQLException {
        LinkedList<Integer> lids = getLids(labels, con);
        if (lids == null || lids.size() == 0) {
            return null;
        }

        StringBuilder builder = new StringBuilder("SELECT rid FROM ROOMLABEL WHERE lid = ?");
        for (int i = 0; i < lids.size() - 1; i++) {
            builder.append(" INTERSECT SELECT rid FROM ROOMLABEL WHERE lid = ?");
        }

        PreparedStatement ps = con.prepareStatement(builder.toString());
        int i = 1;
        for (Integer lid: lids) {
            ps.setInt(i++,lid);
        }

        ResultSet rs = ps.executeQuery();

        LinkedList<Integer> toReturn = new LinkedList<>();
        while (rs.next()) {
            toReturn.add(rs.getInt("rid"));
        }
        rs.close();

        return toReturn;
    }

    public static Iterable<Room> getRoomsWithLabel(int lid, Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT ROOM.rid, ROOM.name "
                + "FROM ROOM join ROOMLABEL on (ROOMLABEL.rid = ROOM.rid) "
                + "WHERE lid = ?");

        ps.setInt(1, lid);
        ResultSet rs = ps.executeQuery();

        LinkedList<Room> toReturn = new LinkedList<>();
        while (rs.next()) {
            toReturn.add(new Room(rs.getInt("rid"), rs.getString("name")));
        }
        rs.close();

        return toReturn;
    }

    public static Iterable<Label> getLabelsFromRid(int rid, Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT name, LABEL.lid "
                + "FROM LABEL join ROOMLABEL on (ROOMLABEL.lid = LABEL.lid) "
                + "WHERE ROOMLABEL.rid = ?");
        ps.setInt(1, rid);

        ResultSet rs = ps.executeQuery();

        LinkedList<Label> labels = new LinkedList<>();
        while (rs.next()) {
            labels.add(new Label(rs.getInt("lid"), rs.getString("name")));
        }
        rs.close();

        return labels;
    }

    public static Iterable<Booking> getBookingsFromUid(int uid, Connection con) throws SQLException, CommandException {
        PreparedStatement ps = con.prepareStatement("SELECT * "
                + "FROM BOOKING "
                + "WHERE uid = ?");
        ps.setInt(1, uid);

        ResultSet rs = ps.executeQuery();

        LinkedList<Booking> bookings = new LinkedList<>();
        while (rs.next()) {
            Date beginInst;
            Date endInst;
            try {
                beginInst = parseTimeWithTimezone(rs.getString("begin_inst"), "yyyy-MM-dd HH:mm:ss");
                endInst = parseTimeWithTimezone(rs.getString("end_inst"),"yyyy-MM-dd HH:mm:ss");
            } catch (ParseException e) {
                throw new ParseArgumentException("Failed to parse dates");
            }

            bookings.add(new Booking(
                    rs.getInt("bid"),
                    rs.getInt("rid"),
                    beginInst, endInst));
        }
        rs.close();

        return bookings;
    }

    public static boolean dateOverlaps(Date begin, Date end, int rid, int bid, Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("select rid, begin_inst, end_inst"
                + " from booking"
                + " where (?, ?) overlaps (begin_inst, end_inst)"
                + " and ? = rid and ? != bid");
        ps.setTimestamp(1, new java.sql.Timestamp(begin.getTime()));
        ps.setTimestamp(2, new java.sql.Timestamp(end.getTime()));
        ps.setInt(3, rid);
        ps.setInt(4, bid);
        ResultSet rs = ps.executeQuery();

        boolean toReturn = rs.next();
        rs.close();

        return toReturn;
    }

    public static boolean dateOverlaps(Date begin, Date end, int rid, Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("select rid, begin_inst, end_inst"
                + " from booking"
                + " where (?, ?) overlaps (begin_inst, end_inst)"
                + " and ? = rid");
        ps.setTimestamp(1, new java.sql.Timestamp(begin.getTime()));
        ps.setTimestamp(2, new java.sql.Timestamp(end.getTime()));
        ps.setInt(3, rid);

        ResultSet rs = ps.executeQuery();

        boolean toReturn = rs.next();
        rs.close();

        return toReturn;
    }

    public static String getLabelName(int labelId, Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT name FROM label WHERE lid = ?");
        ps.setInt(1, labelId);
        ResultSet rs = ps.executeQuery();
        String labelName = null;
        if (rs.next()) {
            labelName = rs.getString("name");
        }
        rs.close();

        return labelName;
    }

    public static String getRoomName(int roomId, Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT name FROM room WHERE rid = ?");
        ps.setInt(1, roomId);
        ResultSet rs = ps.executeQuery();
        String roomName = null;
        if (rs.next()) {
            roomName = rs.getString("name");
        }
        rs.close();

        return roomName;
    }

    public static boolean hasBookings(int roomId, Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT bid FROM BOOKING WHERE rid = ?");
        ps.setInt(1, roomId);
        ResultSet rs = ps.executeQuery();

        boolean toReturn = rs.next();
        rs.close();

        return toReturn;
    }
}
