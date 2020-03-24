package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.PsqlConnectionHandler;
import pt.isel.ls.utils.ArrayUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Array;
import java.util.LinkedList;

public class PostRoomsCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) {
        //TODO:
        CommandResult result = new CommandResult();
        try (Connection con = PsqlConnectionHandler.getConnection()) {
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
                ps.setInt(4, capacity == null ? 0 : Integer.parseInt(capacity)); //By default, the value is 0 if no capacity is given

                final int success = ps.executeUpdate();

                //Get rid
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                int rid = rs.getInt("rid");
                result.addResult("RID = " + rid);

                LinkedList<Integer> lids = getLids(con, commandRequest.getParams().getValues("label"));
                fillRoomLabelTable(con, rid, lids);

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

    private LinkedList<Integer> getLids(Connection con, Iterable<String> label) throws SQLException {
        //TODO: This isn't implemented properly
        PreparedStatement ps = con.prepareStatement("SELECT lid FROM LABEL "
                        + "WHERE name = ?"
        );
        String[] labels = ArrayUtils.convertIterableToArray(label);
        Array array = con.createArrayOf("INT", labels);
        ps.setArray(1, array);
        ResultSet rs = ps.executeQuery();
        LinkedList<Integer> toReturn = new LinkedList<>();
        while (rs.next()) {
            toReturn.add(rs.getInt("lid"));
        }

        return null;
    }

    private void fillRoomLabelTable(Connection con, int rid, LinkedList<Integer> lids) throws SQLException {
        StringBuilder builder = new StringBuilder("insert into ROOMLABEL values(?,?");
        for (int i = 0; i < lids.size() -1 ; i++) {
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
