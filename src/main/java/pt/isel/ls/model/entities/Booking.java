package pt.isel.ls.model.entities;

import java.util.Date;

public class Booking implements Entity {
    private int bid;
    private int uid = -1;
    private int rid = -1;
    private Date begin_inst;
    private Date end_inst;

    public Booking(int bid, int uid, int rid, Date begin_inst, Date end_inst) {
        this.bid = bid;
        this.uid = uid;
        this.rid = rid;
        this.begin_inst = begin_inst;
        this.end_inst = end_inst;
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
        return begin_inst;
    }

    public Date getEndInst() {
        return end_inst;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.BOOKING;
    }
}
