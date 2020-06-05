package pt.isel.ls.model.commands.sql;

import org.postgresql.ds.PGSimpleDataSource;
import pt.isel.ls.model.commands.common.exceptions.CommandException;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {

    public static final String  DUPLICATE_COLUMN_ERROR = "23505";
    public static final String  CONNECTION_REFUSED_ERROR = "08001";


    private final String connectionUrl;

    public TransactionManager(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public void executeTransaction(SqlFunction f) throws CommandException, SQLException {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL(connectionUrl);

        Connection con = ds.getConnection();
        con.setAutoCommit(false);
        try {
            f.execute(con);
            con.commit();
        } catch (SQLException | CommandException e) {
            con.rollback();
            con.close();
            throw e;
        }
        con.close();
    }
}
