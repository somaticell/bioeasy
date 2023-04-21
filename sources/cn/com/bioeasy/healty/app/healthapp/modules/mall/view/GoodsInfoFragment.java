package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import cn.com.bioeasy.healty.app.healthapp.adapter.ItemRecommendAdapter;
import cn.com.bioeasy.healty.app.healthapp.adapter.NetworkImageHolderView;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import cn.com.bioeasy.healty.app.healthapp.injection.component.FragmentComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.GoodsPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.Goods;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.GoodsEvaluation;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.GoodsEvaluationStar;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.GoodsWithBLOBs;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.RecommendGoodsBean;
import com.alibaba.fastjson.JSON;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class GoodsInfoFragment extends BIBaseFragment {
    public ProductsActivity activity;
    @Bind({2131231254})
    RelativeLayout commentPanel;
    /* access modifiers changed from: private */
    public CommonAdapter commonAdapter;
    @Bind({2131231259})
    LinearLayout emptyCommentPanel;
    private int goodID;
    @Inject
    GoodsPresenter goodsPresenter;
    @Bind({2131231268})
    public LinearLayout ll_current_goods;
    @Bind({2131231272})
    public LinearLayout ll_recommend;
    @Bind({2131231261})
    RecyclerView recyclerView;
    @Bind({2131231270})
    public TextView tv_comment_count;
    @Bind({2131231269})
    public TextView tv_current_goods;
    @Bind({2131231256})
    public TextView tv_good_comment;
    @Bind({2131231265})
    public TextView tv_goods_summary;
    @Bind({2131231264})
    public TextView tv_goods_title;
    @Bind({2131231266})
    public TextView tv_new_price;
    @Bind({2131231267})
    public TextView tv_old_price;
    @Bind({2131231263})
    public ConvenientBanner vp_item_goods_img;
    @Bind({2131231273})
    ConvenientBanner vp_recommend;

    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (ProductsActivity) context;
    }

    public void setGoodsId(int goodID2) {
        this.goodID = goodID2;
    }

    public void onResume() {
        super.onResume();
        this.vp_item_goods_img.startTurning(4000);
    }

    public void onPause() {
        super.onPause();
        this.vp_item_goods_img.stopTurning();
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.fragment_goods_info;
    }

    public BasePresenter getPresenter() {
        return this.goodsPresenter;
    }

    /* access modifiers changed from: protected */
    public void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents(View rootView) {
        this.tv_old_price.getPaint().setFlags(16);
        this.vp_recommend.setPageIndicator(new int[]{R.drawable.shape_item_index_white, R.drawable.shape_item_index_red});
        this.vp_recommend.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        setCommentData();
        setRecommendGoods();
    }

    private void setCommentData() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.commonAdapter = new CommonAdapter<GoodsEvaluation>(getActivity(), new ArrayList(), R.layout.goods_eva_item) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder holder, GoodsEvaluation evaluation, int position) {
                holder.setText((int) R.id.tv_eva_username, evaluation.getUserName());
                holder.setText((int) R.id.tv_eva_time, DateUtil.getDate("yyyy-MM-dd", evaluation.getCtime().longValue()));
                holder.setText((int) R.id.tv_eva_content, evaluation.getEvaluation());
                holder.setRating(R.id.eva_quality, (float) evaluation.getQuality().intValue());
                if (!StringUtil.isNullOrEmpty(evaluation.getProps())) {
                    List<String> urls = JSON.parseArray(evaluation.getProps(), String.class);
                    GoodsInfoFragment.this.buildProductPreviewGallery((LinearLayout) holder.getView(R.id.eva_list_gallery_lyaout), urls, position);
                }
            }
        };
        this.recyclerView.setAdapter(this.commonAdapter);
        this.goodsPresenter.getGoodsEvaluationList(this.goodID, 0, 1, 1, new BasePresenter.ICallback<PageResult<GoodsEvaluation>>() {
            public void onResult(PageResult<GoodsEvaluation> result) {
                if (result.getTotal() > 0) {
                    GoodsInfoFragment.this.tv_comment_count.setText(GoodsInfoFragment.this.getString(R.string.GOODS_EVA_USER) + "(" + result.getTotal() + ")");
                    GoodsInfoFragment.this.commentPanel.setVisibility(0);
                    GoodsInfoFragment.this.emptyCommentPanel.setVisibility(8);
                    GoodsInfoFragment.this.commonAdapter.addItemList(result.getRows());
                    return;
                }
                GoodsInfoFragment.this.commentPanel.setVisibility(8);
                GoodsInfoFragment.this.emptyCommentPanel.setVisibility(0);
            }
        });
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
                    View imgEntryView = LayoutInflater.from(GoodsInfoFragment.this.getActivity()).inflate(R.layout.scale_photo_view, (ViewGroup) null);
                    final AlertDialog dialog = new AlertDialog.Builder(GoodsInfoFragment.this.getActivity()).create();
                    Glide.with(GoodsInfoFragment.this.getActivity()).load(url).placeholder((int) R.drawable.product_default).fitCenter().diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) imgEntryView.findViewById(R.id.large_image));
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

    private void setRecommendGoods() {
        this.goodsPresenter.getRecommendGoodsList(new BasePresenter.ICallback<List<Goods>>() {
            public void onResult(List<Goods> data) {
                boolean z = false;
                List<List<RecommendGoodsBean>> handledData = GoodsInfoFragment.this.handleRecommendGoods(data);
                GoodsInfoFragment.this.vp_recommend.setManualPageable(handledData.size() != 1);
                ConvenientBanner convenientBanner = GoodsInfoFragment.this.vp_recommend;
                if (handledData.size() != 1) {
                    z = true;
                }
                convenientBanner.setCanLoop(z);
                GoodsInfoFragment.this.vp_recommend.setPages(new CBViewHolderCreator() {
                    public Object createHolder() {
                        ItemRecommendAdapter adapter = new ItemRecommendAdapter();
                        adapter.setOnItemClickListener(new ItemRecommendAdapter.OnItemClickListener() {
                            public void onClick(RecommendGoodsBean goodsBean) {
                                ProductsActivity.startAction(GoodsInfoFragment.this.getActivity(), goodsBean.getGoodsId().intValue());
                            }
                        });
                        return adapter;
                    }
                }, handledData);
            }
        });
    }

    @OnClick({2131231254})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.ll_comment:
                this.activity.vp_content.setCurrentItem(2, true);
                return;
            default:
                return;
        }
    }

    public void setLoopView(List<String> imgUrls) {
        if (imgUrls.size() > 1) {
            this.vp_item_goods_img.setPageIndicator(new int[]{R.drawable.index_white, R.drawable.index_red});
            this.vp_item_goods_img.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        }
        this.vp_item_goods_img.setPages(new CBViewHolderCreator() {
            public Object createHolder() {
                return new NetworkImageHolderView();
            }
        }, imgUrls);
    }

    /* access modifiers changed from: private */
    public List<List<RecommendGoodsBean>> handleRecommendGoods(List<Goods> data) {
        List<List<RecommendGoodsBean>> handleData = new ArrayList<>();
        int length = data.size() % 2 != 0 ? (data.size() / 2) + 1 : data.size() / 2;
        for (int i = 0; i < length; i++) {
            List<RecommendGoodsBean> recommendGoods = new ArrayList<>();
            recommendGoods.add(createRecommendBean(data.get(i * 2)));
            if ((i * 2) + 1 < data.size()) {
                recommendGoods.add(createRecommendBean(data.get((i * 2) + 1)));
            }
            handleData.add(recommendGoods);
        }
        return handleData;
    }

    private RecommendGoodsBean createRecommendBean(Goods goods) {
        RecommendGoodsBean goodsBean = new RecommendGoodsBean();
        goodsBean.setImage(goods.getIcon());
        goodsBean.setTitle(goods.getName());
        goodsBean.setCurrentPrice(goods.getShopPrice());
        goodsBean.setPrice(goods.getMarketPrice());
        goodsBean.setGoodsId(Integer.valueOf(goods.getId()));
        return goodsBean;
    }

    public void setGoodsDetail(GoodsWithBLOBs goodsWithBLOBs) {
        String gallerys = goodsWithBLOBs.getGallerys();
        List<String> imgUrls = new ArrayList<>();
        if (!StringUtil.isNullOrEmpty(gallerys)) {
            for (String imgUrl : JSON.parseArray(gallerys, String.class)) {
                if (!StringUtil.isNullOrEmpty(imgUrl)) {
                    imgUrls.add(imgUrl);
                }
            }
        }
        setLoopView(imgUrls);
        String productName = goodsWithBLOBs.getName();
        String shopPrice = goodsWithBLOBs.getShopPrice();
        String marketPrice = goodsWithBLOBs.getMarketPrice();
        if (!TextUtils.isEmpty(productName)) {
            this.tv_goods_title.setText(productName);
        }
        if (!TextUtils.isEmpty(goodsWithBLOBs.getSummary())) {
            this.tv_goods_summary.setText(goodsWithBLOBs.getSummary());
        } else {
            this.tv_goods_summary.setVisibility(8);
        }
        if (!TextUtils.isEmpty(shopPrice)) {
            this.tv_new_price.setText("￥" + shopPrice);
        }
        if (!TextUtils.isEmpty(marketPrice)) {
            this.tv_old_price.setText("￥" + marketPrice);
        }
        List<GoodsEvaluationStar> starList = goodsWithBLOBs.getStarList();
        int goodCount = 0;
        int totalCount = 0;
        if (starList != null && starList.size() > 0) {
            for (GoodsEvaluationStar starData : starList) {
                if (starData.getStar() >= 4) {
                    goodCount += starData.getCount();
                }
                totalCount += starData.getCount();
            }
        }
        if (totalCount > 0) {
            this.tv_good_comment.setText(getString(R.string.GOODS_EVA_RATE) + ":" + (String.valueOf((int) (((((double) goodCount) * 1.0d) / ((double) totalCount)) * 100.0d)) + "%"));
        }
        if (goodsWithBLOBs.getSpecs().size() == 0) {
            this.ll_current_goods.setVisibility(8);
        }
    }
}
