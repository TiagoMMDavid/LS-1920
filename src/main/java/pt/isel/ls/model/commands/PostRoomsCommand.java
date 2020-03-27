package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.Iterator;
import java.util.LinkedList;

public class PostRoomsCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) {
        CommandResult result = new CommandResult();
        try (Connection con = commandRequest.getConnectionHandler().getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO ROOM "
                            + "(name, description, location, capacity) Values(?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS
            );
            String name = commandRequest.getParams().getValue("name");
            String description = commandRequest.getParams().getValue("description");
            String location = commandRequest.getParams().getValue("location");
            String capacity = commandRequest.getParams().getValue("capacity");

            if (name != null && location != null) {
                ps.setString(1, name);
                ps.setString(2, description);
                ps.setString(3, location);

                if (capacity == null) {
                    ps.setNull(4, Types.INTEGER);
                } else {
                    ps.setInt(4, Integer.parseInt(capacity));
                }

                final int success = ps.executeUpdate();

                //Get rid
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                int rid = rs.getInt("rid");
                result.addResult("RID = " + rid);

                Iterable<String> labels = commandRequest.getParams().getValues("label");
                if (labels != null) {
                    LinkedList<Integer> lids = getLids(con, labels);
                    fillRoomLabelTable(con, rid, lids);
                }

                result.setSuccess(success > 0);
                result.setTitle("Room <" + name + "> added successfully");

                con.commit();
            } else {
                throw new IllegalArgumentException("No arguments found / Invalid arguments");
            }
            ps.close();

        } catch (SQLException e) {
            result.setSuccess(false);
            result.clearResults();
            result.setTitle(e.getMessage());
        }
        return result;
    }

    private LinkedList<Integer> getLids(Connection con, Iterable<String> labels) throws SQLException {
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

    private void fillRoomLabelTable(Connection con, int rid, LinkedList<Integer> lids) throws SQLException {
        StringBuilder builder = new StringBuilder("insert into ROOMLABEL values(?,?");
        for (int i = 0; i < lids.size() - 1; i++) {
            builder.append("), (?,?");
        }
        builder.append(")");

        PreparedStatement ps = con.prepareStatement(builder.toString());
        int i = 1;
        for (Integer lid: lids) {
            ps.setInt(i++,lid);
            ps.setInt(i++,rid);
        }

        ps.executeUpdate();
    }
}
