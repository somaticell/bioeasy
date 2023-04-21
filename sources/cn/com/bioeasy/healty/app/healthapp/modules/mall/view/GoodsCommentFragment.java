package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.utils.DateUtil;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import cn.com.bioeasy.healty.app.healthapp.injection.component.FragmentComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.GoodsPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.GoodsEvaluation;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.GoodsEvaluationStar;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.GoodsWithBLOBs;
import cn.com.bioeasy.healty.app.healthapp.widgets.BioeasyLoadMoreViewFooter;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class GoodsCommentFragment extends BIBaseFragment {
    /* access modifiers changed from: private */
    public CommonAdapter commonAdapter;
    private TextView currentSelectComment;
    @Bind({2131231259})
    LinearLayout emptyCommentPanel;
    @Bind({2131230953})
    RatingBar ev_quality;
    private int goodID;
    @Inject
    GoodsPresenter goodsPresenter;
    private int pageIndex;
    @Bind({2131231260})
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @Bind({2131231261})
    RecyclerView recyclerView;
    /* access modifiers changed from: private */
    public int stars = 0;
    @Bind({2131231258})
    TextView tv_bad_comment;
    @Bind({2131231255})
    TextView tv_comment_count;
    @Bind({2131231252})
    TextView tv_eva_quality;
    @Bind({2131231256})
    TextView tv_good_comment;
    @Bind({2131231253})
    TextView tv_good_comment_rate;
    @Bind({2131231257})
    TextView tv_medial_comment;

    public void setGoodsId(int goodID2) {
        this.goodID = goodID2;
    }

    public BasePresenter getPresenter() {
        return this.goodsPresenter;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.fragment_goods_comment;
    }

    /* access modifiers changed from: protected */
    public void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents(View rootView) {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        this.commonAdapter = new CommonAdapter<GoodsEvaluation>(getActivity(), new ArrayList(), R.layout.goods_eva_item) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder holder, GoodsEvaluation evaluation, int position) {
                holder.setText((int) R.id.tv_eva_username, evaluation.getUserName());
                holder.setText((int) R.id.tv_eva_time, DateUtil.getDate("yyyy-MM-dd", evaluation.getCtime().longValue()));
                holder.setText((int) R.id.tv_eva_content, evaluation.getEvaluation());
                holder.setRating(R.id.eva_quality, (float) evaluation.getQuality().intValue());
                if (!StringUtil.isNullOrEmpty(evaluation.getProps())) {
                    List<String> urls = JSON.parseArray(evaluation.getProps(), String.class);
                    GoodsCommentFragment.this.buildProductPreviewGallery((LinearLayout) holder.getView(R.id.eva_list_gallery_lyaout), urls, position);
                }
            }
        };
        this.recyclerView.setAdapter(this.commonAdapter);
        this.currentSelectComment = this.tv_comment_count;
        this.currentSelectComment.setTextColor(-16776961);
        initEvent();
        loadMoreData();
    }

    /* access modifiers changed from: private */
    public void buildProductPreviewGallery(LinearLayout gallery, List<String> icons, int position) {
        gallery.removeAllViews();
        for (int i = 0; i < icons.size(); i++) {
            final String url = icons.get(i);
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_index_gallery_item, gallery, false);
            view.setTag(Integer.valueOf(position));
            ImageView img = (ImageView) view.findViewById(R.id.id_index_gallery_item_image);
            Glide.with(getActivity()).load(url).placeholder((int) R.drawable.product_default).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img);
            gallery.addView(view);
            img.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    View imgEntryView = LayoutInflater.from(GoodsCommentFragment.this.getActivity()).inflate(R.layout.scale_photo_view, (ViewGroup) null);
                    final AlertDialog dialog = new AlertDialog.Builder(GoodsCommentFragment.this.getActivity()).create();
                    Glide.with(GoodsCommentFragment.this.getActivity()).load(url).placeholder((int) R.drawable.product_default).fitCenter().diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) imgEntryView.findViewById(R.id.large_image));
                    dialog.setView(imgEntryView);
                    dialog.show();
                    imgEntryView.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View paramView) {
                            dialog.cancel();
                        }
                    });
                }
            });
        }
    }

    @OnClick({2131231255, 2131231256, 2131231257, 2131231258})
    public void onClick(View view) {
        this.currentSelectComment.setTextColor(-16777216);
        switch (view.getId()) {
            case R.id.tv_all_comment:
                this.currentSelectComment = this.tv_comment_count;
                this.stars = 0;
                break;
            case R.id.tv_good_comment:
                this.currentSelectComment = this.tv_good_comment;
                this.stars = 5;
                break;
            case R.id.tv_medial_comment:
                this.currentSelectComment = this.tv_medial_comment;
                this.stars = 3;
                break;
            case R.id.tv_bad_comment:
                this.currentSelectComment = this.tv_bad_comment;
                this.stars = 1;
                break;
        }
        this.currentSelectComment.setTextColor(-16776961);
        refreshData();
    }

    public void onResume() {
        super.onResume();
    }

    private void initEvent() {
        this.ptrClassicFrameLayout.setFooterView(new BioeasyLoadMoreViewFooter());
        this.ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            public void onRefreshBegin(PtrFrameLayout frame) {
                GoodsCommentFragment.this.refreshData();
            }
        });
        this.ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void loadMore() {
                GoodsCommentFragment.this.loadMoreData();
            }
        });
    }

    public void refreshData() {
        this.pageIndex = 1;
        this.goodsPresenter.getGoodsEvaluationList(this.goodID, this.stars, 10, this.pageIndex, new BasePresenter.ICallback<PageResult<GoodsEvaluation>>() {
            public void onResult(PageResult<GoodsEvaluation> result) {
                if (GoodsCommentFragment.this.stars == 0) {
                    GoodsCommentFragment.this.tv_comment_count.setText(GoodsCommentFragment.this.getString(R.string.GOODS_EVA_ALL) + "(" + result.getTotal() + ")");
                }
                GoodsCommentFragment.this.commonAdapter.refresItems(result.getRows());
                if (result.getRows().size() == 0) {
                    GoodsCommentFragment.this.ptrClassicFrameLayout.setLoadMoreEnable(false);
                }
                GoodsCommentFragment.this.ptrClassicFrameLayout.refreshComplete();
            }
        });
    }

    public void loadMoreData() {
        this.pageIndex++;
        this.goodsPresenter.getGoodsEvaluationList(this.goodID, this.stars, 10, this.pageIndex, new BasePresenter.ICallback<PageResult<GoodsEvaluation>>() {
            public void onResult(PageResult<GoodsEvaluation> result) {
                if (GoodsCommentFragment.this.stars == 0) {
                    GoodsCommentFragment.this.tv_comment_count.setText(GoodsCommentFragment.this.getString(R.string.GOODS_EVA_ALL) + "(" + result.getTotal() + ")");
                }
                if (result.getRows().size() > 0) {
                    GoodsCommentFragment.this.commonAdapter.addItemList(result.getRows());
                } else {
                    GoodsCommentFragment.this.ptrClassicFrameLayout.setLoadMoreEnable(false);
                }
                GoodsCommentFragment.this.ptrClassicFrameLayout.refreshComplete();
            }
        });
    }

    public void setGoodsDetail(GoodsWithBLOBs goodsWithBLOBs) {
        List<GoodsEvaluationStar> starList = goodsWithBLOBs.getStarList();
        int goodCount = 0;
        int badCount = 0;
        int mediumCount = 0;
        int totalCount = 0;
        int totalScore = 0;
        if (starList != null && starList.size() > 0) {
            for (GoodsEvaluationStar starData : starList) {
                if (starData.getStar() >= 4) {
                    goodCount += starData.getCount();
                }
                if (starData.getStar() <= 2) {
                    badCount += starData.getCount();
                }
                if (starData.getStar() == 3) {
                    mediumCount += starData.getCount();
                }
                totalScore += starData.getStar() * starData.getCount();
                totalCount += starData.getCount();
            }
        }
        if (totalCount > 0) {
            this.tv_good_comment_rate.setText(getString(R.string.GOODS_EVA_RATE) + "：" + String.valueOf((int) (((((double) goodCount) * 1.0d) / ((double) totalCount)) * 100.0d)) + "%");
            this.tv_good_comment.setText(getString(R.string.GOODS_EVA_G) + "(" + goodCount + ")");
            this.tv_medial_comment.setText(getString(R.string.GOODS_EVA_M) + "(" + mediumCount + ")");
            this.tv_bad_comment.setText(getString(R.string.GOODS_EVA_L) + "(" + badCount + ")");
            this.ev_quality.setRating((float) (totalScore / totalCount));
            this.tv_eva_quality.setText(new DecimalFormat("#.0").format((long) (totalScore / totalCount)) + getString(R.string.GOODS_EVA_SCORE));
        }
    }
}
