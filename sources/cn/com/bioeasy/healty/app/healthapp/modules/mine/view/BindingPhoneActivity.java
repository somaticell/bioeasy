package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

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
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.MainActivity;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.constant.IntentActions;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.user.RegisterPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.OpenAccount;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.UserInfo;
import cn.com.bioeasy.healty.app.healthapp.modules.user.view.RegisterView;
import com.alipay.sdk.cons.a;
import javax.inject.Inject;

public class BindingPhoneActivity extends BIBaseActivity implements TextWatcher, RegisterView {
    private CountDownButtonHelper helper;
    @Bind({2131230903})
    Button mBtBind;
    @Bind({2131230901})
    Button mBtCode;
    @Bind({2131230896})
    TextView mCurrentPhone;
    @Bind({2131230902})
    EditText mEtCode;
    @Bind({2131230900})
    EditText mEtPhone;
    @Bind({2131230895})
    RelativeLayout mRlOnepage;
    @Bind({2131230904})
    RelativeLayout mRlThreepage;
    @Bind({2131230898})
    RelativeLayout mRlTwopage;
    @Bind({2131231008})
    TextView mTvTitle;
    /* access modifiers changed from: private */
    public OpenAccount openAccount;
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
        return R.layout.activity_binding_phone;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.mTvTitle.setText(getResources().getString(R.string.bind_phone));
        this.mEtCode.addTextChangedListener(this);
        this.mEtPhone.addTextChangedListener(this);
        this.openAccount = (OpenAccount) getIntent().getSerializableExtra(IntentActions.OPEN_ACCOUNT);
        if (this.openAccount == null) {
            this.mCurrentPhone.setText(getString(R.string.binding_phone) + HealthApp.x.getUserName());
            commShow(0, 8, 8);
        } else {
            commShow(8, 0, 8);
        }
        this.helper = new CountDownButtonHelper(this.mBtCode, getString(R.string.send_authentication_code), 60, 1);
    }

    @OnClick({2131230901, 2131231050, 2131230897, 2131230907, 2131230903})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change_phone:
                commShow(8, 0, 8);
                return;
            case R.id.bt_code:
                obtainCode();
                return;
            case R.id.btn_bind:
                checkVerifyCode();
                return;
            case R.id.btn_back_main:
                toMainHome();
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
            showMessage((int) R.string.number_format_wrong);
            return;
        }
        this.helper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {
            public void finish() {
                BindingPhoneActivity.this.mBtCode.setText(BindingPhoneActivity.this.getString(R.string.resend));
            }
        });
        this.helper.start();
        this.registerPresenter.sendVerifyCode(getPhoneNum(), 2);
    }

    private void checkVerifyCode() {
        final String phoneNum = getPhoneNum();
        this.registerPresenter.checkVerifyCode(phoneNum, getValidationCode(), 2, new BasePresenter.ICallback<String>() {
            public void onResult(String result) {
                if (!a.e.equals(result)) {
                    BindingPhoneActivity.this.showMessage((int) R.string.input_expired);
                } else if (BindingPhoneActivity.this.openAccount != null) {
                    BindingPhoneActivity.this.openAccount.setMobile(phoneNum);
                    BindingPhoneActivity.this.login();
                } else {
                    BindingPhoneActivity.this.bindPhone();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void bindPhone() {
        this.registerPresenter.bindPhone(HealthApp.x.getUserName(), getPhoneNum(), new BasePresenter.ICallback<UserInfo>() {
            public void onResult(UserInfo result) {
                BindingPhoneActivity.this.commShow(8, 8, 0);
            }
        });
    }

    /* access modifiers changed from: private */
    public void login() {
        this.registerPresenter.addOpenAccount(this.openAccount, new BasePresenter.ICallback<UserInfo>() {
            public void onResult(UserInfo result) {
                BindingPhoneActivity.this.commShow(8, 8, 0);
            }
        });
    }

    private String getPhoneNum() {
        return this.mEtPhone.getText().toString().trim();
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
            this.mBtBind.setClickable(true);
            this.mBtBind.setAlpha(1.0f);
        } else {
            this.mBtBind.setAlpha(0.5f);
            this.mBtBind.setClickable(false);
        }
        if (this.mEtPhone.getText().length() > 0) {
            this.mBtCode.setClickable(true);
            this.mBtCode.setBackgroundResource(R.drawable.shape_obtain_select);
            return;
        }
        this.mBtCode.setClickable(false);
        this.mBtCode.setBackgroundResource(R.drawable.shape_obtain_normal);
    }

    public void afterTextChanged(Editable s) {
    }

    public void toMainHome() {
        finish();
        switchPage(MainActivity.class);
    }
}
