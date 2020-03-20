package pt.isel.ls.model.commands;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.model.commands.common.PsqlConnectionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetCommandsTest {

    @BeforeClass
    public static void fillTables() throws SQLException {
        Connection con = PsqlConnectionHandler.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement();
        } catch (SQLException e) {
            con.rollback();
        } finally {
            con.close();
        }
    }

    @AfterClass
    public static void ClearTables() throws SQLException {
        Connection con = PsqlConnectionHandler.getConnection();
        try {
            String[] tables = {"BOOKING", "ROOMLABEL", "LABEL", "ROOM", "USERS"};
            for (String tableName : tables) {
                PreparedStatement ps = con.prepareStatement("delete from ?");
                ps.setString(1, tableName);
                ps.execute();
            }
            con.commit();
        } catch (SQLException e) {
            con.rollback();
        } finally {
            con.close();
        }
    }

    @Test
    public void GetBookingsByRoomAndBookingIdTest() {
        //TODO:
    }
}
