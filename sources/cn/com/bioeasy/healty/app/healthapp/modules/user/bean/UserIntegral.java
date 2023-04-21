package cn.com.bioeasy.healty.app.healthapp.modules.user.bean;

import java.io.Serializable;

public class UserIntegral implements Serializable {
    private static final long serialVersionUID = 1;
    private long ctime;
    private Integer id;
    private Integer integral;
    private long mtime;
    private String props;
    private Integer type;
    private Integer userId;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id2) {
        this.id = id2;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId2) {
        this.userId = userId2;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type2) {
        this.type = type2;
    }

    public Integer getIntegral() {
        return this.integral;
    }

    public void setIntegral(Integer integral2) {
        this.integral = integral2;
    }

    public String getProps() {
        return this.props;
    }

    public void setProps(String props2) {
        this.props = props2 == null ? null : props2.trim();
    }

    public long getCtime() {
        return this.ctime;
    }

    public void setCtime(long ctime2) {
        this.ctime = ctime2;
    }

    public long getMtime() {
        return this.mtime;
    }

    public void setMtime(long mtime2) {
        this.mtime = mtime2;
    }
}
