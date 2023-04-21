package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.Bind;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.app.widgets.SearchView;
import cn.com.bioeasy.app.widgets.abslistview.CommonAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.MultiItemTypeAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.GoodsPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.Goods;
import cn.com.bioeasy.healty.app.healthapp.widgets.BioeasyLoadMoreViewFooter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class GoodsSearchActivity extends BIBaseActivity implements SearchView.SearchViewListener, GoodsView {
    /* access modifiers changed from: private */
    public CommonAdapter<String> autoCompleteAdapter;
    /* access modifiers changed from: private */
    public List<String> autoCompleteData = new ArrayList();
    @Inject
    GoodsPresenter goodsPresenter;
    private cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter<String> hotResultAdapter;
    private List<String> hotResultData = new ArrayList();
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
    private String searchText = null;

    static /* synthetic */ int access$310(GoodsSearchActivity x0) {
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
        return this.goodsPresenter;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_search;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.mlvResult.setLayoutManager(new LinearLayoutManager(this));
        this.resultAdapter = new cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter<Goods>(this, new ArrayList(), R.layout.goods_show_list_item) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder holder, Goods item, int position) {
                holder.setText((int) R.id.product_name_txtv, item.getName());
                holder.setVisible(R.id.product_spec_txtv, false);
                holder.setText((int) R.id.product_price_txtv, "¥" + (StringUtil.isNullOrEmpty(item.getShopPrice()) ? "0.0" : item.getShopPrice()));
                holder.setVisible(R.id.product_count_txtv, false);
                Glide.with((FragmentActivity) GoodsSearchActivity.this).load(item.getIcon()).placeholder((int) R.drawable.product_default).diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) holder.getView(R.id.product_pic_imgv));
            }
        };
        this.resultAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<Goods>() {
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Goods o, int position) {
                ProductsActivity.startAction(GoodsSearchActivity.this, o.getId());
            }

            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Goods o, int position) {
                return false;
            }
        });
        this.mlvResult.setAdapter(new RecyclerAdapterWithHF(this.resultAdapter));
        this.mlvHotSearch.setLayoutManager(new LinearLayoutManager(this));
        this.hotResultData.add(getString(R.string.GOODS_TYPE_1));
        this.hotResultData.add(getString(R.string.GOODS_TYPE_2));
        this.hotResultData.add(getString(R.string.GOODS_TYPE_3));
        this.hotResultData.add(getString(R.string.GOODS_TYPE_4));
        this.hotResultData.add(getString(R.string.GOODS_TYPE_5));
        this.hotResultData.add(getString(R.string.GOODS_TYPE_6));
        this.hotResultData.add(getString(R.string.GOODS_TYPE_7));
        this.hotResultAdapter = new cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter<String>(this.context, this.hotResultData, R.layout.item_search_list) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.setVisible(R.id.item_search_iv_icon, false);
                viewHolder.setText((int) R.id.tv_search_title, item);
            }
        };
        this.hotResultAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<String>() {
            public void onItemClick(View view, RecyclerView.ViewHolder holder, String o, int position) {
                GoodsSearchActivity.this.mSearchView.setSearchText(o);
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
                GoodsSearchActivity.this.refreshData();
            }
        });
        this.ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void loadMore() {
                GoodsSearchActivity.this.loadMoreData();
            }
        });
    }

    /* access modifiers changed from: private */
    public void loadMoreData() {
        if (!StringUtil.isNullOrEmpty(this.searchText)) {
            this.pagerNumber++;
            this.goodsPresenter.getSearchGoodsList(this.searchText, (String) null, this.pagerNumber, new BasePresenter.ICallback<PageResult<Goods>>() {
                public void onResult(PageResult<Goods> pageResult) {
                    GoodsSearchActivity.this.mSearchView.setLvTipsVisible(false);
                    if (pageResult == null || pageResult.getRows().size() <= 0) {
                        GoodsSearchActivity.access$310(GoodsSearchActivity.this);
                        GoodsSearchActivity.this.ptrClassicFrameLayout.setLoadMoreEnable(false);
                    } else {
                        GoodsSearchActivity.this.resultAdapter.addItemList(pageResult.getRows());
                        GoodsSearchActivity.this.ptrClassicFrameLayout.setLoadMoreEnable(true);
                    }
                    if (GoodsSearchActivity.this.pagerNumber * 10 < pageResult.getTotal()) {
                        GoodsSearchActivity.this.ptrClassicFrameLayout.loadMoreComplete(true);
                    } else {
                        GoodsSearchActivity.this.ptrClassicFrameLayout.loadMoreComplete(false);
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
            this.goodsPresenter.getSearchGoodsList(this.searchText, (String) null, this.pagerNumber, new BasePresenter.ICallback<PageResult<Goods>>() {
                public void onResult(PageResult<Goods> pageResult) {
                    GoodsSearchActivity.this.mSearchView.setLvTipsVisible(false);
                    GoodsSearchActivity.this.ptrClassicFrameLayout.refreshComplete();
                    if (pageResult == null || pageResult.getRows().size() <= 0) {
                        GoodsSearchActivity.this.ptrClassicFrameLayout.setLoadMoreEnable(false);
                    } else {
                        GoodsSearchActivity.this.resultAdapter.addItemList(pageResult.getRows());
                        GoodsSearchActivity.this.ptrClassicFrameLayout.setLoadMoreEnable(true);
                    }
                    GoodsSearchActivity.this.ptrClassicFrameLayout.refreshComplete();
                }
            });
        }
    }

    private void getAutoCompleteData(String text) {
        this.autoCompleteData.clear();
        if (!StringUtil.isNullOrEmpty(text)) {
            this.mlvHotSearchPanel.setVisibility(8);
            this.goodsPresenter.getGoodsSuggestList(text, (String) null, new BasePresenter.ICallback<List<String>>() {
                public void onResult(List<String> result) {
                    GoodsSearchActivity.this.mSearchView.setLvTipsVisible(result.size() > 0);
                    GoodsSearchActivity.this.autoCompleteData.addAll(result);
                    GoodsSearchActivity.this.autoCompleteAdapter.notifyDataSetChanged();
                }
            });
            return;
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
            refreshData();
        } else {
            this.resultAdapter.clearData();
        }
    }

    public void onRefreshAutoComplete(String text) {
        getAutoCompleteData(text);
    }

    public void onSearch(String text) {
        this.mlvHotSearchPanel.setVisibility(8);
        this.mlvResult.setVisibility(0);
        this.mSearchView.setLvTipsVisible(false);
        getResultData(text);
        Toast.makeText(this, getString(R.string.GOODS_SERACH_COMP), 0).show();
    }

    public void showErrorLayout(int errorType) {
    }
}
