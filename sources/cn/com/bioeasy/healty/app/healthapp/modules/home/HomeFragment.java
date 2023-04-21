package cn.com.bioeasy.healty.app.healthapp.modules.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.InputDeviceCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.utils.DateUtil;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.app.widgets.netstatus.NetUtils;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.MultiItemTypeAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.adapter.ScrollImagesViewPager;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import cn.com.bioeasy.healty.app.healthapp.base.TabsFragment;
import cn.com.bioeasy.healty.app.healthapp.constant.ActionConstant;
import cn.com.bioeasy.healty.app.healthapp.constant.IntentActions;
import cn.com.bioeasy.healty.app.healthapp.injection.component.FragmentComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.Content;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.FuncInfo;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.ContentDetailActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.HomeView;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.HotInformationActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.NearbyMarketActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.OfficialReleaseInfoActivity;
import cn.com.bioeasy.healty.app.healthapp.widgets.BioeasyLoadMoreViewFooter;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class HomeFragment extends BIBaseFragment implements HomeView {
    private Button bt_login;
    private CommonAdapter contentAdapter;
    private RecyclerView funListView;
    @Inject
    HomePresenter homePresenter;
    private RelativeLayout infoNews;
    @Bind({2131231275})
    RecyclerView mrlView;
    private int pageNumber;
    @Bind({2131231030})
    PtrClassicFrameLayout ptrClassicFrameLayout;
    private RollPagerView rollView;
    @Inject
    ScrollImagesViewPager scrollImagesViewPager;
    private TextView welcomeTip;

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.fragment_homepage;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents(View rootView) {
        initHomePage(rootView);
        initRPagerView();
    }

    public BasePresenter getPresenter() {
        return this.homePresenter;
    }

    private void initHomePage(View rootView) {
        this.contentAdapter = new CommonAdapter<Content>(getActivity(), new ArrayList(), R.layout.information_items) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder holder, Content item, int position) {
                View view;
                String[] category = {"", "", HomeFragment.this.getString(R.string.food_safety_information), HomeFragment.this.getString(R.string.healthy_kitchen), HomeFragment.this.getString(R.string.health_diet), HomeFragment.this.getString(R.string.expert_gang)};
                String[] categoryColors = {"", "", "#0ab48f", "#fa7a53", "#3a98e0", "#f5c010"};
                int cat = Integer.parseInt(item.getCategory());
                if (StringUtil.isNullOrEmpty(item.getIcon())) {
                    holder.setVisible(R.id.item_without_image_panel, true);
                    holder.setVisible(R.id.item_with_image_panel, false);
                    holder.setText((int) R.id.tv_infor_title_1, item.getTitle());
                    holder.setText((int) R.id.tv_readcount_1, HomeFragment.this.getString(R.string.read_one) + item.getAccess());
                    holder.setText((int) R.id.tv_date_1, DateUtil.getDate("yyyy-MM-dd", item.getReleaseTime()));
                    holder.setText((int) R.id.tv_content_type_1, category[cat]);
                    holder.setTextColor(R.id.tv_content_type_1, Color.parseColor(categoryColors[cat]));
                    view = holder.getView(R.id.tv_content_type_1);
                } else {
                    holder.setVisible(R.id.item_without_image_panel, false);
                    holder.setVisible(R.id.item_with_image_panel, true);
                    holder.setText((int) R.id.tv_infor_title, item.getTitle());
                    holder.setText((int) R.id.tv_readcount, HomeFragment.this.getString(R.string.read_one) + item.getAccess());
                    holder.setText((int) R.id.tv_date, DateUtil.getDate("yyyy-MM-dd", item.getReleaseTime()));
                    holder.setImageUrl(R.id.iv_items_imageview, item.getIcon());
                    holder.setText((int) R.id.tv_content_type, category[cat]);
                    view = holder.getView(R.id.tv_content_type);
                    holder.setTextColor(R.id.tv_content_type, Color.parseColor(categoryColors[cat]));
                }
                if (view != null) {
                    ((GradientDrawable) view.getBackground()).setStroke(1, Color.parseColor(categoryColors[cat]));
                }
            }
        };
        this.contentAdapter.offset = 1;
        this.contentAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<Content>() {
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Content o, int position) {
                Intent intent = new Intent(HomeFragment.this.getActivity(), ContentDetailActivity.class);
                intent.putExtra(ActionConstant.CONTENT_ID, o);
                HomeFragment.this.getActivity().startActivity(intent);
            }

            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Content o, int position) {
                return false;
            }
        });
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(1);
        this.mrlView.setLayoutManager(mLayoutManager);
        RecyclerAdapterWithHF mAdapter = new RecyclerAdapterWithHF(this.contentAdapter);
        mAdapter.addHeader(initHeadView());
        this.mrlView.setAdapter(mAdapter);
        initEvent();
        loadMoreData();
    }

    private void initEvent() {
        this.ptrClassicFrameLayout.setFooterView(new BioeasyLoadMoreViewFooter());
        this.ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            public void onRefreshBegin(PtrFrameLayout frame) {
                HomeFragment.this.refreshData();
            }
        });
        this.ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void loadMore() {
                HomeFragment.this.loadMoreData();
            }
        });
    }

    private View initHeadView() {
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.item_home_addview, (ViewGroup) null);
        this.rollView = (RollPagerView) header.findViewById(R.id.roll_view_pager);
        this.funListView = (RecyclerView) header.findViewById(R.id.rv_func_list);
        this.funListView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        List<FuncInfo> funcInfoList = new ArrayList<>();
        funcInfoList.add(FuncInfo.create(0, getString(R.string.func_market), R.drawable.func_icon_market, NearbyMarketActivity.class));
        funcInfoList.add(FuncInfo.create(1, getString(R.string.func_my_record), R.drawable.func_icon_my_record, (Class) null));
        funcInfoList.add(FuncInfo.create(2, getString(R.string.func_news), R.drawable.func_icon_news, OfficialReleaseInfoActivity.class));
        CommonAdapter<FuncInfo> funAdapter = new CommonAdapter<FuncInfo>(getActivity(), funcInfoList, R.layout.function_item) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder holder, FuncInfo funcInfo, int position) {
                holder.setText((int) R.id.tv_func_name, funcInfo.getName());
                holder.setImageResource(R.id.iv_func_image, funcInfo.getIcon());
            }
        };
        funAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<FuncInfo>() {
            public void onItemClick(View view, RecyclerView.ViewHolder holder, FuncInfo funcInfo, int position) {
                if (funcInfo.getPage() != null) {
                    HomeFragment.this.switchPage(funcInfo.getPage());
                    return;
                }
                Fragment fragment = HomeFragment.this.getFragmentManager().findFragmentById(R.id.main_bottom);
                if (fragment != null && (fragment instanceof TabsFragment)) {
                    ((TabsFragment) fragment).onTabSelect(1);
                }
            }

            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, FuncInfo o, int position) {
                return false;
            }
        });
        this.funListView.setAdapter(funAdapter);
        this.infoNews = (RelativeLayout) header.findViewById(R.id.func_more_news_panel);
        this.infoNews.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HomeFragment.this.switchPage(HotInformationActivity.class);
            }
        });
        this.bt_login = (Button) header.findViewById(R.id.bt_login);
        this.welcomeTip = (TextView) header.findViewById(R.id.tv_welcome_tip);
        this.bt_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HomeFragment.this.toSignDaily();
            }
        });
        return header;
    }

    private void initRPagerView() {
        this.rollView.setPlayDelay(2000);
        this.rollView.setAnimationDurtion(500);
        this.rollView.setAdapter(this.scrollImagesViewPager);
        this.rollView.setHintView(new ColorPointHintView(getActivity(), InputDeviceCompat.SOURCE_ANY, -1));
    }

    /* access modifiers changed from: protected */
    public void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    /* access modifiers changed from: private */
    public void toSignDaily() {
        if (!HealthApp.x.isLogin()) {
            toLoginPage();
            return;
        }
        Intent intent = new Intent();
        intent.setAction(IntentActions.USER_SIGN);
        getActivity().sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    public void loadMoreData() {
        if (!NetUtils.isNetworkConnected(getActivity())) {
            showErrorLayout(1);
            return;
        }
        this.pageNumber++;
        this.homePresenter.getContentList(10, "", this.pageNumber, false);
    }

    /* access modifiers changed from: private */
    public void refreshData() {
        if (!NetUtils.isNetworkConnected(getActivity())) {
            showErrorLayout(1);
            return;
        }
        this.pageNumber = 1;
        this.homePresenter.getContentList(10, "", this.pageNumber, true);
    }

    public void showErrorLayout(int errorType) {
    }

    public void updateContentList(PageResult<Content> contentList, boolean refresh) {
        if (refresh) {
            this.contentAdapter.clearData();
            this.ptrClassicFrameLayout.refreshComplete();
            if (contentList == null || contentList.getRows().size() <= 0) {
                this.ptrClassicFrameLayout.setLoadMoreEnable(false);
            } else {
                this.contentAdapter.addItemList(contentList.getRows());
                this.ptrClassicFrameLayout.setLoadMoreEnable(true);
            }
            this.ptrClassicFrameLayout.refreshComplete();
            return;
        }
        if (contentList == null || contentList.getRows().size() <= 0) {
            this.pageNumber--;
            this.ptrClassicFrameLayout.setLoadMoreEnable(false);
        } else {
            this.contentAdapter.addItemList(contentList.getRows());
            this.ptrClassicFrameLayout.setLoadMoreEnable(true);
        }
        if (this.pageNumber * 10 < contentList.getTotal()) {
            this.ptrClassicFrameLayout.loadMoreComplete(true);
        } else {
            this.ptrClassicFrameLayout.loadMoreComplete(false);
        }
    }

    public void onResume() {
        super.onResume();
        this.homePresenter.getItemCategoryList();
        if (HealthApp.x.isLogin()) {
            this.bt_login.setText(getString(R.string.bt_sign));
            this.welcomeTip.setText(getString(R.string.tv_welcome_login_tip));
            return;
        }
        this.bt_login.setText(getString(R.string.bt_login));
        this.welcomeTip.setText(getString(R.string.tv_welcome_tip));
    }
}
