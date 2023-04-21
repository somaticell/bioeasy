package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.adapter.ItemTitlePagerAdapter;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.GoodsPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.GoodsWithBLOBs;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.ShopCardItem;
import cn.com.bioeasy.healty.app.healthapp.widgets.NoScrollViewPager;
import com.gxz.PagerSlidingTabStrip;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class ProductsActivity extends BIBaseActivity implements GoodsView {
    private int goodId;
    private GoodsCommentFragment goodsCommentFragment;
    /* access modifiers changed from: private */
    public GoodsDetailFragment goodsDetailFragment;
    /* access modifiers changed from: private */
    public GoodsInfoFragment goodsInfoFragment;
    @Inject
    GoodsPresenter goodsPresenter;
    private GoodsSelectDialog goodsSelectDialog;
    /* access modifiers changed from: private */
    public GoodsWithBLOBs goodsWithBLOBs;
    @Bind({2131231051})
    public PagerSlidingTabStrip psts_tabs;
    @Bind({2131231056})
    public RelativeLayout rlAddShoppingCart;
    @Bind({2131231052})
    public TextView tvTitle;
    @Bind({2131231053})
    public NoScrollViewPager vp_content;

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return this.goodsPresenter;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_product_detail;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        finish();
        return true;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        if (getIntent() != null) {
            this.goodId = getIntent().getIntExtra("goodsBean", 0);
        }
        this.goodsInfoFragment = new GoodsInfoFragment();
        this.goodsDetailFragment = new GoodsDetailFragment();
        this.goodsInfoFragment.setGoodsId(this.goodId);
        this.goodsDetailFragment.setGoodsId(this.goodId);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(this.goodsInfoFragment);
        fragmentList.add(this.goodsDetailFragment);
        this.vp_content.setAdapter(new ItemTitlePagerAdapter(getSupportFragmentManager(), fragmentList, new String[]{getString(R.string.MALL_PRODUCT), getString(R.string.MALL_PRODUCT_DETAIL)}));
        this.vp_content.setOffscreenPageLimit(3);
        this.psts_tabs.setViewPager(this.vp_content);
        this.goodsPresenter.getGoodsDetail(this.goodId, new BasePresenter.ICallback<GoodsWithBLOBs>() {
            public void onResult(GoodsWithBLOBs result) {
                GoodsWithBLOBs unused = ProductsActivity.this.goodsWithBLOBs = result;
                ProductsActivity.this.goodsInfoFragment.setGoodsDetail(ProductsActivity.this.goodsWithBLOBs);
                ProductsActivity.this.goodsDetailFragment.setGoodsDetail(ProductsActivity.this.goodsWithBLOBs);
            }
        });
    }

    @OnClick({2131231050, 2131231056, 2131231054, 2131231057})
    public void onClick(View view) {
        if (view.getId() == R.id.rl_addshopping_cart) {
            if (this.goodsWithBLOBs.getSpecs() == null || this.goodsWithBLOBs.getSpecs().size() == 0) {
                addShopCart();
                return;
            }
            this.goodsSelectDialog = new GoodsSelectDialog(this, this.goodsWithBLOBs);
            this.goodsSelectDialog.showDialog();
        } else if (view.getId() == R.id.rl_shopping_cart) {
            ShopCartActivity.startAction(this, 0);
            finish();
        } else if (view.getId() == R.id.rl_buy) {
            if (this.goodsWithBLOBs.getSpecs() == null || this.goodsWithBLOBs.getSpecs().size() == 0) {
                addShopOrder();
            } else {
                this.goodsSelectDialog = new GoodsSelectDialog(this, this.goodsWithBLOBs);
                this.goodsSelectDialog.showDialog();
            }
            finish();
        } else {
            finish();
        }
    }

    private synchronized void addShopCart() {
        ShopCardItem item = new ShopCardItem();
        item.setGoodId(this.goodId);
        item.setPrice(this.goodsWithBLOBs.getShopPrice());
        item.setAmount(1);
        item.save();
        showMessage((int) R.string.MALL_PRODUCT_CAR);
    }

    private void addShopOrder() {
        ShopCardItem item = new ShopCardItem();
        item.setGoodId(this.goodId);
        item.setPrice(this.goodsWithBLOBs.getShopPrice());
        item.setAmount(1);
        item.setGoods(this.goodsWithBLOBs);
        List<ShopCardItem> listSelectedProductList = new ArrayList<>();
        listSelectedProductList.add(item);
        OrderActivity.startAction(this, listSelectedProductList, StringUtil.isNullOrEmpty(item.getPrice()) ? 0.0d : Double.parseDouble(item.getPrice()));
    }

    public static void startAction(Activity activity, int goodsId) {
        Intent intent = new Intent(activity, ProductsActivity.class);
        intent.putExtra("goodsBean", goodsId);
        activity.startActivity(intent);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.goodsSelectDialog != null) {
            this.goodsSelectDialog.dismiss();
        }
    }

    public void showErrorLayout(int errorType) {
    }
}
