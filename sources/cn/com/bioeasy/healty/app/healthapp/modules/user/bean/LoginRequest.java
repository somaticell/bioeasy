package cn.com.bioeasy.healty.app.healthapp.modules.user.bean;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    private String password;
    private String username;

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
}
