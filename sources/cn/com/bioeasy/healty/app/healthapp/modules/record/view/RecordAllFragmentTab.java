package cn.com.bioeasy.healty.app.healthapp.modules.record.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import butterknife.Bind;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.utils.DateUtil;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import cn.com.bioeasy.healty.app.healthapp.constant.IntentActions;
import cn.com.bioeasy.healty.app.healthapp.injection.component.FragmentComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.SampleResultData;
import cn.com.bioeasy.healty.app.healthapp.modules.record.bean.ProductTypeItemData;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.SampleData;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestStripCategory;
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
import lecho.lib.hellocharts.view.PieChartView;

public class RecordAllFragmentTab extends BIBaseFragment implements RecordView {
    public static final int RECORD_ALL = 2;
    public static final int RECORD_MY = 1;
    /* access modifiers changed from: private */
    public CommonAdapter adapter;
    @Bind({2131230994})
    PieChartView chart;
    private PieChartData data;
    private boolean invalid = false;
    /* access modifiers changed from: private */
    public OnUpdateRecordDataListener listener;
    @Bind({2131231247})
    RelativeLayout mrlPic;
    /* access modifiers changed from: private */
    public int pagerNumber = 0;
    @Bind({2131231030})
    PtrClassicFrameLayout ptrClassicFrameLayout;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (IntentActions.UPDATE_RECORD_LIST.equals(intent.getAction())) {
                int unused = RecordAllFragmentTab.this.recordDays = intent.getIntExtra(IntentActions.UPDATE_RECORD_DAYS, 7);
                RecordAllFragmentTab.this.refreshData();
            }
        }
    };
    /* access modifiers changed from: private */
    public int recordDays = 7;
    @Inject
    RecordPresenter recordPresenter;
    private int recordType;
    @Bind({2131231031})
    RecyclerView recyclerView;
    @Bind({2131230998})
    TableLayout tableLayout;
    @Bind({2131231248})
    RecyclerView testCategoryView;

    interface OnUpdateRecordDataListener {
        void update(int i, int i2);
    }

    static /* synthetic */ int access$510(RecordAllFragmentTab x0) {
        int i = x0.pagerNumber;
        x0.pagerNumber = i - 1;
        return i;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public BasePresenter getPresenter() {
        return this.recordPresenter;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.fragment_comm_all_record;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents(View rootView) {
        this.invalid = true;
        this.testCategoryView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        CommonAdapter catAdapter = new CommonAdapter<TestStripCategory>(getActivity(), HealthApp.x.getDataManager().getCategoryList(), R.layout.chart_pie_legend) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder holder, TestStripCategory category, int position) {
                holder.setText((int) R.id.tv_legend_text, category.getName());
            }
        };
        this.mrlPic.setVisibility(0);
        this.testCategoryView.setAdapter(catAdapter);
        this.recordPresenter.getProductTypeItemDataList(this.recordDays);
        this.tableLayout.setVisibility(0);
        this.ptrClassicFrameLayout.setVisibility(0);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(1);
        this.recyclerView.setLayoutManager(manager);
        this.adapter = new CommonAdapter<SampleResultData>(getActivity(), new ArrayList(), R.layout.record_comm_all_items) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder holder, SampleResultData sampleData, int position) {
                String resultText;
                holder.setText((int) R.id.tv_date, DateUtil.getDate("yyyy-MM-dd", sampleData.getSampleTime().longValue()));
                holder.setText((int) R.id.tv_upload_status, sampleData.getOperatorSn());
                holder.setText((int) R.id.tv_sample_name, sampleData.getProductName());
                if (sampleData.getResult().intValue() == 1) {
                    resultText = "<font color=\"#3bde86\">" + RecordAllFragmentTab.this.getString(R.string.negative_one) + "</font>";
                } else {
                    resultText = "<font color=\"#ff6260\">" + RecordAllFragmentTab.this.getString(R.string.positive_one) + "</font>";
                }
                holder.setText((int) R.id.tv_test_result, Html.fromHtml(resultText));
                if (position % 2 == 0) {
                    holder.itemView.setBackgroundColor(RecordAllFragmentTab.this.getActivity().getResources().getColor(R.color.white));
                } else {
                    holder.itemView.setBackgroundColor(RecordAllFragmentTab.this.getActivity().getResources().getColor(R.color.white_gray));
                }
            }
        };
        this.recyclerView.setAdapter(new RecyclerAdapterWithHF(this.adapter));
        initEvent();
        refreshData();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IntentActions.UPDATE_RECORD_LIST);
        getActivity().registerReceiver(this.receiver, intentFilter);
    }

    private void initEvent() {
        this.ptrClassicFrameLayout.setFooterView(new BioeasyLoadMoreViewFooter());
        this.ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            public void onRefreshBegin(PtrFrameLayout frame) {
                RecordAllFragmentTab.this.refreshData();
            }
        });
        this.ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void loadMore() {
                RecordAllFragmentTab.this.loadMoreData();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    public void setRecordType(int recordType2, OnUpdateRecordDataListener listener2) {
        this.recordType = recordType2;
        this.listener = listener2;
    }

    public void updateSampleData(PageResult<SampleData> pageResult, boolean refresh) {
    }

    public void updateSampleStatic(final int total, final int ok) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if (RecordAllFragmentTab.this.listener != null) {
                    RecordAllFragmentTab.this.listener.update(total, ok);
                }
            }
        });
    }

    public void updateNearbyRecord(List<ProductTypeItemData> list) {
        List<SliceValue> values = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ProductTypeItemData itemData = list.get(i);
            TestStripCategory category = HealthApp.x.getDataManager().getTestStripCategory(itemData.getProductType());
            String catName = category != null ? category.getName() : "";
            SliceValue sliceValue = new SliceValue((float) itemData.getInvalidCount());
            sliceValue.setLabel(catName + ":" + ((int) sliceValue.getValue()) + getString(R.string.batch));
            values.add(sliceValue);
        }
        this.data = new PieChartData(values);
        this.data.setHasLabels(true);
        this.data.setHasLabelsOnlyForSelected(false);
        this.data.setHasLabelsOutside(false);
        this.data.setHasCenterCircle(false);
        this.chart.setPieChartData(this.data);
        this.chart.setCircleFillRatio(1.0f);
    }

    public void onResume() {
        super.onResume();
    }

    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(this.receiver);
    }

    /* access modifiers changed from: private */
    public void loadMoreData() {
        this.pagerNumber++;
        this.recordPresenter.getServerSampleResultData(this.pagerNumber, 2, new BasePresenter.ICallback<PageResult<SampleResultData>>() {
            public void onResult(PageResult<SampleResultData> result) {
                if (result == null || result.getRows().size() <= 0) {
                    RecordAllFragmentTab.access$510(RecordAllFragmentTab.this);
                    RecordAllFragmentTab.this.ptrClassicFrameLayout.setLoadMoreEnable(false);
                } else {
                    RecordAllFragmentTab.this.adapter.addItemList(result.getRows());
                    RecordAllFragmentTab.this.ptrClassicFrameLayout.setLoadMoreEnable(true);
                }
                if (RecordAllFragmentTab.this.pagerNumber * 10 < result.getTotal()) {
                    RecordAllFragmentTab.this.ptrClassicFrameLayout.loadMoreComplete(true);
                } else {
                    RecordAllFragmentTab.this.ptrClassicFrameLayout.loadMoreComplete(false);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void refreshData() {
        this.pagerNumber = 1;
        this.recordPresenter.getProductTypeItemDataList(this.recordDays);
        this.recordPresenter.getServerSampleResultData(this.pagerNumber, 2, new BasePresenter.ICallback<PageResult<SampleResultData>>() {
            public void onResult(PageResult<SampleResultData> result) {
                RecordAllFragmentTab.this.adapter.clearData();
                RecordAllFragmentTab.this.ptrClassicFrameLayout.refreshComplete();
                if (result == null || result.getRows().size() <= 0) {
                    RecordAllFragmentTab.this.ptrClassicFrameLayout.setLoadMoreEnable(false);
                } else {
                    RecordAllFragmentTab.this.adapter.addItemList(result.getRows());
                    RecordAllFragmentTab.this.ptrClassicFrameLayout.setLoadMoreEnable(true);
                }
                RecordAllFragmentTab.this.ptrClassicFrameLayout.refreshComplete();
            }
        });
    }

    public void exportSampleDataList(List<SampleData> list) {
    }
}
