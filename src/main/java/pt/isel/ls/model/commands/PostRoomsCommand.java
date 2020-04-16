package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.entities.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.LinkedList;

import static pt.isel.ls.model.commands.helpers.LabelsHelper.getLids;

public class PostRoomsCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {
        CommandResult result = new CommandResult();
        TransactionManager trans = commandRequest.getTransactionHandler();
        if (!trans.executeTransaction(con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO ROOM "
                            + "(name, description, location, capacity) Values(?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS
            );
            String name = commandRequest.getParams().getString("name");
            String description = commandRequest.getParams().getString("description");
            String location = commandRequest.getParams().getString("location");
            Integer capacity = commandRequest.getParams().getInt("capacity");

            if (name != null && location != null) {
                ps.setString(1, name);
                ps.setString(2, description);
                ps.setString(3, location);

                if (capacity == null) {
                    ps.setNull(4, Types.INTEGER);
                } else {
                    ps.setInt(4, capacity);
                }

                final int success = ps.executeUpdate();

                //Get rid
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                int rid = rs.getInt("rid");
                result.addResult(new Room(rid));

                Iterable<String> labels = commandRequest.getParams().getValues("label");
                if (labels != null) {
                    LinkedList<Integer> lids = getLids(con, labels);
                    fillRoomLabelTable(con, rid, lids);
                }

                result.setSuccess(success > 0);
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

    @Override
    public String toString() {
        return "creates a new room, given the following parameters\n"
                + "• name - the rooms's name.\n"
                + "• description - the rooms's description.\n"
                + "• location - the room's location.\n"
                + "• capacity - the room's person capacity.\n"
                + "• label - the set of labels for the room.";
    }
}
