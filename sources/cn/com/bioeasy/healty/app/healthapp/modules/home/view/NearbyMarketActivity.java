package cn.com.bioeasy.healty.app.healthapp.modules.home.view;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.MultiItemTypeAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.constant.IntentActions;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.BusinessOperator;
import cn.com.bioeasy.healty.app.healthapp.modules.record.view.MarketSearchActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.UploadDataCallback;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.SamplePresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.SampleView;
import cn.com.bioeasy.healty.app.healthapp.widgets.BioeasyLoadMoreViewFooter;
import com.baidu.location.Address;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import java.util.ArrayList;
import javax.inject.Inject;

public class NearbyMarketActivity extends BIBaseActivity implements SampleView {
    /* access modifiers changed from: private */
    public CommonAdapter commonAdapter;
    /* access modifiers changed from: private */
    public int pageNumber = 0;
    @Bind({2131230991})
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @Bind({2131231010})
    RecyclerView recyclerView;
    @Inject
    SamplePresenter samplePresenter;
    @Bind({2131231181})
    FrameLayout searchView;

    static /* synthetic */ int access$110(NearbyMarketActivity x0) {
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
        return R.layout.activity_nearby_market;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.commonAdapter = new CommonAdapter<BusinessOperator>(this, new ArrayList(), R.layout.market_item) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder holder, BusinessOperator item, int position) {
                int totalCount;
                int ngCount;
                holder.setText((int) R.id.tv_market_name, item.getName());
                Address address = HealthApp.x.getDataManager().getAddress();
                String addressStr = (address == null || address.country == null) ? item.getBusinessAddress() : address.country + address.city + address.district;
                if (StringUtil.isNullOrEmpty(addressStr)) {
                    holder.setVisible(R.id.tv_market_address, false);
                } else {
                    holder.setText((int) R.id.tv_market_address, addressStr);
                }
                if (item.getInsTotalCount() != null) {
                    totalCount = item.getInsTotalCount().intValue();
                } else {
                    totalCount = 0;
                }
                if (item.getInsNgCount() != null) {
                    ngCount = item.getInsNgCount().intValue();
                } else {
                    ngCount = 0;
                }
                holder.setText((int) R.id.tv_market_sample, StringUtil.replaceString(NearbyMarketActivity.this.getString(R.string.history_testing_batch), totalCount + "", ngCount + ""));
            }
        };
        this.commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<BusinessOperator>() {
            public void onItemClick(View view, RecyclerView.ViewHolder holder, BusinessOperator o, int position) {
                Intent intent = new Intent(NearbyMarketActivity.this, MarketDetailActivity.class);
                intent.putExtra(IntentActions.MARKET_INFO, o);
                NearbyMarketActivity.this.startActivity(intent);
            }

            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, BusinessOperator o, int position) {
                return false;
            }
        });
        this.recyclerView.setAdapter(new RecyclerAdapterWithHF(this.commonAdapter));
        initEvent();
        checkGPSSetting();
    }

    public void checkGPSSetting() {
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0 && ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 && ActivityCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE") == 0) {
            loadMoreData();
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_PHONE_STATE"}, 99);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 99:
                if (grantResults[0] == 0) {
                    loadMoreData();
                    return;
                } else {
                    showMessage((int) R.string.open_manually);
                    return;
                }
            default:
                return;
        }
    }

    private void initEvent() {
        this.ptrClassicFrameLayout.setFooterView(new BioeasyLoadMoreViewFooter());
        this.ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            public void onRefreshBegin(PtrFrameLayout frame) {
                NearbyMarketActivity.this.refreshData();
            }
        });
        this.ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void loadMore() {
                NearbyMarketActivity.this.loadMoreData();
            }
        });
    }

    public void refreshData() {
        this.pageNumber = 1;
        this.commonAdapter.clearData();
        this.samplePresenter.searchMarketList((String) null, this.pageNumber, new BasePresenter.ICallback<PageResult<BusinessOperator>>() {
            public void onResult(PageResult<BusinessOperator> pageResult) {
                NearbyMarketActivity.this.ptrClassicFrameLayout.refreshComplete();
                if (pageResult == null || pageResult.getRows().size() <= 0) {
                    NearbyMarketActivity.this.ptrClassicFrameLayout.setLoadMoreEnable(false);
                } else {
                    NearbyMarketActivity.this.commonAdapter.addItemList(pageResult.getRows());
                    NearbyMarketActivity.this.ptrClassicFrameLayout.setLoadMoreEnable(true);
                }
                NearbyMarketActivity.this.ptrClassicFrameLayout.refreshComplete();
            }
        });
    }

    public void loadMoreData() {
        this.pageNumber++;
        this.samplePresenter.searchMarketList((String) null, this.pageNumber, new BasePresenter.ICallback<PageResult<BusinessOperator>>() {
            public void onResult(PageResult<BusinessOperator> pageResult) {
                if (pageResult == null || pageResult.getRows().size() <= 0) {
                    NearbyMarketActivity.access$110(NearbyMarketActivity.this);
                    NearbyMarketActivity.this.ptrClassicFrameLayout.setLoadMoreEnable(false);
                } else {
                    NearbyMarketActivity.this.commonAdapter.addItemList(pageResult.getRows());
                    NearbyMarketActivity.this.ptrClassicFrameLayout.setLoadMoreEnable(true);
                }
                if (NearbyMarketActivity.this.pageNumber * 10 <= pageResult.getTotal()) {
                    NearbyMarketActivity.this.ptrClassicFrameLayout.loadMoreComplete(true);
                } else {
                    NearbyMarketActivity.this.ptrClassicFrameLayout.loadMoreComplete(false);
                }
            }
        });
    }

    @OnClick({2131231050, 2131231181})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                return;
            case R.id.fl_search_layout:
                switchPage(MarketSearchActivity.class);
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
