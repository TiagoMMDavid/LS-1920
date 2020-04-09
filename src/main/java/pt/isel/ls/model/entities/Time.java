package pt.isel.ls.model.entities;

import java.util.Date;

public class Time implements Entity {
    private Date time;

    public Time(Date time) {
        this.time = time;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.TIME;
    }

    public Date getTime() {
        return time;
    }
}
