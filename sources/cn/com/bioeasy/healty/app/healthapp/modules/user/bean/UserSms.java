package cn.com.bioeasy.healty.app.healthapp.modules.user.bean;

import java.io.Serializable;

public class UserSms implements Serializable {
    public static final int TYPE_LOGIN = 1;
    public static final int TYPE_RESET_PASSWORD = 2;
    private String code;
    private String mobile;
    private int type;

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile2) {
        this.mobile = mobile2;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code2) {
        this.code = code2;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }
}
