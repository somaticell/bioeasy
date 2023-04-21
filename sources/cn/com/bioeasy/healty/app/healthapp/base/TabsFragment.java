package cn.com.bioeasy.healty.app.healthapp.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.injection.component.FragmentComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.home.HomeFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.ShoppingFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.MineFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.RecordFragment;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.BlueListActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.TestItemCategoryActivity;

public class TabsFragment extends BIBaseFragment {
    private View currentTabButton;
    private int currentTabIndex = 0;
    private View currentTabText;
    private Fragment[] fragments;

    public BasePresenter getPresenter() {
        return null;
    }

    public void onActivityCreated(Bundle paramBundle) {
        super.onActivityCreated(paramBundle);
        setRetainInstance(true);
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.fragment_tabs;
    }

    public void initViewsAndEvents(View view) {
        this.fragments = new Fragment[4];
        this.currentTabButton = view.findViewById(R.id.iv_home);
        this.currentTabText = view.findViewById(R.id.tv_home);
        onClickTabSelect(this.currentTabIndex);
    }

    private void onClickTabSelect(int index) {
        this.currentTabText.setSelected(true);
        ((TextView) this.currentTabText).setTextColor(getResources().getColor(R.color.tab_bg));
        this.currentTabButton.setSelected(true);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (this.fragments[index] == null) {
            transaction.add(R.id.main_vp_container, getFragment(index), "tab_" + index);
        } else {
            transaction.show(this.fragments[index]);
        }
        if (this.currentTabIndex != index) {
            transaction.hide(this.fragments[this.currentTabIndex]);
        }
        transaction.commit();
        this.currentTabIndex = index;
    }

    private Fragment getFragment(int index) {
        if (this.fragments[index] != null) {
            return this.fragments[index];
        }
        switch (index) {
            case 0:
                this.fragments[index] = new HomeFragment();
                break;
            case 1:
                this.fragments[index] = new RecordFragment();
                break;
            case 2:
                this.fragments[index] = new ShoppingFragment();
                break;
            case 3:
                this.fragments[index] = new MineFragment();
                break;
        }
        return this.fragments[index];
    }

    public void onTabSelect(int tabIndex) {
        this.currentTabText.setSelected(false);
        ((TextView) this.currentTabText).setTextColor(getResources().getColor(R.color.gz_black));
        this.currentTabButton.setSelected(false);
        switch (tabIndex) {
            case 0:
                tabIndex = 0;
                this.currentTabButton = getView().findViewById(R.id.iv_home);
                this.currentTabText = getView().findViewById(R.id.tv_home);
                break;
            case 1:
                tabIndex = 1;
                this.currentTabButton = getView().findViewById(R.id.iv_record);
                this.currentTabText = getView().findViewById(R.id.tv_record);
                break;
            case R.id.lin_test:
                if (!HealthApp.x.isConnected()) {
                    switchPage(BlueListActivity.class);
                    break;
                } else {
                    switchPage(TestItemCategoryActivity.class);
                    break;
                }
            case R.id.lin_shopping:
                tabIndex = 2;
                this.currentTabButton = getView().findViewById(R.id.iv_shopping);
                this.currentTabText = getView().findViewById(R.id.tv_shopping);
                break;
            case R.id.lin_mine:
                tabIndex = 3;
                this.currentTabButton = getView().findViewById(R.id.iv_mine);
                this.currentTabText = getView().findViewById(R.id.tv_mine);
                break;
        }
        onClickTabSelect(tabIndex);
    }

    @OnClick({2131231328, 2131231337, 2131231340, 2131231331, 2131231334})
    public void onClick(View v) {
        this.currentTabText.setSelected(false);
        ((TextView) this.currentTabText).setTextColor(getResources().getColor(R.color.gz_black));
        this.currentTabButton.setSelected(false);
        int index = 0;
        switch (v.getId()) {
            case R.id.lin_home:
                index = 0;
                this.currentTabButton = v.findViewById(R.id.iv_home);
                this.currentTabText = v.findViewById(R.id.tv_home);
                break;
            case R.id.lin_record:
                index = 1;
                this.currentTabButton = v.findViewById(R.id.iv_record);
                this.currentTabText = v.findViewById(R.id.tv_record);
                break;
            case R.id.lin_test:
                if (!HealthApp.x.isConnected()) {
                    switchPage(BlueListActivity.class);
                    break;
                } else {
                    switchPage(TestItemCategoryActivity.class);
                    break;
                }
            case R.id.lin_shopping:
                index = 2;
                this.currentTabButton = v.findViewById(R.id.iv_shopping);
                this.currentTabText = v.findViewById(R.id.tv_shopping);
                break;
            case R.id.lin_mine:
                index = 3;
                this.currentTabButton = v.findViewById(R.id.iv_mine);
                this.currentTabText = v.findViewById(R.id.tv_mine);
                break;
        }
        onClickTabSelect(index);
    }

    /* access modifiers changed from: protected */
    public void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }
}
