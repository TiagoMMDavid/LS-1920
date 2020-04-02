package pt.isel.ls.model.commands.sql;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;

public class TransactionManager {
    private final String connectionUrl;
    private final String user;
    private final String password;

    public TransactionManager(String ip, int port, String dbName, String user, String password) {
        this.connectionUrl = "jdbc:postgresql://" + ip + ":" + port + "/" + dbName;
        this.user = user;
        this.password = password;
    }

    public boolean executeTransaction(SqlFunction f) throws Exception {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL(connectionUrl);
        ds.setUser(user);
        ds.setPassword(password);

        Connection con = ds.getConnection();
        con.setAutoCommit(false);
        boolean toReturn = true;
        try {
            f.execute(con);
            con.commit();
        }
        catch (Exception e) {
            con.rollback();
            toReturn = false;
        }
        finally {
            con.close();
        }
        return toReturn;
    }
}
