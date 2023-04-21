package cn.com.bioeasy.healty.app.healthapp.modules.test.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.MultiItemTypeAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.ble.BLERepository;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestStrip;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestStripCategory;
import java.util.List;
import javax.inject.Inject;

public class TestItemCategoryActivity extends BIBaseActivity implements ItemView {
    public static final String ITEM_CATEGORY_ID = "ITEM_CATEGORY_ID";
    public static final String ITEM_CATEGORY_NAME = "ITEM_CATEGORY_NAME";
    @Inject
    BLERepository bleRepository;
    public List<TestStripCategory> categoryList;
    @Bind({2131231038})
    TextView currentDeviceName;
    @Bind({2131231135})
    RecyclerView recyclerView;
    @Inject
    TestItemPresenter testItemPresenter;
    @Bind({2131231008})
    TextView tvTitle;

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return this.testItemPresenter;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_test_function;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.tvTitle.setText(R.string.tv_blue_select_function);
        this.currentDeviceName.setText(String.format(getString(R.string.tv_blue_connect_name), new Object[]{this.bleRepository.getDeviceName()}));
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        this.testItemPresenter.getItemCategoryList();
    }

    @OnClick({2131231050, 2131231039})
    public void onClicks(View v) {
        switch (v.getId()) {
            case R.id.bt_change:
                finish();
                return;
            case R.id.iv_back:
                finish();
                return;
            default:
                return;
        }
    }

    public void updateItemCategoryList(List<TestStripCategory> list) {
        this.categoryList = list;
        CommonAdapter<TestStripCategory> adapter = new CommonAdapter<TestStripCategory>(this.context, list, R.layout.strip_category_item) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder holder, TestStripCategory category, int position) {
                holder.setText((int) R.id.category_name, category.getName());
                RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.rl_cat_panel);
                if (position % 2 == 1) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
                    params.addRule(9);
                    params.setMargins(30, 0, 0, 0);
                } else {
                    RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
                    params2.addRule(11);
                    params2.setMargins(0, 0, 30, 0);
                }
                holder.setImageResource(R.id.iv_category, category.getIcon());
                GradientDrawable myGrad = (GradientDrawable) relativeLayout.getBackground();
                myGrad.setStroke(1, Color.parseColor(category.getColor()));
                myGrad.setColor(Color.parseColor(category.getColor()));
            }
        };
        this.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<TestStripCategory>() {
            public void onItemClick(View view, RecyclerView.ViewHolder holder, TestStripCategory category, int position) {
                TestItemCategoryActivity.this.toGoSelectTestQuotaActivity(category);
            }

            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, TestStripCategory o, int position) {
                return false;
            }
        });
    }

    /* access modifiers changed from: private */
    public void toGoSelectTestQuotaActivity(TestStripCategory category) {
        Intent intent = new Intent(this, TestItemActivity.class);
        intent.putExtra(ITEM_CATEGORY_ID, category.getId());
        intent.putExtra(ITEM_CATEGORY_NAME, category.getName());
        startActivity(intent);
    }

    public void updateItemList(List<TestStrip> list) {
    }
}
