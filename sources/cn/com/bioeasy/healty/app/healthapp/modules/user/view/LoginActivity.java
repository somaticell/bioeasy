package cn.com.bioeasy.healty.app.healthapp.modules.user.view;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.constant.IntentActions;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.BindingPhoneActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.user.LoginPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.OpenAccount;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import com.alibaba.fastjson.JSON;
import java.util.Date;
import java.util.HashMap;
import javax.inject.Inject;

public class LoginActivity extends BIBaseActivity implements LoginView, PlatformActionListener {
    @Bind({2131230979})
    CheckBox cbPwd;
    @Bind({2131230900})
    EditText edtAccount;
    @Bind({2131230978})
    EditText edtPsw;
    @Inject
    LoginPresenter loginPresenter;
    private PlatformDb platDB;
    @Bind({2131230982})
    TextView tvForgetpsw;

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_login;
    }

    @OnClick({2131230980, 2131230985, 2131230982, 2131230979, 2131230977, 2131230988})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_close:
                finish();
                return;
            case R.id.cb_pwd:
                changePwd();
                return;
            case R.id.login:
                this.loginPresenter.login();
                return;
            case R.id.tv_forgetpsw:
                toResetPasswdPage();
                return;
            case R.id.tv_register:
                toRegisterPage();
                return;
            case R.id.iv_wechat_login:
                authorize(ShareSDK.getPlatform(this, Wechat.NAME));
                return;
            default:
                return;
        }
    }

    private void changePwd() {
        this.cbPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    LoginActivity.this.edtPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    LoginActivity.this.edtPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
    }

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return this.loginPresenter;
    }

    public String getPhoneNum() {
        return this.edtAccount.getText().toString().trim();
    }

    public String getPassword() {
        return this.edtPsw.getText().toString().trim();
    }

    public void toMainPage() {
        finish();
    }

    public void toRegisterPage() {
        switchPage(RegisterActivity.class);
    }

    public void toResetPasswdPage() {
        switchPage(ForgetPwdActivity.class);
    }

    private void authorize(Platform plat) {
        if (plat != null) {
            plat.SSOSetting(true);
            plat.setPlatformActionListener(this);
            plat.authorize();
            plat.showUser((String) null);
        }
    }

    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> map) {
        if (arg1 == 8) {
            this.platDB = arg0.getDb();
            if (arg0.getName().equals(Wechat.NAME)) {
                String token = this.platDB.getToken();
                String userId = this.platDB.getUserId();
                String name = this.platDB.getUserName();
                String gender = this.platDB.getUserGender();
                String headImageUrl = this.platDB.getUserIcon();
                if ("m".equals(gender)) {
                }
                final OpenAccount wxOpenAccount = new OpenAccount();
                wxOpenAccount.setOpenId(userId);
                wxOpenAccount.setToken(token);
                wxOpenAccount.setCtime(Long.valueOf(new Date().getTime()));
                wxOpenAccount.setProps(JSON.toJSONString(this.platDB));
                wxOpenAccount.setPlatform("wx");
                this.loginPresenter.wxLogin(wxOpenAccount, new BasePresenter.ICallback<OpenAccount>() {
                    public void onResult(OpenAccount result) {
                        if (result == null || result.getUser() == null) {
                            Intent intent = new Intent(LoginActivity.this, BindingPhoneActivity.class);
                            intent.putExtra(IntentActions.OPEN_ACCOUNT, wxOpenAccount);
                            LoginActivity.this.startActivity(intent);
                            return;
                        }
                        LoginActivity.this.toMainPage();
                    }
                });
            } else if (!arg0.getName().equals(SinaWeibo.NAME) && arg0.getName().equals(QQ.NAME)) {
                String token2 = this.platDB.getToken();
                String userId2 = this.platDB.getUserId();
                String name2 = map.get("nickname").toString();
                String obj = map.get("gender").toString();
                String headImageUrl2 = map.get("figureurl_qq_2").toString();
                String obj2 = map.get("city").toString();
                String obj3 = map.get("province").toString();
                getUserInfo(name2, headImageUrl2);
            }
        }
    }

    public void onError(Platform platform, int i, Throwable throwable) {
        showMessage((int) R.string.login_failure);
    }

    public void onCancel(Platform platform, int i) {
    }

    private void getUserInfo(String name, String imgUrl) {
        runOnUiThread(new Runnable() {
            public void run() {
            }
        });
    }
}
