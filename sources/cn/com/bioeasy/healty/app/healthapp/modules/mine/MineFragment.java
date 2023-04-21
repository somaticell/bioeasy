package cn.com.bioeasy.healty.app.healthapp.modules.mine;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import cn.com.bioeasy.healty.app.healthapp.constant.IntentActions;
import cn.com.bioeasy.healty.app.healthapp.injection.component.FragmentComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.AboutMeActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.InfoActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.MineView;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.MyOrderActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.UserInfo;

public class MineFragment extends BIBaseFragment implements MineView {
    @Bind({2131231001})
    TextView mTVScore;
    @Bind({2131231280})
    TextView mTvLoginTip;
    @Bind({2131230971})
    TextView mTvNickname;
    @Bind({2131231281})
    TextView mTvPersonId;

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.fragment_mine;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents(View rootView) {
        this.mTVScore.setText(String.valueOf(HealthApp.x.getUserInfo() != null ? HealthApp.x.getUserInfo().getIntegral() : 0));
    }

    /* access modifiers changed from: protected */
    public void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @OnClick({2131230878, 2131231282, 2131231288, 2131231290, 2131231292, 2131231296, 2131231294, 2131231284, 2131231299})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_mine_login:
                if (HealthApp.x.isLogin()) {
                    switchPage(InfoActivity.class);
                    return;
                } else {
                    toLoginPage();
                    return;
                }
            case R.id.rl_score:
                showMessage((int) R.string.not_on_line);
                return;
            case R.id.rl_all_order:
                startupOrderList(-1);
                return;
            case R.id.rl_pay:
                startupOrderList(0);
                return;
            case R.id.rl_send:
                startupOrderList(1);
                return;
            case R.id.rl_obtain:
                startupOrderList(2);
                return;
            case R.id.rl_pingjia:
                startupOrderList(3);
                return;
            case R.id.rl_refund:
                startupOrderList(4);
                return;
            case R.id.rl_about:
                switchPage(AboutMeActivity.class);
                return;
            default:
                return;
        }
    }

    public void startupOrderList(int orderStatus) {
        if (!HealthApp.x.isLogin()) {
            toLoginPage();
            return;
        }
        Intent allOrderList = new Intent(getActivity(), MyOrderActivity.class);
        allOrderList.putExtra(IntentActions.ORDER_STATUS, orderStatus);
        getActivity().startActivity(allOrderList);
    }

    public BasePresenter getPresenter() {
        return null;
    }

    private void initLoginData() {
        if (HealthApp.x.isLogin()) {
            this.mTvNickname.setVisibility(0);
            this.mTvPersonId.setVisibility(0);
            this.mTVScore.setVisibility(0);
            UserInfo userLogin = HealthApp.x.getUserInfo();
            this.mTvNickname.setText(String.format(getString(R.string.login_nickName), new Object[]{TextUtils.isEmpty(userLogin.getNickName()) ? userLogin.getUserName() : userLogin.getNickName()}));
            if (userLogin.getUserId() != 0) {
                this.mTvPersonId.setText(String.format(getString(R.string.login_id), new Object[]{String.valueOf(userLogin.getUserId())}));
            }
            this.mTvLoginTip.setVisibility(8);
            this.mTVScore.setText(String.valueOf(userLogin.getIntegral()));
            return;
        }
        this.mTvNickname.setText("");
        this.mTvPersonId.setText("");
        this.mTvLoginTip.setVisibility(0);
        this.mTvNickname.setVisibility(8);
        this.mTvPersonId.setVisibility(8);
        this.mTVScore.setText("");
    }

    public void onResume() {
        super.onResume();
        initLoginData();
    }
}
