package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.support.v4.app.Fragment;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.adapter.ScrollImagesViewPager;
import cn.com.bioeasy.healty.app.healthapp.adapter.ViewPagerAdapter;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import cn.com.bioeasy.healty.app.healthapp.injection.component.FragmentComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.GoodsPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.CategoryNode;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.MyOrderActivity;
import com.flyco.tablayout.SlidingTabLayout;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class ShoppingFragment extends BIBaseFragment implements GoodsView {
    /* access modifiers changed from: private */
    public ArrayList<Fragment> mFragments = new ArrayList<>();
    @Bind({2131230999})
    SlidingTabLayout mTabLayout;
    @Bind({2131230963})
    ViewPager mViewPage;
    @Inject
    GoodsPresenter presenter;
    @Bind({2131231321})
    RollPagerView rollView;
    @Inject
    ScrollImagesViewPager scrollImagesViewPager;
    /* access modifiers changed from: private */
    public ViewPagerAdapter viewPagerAdapter;

    /* access modifiers changed from: protected */
    public void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    public BasePresenter getPresenter() {
        return this.presenter;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.fragment_shopping;
    }

    @OnClick({2131231322, 2131231181, 2131231323})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_search_layout:
                switchPage(GoodsSearchActivity.class);
                return;
            case R.id.iv_shop_cart:
                ShopCartActivity.startAction(getActivity(), 0);
                return;
            case R.id.tv_my_order:
                switchPage(MyOrderActivity.class);
                return;
            default:
                return;
        }
    }

    private void initRPagerView() {
        this.rollView.setPlayDelay(2000);
        this.rollView.setAnimationDurtion(500);
        this.rollView.setAdapter(this.scrollImagesViewPager);
        this.rollView.setHintView(new ColorPointHintView(getActivity(), InputDeviceCompat.SOURCE_ANY, -1));
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents(View rootView) {
        initRPagerView();
        this.presenter.getGoodsCategoryList(new BasePresenter.ICallback<List<CategoryNode>>() {
            public void onResult(List<CategoryNode> list) {
                String[] mTitles = new String[0];
                if (list.size() > 0) {
                    List<CategoryNode> mainNodeList = list.get(0).getChildren();
                    mTitles = new String[mainNodeList.size()];
                    for (int i = 0; i < mainNodeList.size(); i++) {
                        mTitles[i] = mainNodeList.get(i).getName();
                        ShoppingFragment.this.mFragments.add(GoodsListFragment.getInstance(mainNodeList.get(i)));
                    }
                }
                if (ShoppingFragment.this.viewPagerAdapter == null) {
                    ViewPagerAdapter unused = ShoppingFragment.this.viewPagerAdapter = new ViewPagerAdapter(ShoppingFragment.this.getChildFragmentManager(), ShoppingFragment.this.mFragments, mTitles);
                } else {
                    ShoppingFragment.this.viewPagerAdapter.setFragments(ShoppingFragment.this.getChildFragmentManager(), ShoppingFragment.this.mFragments, mTitles);
                }
                ShoppingFragment.this.mViewPage.setAdapter(ShoppingFragment.this.viewPagerAdapter);
                ShoppingFragment.this.mTabLayout.setViewPager(ShoppingFragment.this.mViewPage);
            }
        });
    }

    public void showErrorLayout(int errorType) {
    }
}
