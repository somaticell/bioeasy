package cn.com.bioeasy.healty.app.healthapp.modules.home.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.Bind;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.utils.DateUtil;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.app.widgets.SearchView;
import cn.com.bioeasy.app.widgets.abslistview.CommonAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.MultiItemTypeAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.constant.ActionConstant;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.home.HomePresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.Content;
import cn.com.bioeasy.healty.app.healthapp.widgets.BioeasyLoadMoreViewFooter;
import com.bumptech.glide.Glide;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class ContentSearchActivity extends BIBaseActivity implements SearchView.SearchViewListener, HomeView {
    /* access modifiers changed from: private */
    public CommonAdapter<String> autoCompleteAdapter;
    /* access modifiers changed from: private */
    public List<String> autoCompleteData = new ArrayList();
    @Inject
    HomePresenter homePresenter;
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
    public int pagerNumber;
    @Bind({2131231030})
    PtrClassicFrameLayout ptrClassicFrameLayout;
    /* access modifiers changed from: private */
    public cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter resultAdapter;
    private String searchText;

    static /* synthetic */ int access$310(ContentSearchActivity x0) {
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
        return this.homePresenter;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_search;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.mlvResult.setLayoutManager(new LinearLayoutManager(this));
        this.resultAdapter = new cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter<Content>(this, new ArrayList(), R.layout.information_items) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder holder, Content item, int position) {
                View view;
                String[] category = {"", "this.getString(R.string.food_safety_information)", "this.getString(R.string.healthy_kitchen)", "this.getString(R.string.health_diet)", "this.getString(R.string.expert_gang)"};
                String[] categoryColors = {"", "#0ab48f", "#fa7a53", "#3a98e0", "#f5c010"};
                int cat = Integer.parseInt(item.getCategory());
                if (StringUtil.isNullOrEmpty(item.getIcon())) {
                    holder.setVisible(R.id.item_without_image_panel, true);
                    holder.setVisible(R.id.item_with_image_panel, false);
                    holder.setText((int) R.id.tv_infor_title_1, item.getTitle());
                    holder.setText((int) R.id.tv_readcount_1, "this.getString(R.string.read_one)" + item.getAccess());
                    holder.setText((int) R.id.tv_date_1, DateUtil.getDate("yyyy-MM-dd", item.getReleaseTime()));
                    holder.setText((int) R.id.tv_content_type_1, category[cat]);
                    holder.setTextColor(R.id.tv_content_type_1, Color.parseColor(categoryColors[cat]));
                    view = holder.getView(R.id.tv_content_type_1);
                } else {
                    holder.setVisible(R.id.item_without_image_panel, false);
                    holder.setVisible(R.id.item_with_image_panel, true);
                    holder.setText((int) R.id.tv_infor_title, item.getTitle());
                    holder.setText((int) R.id.tv_readcount, "this.getString(R.string.read_one)" + item.getAccess());
                    holder.setText((int) R.id.tv_date, DateUtil.getDate("yyyy-MM-dd", item.getReleaseTime()));
                    Glide.with(this.mContext).load(item.getIcon()).centerCrop().placeholder((int) R.drawable.ic_split_graph).override(100, 60).error((int) R.drawable.ic_split_graph).into((ImageView) holder.getView(R.id.iv_items_imageview));
                    holder.setText((int) R.id.tv_content_type, category[cat]);
                    view = holder.getView(R.id.tv_content_type);
                    holder.setTextColor(R.id.tv_content_type, Color.parseColor(categoryColors[cat]));
                }
                if (view != null) {
                    ((GradientDrawable) view.getBackground()).setStroke(1, Color.parseColor(categoryColors[cat]));
                }
            }
        };
        this.resultAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<Content>() {
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Content o, int position) {
                Intent intent = new Intent(ContentSearchActivity.this, ContentDetailActivity.class);
                intent.putExtra(ActionConstant.CONTENT_ID, o);
                ContentSearchActivity.this.startActivity(intent);
            }

            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Content o, int position) {
                return false;
            }
        });
        this.mlvResult.setAdapter(this.resultAdapter);
        this.mlvResult.setAdapter(new RecyclerAdapterWithHF(this.resultAdapter));
        this.mlvHotSearch.setLayoutManager(new LinearLayoutManager(this));
        this.hotResultData.add("this.getString(R.string.food_safety_information)");
        this.hotResultData.add("this.getString(R.string.healthy_kitchen)");
        this.hotResultData.add("this.getString(R.string.health_diet)");
        this.hotResultData.add("this.getString(R.string.expert_gang)");
        this.hotResultAdapter = new cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter<String>(this.context, this.hotResultData, R.layout.item_search_list) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.setVisible(R.id.item_search_iv_icon, false);
                viewHolder.setText((int) R.id.tv_search_title, item);
            }
        };
        this.hotResultAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<String>() {
            public void onItemClick(View view, RecyclerView.ViewHolder holder, String o, int position) {
                ContentSearchActivity.this.mSearchView.setSearchText(o);
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
                ContentSearchActivity.this.refreshData();
            }
        });
        this.ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void loadMore() {
                ContentSearchActivity.this.loadMoreData();
            }
        });
    }

    /* access modifiers changed from: private */
    public void loadMoreData() {
        if (!StringUtil.isNullOrEmpty(this.searchText)) {
            this.pagerNumber++;
            this.homePresenter.getSearchContentList(this.searchText, (String) null, this.pagerNumber, new BasePresenter.ICallback<PageResult<Content>>() {
                public void onResult(PageResult<Content> pageResult) {
                    ContentSearchActivity.this.mSearchView.setLvTipsVisible(false);
                    if (pageResult == null || pageResult.getRows().size() <= 0) {
                        ContentSearchActivity.access$310(ContentSearchActivity.this);
                        ContentSearchActivity.this.ptrClassicFrameLayout.setLoadMoreEnable(false);
                    } else {
                        ContentSearchActivity.this.resultAdapter.addItemList(pageResult.getRows());
                        ContentSearchActivity.this.ptrClassicFrameLayout.setLoadMoreEnable(true);
                    }
                    if (ContentSearchActivity.this.pagerNumber * 10 < pageResult.getTotal()) {
                        ContentSearchActivity.this.ptrClassicFrameLayout.loadMoreComplete(true);
                    } else {
                        ContentSearchActivity.this.ptrClassicFrameLayout.loadMoreComplete(false);
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
            this.homePresenter.getSearchContentList(this.searchText, (String) null, this.pagerNumber, new BasePresenter.ICallback<PageResult<Content>>() {
                public void onResult(PageResult<Content> pageResult) {
                    ContentSearchActivity.this.mSearchView.setLvTipsVisible(false);
                    ContentSearchActivity.this.ptrClassicFrameLayout.refreshComplete();
                    if (pageResult == null || pageResult.getRows().size() <= 0) {
                        ContentSearchActivity.this.ptrClassicFrameLayout.setLoadMoreEnable(false);
                    } else {
                        ContentSearchActivity.this.resultAdapter.addItemList(pageResult.getRows());
                        ContentSearchActivity.this.ptrClassicFrameLayout.setLoadMoreEnable(true);
                    }
                    ContentSearchActivity.this.ptrClassicFrameLayout.refreshComplete();
                }
            });
        }
    }

    private void getAutoCompleteData(String text) {
        this.autoCompleteData.clear();
        if (!StringUtil.isNullOrEmpty(text)) {
            this.mlvHotSearchPanel.setVisibility(8);
            this.homePresenter.getContentSuggestList(text, (String) null, new BasePresenter.ICallback<List<String>>() {
                public void onResult(List<String> result) {
                    ContentSearchActivity.this.mSearchView.setLvTipsVisible(result.size() > 0);
                    ContentSearchActivity.this.autoCompleteData.addAll(result);
                    ContentSearchActivity.this.autoCompleteAdapter.notifyDataSetChanged();
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
        Toast.makeText(this, getString(R.string.complete_the_search), 0).show();
    }

    public void showErrorLayout(int errorType) {
    }

    public void updateContentList(PageResult<Content> pageResult, boolean refresh) {
    }
}
