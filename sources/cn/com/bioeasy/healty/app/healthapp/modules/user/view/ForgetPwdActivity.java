package cn.com.bioeasy.healty.app.healthapp.modules.user.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.device.CountDownButtonHelper;
import cn.com.bioeasy.app.utils.NumberUtils;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.MainActivity;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.user.RegisterPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.UserInfo;
import com.alipay.sdk.cons.a;
import javax.inject.Inject;
import org.achartengine.ChartFactory;

public class ForgetPwdActivity extends BIBaseActivity implements TextWatcher, RegisterView {
    private String code;
    private CountDownButtonHelper helper;
    @Bind({2131230901})
    Button mBtCode;
    @Bind({2131230943})
    Button mBtNext;
    @Bind({2131230940})
    EditText mEtAccount;
    @Bind({2131230942})
    EditText mEtCode;
    @Bind({2131230927})
    EditText mEtagainPwd;
    @Bind({2131230926})
    EditText mEtnewPwd;
    @Bind({2131230924})
    RelativeLayout mRlOnepage;
    @Bind({2131230945})
    RelativeLayout mRlThreepage;
    @Bind({2131230930})
    RelativeLayout mRlTwopage;
    @Bind({2131231008})
    TextView mTvTitle;
    private String phone;
    @Inject
    RegisterPresenter registerPresenter;

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return this.registerPresenter;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_forget_pwd;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.mEtCode.addTextChangedListener(this);
        this.mEtAccount.addTextChangedListener(this);
        this.mBtNext.setClickable(false);
        this.mBtNext.setAlpha(0.5f);
        String title = getIntent().getStringExtra(ChartFactory.TITLE);
        if (StringUtil.isNullOrEmpty(title)) {
            title = getResources().getString(R.string.login_forget_pwd);
        }
        this.mTvTitle.setText(title);
        if (HealthApp.x.isLogin()) {
            this.mEtAccount.setText(HealthApp.x.getUserName());
        }
        this.helper = new CountDownButtonHelper(this.mBtCode, getString(R.string.send_authentication_code), 60, 1);
    }

    @OnClick({2131230943, 2131230901, 2131231050, 2131230944, 2131230907})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.bt_code:
                obtainCode();
                return;
            case R.id.btn_back_main:
                toMainHome();
                return;
            case R.id.btn_next:
                checkVerifyCode();
                return;
            case R.id.btn_finsh:
                resetPassword();
                return;
            case R.id.iv_back:
                finish();
                return;
            default:
                return;
        }
    }

    private void obtainCode() {
        if (!NumberUtils.isMobileNO(getPhoneNum())) {
            showMessage(getString(R.string.number_format_wrong));
            return;
        }
        this.helper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {
            public void finish() {
                ForgetPwdActivity.this.mBtCode.setText(R.string.resend);
            }
        });
        this.helper.start();
        this.registerPresenter.sendVerifyCode(getPhoneNum(), 2);
    }

    private void checkVerifyCode() {
        this.registerPresenter.checkVerifyCode(getPhoneNum(), getValidationCode(), 2, new BasePresenter.ICallback<String>() {
            public void onResult(String result) {
                if (a.e.equals(result)) {
                    ForgetPwdActivity.this.commShow(8, 0, 8);
                } else {
                    ForgetPwdActivity.this.showMessage((int) R.string.input_expired);
                }
            }
        });
    }

    private void resetPassword() {
        if (StringUtil.isNullOrEmpty(getPassword()) || StringUtil.isNullOrEmpty(getConfirmPassword())) {
            showMessage((int) R.string.password_not_empty);
        } else if (!getPassword().equals(getConfirmPassword())) {
            showMessage((int) R.string.password_are_inconsistent);
        } else {
            this.registerPresenter.resetPwd(getPhoneNum(), getPassword(), new BasePresenter.ICallback<UserInfo>() {
                public void onResult(UserInfo result) {
                    ForgetPwdActivity.this.commShow(8, 8, 0);
                }
            });
        }
    }

    private String getPassword() {
        return this.mEtnewPwd.getText().toString().trim();
    }

    private String getConfirmPassword() {
        return this.mEtagainPwd.getText().toString().trim();
    }

    private String getPhoneNum() {
        return this.mEtAccount.getText().toString().trim();
    }

    private String getValidationCode() {
        return this.mEtCode.getText().toString().trim();
    }

    /* access modifiers changed from: private */
    public void commShow(int gone, int visible, int gone2) {
        this.mRlOnepage.setVisibility(gone);
        this.mRlTwopage.setVisibility(visible);
        this.mRlThreepage.setVisibility(gone2);
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (this.mEtCode.getText().length() > 0) {
            this.mBtNext.setClickable(true);
            this.mBtNext.setAlpha(1.0f);
        } else {
            this.mBtNext.setAlpha(0.5f);
            this.mBtNext.setClickable(false);
        }
        if (this.mEtAccount.getText().length() > 0) {
            this.mBtCode.setClickable(true);
            this.mBtCode.setBackgroundResource(R.drawable.shape_obtain_select);
            return;
        }
        this.mBtCode.setClickable(false);
        this.mBtCode.setBackgroundResource(R.drawable.shape_obtain_normal);
    }

    public void afterTextChanged(Editable s) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.helper.countDownTimer != null) {
            this.helper.countDownTimer.cancel();
            this.helper.countDownTimer = null;
            this.helper = null;
        }
    }

    public void toMainHome() {
        finish();
        switchPage(MainActivity.class);
    }
}
