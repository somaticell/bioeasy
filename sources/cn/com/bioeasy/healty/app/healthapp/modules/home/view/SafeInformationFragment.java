package cn.com.bioeasy.healty.app.healthapp.modules.home.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.Bind;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.utils.DateUtil;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.app.widgets.netstatus.NetUtils;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.MultiItemTypeAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.app.widgets.recyclerview.divider.RecycleViewDivider;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import cn.com.bioeasy.healty.app.healthapp.constant.ActionConstant;
import cn.com.bioeasy.healty.app.healthapp.injection.component.FragmentComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.home.HomePresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.Content;
import cn.com.bioeasy.healty.app.healthapp.widgets.BioeasyLoadMoreViewFooter;
import cn.com.bioeasy.healty.app.healthapp.widgets.EmptyLayout;
import com.bumptech.glide.Glide;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import java.util.ArrayList;
import javax.inject.Inject;

public class SafeInformationFragment extends BIBaseFragment implements HomeView {
    private static final String KEY_CATEGORY = "TYPE";
    private CommonAdapter adapter;
    private String category;
    @Bind({2131231032})
    EmptyLayout emptyLayout;
    @Inject
    HomePresenter homePresenter;
    private boolean loadEnd;
    @Bind({2131231275})
    RecyclerView mrlView;
    private int pageNumber = 1;
    @Bind({2131231030})
    PtrClassicFrameLayout ptrClassicFrameLayout;

    public static SafeInformationFragment getInstance(int type) {
        SafeInformationFragment safeInformationFragment = new SafeInformationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_CATEGORY, type);
        safeInformationFragment.setArguments(bundle);
        return safeInformationFragment;
    }

    /* access modifiers changed from: protected */
    public void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    public BasePresenter getPresenter() {
        return this.homePresenter;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.fragment_hotinformation;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents(View rootView) {
        if (getArguments() != null) {
            this.category = String.valueOf(getArguments().getInt(KEY_CATEGORY));
        }
        this.adapter = new CommonAdapter<Content>(getActivity(), new ArrayList(), R.layout.information_items) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder holder, Content item, int position) {
                View view;
                String[] category = {"", SafeInformationFragment.this.getString(R.string.tv_food), SafeInformationFragment.this.getString(R.string.food_safety_information), SafeInformationFragment.this.getString(R.string.healthy_kitchen), SafeInformationFragment.this.getString(R.string.health_diet), SafeInformationFragment.this.getString(R.string.expert_gang)};
                String[] categoryColors = {"", "#0ab48f", "#0ab48f", "#fa7a53", "#3a98e0", "#f5c010"};
                int cat = Integer.parseInt(item.getCategory());
                if (StringUtil.isNullOrEmpty(item.getIcon())) {
                    holder.setVisible(R.id.item_without_image_panel, true);
                    holder.setVisible(R.id.item_with_image_panel, false);
                    holder.setText((int) R.id.tv_infor_title_1, item.getTitle());
                    holder.setText((int) R.id.tv_readcount_1, SafeInformationFragment.this.getString(R.string.read_one) + item.getAccess());
                    holder.setText((int) R.id.tv_date_1, DateUtil.getDate("yyyy-MM-dd", item.getReleaseTime()));
                    holder.setText((int) R.id.tv_content_type_1, category[cat]);
                    holder.setTextColor(R.id.tv_content_type_1, Color.parseColor(categoryColors[cat]));
                    view = holder.getView(R.id.tv_content_type_1);
                } else {
                    holder.setVisible(R.id.item_without_image_panel, false);
                    holder.setVisible(R.id.item_with_image_panel, true);
                    holder.setText((int) R.id.tv_infor_title, item.getTitle());
                    holder.setText((int) R.id.tv_readcount, SafeInformationFragment.this.getString(R.string.read_one) + item.getAccess());
                    holder.setText((int) R.id.tv_date, DateUtil.getDate("yyyy-MM-dd", item.getReleaseTime()));
                    Glide.with(SafeInformationFragment.this.getContext()).load(item.getIcon()).centerCrop().placeholder((int) R.drawable.ic_split_graph).override(100, 60).error((int) R.drawable.ic_split_graph).into((ImageView) holder.getView(R.id.iv_items_imageview));
                    holder.setText((int) R.id.tv_content_type, category[cat]);
                    view = holder.getView(R.id.tv_content_type);
                    holder.setTextColor(R.id.tv_content_type, Color.parseColor(categoryColors[cat]));
                }
                if (view != null) {
                    ((GradientDrawable) view.getBackground()).setStroke(1, Color.parseColor(categoryColors[cat]));
                }
            }
        };
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(1);
        this.mrlView.setLayoutManager(mLayoutManager);
        this.mrlView.addItemDecoration(new RecycleViewDivider(getActivity(), 1, 1, getResources().getColor(R.color.comm_spiteline)));
        this.mrlView.setAdapter(new RecyclerAdapterWithHF(this.adapter));
        this.adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<Content>() {
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Content o, int position) {
                Intent intent = new Intent(SafeInformationFragment.this.getActivity(), ContentDetailActivity.class);
                intent.putExtra(ActionConstant.CONTENT_ID, o);
                SafeInformationFragment.this.getActivity().startActivity(intent);
            }

            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Content o, int position) {
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
                SafeInformationFragment.this.refreshData();
            }
        });
        this.ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void loadMore() {
                SafeInformationFragment.this.loadMoreData();
            }
        });
        this.emptyLayout.setOnLayoutClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (SafeInformationFragment.this.emptyLayout.getErrorState() != 2) {
                    SafeInformationFragment.this.emptyLayout.setErrorType(2);
                    SafeInformationFragment.this.refreshData();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void loadMoreData() {
        if (!NetUtils.isNetworkConnected(getActivity())) {
            showErrorLayout(1);
        } else if (!this.loadEnd) {
            this.pageNumber++;
            this.homePresenter.getContentList(10, this.category, this.pageNumber, false);
        }
    }

    /* access modifiers changed from: private */
    public void refreshData() {
        this.pageNumber = 1;
        this.loadEnd = false;
        this.homePresenter.getContentList(10, this.category, this.pageNumber, true);
    }

    public void showErrorLayout(int errorType) {
        this.emptyLayout.setErrorType(errorType);
    }

    public void updateContentList(PageResult<Content> contentList, boolean refresh) {
        if (refresh) {
            if (contentList != null) {
                this.adapter.refresItems(contentList.getRows());
                this.ptrClassicFrameLayout.setLoadMoreEnable(true);
                this.emptyLayout.setErrorType(4);
            } else {
                this.loadEnd = true;
                this.ptrClassicFrameLayout.setLoadMoreEnable(false);
            }
            this.ptrClassicFrameLayout.refreshComplete();
            return;
        }
        if (contentList == null || contentList.getRows().size() <= 0) {
            this.pageNumber--;
            this.loadEnd = true;
            this.ptrClassicFrameLayout.setLoadMoreEnable(false);
        } else {
            this.adapter.clearData();
            this.adapter.addItemList(contentList.getRows());
            this.ptrClassicFrameLayout.setLoadMoreEnable(true);
            this.emptyLayout.setErrorType(4);
        }
        if (this.pageNumber * 10 < contentList.getTotal()) {
            this.ptrClassicFrameLayout.loadMoreComplete(true);
        } else {
            this.ptrClassicFrameLayout.loadMoreComplete(false);
        }
    }
}
