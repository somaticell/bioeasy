package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.adapter.ViewPagerAdapter;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.constant.IntentActions;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import com.flyco.tablayout.SlidingTabLayout;
import java.util.ArrayList;

public class MyOrderActivity extends BIBaseActivity {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    @Bind({2131230999})
    SlidingTabLayout mTabLayout;
    @Bind({2131231008})
    TextView mTitle;
    private final String[] mTitles = {getString(R.string.all), getString(R.string.to_be_paid), getString(R.string.consignment), getString(R.string.collect_goods), getString(R.string.to_be_evaluated)};
    @Bind({2131230963})
    ViewPager mViewPage;
    private ViewPagerAdapter viewPagerAdapter;

    public BasePresenter getPresenter() {
        return null;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_my_order;
    }

    @OnClick({2131231050})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.mTitle.setText(getString(R.string.order_mine));
        this.mViewPage.setOffscreenPageLimit(1);
        for (int i = 0; i < this.mTitles.length; i++) {
            this.mFragments.add(OrderStateFragment.newInstance(i - 1));
        }
        if (this.viewPagerAdapter == null) {
            this.viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this.mFragments, this.mTitles);
        } else {
            this.viewPagerAdapter.setFragments(getSupportFragmentManager(), this.mFragments, this.mTitles);
        }
        this.mViewPage.setAdapter(this.viewPagerAdapter);
        this.mTabLayout.setViewPager(this.mViewPage);
        this.mTabLayout.setCurrentTab(getIntent().getIntExtra(IntentActions.ORDER_STATUS, -1) + 1);
    }

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }
}
