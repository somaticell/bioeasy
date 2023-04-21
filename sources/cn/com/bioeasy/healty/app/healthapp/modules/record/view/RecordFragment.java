package cn.com.bioeasy.healty.app.healthapp.modules.record.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.excel.ExcelUtil;
import cn.com.bioeasy.app.file.OpenFileUtil;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.utils.DateUtil;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import cn.com.bioeasy.healty.app.healthapp.constant.IntentActions;
import cn.com.bioeasy.healty.app.healthapp.injection.component.FragmentComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.record.bean.ProductTypeItemData;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.SampleData;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.SampleItemData;
import cn.com.bioeasy.healty.app.healthapp.widgets.BioeasyLoadMoreViewFooter;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.litepal.crud.DataSupport;

public class RecordFragment extends BIBaseFragment implements RecordView {
    public static final int RECORD_ALL = 2;
    public static final int RECORD_MY = 1;
    private CommonAdapter commonAdapter;
    private String exportFilePath = null;
    @Bind({2131231180})
    TextView exportRecord;
    private boolean invalid = false;
    @Bind({2131231050})
    ImageView mIvBack;
    @Bind({2131231008})
    TextView mTitle;
    private int pagerNumber = 0;
    @Bind({2131231030})
    PtrClassicFrameLayout ptrClassicFrameLayout;
    /* access modifiers changed from: private */
    public int recordDays = 0;
    @Inject
    RecordPresenter recordPresenter;
    @Bind({2131231031})
    RecyclerView recyclerView;
    @Bind({2131230998})
    TableLayout tableLayout;
    @Bind({2131230997})
    TextView testNgCount;
    @Bind({2131230996})
    TextView testOkCount;
    @Bind({2131231301})
    Spinner testRecordDays;
    @Bind({2131231302})
    TextView testTotalCount;

    /* access modifiers changed from: protected */
    public void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    public BasePresenter getPresenter() {
        return this.recordPresenter;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.fragment_record;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents(View rootView) {
        this.mIvBack.setVisibility(8);
        this.mTitle.setText(getString(R.string.detection_record));
        final String[] resultStr = {"<font color=\"#fcb813\">" + getString(R.string.weak_positive) + "</font>", "<font color=\"#3bde86\">" + getString(R.string.negative_one) + "</font>", "<font color=\"#ff6260\">" + getString(R.string.positive_one) + "</font>"};
        List<String> spinDatas = new ArrayList<>();
        spinDatas.add(getString(R.string.today));
        spinDatas.add(getString(R.string.nearly_week));
        spinDatas.add(getString(R.string.nearly_month));
        spinDatas.add(getString(R.string.nearly_three_month));
        this.testRecordDays.setAdapter(new ArrayAdapter<>(getContext(), R.layout.my_spinner, spinDatas));
        this.testRecordDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int unused = RecordFragment.this.recordDays = 0;
                if (i == 1) {
                    int unused2 = RecordFragment.this.recordDays = 7;
                } else if (i == 2) {
                    int unused3 = RecordFragment.this.recordDays = 30;
                } else if (i == 3) {
                    int unused4 = RecordFragment.this.recordDays = 90;
                }
                RecordFragment.this.refreshData();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.exportRecord.setVisibility(0);
        this.tableLayout.setVisibility(0);
        this.ptrClassicFrameLayout.setVisibility(0);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(1);
        this.recyclerView.setLayoutManager(manager);
        this.commonAdapter = new CommonAdapter<SampleData>(getActivity(), new ArrayList(), R.layout.record_comm_items) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder holder, SampleData sampleData, int position) {
                String statusStr;
                String opStr;
                holder.setText((int) R.id.tv_date, DateUtil.getDate("yyyy-MM-dd HH:mm:ss", DateUtil.getDateFormStr(sampleData.getTime()).getTime()));
                if (sampleData.getStatus() == 1) {
                    statusStr = RecordFragment.this.getString(R.string.Uploaded);
                } else {
                    statusStr = "<font color=\"#ff6260\">" + RecordFragment.this.getString(R.string.not_uploaded) + "</font>";
                }
                holder.setText((int) R.id.tv_upload_status, Html.fromHtml(statusStr));
                holder.setText((int) R.id.tv_sample_name, sampleData.getSampleName());
                holder.setText((int) R.id.tv_strip_name, sampleData.getStripName());
                holder.setText((int) R.id.tv_test_result, Html.fromHtml(resultStr[sampleData.getResult()]));
                ItemClickListener listener = new ItemClickListener();
                listener.setSampleData(sampleData);
                holder.setOnClickListener(R.id.tv_look, listener);
                if (position % 2 == 0) {
                    holder.itemView.setBackgroundColor(RecordFragment.this.getActivity().getResources().getColor(R.color.white));
                } else {
                    holder.itemView.setBackgroundColor(RecordFragment.this.getActivity().getResources().getColor(R.color.white_gray));
                }
                if (sampleData.getStatus() == 1) {
                    opStr = "<font color=\"#60cdff\">" + RecordFragment.this.getString(R.string.record_look) + "</font>";
                } else {
                    opStr = "<font color=\"#60cdff\">" + RecordFragment.this.getString(R.string.record_look) + "</font>";
                }
                holder.setText((int) R.id.tv_look, Html.fromHtml(opStr));
            }
        };
        this.recyclerView.setAdapter(new RecyclerAdapterWithHF(this.commonAdapter));
        initEvent();
        refreshData();
    }

    @OnClick({2131231180})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_tv_export_record:
                this.recordPresenter.exportSampleData(this.recordDays, this.invalid);
                return;
            default:
                return;
        }
    }

    private void initEvent() {
        this.ptrClassicFrameLayout.setFooterView(new BioeasyLoadMoreViewFooter());
        this.ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            public void onRefreshBegin(PtrFrameLayout frame) {
                RecordFragment.this.refreshData();
            }
        });
        this.ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void loadMore() {
                RecordFragment.this.loadMoreData();
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void updateSampleStatic(int total, int ok) {
        this.testTotalCount.setText(String.valueOf(total));
        this.testNgCount.setText(String.valueOf(total - ok));
        this.testOkCount.setText(String.valueOf(ok));
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

    private class ItemClickListener implements View.OnClickListener {
        private SampleData sampleData;

        private ItemClickListener() {
        }

        public void setSampleData(SampleData sampleData2) {
            this.sampleData = sampleData2;
        }

        public void onClick(View v) {
            Intent intent = new Intent(RecordFragment.this.getActivity(), TestDetailActivity.class);
            intent.putExtra(IntentActions.SAMPLE_ID, this.sampleData.getId());
            RecordFragment.this.getActivity().startActivity(intent);
        }
    }

    public void updateSampleData(PageResult<SampleData> listPageResult, boolean refresh) {
        if (refresh) {
            this.commonAdapter.clearData();
            this.ptrClassicFrameLayout.refreshComplete();
            if (listPageResult == null || listPageResult.getRows().size() <= 0) {
                this.ptrClassicFrameLayout.setLoadMoreEnable(false);
            } else {
                this.commonAdapter.addItemList(listPageResult.getRows());
                this.ptrClassicFrameLayout.setLoadMoreEnable(true);
            }
            this.ptrClassicFrameLayout.refreshComplete();
            return;
        }
        if (listPageResult == null || listPageResult.getRows().size() <= 0) {
            this.pagerNumber--;
            this.ptrClassicFrameLayout.setLoadMoreEnable(false);
        } else {
            this.commonAdapter.addItemList(listPageResult.getRows());
            this.ptrClassicFrameLayout.setLoadMoreEnable(true);
        }
        if (this.pagerNumber * 10 < listPageResult.getTotal()) {
            this.ptrClassicFrameLayout.loadMoreComplete(true);
        } else {
            this.ptrClassicFrameLayout.loadMoreComplete(false);
        }
    }

    public void updateNearbyRecord(List<ProductTypeItemData> list) {
    }

    public void onResume() {
        super.onResume();
        refreshData();
    }

    public void exportSampleDataList(List<SampleData> list) {
        if (list.size() > 0) {
            String[] resultStr = {getString(R.string.weak_positive), getString(R.string.negative_one), getString(R.string.positive_one)};
            String filePath = HealthApp.x.getDataManager().getExportFilePath() + (getString(R.string.detection_record) + "(" + DateUtil.getDate("yyyy-MM-dd HH:mm:ss", new Date().getTime()) + ")") + ".xls";
            ExcelUtil.initExcel(filePath, new String[]{"NO", "    " + getString(R.string.sample_name) + "    ", "    " + getString(R.string.detection_project) + "    ", getString(R.string.detection_result), "T1", "T2", "T3", "T4", getString(R.string.test_time)});
            List<String[]> sList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                SampleData sData = list.get(i);
                String[] si = new String[9];
                si[0] = String.valueOf(i + 1);
                si[1] = sData.getSampleName();
                si[2] = sData.getStripName();
                si[3] = resultStr[sData.getResult()];
                si[4] = "";
                si[5] = "";
                si[6] = "";
                si[7] = "";
                List<SampleItemData> siList = DataSupport.where("sampledata_id = ? order by id desc", String.valueOf(sData.getId())).find(SampleItemData.class);
                sData.setItemDataList(siList);
                for (int j = 0; j < siList.size(); j++) {
                    si[j + 4] = ((siList.get(j).getItemName() + " ") + resultStr[siList.get(j).getResult()]) + "(" + siList.get(j).getValue() + ")";
                }
                si[8] = DateUtil.getDate("yyyy-MM-dd HH:mm:ss", DateUtil.getDateFormStr(sData.getTime()).getTime());
                sList.add(si);
            }
            ExcelUtil.writeObjListToExcel(sList, filePath, this.context);
            Intent i2 = OpenFileUtil.openFile(filePath);
            if (i2 != null) {
                startActivity(i2);
            }
        }
    }
}
