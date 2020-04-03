package pt.isel.ls.model.entities;

import java.util.Date;

public class Booking implements Entity {
    private int bid;
    private int uid = -1;
    private int rid = -1;
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

    @Override
    public EntityType getEntityType() {
        return EntityType.BOOKING;
    }
}
