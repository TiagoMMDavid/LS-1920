package pt.isel.ls.model.commands.sql;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;

public class TransactionManager {
    private final String connectionUrl;

    public TransactionManager(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public boolean executeTransaction(SqlFunction f) throws Exception {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL(connectionUrl);

        Connection con = ds.getConnection();
        con.setAutoCommit(false);
        boolean toReturn = true;
        try {
            f.execute(con);
            con.commit();
        } catch (Exception e) {
            con.rollback();
            toReturn = false;
        } finally {
            con.close();
        }
        return toReturn;
    }
}
