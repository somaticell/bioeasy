package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.widgets.netstatus.NetUtils;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.MultiItemTypeAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import cn.com.bioeasy.healty.app.healthapp.injection.component.FragmentComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.GoodsPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.CategoryNode;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.Goods;
import cn.com.bioeasy.healty.app.healthapp.widgets.BioeasyLoadMoreViewFooter;
import cn.com.bioeasy.healty.app.healthapp.widgets.EmptyLayout;
import com.bumptech.glide.Glide;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class GoodsListFragment extends BIBaseFragment implements GoodsView {
    /* access modifiers changed from: private */
    public CommonAdapter adapter;
    private CategoryNode categoryNode;
    @Bind({2131231032})
    EmptyLayout emptyLayout;
    @Inject
    GoodsPresenter goodsPresenter;
    /* access modifiers changed from: private */
    public boolean loadEnd;
    @Bind({2131231275})
    RecyclerView mRecyclerView;
    /* access modifiers changed from: private */
    public int pagerNumber = 1;
    @Bind({2131231030})
    PtrClassicFrameLayout ptrClassicFrameLayout;

    static /* synthetic */ int access$510(GoodsListFragment x0) {
        int i = x0.pagerNumber;
        x0.pagerNumber = i - 1;
        return i;
    }

    public static GoodsListFragment getInstance(CategoryNode categoryNode2) {
        GoodsListFragment sf = new GoodsListFragment();
        sf.categoryNode = categoryNode2;
        return sf;
    }

    /* access modifiers changed from: protected */
    public void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    public BasePresenter getPresenter() {
        return this.goodsPresenter;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.fragment_measurement;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents(View rootView) {
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.adapter = new CommonAdapter<Goods>(getActivity(), new ArrayList(), R.layout.measur_items) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder holder, Goods goods, int position) {
                String priceFormat = GoodsListFragment.this.context.getResources().getString(R.string.product_price);
                String countsFormat = GoodsListFragment.this.context.getResources().getString(R.string.product_salecounts);
                String price = String.format(priceFormat, new Object[]{goods.getShopPrice() + ""});
                String counts = String.format(countsFormat, new Object[]{goods.getSaleCount() + ""});
                holder.setText((int) R.id.tv_productName, goods.getName());
                holder.setText((int) R.id.tv_productPrice, price);
                holder.setText((int) R.id.count, counts);
                ((TextView) holder.getView(R.id.tv_old_price)).getPaint().setFlags(16);
                if (!TextUtils.isEmpty(goods.getMarketPrice())) {
                    holder.setText((int) R.id.tv_old_price, "￥" + goods.getMarketPrice());
                }
                holder.setText((int) R.id.tv_productSummary, goods.getSummary());
                Glide.with(GoodsListFragment.this.context).load(goods.getIcon()).centerCrop().placeholder((int) R.drawable.ic_split_graph).error((int) R.drawable.ic_split_graph).into((ImageView) holder.getView(R.id.measur_img));
            }
        };
        this.mRecyclerView.setAdapter(new RecyclerAdapterWithHF(this.adapter));
        this.adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<Goods>() {
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Goods o, int position) {
                ProductsActivity.startAction(GoodsListFragment.this.getActivity(), o.getId());
            }

            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Goods o, int position) {
                return false;
            }
        });
        initEvent();
        refreshData();
    }

    private void initEvent() {
        this.ptrClassicFrameLayout.setFooterView(new BioeasyLoadMoreViewFooter());
        this.ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            public void onRefreshBegin(PtrFrameLayout frame) {
                GoodsListFragment.this.refreshData();
            }
        });
        this.ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void loadMore() {
                GoodsListFragment.this.loadMoreData();
            }
        });
        this.emptyLayout.setOnLayoutClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (GoodsListFragment.this.emptyLayout.getErrorState() != 2) {
                    GoodsListFragment.this.emptyLayout.setErrorType(2);
                    GoodsListFragment.this.loadMoreData();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void loadMoreData() {
        if (!NetUtils.isNetworkConnected(getActivity())) {
            showErrorLayout(1);
        } else if (!this.loadEnd) {
            this.pagerNumber++;
            this.goodsPresenter.getGoodsList(this.categoryNode.getId().intValue(), 10, this.pagerNumber, new BasePresenter.ICallback<PageResult<Goods>>() {
                public void onResult(PageResult<Goods> goodsBean) {
                    if (goodsBean == null || goodsBean.getRows().size() <= 0) {
                        GoodsListFragment.access$510(GoodsListFragment.this);
                        boolean unused = GoodsListFragment.this.loadEnd = true;
                        GoodsListFragment.this.ptrClassicFrameLayout.setLoadMoreEnable(false);
                    } else {
                        List<Goods> goodsList = goodsBean.getRows();
                        if (GoodsListFragment.this.pagerNumber == 1) {
                            GoodsListFragment.this.adapter.refresItems(goodsList);
                        } else {
                            GoodsListFragment.this.adapter.addItemList(goodsList);
                        }
                        GoodsListFragment.this.ptrClassicFrameLayout.setLoadMoreEnable(true);
                        GoodsListFragment.this.emptyLayout.setErrorType(4);
                    }
                    if (GoodsListFragment.this.pagerNumber * 10 < goodsBean.getTotal()) {
                        GoodsListFragment.this.ptrClassicFrameLayout.loadMoreComplete(true);
                    } else {
                        GoodsListFragment.this.ptrClassicFrameLayout.loadMoreComplete(false);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void refreshData() {
        if (!NetUtils.isNetworkConnected(getActivity())) {
            showErrorLayout(1);
            return;
        }
        this.loadEnd = false;
        this.pagerNumber = 1;
        this.goodsPresenter.getGoodsList(this.categoryNode.getId().intValue(), 10, this.pagerNumber, new BasePresenter.ICallback<PageResult<Goods>>() {
            public void onResult(PageResult<Goods> goodsBean) {
                if (GoodsListFragment.this.adapter != null) {
                    GoodsListFragment.this.adapter.refresItems(goodsBean.getRows());
                    GoodsListFragment.this.ptrClassicFrameLayout.setLoadMoreEnable(true);
                    GoodsListFragment.this.emptyLayout.setErrorType(4);
                } else {
                    boolean unused = GoodsListFragment.this.loadEnd = true;
                    GoodsListFragment.this.ptrClassicFrameLayout.setLoadMoreEnable(false);
                }
                GoodsListFragment.this.ptrClassicFrameLayout.refreshComplete();
            }
        });
    }

    public void showErrorLayout(int errorType) {
        this.emptyLayout.setErrorType(errorType);
    }
}
