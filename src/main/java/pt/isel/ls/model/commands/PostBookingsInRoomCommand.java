package pt.isel.ls.model.commands;

import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.PsqlConnectionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostBookingsInRoomCommand implements CommandHandler {
    @Override
    public CommandResult execute(CommandRequest commandRequest) {
        CommandResult result = new CommandResult();
        try (Connection con = PsqlConnectionHandler.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO BOOKING "
                    + "(uid, rid, begin_inst, end_inst) Values(?,?,?,?)"
            );
            String uid = commandRequest.getParams().getValue("uid");
            String rid = commandRequest.getPath().getVariable(0);
            String duration = commandRequest.getParams().getValue("duration");
            String begin = commandRequest.getParams().getValue("begin");
            if (uid != null && rid != null && duration != null && begin != null) {
                ps.setString(1, uid);
                ps.setString(2, rid);
                ps.setString(3, begin);
                ps.setString(4, null);
                int success = ps.executeUpdate();
                con.commit();
                result.setSuccess(success > 0);
                result.setTitle("User <" + uid + "> added successfully");
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
}
