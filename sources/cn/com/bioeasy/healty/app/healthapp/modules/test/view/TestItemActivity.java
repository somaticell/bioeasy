package cn.com.bioeasy.healty.app.healthapp.modules.test.view;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.widgets.abslistview.CommonAdapter;
import cn.com.bioeasy.app.widgets.abslistview.ViewHolder;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.ble.BLERepository;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestStrip;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestStripCategory;
import java.util.List;
import javax.inject.Inject;

public class TestItemActivity extends BIBaseActivity implements ItemView {
    public static String TEST_STRIP_ID = "TEST_STRIP_ID";
    public static String TEST_STRIP_NAME = "TEST_STRIP_NAME";
    /* access modifiers changed from: private */
    public CommonAdapter adapter;
    @Inject
    BLERepository bleRepository;
    private int categoryId;
    @Bind({2131231087})
    ListView mListView;
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
        return R.layout.activity_test_quota;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.tvTitle.setText(getIntent().getStringExtra(TestItemCategoryActivity.ITEM_CATEGORY_NAME));
        this.categoryId = getIntent().getIntExtra(TestItemCategoryActivity.ITEM_CATEGORY_ID, 0);
        this.testItemPresenter.getItemList(this.categoryId, 1);
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                TestStrip testStrip = (TestStrip) TestItemActivity.this.adapter.getItem(position);
                Intent intent = new Intent(TestItemActivity.this.getApplicationContext(), TestResultActivity.class);
                intent.putExtra(TestItemActivity.TEST_STRIP_ID, testStrip.getId());
                intent.putExtra(TestItemActivity.TEST_STRIP_NAME, testStrip.getName());
                TestItemActivity.this.startActivity(intent);
            }
        });
    }

    @OnClick({2131231050})
    public void OnClick(View view) {
        finish();
    }

    public void updateItemCategoryList(List<TestStripCategory> list) {
    }

    public void updateItemList(List<TestStrip> list) {
        this.adapter = new CommonAdapter<TestStrip>(this.context, R.layout.listitem_check_type, list) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder viewHolder, TestStrip item, int position) {
                viewHolder.setText(R.id.txt_check_type, item.getName());
            }
        };
        this.mListView.setAdapter(this.adapter);
    }
}
