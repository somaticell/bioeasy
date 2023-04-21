package cn.com.bioeasy.healty.app.healthapp.modules.user.bean;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Date ctime;
    private Integer deviceId;
    private Integer id;
    private Date loginTime;
    private String mobile;
    private Date mtime;
    private String nickName;
    private Integer orgId;
    private String password;
    private String props;
    private String salt;
    private Integer status;
    private String username;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id2) {
        this.id = id2;
    }

    public Date getCtime() {
        return this.ctime;
    }

    public void setCtime(Date ctime2) {
        this.ctime = ctime2;
    }

    public Date getMtime() {
        return this.mtime;
    }

    public void setMtime(Date mtime2) {
        this.mtime = mtime2;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username2) {
        this.username = username2;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password2) {
        this.password = password2;
    }

    public String getSalt() {
        return this.salt;
    }

    public void setSalt(String salt2) {
        this.salt = salt2;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status2) {
        this.status = status2;
    }

    public Date getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(Date loginTime2) {
        this.loginTime = loginTime2;
    }

    public Integer getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(Integer deviceId2) {
        this.deviceId = deviceId2;
    }

    public Integer getOrgId() {
        return this.orgId;
    }

    public void setOrgId(Integer orgId2) {
        this.orgId = orgId2;
    }

    public String getProps() {
        return this.props;
    }

    public void setProps(String props2) {
        this.props = props2;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile2) {
        this.mobile = mobile2;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName2) {
        this.nickName = nickName2;
    }
}
