package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.MainActivity;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.user.LoginPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.UserInfo;
import cn.com.bioeasy.healty.app.healthapp.modules.user.view.LoginView;
import javax.inject.Inject;

public class EditNickNameActivity extends BIBaseActivity implements TextWatcher, LoginView {
    @Inject
    LoginPresenter loginPresenter;
    @Bind({2131231011})
    EditText mEtNickName;
    @Bind({2131231012})
    ImageView mIvDelete;
    @Bind({2131231178})
    TextView mTvFinish;
    @Bind({2131231008})
    TextView mTvTitle;

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return this.loginPresenter;
    }

    @OnClick({2131231050, 2131231178, 2131231012})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.iv_delete:
                this.mEtNickName.setText("");
                return;
            case R.id.iv_back:
                finish();
                return;
            case R.id.id_tv_add_all:
                updateUser();
                return;
            default:
                return;
        }
    }

    private void updateUser() {
        if (StringUtil.isNullOrEmpty(this.mEtNickName.getText().toString())) {
            showMessage((int) R.string.nickname_cannot_empty);
        } else {
            this.loginPresenter.updateNickName(this.mEtNickName.getText().toString(), new BasePresenter.ICallback<Integer>() {
                public void onResult(Integer result) {
                    if (HealthApp.x.getUserInfo() != null) {
                        HealthApp.x.getUserInfo().setNickName(EditNickNameActivity.this.mEtNickName.getText().toString());
                        HealthApp.x.getDataManager().setUserInfo(HealthApp.x.getUserInfo());
                    }
                    EditNickNameActivity.this.toMainPage();
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_nickname;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.mTvTitle.setText(getResources().getString(R.string.info_edit_nick));
        this.mTvFinish.setVisibility(0);
        this.mTvFinish.setText(getString(R.string.register_finish));
        UserInfo userInfo = HealthApp.x.getUserInfo();
        if (userInfo != null && !StringUtil.isNullOrEmpty(userInfo.getNickName())) {
            this.mEtNickName.setText(userInfo.getNickName());
        }
        this.mEtNickName.addTextChangedListener(this);
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            this.mIvDelete.setVisibility(0);
        } else {
            this.mIvDelete.setVisibility(8);
        }
    }

    public void afterTextChanged(Editable s) {
    }

    public void toRegisterPage() {
    }

    public String getPhoneNum() {
        return null;
    }

    public String getPassword() {
        return null;
    }

    public void toMainPage() {
        finish();
        switchPage(MainActivity.class);
    }

    public void toResetPasswdPage() {
    }
}
