package cn.com.bioeasy.healty.app.healthapp.modules.user.bean;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private String deviceName;
    private int integral;
    private String nickName;
    private int orgId;
    private String orgName;
    private String token;
    private int userId;
    private String userName;

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId2) {
        this.userId = userId2;
    }

    public int getOrgId() {
        return this.orgId;
    }

    public void setOrgId(int orgId2) {
        this.orgId = orgId2;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName2) {
        this.orgName = orgName2;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName2) {
        this.deviceName = deviceName2;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName2) {
        this.userName = userName2;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token2) {
        this.token = token2;
    }

    public int getIntegral() {
        return this.integral;
    }

    public void setIntegral(int integral2) {
        this.integral = integral2;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName2) {
        this.nickName = nickName2;
    }
}
