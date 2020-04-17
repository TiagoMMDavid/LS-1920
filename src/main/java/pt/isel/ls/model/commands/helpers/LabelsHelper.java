package pt.isel.ls.model.commands.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;

public class LabelsHelper {
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
}
