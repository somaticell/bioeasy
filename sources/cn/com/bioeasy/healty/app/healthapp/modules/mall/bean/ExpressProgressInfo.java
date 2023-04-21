package cn.com.bioeasy.healty.app.healthapp.modules.mall.bean;

import java.io.Serializable;

public class ExpressProgressInfo implements Serializable {
    private String context;
    private String time;

    public String getTime() {
        return this.time;
    }

    public void setTime(String time2) {
        this.time = time2;
    }

    public String getContext() {
        return this.context;
    }

    public void setContext(String context2) {
        this.context = context2;
    }
}
