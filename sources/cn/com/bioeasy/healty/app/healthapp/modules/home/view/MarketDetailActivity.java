package cn.com.bioeasy.healty.app.healthapp.modules.home.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.utils.DateUtil;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.constant.IntentActions;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.BusinessOperator;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.SampleItemResultData;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.UploadDataCallback;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.SamplePresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.SampleView;
import cn.com.bioeasy.healty.app.healthapp.widgets.BioeasyLoadMoreViewFooter;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

public class MarketDetailActivity extends BIBaseActivity implements SampleView {
    /* access modifiers changed from: private */
    public CommonAdapter commonAdapter;
    @Bind({2131231008})
    TextView mTitle;
    private BusinessOperator market = null;
    /* access modifiers changed from: private */
    public int pageNumber = 0;
    @Bind({2131230991})
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @Bind({2131230992})
    RecyclerView recyclerView;
    @Inject
    SamplePresenter samplePresenter;

    static /* synthetic */ int access$110(MarketDetailActivity x0) {
        int i = x0.pageNumber;
        x0.pageNumber = i - 1;
        return i;
    }

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return this.samplePresenter;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_market_detail;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.market = (BusinessOperator) getIntent().getSerializableExtra(IntentActions.MARKET_INFO);
        this.mTitle.setText(this.market.getName());
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.commonAdapter = new CommonAdapter<SampleItemResultData>(this, new ArrayList(), R.layout.market_sample_items) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder holder, SampleItemResultData sampleData, int position) {
                holder.setText((int) R.id.tv_date, DateUtil.getDate("yyyy-MM-dd", sampleData.getSampleTime().longValue()));
                holder.setText((int) R.id.tv_sample_name, sampleData.getProductName());
                holder.setText((int) R.id.tv_test_result, Html.fromHtml(sampleData.getResult().intValue() == 1 ? "<font color=\"#3bde86\">this.getString(R.string.negative_one)</font>" : "<font color=\"#ff6260\">this.getString(R.string.positive_one)</font>"));
                if (position % 2 == 1) {
                    holder.itemView.setBackgroundColor(MarketDetailActivity.this.getResources().getColor(R.color.white));
                } else {
                    holder.itemView.setBackgroundColor(MarketDetailActivity.this.getResources().getColor(R.color.white_gray));
                }
                holder.setText((int) R.id.tv_test_item, sampleData.getItemName());
            }
        };
        RecyclerAdapterWithHF mAdapter = new RecyclerAdapterWithHF(this.commonAdapter);
        mAdapter.addHeader(initHeadView());
        this.recyclerView.setAdapter(mAdapter);
        initEvent();
        loadMoreData();
    }

    private View initHeadView() {
        View header = LayoutInflater.from(this).inflate(R.layout.activity_market_summary_data, (ViewGroup) null);
        TextView testTotal = (TextView) header.findViewById(R.id.tv_test_total);
        TextView testNgCount = (TextView) header.findViewById(R.id.tv_test_ng_count);
        TextView testOkCount = (TextView) header.findViewById(R.id.tv_test_ok_count);
        PieChartView chart = (PieChartView) header.findViewById(R.id.chart);
        ((TextView) header.findViewById(R.id.tv_market_address)).setText(this.market.getBusinessAddress());
        int totalCount = this.market.getInsTotalCount() != null ? this.market.getInsTotalCount().intValue() : 0;
        int ngCount = this.market.getInsNgCount() != null ? this.market.getInsNgCount().intValue() : 0;
        int okCount = totalCount - ngCount;
        testTotal.setText(getString(R.string.test_totalNumber) + String.valueOf(totalCount));
        testNgCount.setText(getString(R.string.positive_batch) + String.valueOf(ngCount));
        testOkCount.setText(getString(R.string.negative_batch) + String.valueOf(okCount));
        List<SliceValue> values = new ArrayList<>();
        SliceValue sliceValue = new SliceValue((float) okCount, ChartUtils.COLOR_GREEN);
        sliceValue.setLabel(getString(R.string.negative) + ":" + ((int) sliceValue.getValue()) + getString(R.string.batch));
        values.add(sliceValue);
        SliceValue sliceValue2 = new SliceValue((float) ngCount, ChartUtils.COLOR_RED);
        sliceValue2.setLabel(getString(R.string.positive) + ":" + ((int) sliceValue2.getValue()) + getString(R.string.batch));
        values.add(sliceValue2);
        PieChartData data = new PieChartData(values);
        data.setHasLabels(true);
        data.setHasLabelsOnlyForSelected(false);
        data.setHasLabelsOutside(false);
        data.setHasCenterCircle(false);
        chart.setPieChartData(data);
        chart.setCircleFillRatio(1.0f);
        return header;
    }

    private void initEvent() {
        this.ptrClassicFrameLayout.setFooterView(new BioeasyLoadMoreViewFooter());
        this.ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            public void onRefreshBegin(PtrFrameLayout frame) {
                MarketDetailActivity.this.refreshData();
            }
        });
        this.ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void loadMore() {
                MarketDetailActivity.this.loadMoreData();
            }
        });
    }

    public void refreshData() {
        this.pageNumber = 1;
        this.commonAdapter.clearData();
        this.samplePresenter.getSampleItemResultData(this.market.getSn(), this.pageNumber, new BasePresenter.ICallback<PageResult<SampleItemResultData>>() {
            public void onResult(PageResult<SampleItemResultData> pageResult) {
                MarketDetailActivity.this.ptrClassicFrameLayout.refreshComplete();
                if (pageResult == null || pageResult.getRows().size() <= 0) {
                    MarketDetailActivity.this.ptrClassicFrameLayout.setLoadMoreEnable(false);
                } else {
                    MarketDetailActivity.this.commonAdapter.addItemList(pageResult.getRows());
                    MarketDetailActivity.this.ptrClassicFrameLayout.setLoadMoreEnable(true);
                }
                MarketDetailActivity.this.ptrClassicFrameLayout.refreshComplete();
            }
        });
    }

    public void loadMoreData() {
        this.pageNumber++;
        this.samplePresenter.getSampleItemResultData(this.market.getSn(), this.pageNumber, new BasePresenter.ICallback<PageResult<SampleItemResultData>>() {
            public void onResult(PageResult<SampleItemResultData> pageResult) {
                if (pageResult == null || pageResult.getRows().size() <= 0) {
                    MarketDetailActivity.access$110(MarketDetailActivity.this);
                    MarketDetailActivity.this.ptrClassicFrameLayout.setLoadMoreEnable(false);
                } else {
                    MarketDetailActivity.this.commonAdapter.addItemList(pageResult.getRows());
                    MarketDetailActivity.this.ptrClassicFrameLayout.setLoadMoreEnable(true);
                }
                if (MarketDetailActivity.this.pageNumber * 10 < pageResult.getTotal()) {
                    MarketDetailActivity.this.ptrClassicFrameLayout.loadMoreComplete(true);
                } else {
                    MarketDetailActivity.this.ptrClassicFrameLayout.loadMoreComplete(false);
                }
            }
        });
    }

    @OnClick({2131231050})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    public void uploadDateResult(UploadDataCallback callback) {
    }
}
