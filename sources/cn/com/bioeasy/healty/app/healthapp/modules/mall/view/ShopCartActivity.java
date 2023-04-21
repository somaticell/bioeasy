package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.utils.ListUtils;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.adapter.ShopCartListAdapter;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.GoodsPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.Goods;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.GoodsWithBLOBs;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.ShopCardItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.litepal.crud.DataSupport;

public class ShopCartActivity extends BIBaseActivity implements GoodsView {
    /* access modifiers changed from: private */
    public ShopCartListAdapter adapter;
    private int currentSelectGoodId = 0;
    @Inject
    GoodsPresenter goodsPresenter;
    @Bind({2131231550})
    CheckBox id_cb_select_all;
    @Bind({2131231549})
    RelativeLayout id_rl_foot;
    @Bind({2131231554})
    TextView id_tv_totalCount_jiesuan;
    @Bind({2131231552})
    TextView id_tv_totalPrice;
    /* access modifiers changed from: private */
    public List<ShopCardItem> listSelectedProductList;
    @Bind({2131231103})
    ListView mListView;
    @Bind({2131231178})
    TextView mtvEdit;
    @Bind({2131231104})
    RelativeLayout rlEmpty;
    /* access modifiers changed from: private */
    public List<ShopCardItem> shopCardItems;
    /* access modifiers changed from: private */
    public int totalCountBiz;
    public double totalGoodPrice;
    @Bind({2131231008})
    TextView tvTitle;

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
        return R.layout.activity_shop_cart;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.currentSelectGoodId = getIntent().getIntExtra("currentSelectGoodId", 0);
        this.mtvEdit.setVisibility(0);
        this.tvTitle.setText(getString(R.string.shopping_cart));
        this.adapter = new ShopCartListAdapter(this);
        this.mListView.setAdapter(this.adapter);
        initData();
        initListener();
    }

    private void initData() {
        this.shopCardItems = DataSupport.findAll(ShopCardItem.class, new long[0]);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.shopCardItems.size(); i++) {
            sb.append(this.shopCardItems.get(i).getGoodId());
            if (i < this.shopCardItems.size() - 1) {
                sb.append(ListUtils.DEFAULT_JOIN_SEPARATOR);
            }
        }
        if (!StringUtil.isNullOrEmpty(sb.toString())) {
            this.goodsPresenter.getGoodsListByIds(sb.toString(), new BasePresenter.ICallback<List<GoodsWithBLOBs>>() {
                public void onResult(List<GoodsWithBLOBs> result) {
                    Map<Integer, Goods> map = new HashMap<>();
                    for (Goods goods : result) {
                        map.put(Integer.valueOf(goods.getId()), goods);
                    }
                    for (ShopCardItem item : ShopCartActivity.this.shopCardItems) {
                        if (map.containsKey(Integer.valueOf(item.getGoodId()))) {
                            item.setGoods(map.get(Integer.valueOf(item.getGoodId())));
                        }
                    }
                    ShopCartActivity.this.adapter.addDataList(ShopCartActivity.this.shopCardItems);
                }
            });
        } else {
            setupViewsShow(false);
        }
    }

    private void initListener() {
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ShopCartActivity.this.startUpProductDetail((ShopCardItem) ShopCartActivity.this.adapter.getItem(position));
            }
        });
        this.id_cb_select_all.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v instanceof CheckBox) {
                    ShopCartActivity.this.adapter.setupAllChecked(((CheckBox) v).isChecked());
                }
            }
        });
        this.adapter.setOnGoodsCheckedChangeListener(new ShopCartListAdapter.OnGoodsCheckedChangeListener() {
            public void onGoodsCheckedChange(int totalCount, double totalPrice) {
                int unused = ShopCartActivity.this.totalCountBiz = totalCount;
                ShopCartActivity.this.totalGoodPrice = totalPrice;
                ShopCartActivity.this.id_tv_totalPrice.setText(String.format(ShopCartActivity.this.getString(R.string.total), new Object[]{String.valueOf(totalPrice)}));
                ShopCartActivity.this.id_tv_totalCount_jiesuan.setText(String.format(ShopCartActivity.this.getString(R.string.jiesuan), new Object[]{String.valueOf(totalCount)}));
            }
        });
        this.adapter.setOnCheckHasGoodsListener(new ShopCartListAdapter.OnCheckHasGoodsListener() {
            public void onCheckHasGoods(boolean isHasGoods) {
                ShopCartActivity.this.setupViewsShow(isHasGoods);
            }
        });
        this.adapter.setOnCheckProductListener(new ShopCartListAdapter.OnSelectedProductListener() {
            public void selectEdPProductListener(List<ShopCardItem> goodsList) {
                if (goodsList != null) {
                    List unused = ShopCartActivity.this.listSelectedProductList = goodsList;
                }
            }
        });
        this.adapter.setOnAllCheckedBoxNeedChangeListener(new ShopCartListAdapter.OnAllCheckedBoxNeedChangeListener() {
            public void onCheckedBoxNeedChange(boolean allParentIsChecked) {
                if (allParentIsChecked) {
                    ShopCartActivity.this.id_cb_select_all.setChecked(true);
                } else {
                    ShopCartActivity.this.id_cb_select_all.setChecked(false);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void startUpProductDetail(ShopCardItem item) {
        ProductsActivity.startAction(this, item.getGoodId());
    }

    /* access modifiers changed from: private */
    public void setupViewsShow(boolean isHasGoods) {
        if (isHasGoods) {
            this.rlEmpty.setVisibility(8);
            this.id_rl_foot.setVisibility(0);
            this.mtvEdit.setVisibility(0);
            return;
        }
        this.rlEmpty.setVisibility(0);
        this.id_rl_foot.setVisibility(8);
        this.mtvEdit.setVisibility(8);
    }

    @OnClick({2131231050, 2131231178, 2131231554})
    public void onClick(View view) {
        if (view.getId() == R.id.id_tv_add_all) {
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                if ("编辑".equals(tv.getText())) {
                    this.adapter.setupEditingAll(true);
                    tv.setText("完成");
                    changeFootShowDeleteView(true);
                    return;
                }
                this.adapter.setupEditingAll(false);
                ShopCartListAdapter shopCartListAdapter = this.adapter;
                tv.setText("编辑");
                changeFootShowDeleteView(false);
            }
        } else if (view.getId() == R.id.tv_totalCount_jiesuan) {
            jieSuan();
        } else {
            finish();
        }
    }

    private void jieSuan() {
        if (this.totalCountBiz <= 0) {
            showMessage((int) R.string.MALL_PRODUCT_SELECT);
        } else if (!HealthApp.x.isLogin()) {
            toLoginPage();
        } else if (this.listSelectedProductList != null) {
            OrderActivity.startAction(this, this.listSelectedProductList, this.totalGoodPrice);
        }
    }

    public void changeFootShowDeleteView(boolean showDeleteView) {
        if (showDeleteView) {
            this.mtvEdit.setText("完成");
        } else {
            this.mtvEdit.setText("编辑");
        }
    }

    /* access modifiers changed from: protected */
    public void onPostResume() {
        super.onPostResume();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        finish();
        return true;
    }

    public static void startAction(Activity activity, int id) {
        Intent intent = new Intent(activity, ShopCartActivity.class);
        intent.putExtra("currentSelectGoodId", id);
        activity.startActivity(intent);
    }

    public void showErrorLayout(int errorType) {
    }
}
