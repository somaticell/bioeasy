package cn.com.bioeasy.healty.app.healthapp.modules.home.bean;

import java.io.Serializable;
import org.litepal.crud.DataSupport;

public class MarketSearchItem extends DataSupport implements Serializable {
    private int id;
    private String name;
    private long time;

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time2) {
        this.time = time2;
    }
}
