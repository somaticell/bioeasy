package cn.com.bioeasy.healty.app.healthapp.modules.home.view;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.adapter.ViewPagerAdapter;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import com.flyco.tablayout.SlidingTabLayout;
import java.util.ArrayList;
import java.util.List;

public class HotInformationActivity extends BIBaseActivity {
    @Bind({2131231175})
    ImageView ivSearch;
    @Bind({2131230962})
    SlidingTabLayout mTabLayout;
    private String[] mTitles;
    @Bind({2131230963})
    ViewPager mViewPage;
    @Bind({2131231008})
    TextView tvTitle;
    private int[] types;
    ViewPagerAdapter viewPagerAdapter;

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return null;
    }

    @OnClick({2131231050, 2131231175})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                return;
            case R.id.iv_search:
                switchPage(ContentSearchActivity.class);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_hotinfomation;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.tvTitle.setText(getResources().getString(R.string.hot_information));
        this.ivSearch.setVisibility(0);
        this.mTitles = new String[]{getString(R.string.all), getString(R.string.food_safety_information), getString(R.string.healthy_kitchen), getString(R.string.health_diet), getString(R.string.expert_gang)};
        this.types = new int[]{-1, 2, 3, 4, 5};
        if (this.viewPagerAdapter == null) {
            this.viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), initFragments(), this.mTitles);
        } else {
            this.viewPagerAdapter.setFragments(getSupportFragmentManager(), initFragments(), this.mTitles);
        }
        this.mViewPage.setAdapter(this.viewPagerAdapter);
        this.mTabLayout.setViewPager(this.mViewPage);
    }

    private List<Fragment> initFragments() {
        List<Fragment> mNewsFragmentList = new ArrayList<>();
        for (int i = 0; i < this.mTitles.length; i++) {
            mNewsFragmentList.add(SafeInformationFragment.getInstance(this.types[i]));
        }
        return mNewsFragmentList;
    }
}
