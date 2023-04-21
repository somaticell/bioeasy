package cn.com.bioeasy.healty.app.healthapp.modules.record.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TableLayout;
import butterknife.Bind;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.utils.DateUtil;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import cn.com.bioeasy.healty.app.healthapp.constant.IntentActions;
import cn.com.bioeasy.healty.app.healthapp.injection.component.FragmentComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.record.bean.ProductTypeItemData;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.SampleData;
import cn.com.bioeasy.healty.app.healthapp.widgets.BioeasyLoadMoreViewFooter;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class RecordFragmentTab extends BIBaseFragment implements RecordView {
    public static final int RECORD_ALL = 2;
    public static final int RECORD_MY = 1;
    private CommonAdapter adapter;
    private boolean invalid = false;
    /* access modifiers changed from: private */
    public OnUpdateRecordDataListener listener;
    private int pagerNumber = 0;
    @Bind({2131231030})
    PtrClassicFrameLayout ptrClassicFrameLayout;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (IntentActions.UPDATE_RECORD_LIST.equals(intent.getAction())) {
                int unused = RecordFragmentTab.this.recordDays = intent.getIntExtra(IntentActions.UPDATE_RECORD_DAYS, 7);
                RecordFragmentTab.this.refreshData();
            }
        }
    };
    /* access modifiers changed from: private */
    public int recordDays = 7;
    @Inject
    RecordPresenter recordPresenter;
    @Bind({2131231031})
    RecyclerView recyclerView;
    @Bind({2131230998})
    TableLayout tableLayout;

    interface OnUpdateRecordDataListener {
        void update(int i, int i2);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public BasePresenter getPresenter() {
        return this.recordPresenter;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.fragment_comm_record;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents(View rootView) {
        final String[] resultStr = {"<font color=\"#fcb813\">" + getString(R.string.weak_positive) + "</font>", "<font color=\"#3bde86\">" + getString(R.string.negative_one) + "</font>", "<font color=\"#ff6260\">" + getString(R.string.positive_one) + "</font>"};
        this.tableLayout.setVisibility(0);
        this.ptrClassicFrameLayout.setVisibility(0);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(1);
        this.recyclerView.setLayoutManager(manager);
        this.adapter = new CommonAdapter<SampleData>(getActivity(), new ArrayList(), R.layout.record_comm_items) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder holder, SampleData sampleData, int position) {
                String statusStr;
                String opStr;
                holder.setText((int) R.id.tv_date, DateUtil.getDate("yyyy-MM-dd", DateUtil.getDateFormStr(sampleData.getTime()).getTime()));
                if (sampleData.getStatus() == 1) {
                    statusStr = RecordFragmentTab.this.getString(R.string.Uploaded);
                } else {
                    statusStr = "<font color=\"#ff6260\">" + RecordFragmentTab.this.getString(R.string.not_uploaded) + "</font>";
                }
                holder.setText((int) R.id.tv_upload_status, Html.fromHtml(statusStr));
                holder.setText((int) R.id.tv_sample_name, sampleData.getSampleName());
                holder.setText((int) R.id.tv_strip_name, sampleData.getStripName());
                holder.setText((int) R.id.tv_test_result, Html.fromHtml(resultStr[sampleData.getResult()]));
                ItemClickListener listener = new ItemClickListener();
                listener.setSampleData(sampleData);
                holder.setOnClickListener(R.id.tv_look, listener);
                if (position % 2 == 0) {
                    holder.itemView.setBackgroundColor(RecordFragmentTab.this.getActivity().getResources().getColor(R.color.white));
                } else {
                    holder.itemView.setBackgroundColor(RecordFragmentTab.this.getActivity().getResources().getColor(R.color.white_gray));
                }
                if (sampleData.getStatus() == 1) {
                    opStr = "<font color=\"#60cdff\">" + RecordFragmentTab.this.getString(R.string.record_look) + "</font>";
                } else {
                    opStr = "<font color=\"#60cdff\">" + RecordFragmentTab.this.getString(R.string.upload_title) + "</font>";
                }
                holder.setText((int) R.id.tv_look, Html.fromHtml(opStr));
            }
        };
        this.recyclerView.setAdapter(new RecyclerAdapterWithHF(this.adapter));
        initEvent();
        refreshData();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IntentActions.UPDATE_RECORD_LIST);
        getActivity().registerReceiver(this.receiver, intentFilter);
    }

    private class ItemClickListener implements View.OnClickListener {
        private SampleData sampleData;

        private ItemClickListener() {
        }

        public void setSampleData(SampleData sampleData2) {
            this.sampleData = sampleData2;
        }

        public void onClick(View v) {
            Intent intent = new Intent(RecordFragmentTab.this.getActivity(), TestDetailActivity.class);
            intent.putExtra(IntentActions.SAMPLE_ID, this.sampleData.getId());
            RecordFragmentTab.this.getActivity().startActivity(intent);
        }
    }

    private void initEvent() {
        this.ptrClassicFrameLayout.setFooterView(new BioeasyLoadMoreViewFooter());
        this.ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            public void onRefreshBegin(PtrFrameLayout frame) {
                RecordFragmentTab.this.refreshData();
            }
        });
        this.ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void loadMore() {
                RecordFragmentTab.this.loadMoreData();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    public void setRecordType(int recordType, OnUpdateRecordDataListener listener2) {
        this.listener = listener2;
    }

    public void updateSampleData(PageResult<SampleData> listPageResult, boolean refresh) {
        if (refresh) {
            this.adapter.clearData();
            this.ptrClassicFrameLayout.refreshComplete();
            if (listPageResult == null || listPageResult.getRows().size() <= 0) {
                this.ptrClassicFrameLayout.setLoadMoreEnable(false);
            } else {
                this.adapter.addItemList(listPageResult.getRows());
                this.ptrClassicFrameLayout.setLoadMoreEnable(true);
            }
            this.ptrClassicFrameLayout.refreshComplete();
            return;
        }
        if (listPageResult == null || listPageResult.getRows().size() <= 0) {
            this.pagerNumber--;
            this.ptrClassicFrameLayout.setLoadMoreEnable(false);
        } else {
            this.adapter.addItemList(listPageResult.getRows());
            this.ptrClassicFrameLayout.setLoadMoreEnable(true);
        }
        if (this.pagerNumber * 10 < listPageResult.getTotal()) {
            this.ptrClassicFrameLayout.loadMoreComplete(true);
        } else {
            this.ptrClassicFrameLayout.loadMoreComplete(false);
        }
    }

    public void updateSampleStatic(final int total, final int ok) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if (RecordFragmentTab.this.listener != null) {
                    RecordFragmentTab.this.listener.update(total, ok);
                }
            }
        });
    }

    public void updateNearbyRecord(List<ProductTypeItemData> list) {
    }

    public void onResume() {
        super.onResume();
        refreshData();
    }

    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(this.receiver);
    }

    /* access modifiers changed from: private */
    public void loadMoreData() {
        this.pagerNumber++;
        this.recordPresenter.getSampleData(10, this.pagerNumber, this.recordDays, this.invalid, false);
    }

    /* access modifiers changed from: private */
    public void refreshData() {
        this.pagerNumber = 1;
        this.recordPresenter.getSampleData(10, this.pagerNumber, this.recordDays, this.invalid, true);
    }

    public void exportSampleDataList(List<SampleData> list) {
    }
}
