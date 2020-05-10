package pt.isel.ls.model.commands.helpers;

import pt.isel.ls.model.entities.Booking;
import pt.isel.ls.model.entities.Label;
import pt.isel.ls.model.entities.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

public class DatabaseDataHelper {

    public static LinkedList<Integer> getLids(Connection con, Iterable<String> labels) throws SQLException {
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

        return toReturn;
    }

    public static LinkedList<Integer> getRidsWithLabels(Connection con, Iterable<String> labels) throws SQLException {
        LinkedList<Integer> lids = getLids(con, labels);
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

        return toReturn;
    }

    public static Iterable<Room> getRoomsWithLabel(Connection con, int lid) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT rid FROM ROOMLABEL WHERE lid = ?");
        ps.setInt(1, lid);
        ResultSet rs = ps.executeQuery();

        LinkedList<Room> toReturn = new LinkedList<>();
        while (rs.next()) {
            toReturn.add(new Room(rs.getInt("rid")));
        }
        return toReturn;
    }

    public static Iterable<Label> getLabelsFromRid(Connection con, int rid) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT name, LABEL.lid "
                + "FROM LABEL join ROOMLABEL on (ROOMLABEL.lid = LABEL.lid) "
                + "WHERE ROOMLABEL.rid = ?");
        ps.setInt(1, rid);

        ResultSet rs = ps.executeQuery();

        LinkedList<Label> labels = new LinkedList<>();
        while (rs.next()) {
            labels.add(new Label(rs.getInt("lid"), rs.getString("name")));
        }

        return labels;
    }

    public static Iterable<Booking> getBookingsFromUid(Connection con, int uid) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * "
                + "FROM BOOKING "
                + "WHERE uid = ?");
        ps.setInt(1, uid);

        ResultSet rs = ps.executeQuery();

        LinkedList<Booking> bookings = new LinkedList<>();
        while (rs.next()) {
            bookings.add(new Booking(
                    rs.getInt("bid"),
                    rs.getInt("rid"),
                    uid));
        }

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
        return rs.next();
    }
}
