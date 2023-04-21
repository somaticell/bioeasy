package cn.com.bioeasy.healty.app.healthapp.modules.user.bean;

import java.io.Serializable;

public class OpenAccount implements Serializable {
    private static final long serialVersionUID = 1;
    private Long ctime;
    private String mobile;
    private Long mtime;
    private String openId;
    private String platform;
    private String props;
    private String token;
    private UserInfo user;
    private Integer userId;

    public String getToken() {
        return this.token;
    }

    public void setToken(String token2) {
        this.token = token2 == null ? null : token2.trim();
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String platform2) {
        this.platform = platform2 == null ? null : platform2.trim();
    }

    public Long getCtime() {
        return this.ctime;
    }

    public void setCtime(Long ctime2) {
        this.ctime = ctime2;
    }

    public Long getMtime() {
        return this.mtime;
    }

    public void setMtime(Long mtime2) {
        this.mtime = mtime2;
    }

    public String getProps() {
        return this.props;
    }

    public void setProps(String props2) {
        this.props = props2 == null ? null : props2.trim();
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile2) {
        this.mobile = mobile2;
    }

    public UserInfo getUser() {
        return this.user;
    }

    public void setUser(UserInfo user2) {
        this.user = user2;
    }

    public String getOpenId() {
        return this.openId;
    }

    public void setOpenId(String openId2) {
        this.openId = openId2 == null ? null : openId2.trim();
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId2) {
        this.userId = userId2;
    }
}
