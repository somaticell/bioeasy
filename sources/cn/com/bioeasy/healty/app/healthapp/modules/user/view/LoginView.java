package cn.com.bioeasy.healty.app.healthapp.modules.user.view;

import cn.com.bioeasy.app.base.BaseView;

public interface LoginView extends BaseView {
    String getPassword();

    String getPhoneNum();

    void toMainPage();

    void toRegisterPage();

    void toResetPasswdPage();
}
