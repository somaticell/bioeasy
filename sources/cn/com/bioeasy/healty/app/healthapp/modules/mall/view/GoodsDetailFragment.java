package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.utils.ScreenUtils;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import cn.com.bioeasy.healty.app.healthapp.injection.component.FragmentComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.GoodsPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.GoodsWithBLOBs;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class GoodsDetailFragment extends BIBaseFragment {
    private FragmentManager fragmentManager;
    private float fromX;
    private int goodID;
    private GoodsConfigFragment goodsConfigFragment;
    private GoodsDetailWebFragment goodsDetailWebFragment;
    @Inject
    GoodsPresenter goodsPresenter;
    GoodsWithBLOBs goodsWithBLOBs;
    private Fragment nowFragment;
    private int nowIndex;
    private List<TextView> tabTextList;
    @Bind({2131231358})
    TextView tv_goods_config;
    @Bind({2131231357})
    TextView tv_goods_detail;
    @Bind({2131231359})
    View v_tab_cursor;

    public BasePresenter getPresenter() {
        return this.goodsPresenter;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.fragment_goods_detail;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents(View rootView) {
        this.tabTextList = new ArrayList();
        this.tabTextList.add(this.tv_goods_detail);
        this.tabTextList.add(this.tv_goods_config);
        ViewGroup.LayoutParams lp = this.v_tab_cursor.getLayoutParams();
        lp.width = ScreenUtils.getScreenW(getActivity()) / 2;
        this.v_tab_cursor.setLayoutParams(lp);
        this.fragmentManager = getChildFragmentManager();
        this.goodsDetailWebFragment = new GoodsDetailWebFragment();
        this.nowFragment = this.goodsDetailWebFragment;
        this.fragmentManager.beginTransaction().replace(R.id.fl_content, this.nowFragment).commitAllowingStateLoss();
        if (this.goodsWithBLOBs != null) {
            this.goodsDetailWebFragment.initData(this.goodsWithBLOBs.getDetail());
        }
    }

    /* access modifiers changed from: protected */
    public void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @OnClick({2131231357, 2131231358})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_goods_detail:
                if (this.goodsDetailWebFragment == null) {
                    this.goodsDetailWebFragment = new GoodsDetailWebFragment();
                    if (this.goodsWithBLOBs != null) {
                        this.goodsDetailWebFragment.initData(this.goodsWithBLOBs.getDetail());
                    }
                }
                switchFragment(this.nowFragment, this.goodsDetailWebFragment);
                this.nowIndex = 0;
                this.nowFragment = this.goodsDetailWebFragment;
                scrollCursor();
                return;
            case R.id.tv_goods_config:
                if (this.goodsConfigFragment == null) {
                    this.goodsConfigFragment = new GoodsConfigFragment();
                    if (this.goodsWithBLOBs != null) {
                        this.goodsConfigFragment.initConfig(this.goodsWithBLOBs.getAttrs());
                    }
                }
                switchFragment(this.nowFragment, this.goodsConfigFragment);
                this.nowIndex = 1;
                this.nowFragment = this.goodsConfigFragment;
                scrollCursor();
                return;
            default:
                return;
        }
    }

    private void scrollCursor() {
        TranslateAnimation anim = new TranslateAnimation(this.fromX, (float) (this.nowIndex * this.v_tab_cursor.getWidth()), 0.0f, 0.0f);
        anim.setFillAfter(true);
        anim.setDuration(50);
        this.fromX = (float) (this.nowIndex * this.v_tab_cursor.getWidth());
        this.v_tab_cursor.startAnimation(anim);
        int i = 0;
        while (i < this.tabTextList.size()) {
            this.tabTextList.get(i).setTextColor(i == this.nowIndex ? getResources().getColor(R.color.text_red) : getResources().getColor(R.color.text_black));
            i++;
        }
    }

    private void switchFragment(Fragment fromFragment, Fragment toFragment) {
        if (this.nowFragment != toFragment) {
            FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
            if (!toFragment.isAdded()) {
                fragmentTransaction.hide(fromFragment).add((int) R.id.fl_content, toFragment).commitAllowingStateLoss();
            } else {
                fragmentTransaction.hide(fromFragment).show(toFragment).commitAllowingStateLoss();
            }
        }
    }

    public void setGoodsId(int goodID2) {
        this.goodID = goodID2;
    }

    public void setGoodsDetail(GoodsWithBLOBs goodsWithBLOBs2) {
        this.goodsWithBLOBs = goodsWithBLOBs2;
        if (this.nowFragment != null && (this.nowFragment instanceof GoodsDetailWebFragment)) {
            this.goodsDetailWebFragment.initData(this.goodsWithBLOBs.getDetail());
        }
        if (this.nowFragment != null && (this.nowFragment instanceof GoodsConfigFragment)) {
            this.goodsConfigFragment.initConfig(this.goodsWithBLOBs.getAttrs());
        }
    }
}
