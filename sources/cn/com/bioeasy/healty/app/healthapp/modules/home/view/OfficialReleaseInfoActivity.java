package cn.com.bioeasy.healty.app.healthapp.modules.home.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.utils.DateUtil;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.app.widgets.netstatus.NetUtils;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.MultiItemTypeAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.constant.ActionConstant;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.home.HomePresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.Content;
import cn.com.bioeasy.healty.app.healthapp.widgets.BioeasyLoadMoreViewFooter;
import cn.com.bioeasy.healty.app.healthapp.widgets.EmptyLayout;
import com.alipay.sdk.cons.a;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import java.util.ArrayList;
import javax.inject.Inject;

public class OfficialReleaseInfoActivity extends BIBaseActivity implements HomeView {
    private static String CATEGORY = a.e;
    private CommonAdapter adapter;
    @Bind({2131231032})
    EmptyLayout emptyLayout;
    @Inject
    HomePresenter homePresenter;
    private boolean loadEnd;
    @Bind({2131231031})
    RecyclerView mRlView;
    private int pageNumber = 1;
    @Bind({2131231030})
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @Bind({2131231008})
    TextView tvTitle;

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    public BasePresenter getPresenter() {
        return this.homePresenter;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_official_release;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.tvTitle.setText(getResources().getString(R.string.tv_food));
        this.adapter = new CommonAdapter<Content>(this.context, new ArrayList(), R.layout.official_release_items) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder viewHolder, Content item, int position) {
                viewHolder.setText((int) R.id.tv_readcount, OfficialReleaseInfoActivity.this.getString(R.string.read_one) + item.getAccess());
                if (StringUtil.isNullOrEmpty(item.getSummary())) {
                    viewHolder.setVisible(R.id.tv_summary, false);
                } else {
                    viewHolder.setText((int) R.id.tv_summary, item.getSummary());
                }
                viewHolder.setText((int) R.id.tv_title, item.getTitle()).setText((int) R.id.tv_date, DateUtil.getDate("yyyy-MM-dd", item.getReleaseTime()));
            }
        };
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(1);
        this.mRlView.setLayoutManager(mLayoutManager);
        this.mRlView.setAdapter(new RecyclerAdapterWithHF(this.adapter));
        this.adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<Content>() {
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Content o, int position) {
                Intent intent = new Intent(OfficialReleaseInfoActivity.this, ContentDetailActivity.class);
                intent.putExtra(ActionConstant.CONTENT_ID, o);
                OfficialReleaseInfoActivity.this.startActivity(intent);
            }

            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Content o, int position) {
                return false;
            }
        });
        initEvent();
        refreshData();
    }

    @OnClick({2131231050})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                return;
            default:
                return;
        }
    }

    private void initEvent() {
        this.ptrClassicFrameLayout.setFooterView(new BioeasyLoadMoreViewFooter());
        this.ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            public void onRefreshBegin(PtrFrameLayout frame) {
                OfficialReleaseInfoActivity.this.refreshData();
            }
        });
        this.ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void loadMore() {
                OfficialReleaseInfoActivity.this.loadMoreData();
            }
        });
        this.emptyLayout.setOnLayoutClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (OfficialReleaseInfoActivity.this.emptyLayout.getErrorState() != 2) {
                    OfficialReleaseInfoActivity.this.emptyLayout.setErrorType(2);
                    OfficialReleaseInfoActivity.this.loadMoreData();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void loadMoreData() {
        if (!NetUtils.isNetworkConnected(this)) {
            showErrorLayout(1);
        } else if (!this.loadEnd) {
            this.pageNumber++;
            this.homePresenter.getContentList(10, CATEGORY, this.pageNumber, false);
        }
    }

    /* access modifiers changed from: private */
    public void refreshData() {
        if (!NetUtils.isNetworkConnected(this)) {
            showErrorLayout(1);
            return;
        }
        this.pageNumber = 1;
        this.loadEnd = false;
        this.homePresenter.getContentList(10, CATEGORY, this.pageNumber, true);
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
