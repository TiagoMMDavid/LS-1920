package pt.isel.ls.model.entities;

import java.util.Date;

public class Booking {
    private int bid;
    private int uid;
    private int rid;
    private Date beginInst;
    private Date endInst;

    public Booking(int bid, int uid, int rid, Date beginInst, Date endInst) {
        this.bid = bid;
        this.uid = uid;
        this.rid = rid;
        this.beginInst = beginInst;
        this.endInst = endInst;
    }

    public Booking(int bid) {
        this.bid = bid;
    }

    public int getBid() {
        return bid;
    }

    public int getUid() {
        return uid;
    }

    public int getRid() {
        return rid;
    }

    public Date getBeginInst() {
        return beginInst;
    }

    public Date getEndInst() {
        return endInst;
    }
}
