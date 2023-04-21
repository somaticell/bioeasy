package cn.com.bioeasy.healty.app.healthapp.modules.record.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.app.widgets.SearchView;
import cn.com.bioeasy.app.widgets.abslistview.CommonAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.MultiItemTypeAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.constant.IntentActions;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.BusinessOperator;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.MarketSearchItem;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.MarketDetailActivity;
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
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

public class MarketSearchActivity extends BIBaseActivity implements SearchView.SearchViewListener, SampleView {
    /* access modifiers changed from: private */
    public CommonAdapter<String> autoCompleteAdapter;
    /* access modifiers changed from: private */
    public List<String> autoCompleteData = new ArrayList();
    @Bind({2131231081})
    TextView hostSearch;
    /* access modifiers changed from: private */
    public cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter<String> hotResultAdapter;
    /* access modifiers changed from: private */
    public List<String> hotResultData = new ArrayList();
    @Bind({2131231079})
    SearchView mSearchView;
    @Bind({2131231082})
    RecyclerView mlvHotSearch;
    @Bind({2131231080})
    LinearLayout mlvHotSearchPanel;
    @Bind({2131231083})
    RecyclerView mlvResult;
    /* access modifiers changed from: private */
    public int pagerNumber = 1;
    @Bind({2131231030})
    PtrClassicFrameLayout ptrClassicFrameLayout;
    /* access modifiers changed from: private */
    public cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter resultAdapter;
    @Inject
    SamplePresenter samplePresenter;
    private String searchText = null;

    static /* synthetic */ int access$610(MarketSearchActivity x0) {
        int i = x0.pagerNumber;
        x0.pagerNumber = i - 1;
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
        return R.layout.activity_search;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.mlvResult.setLayoutManager(new LinearLayoutManager(this));
        this.resultAdapter = new cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter<BusinessOperator>(this, new ArrayList(), R.layout.market_item) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder holder, BusinessOperator item, int position) {
                int totalCount;
                int ngCount;
                holder.setText((int) R.id.tv_market_name, item.getName());
                Address address = HealthApp.x.getDataManager().getAddress();
                String addressStr = address != null ? address.country + address.city + address.district : item.getBusinessAddress();
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
                holder.setText((int) R.id.tv_market_sample, StringUtil.replaceString(MarketSearchActivity.this.getString(R.string.history_testing_batch), totalCount + "", ngCount + ""));
            }
        };
        this.resultAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<BusinessOperator>() {
            public void onItemClick(View view, RecyclerView.ViewHolder holder, BusinessOperator o, int position) {
                Intent intent = new Intent(MarketSearchActivity.this, MarketDetailActivity.class);
                intent.putExtra(IntentActions.MARKET_INFO, o);
                MarketSearchActivity.this.startActivity(intent);
            }

            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, BusinessOperator o, int position) {
                return false;
            }
        });
        this.mlvResult.setAdapter(new RecyclerAdapterWithHF(this.resultAdapter));
        this.hostSearch.setText(getString(R.string.search_history));
        this.mlvHotSearch.setLayoutManager(new LinearLayoutManager(this));
        this.samplePresenter.getSearchMarketHistory(new BasePresenter.ICallback<List<String>>() {
            public void onResult(List<String> result) {
                MarketSearchActivity.this.hotResultData.clear();
                MarketSearchActivity.this.hotResultData.addAll(result);
                cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter unused = MarketSearchActivity.this.hotResultAdapter = new cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter<String>(MarketSearchActivity.this.context, MarketSearchActivity.this.hotResultData, R.layout.item_search_list) {
                    /* access modifiers changed from: protected */
                    public void convert(ViewHolder viewHolder, String item, int position) {
                        viewHolder.setVisible(R.id.item_search_iv_icon, false);
                        viewHolder.setText((int) R.id.tv_search_title, item);
                    }
                };
            }
        });
        this.hotResultAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<String>() {
            public void onItemClick(View view, RecyclerView.ViewHolder holder, String o, int position) {
                MarketSearchActivity.this.mSearchView.setSearchText(o);
            }

            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, String o, int position) {
                return false;
            }
        });
        this.mlvHotSearch.setAdapter(this.hotResultAdapter);
        this.autoCompleteAdapter = new CommonAdapter<String>(this, R.layout.search_auto_complete_item, this.autoCompleteData) {
            /* access modifiers changed from: protected */
            public void convert(cn.com.bioeasy.app.widgets.abslistview.ViewHolder viewHolder, String item, int position) {
                viewHolder.setText(R.id.tv_item, item);
            }
        };
        this.mSearchView.setSearchViewListener(this);
        this.mSearchView.setAutoCompleteAdapter(this.autoCompleteAdapter);
        initEvent();
    }

    private void initEvent() {
        this.ptrClassicFrameLayout.setFooterView(new BioeasyLoadMoreViewFooter());
        this.ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            public void onRefreshBegin(PtrFrameLayout frame) {
                MarketSearchActivity.this.refreshData();
            }
        });
        this.ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void loadMore() {
                MarketSearchActivity.this.loadMoreData();
            }
        });
    }

    /* access modifiers changed from: private */
    public void loadMoreData() {
        if (!StringUtil.isNullOrEmpty(this.searchText)) {
            this.pagerNumber++;
            this.samplePresenter.searchMarketList(this.searchText, this.pagerNumber, new BasePresenter.ICallback<PageResult<BusinessOperator>>() {
                public void onResult(PageResult<BusinessOperator> pageResult) {
                    if (pageResult == null || pageResult.getRows().size() <= 0) {
                        MarketSearchActivity.access$610(MarketSearchActivity.this);
                        MarketSearchActivity.this.ptrClassicFrameLayout.setLoadMoreEnable(false);
                    } else {
                        MarketSearchActivity.this.resultAdapter.addItemList(pageResult.getRows());
                        MarketSearchActivity.this.ptrClassicFrameLayout.setLoadMoreEnable(true);
                    }
                    if (MarketSearchActivity.this.pagerNumber * 10 < pageResult.getTotal()) {
                        MarketSearchActivity.this.ptrClassicFrameLayout.loadMoreComplete(true);
                    } else {
                        MarketSearchActivity.this.ptrClassicFrameLayout.loadMoreComplete(false);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void refreshData() {
        if (!StringUtil.isNullOrEmpty(this.searchText)) {
            this.pagerNumber = 1;
            this.resultAdapter.clearData();
            this.samplePresenter.searchMarketList(this.searchText, this.pagerNumber, new BasePresenter.ICallback<PageResult<BusinessOperator>>() {
                public void onResult(PageResult<BusinessOperator> pageResult) {
                    MarketSearchActivity.this.ptrClassicFrameLayout.refreshComplete();
                    if (pageResult == null || pageResult.getRows().size() <= 0) {
                        MarketSearchActivity.this.ptrClassicFrameLayout.setLoadMoreEnable(false);
                    } else {
                        MarketSearchActivity.this.resultAdapter.addItemList(pageResult.getRows());
                        MarketSearchActivity.this.ptrClassicFrameLayout.setLoadMoreEnable(true);
                    }
                    MarketSearchActivity.this.ptrClassicFrameLayout.refreshComplete();
                }
            });
        }
    }

    private void getAutoCompleteData(String text) {
        this.autoCompleteData.clear();
        if (!StringUtil.isNullOrEmpty(text)) {
            this.mlvHotSearchPanel.setVisibility(8);
            this.samplePresenter.getMarketSuggestList(text, new BasePresenter.ICallback<List<String>>() {
                public void onResult(List<String> result) {
                    MarketSearchActivity.this.mSearchView.setLvTipsVisible(result.size() > 0);
                    MarketSearchActivity.this.autoCompleteData.addAll(result);
                    MarketSearchActivity.this.autoCompleteAdapter.notifyDataSetChanged();
                    if (result.size() == 0 && MarketSearchActivity.this.resultAdapter.getItemCount() == 0) {
                        MarketSearchActivity.this.mlvHotSearchPanel.setVisibility(0);
                    }
                }
            });
            return;
        }
        if (this.resultAdapter.getItemCount() == 0) {
            this.mlvHotSearchPanel.setVisibility(0);
        }
        this.mSearchView.setLvTipsVisible(false);
        if (this.mlvResult.getVisibility() == 8) {
            this.mlvHotSearchPanel.setVisibility(0);
        } else {
            this.mlvHotSearchPanel.setVisibility(8);
        }
        this.autoCompleteAdapter.notifyDataSetChanged();
    }

    private void getResultData(String text) {
        this.searchText = text;
        if (!StringUtil.isNullOrEmpty(text)) {
            MarketSearchItem searchItem = new MarketSearchItem();
            searchItem.setName(text);
            searchItem.setTime(new Date().getTime());
            searchItem.save();
            if (this.hotResultAdapter.getItemCount() >= 10) {
                this.hotResultAdapter.deleteItem(this.hotResultAdapter.getItemCount() - 1);
            }
            this.hotResultAdapter.addItem(text);
            refreshData();
            return;
        }
        this.resultAdapter.clearData();
    }

    public void onRefreshAutoComplete(String text) {
        getAutoCompleteData(text);
    }

    public void onSearch(String text) {
        this.mlvHotSearchPanel.setVisibility(8);
        this.mlvResult.setVisibility(0);
        this.mSearchView.setLvTipsVisible(false);
        getResultData(text);
        Toast.makeText(this, getString(R.string.complete_search), 0).show();
    }

    public void uploadDateResult(UploadDataCallback callback) {
    }
}
