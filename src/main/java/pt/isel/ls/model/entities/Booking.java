package pt.isel.ls.model.entities;

import java.util.Date;

public class Booking {
    private int bid;
    private int uid;
    private int rid;
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

    public Date getBegin_inst() {
        return begin_inst;
    }

    public Date getEnd_inst() {
        return end_inst;
    }
}
