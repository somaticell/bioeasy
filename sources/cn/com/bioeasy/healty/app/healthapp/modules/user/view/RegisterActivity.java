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
import cn.com.bioeasy.app.widgets.CircleImageView;
import cn.com.bioeasy.healty.app.healthapp.MainActivity;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.user.RegisterPresenter;
import com.alipay.sdk.cons.a;
import javax.inject.Inject;

public class RegisterActivity extends BIBaseActivity implements RegisterView, TextWatcher, View.OnFocusChangeListener {
    @Bind({2131231077})
    TextView confirmPassword;
    private CountDownButtonHelper helper;
    @Bind({2131230901})
    Button mBtCode;
    @Bind({2131230943})
    Button mBtNext;
    @Bind({2131230879})
    CircleImageView mCiHead;
    @Bind({2131230900})
    EditText mEtAccount;
    @Bind({2131230902})
    EditText mEtCode;
    @Bind({2131230895})
    RelativeLayout mRlOnepage;
    @Bind({2131230898})
    RelativeLayout mRlTwopage;
    @Bind({2131231073})
    RelativeLayout mrlHead;
    @Bind({2131231076})
    TextView password;
    @Inject
    RegisterPresenter registerPresenter;

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_register;
    }

    @OnClick({2131230943, 2131230901, 2131231050, 2131231078})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.bt_code:
                obtainCode();
                return;
            case R.id.btn_next:
                checkVerifyCode();
                return;
            case R.id.iv_back:
                dealBack();
                return;
            case R.id.btn_rst_finsh:
                register();
                return;
            default:
                return;
        }
    }

    private void checkVerifyCode() {
        this.registerPresenter.checkVerifyCode(getPhoneNum(), getValidationCode(), 1, new BasePresenter.ICallback<String>() {
            public void onResult(String result) {
                if (a.e.equals(result)) {
                    RegisterActivity.this.commShow(1.0f, true, 8, 0);
                } else {
                    RegisterActivity.this.showMessage((int) R.string.input_expired);
                }
            }
        });
    }

    private void obtainCode() {
        if (!NumberUtils.isMobileNO(getPhoneNum())) {
            showMessage((int) R.string.number_format_wrong);
            return;
        }
        this.helper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {
            public void finish() {
                RegisterActivity.this.mBtCode.setText(R.string.resend);
            }
        });
        this.helper.start();
        this.registerPresenter.sendVerifyCode(getPhoneNum(), 1);
    }

    private void register() {
        if (StringUtil.isNullOrEmpty(getPassword()) || StringUtil.isNullOrEmpty(getConfirmPassword())) {
            showMessage((int) R.string.password_not_empty);
        } else if (!getPassword().equals(getConfirmPassword())) {
            showMessage((int) R.string.password_are_inconsistent);
        } else {
            this.registerPresenter.register(getPhoneNum(), getPassword());
        }
    }

    private void dealBack() {
        if (this.mRlTwopage.getVisibility() == 0) {
            this.mRlTwopage.setVisibility(8);
            this.mRlOnepage.setVisibility(0);
            this.mCiHead.setVisibility(0);
            this.mrlHead.setVisibility(8);
            return;
        }
        finish();
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.mBtCode.setClickable(false);
        this.mEtCode.addTextChangedListener(this);
        this.mEtAccount.addTextChangedListener(this);
        this.mEtAccount.setOnFocusChangeListener(this);
        commShow(0.5f, false, 0, 8);
        this.helper = new CountDownButtonHelper(this.mBtCode, getString(R.string.send_authentication_code), 60, 1);
    }

    /* access modifiers changed from: private */
    public void commShow(float alpha, boolean clickable, int visible, int gone) {
        this.mrlHead.setVisibility(gone);
        this.mCiHead.setVisibility(visible);
        this.mBtNext.setAlpha(alpha);
        this.mBtNext.setClickable(clickable);
        this.mBtCode.setClickable(true);
        this.mRlOnepage.setVisibility(visible);
        this.mRlTwopage.setVisibility(gone);
    }

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return this.registerPresenter;
    }

    private String getPhoneNum() {
        return this.mEtAccount.getText().toString().trim();
    }

    private String getValidationCode() {
        return this.mEtCode.getText().toString().trim();
    }

    private String getPassword() {
        return this.password.getText().toString().trim();
    }

    private String getConfirmPassword() {
        return this.confirmPassword.getText().toString().trim();
    }

    public void toMainHome() {
        switchPage(MainActivity.class);
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (this.mEtCode.getText().length() > 0) {
            commShow(1.0f, true, 0, 8);
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

    public void afterTextChanged(Editable editable) {
    }

    public void onBackPressed() {
        dealBack();
    }

    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            getWindow().setSoftInputMode(5);
        }
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
}
