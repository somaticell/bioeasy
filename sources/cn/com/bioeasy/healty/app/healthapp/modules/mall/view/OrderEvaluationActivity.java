package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.constant.IntentActions;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.OrderPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.GoodsEvaluation;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.Order;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.OrderEvaluation;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.OrderItem;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.widget.TweetPicturesPreviewer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import net.oschina.common.widget.RichEditText;

public class OrderEvaluationActivity extends BIBaseActivity implements OrderView {
    @Bind({2131231035})
    Button btOrderEva;
    private CommonAdapter<OrderItem> commonAdapter;
    /* access modifiers changed from: private */
    public Order order;
    private List<OrderItem> orderItems;
    @Inject
    OrderPresenter orderPresenter;
    @Bind({2131231034})
    RatingBar ratingBar;
    @Bind({2131231033})
    RecyclerView recyclerView;
    @Bind({2131231008})
    TextView tvTitle;

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return this.orderPresenter;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_order_evaluation;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.tvTitle.setText(getString(R.string.ORDER_EVA));
        this.order = (Order) getIntent().getSerializableExtra(IntentActions.ORDER_INFO);
        this.orderItems = JSONArray.parseArray(JSON.parseObject(this.order.getProps()).getJSONArray("items").toJSONString(), OrderItem.class);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.commonAdapter = new CommonAdapter<OrderItem>(this, this.orderItems, R.layout.activity_goods_evaluation) {
            /* access modifiers changed from: protected */
            public void convert(final ViewHolder holder, final OrderItem orderItem, int position) {
                final TweetPicturesPreviewer mLayImages = (TweetPicturesPreviewer) holder.getView(R.id.recycler_images);
                holder.setText((int) R.id.product_name_txtv, orderItem.getGoods().getName());
                holder.setText((int) R.id.product_spec_txtv, orderItem.getGetGoodsSpecName());
                holder.setText((int) R.id.product_price_txtv, "¥" + (StringUtil.isNullOrEmpty(orderItem.getTotalPrice()) ? "0.0" : orderItem.getTotalPrice()));
                holder.setText((int) R.id.product_count_txtv, "X" + String.valueOf(orderItem.getAmount()));
                Glide.with((FragmentActivity) OrderEvaluationActivity.this).load(orderItem.getGoods().getIcon()).placeholder((int) R.drawable.product_default).diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) holder.getView(R.id.product_pic_imgv));
                holder.setOnClickListener(R.id.show_eva_btn, new View.OnClickListener() {
                    public void onClick(View v) {
                        boolean visible;
                        boolean z = true;
                        if (holder.getView(R.id.eva_panel).getVisibility() == 0) {
                            visible = true;
                        } else {
                            visible = false;
                        }
                        ViewHolder viewHolder = holder;
                        if (visible) {
                            z = false;
                        }
                        viewHolder.setVisible(R.id.eva_panel, z);
                    }
                });
                holder.setOnClickListener(R.id.bt_goods_eva, new View.OnClickListener() {
                    public void onClick(View v) {
                        final GoodsEvaluation evaluation = new GoodsEvaluation();
                        evaluation.setOrderId(OrderEvaluationActivity.this.order.getId());
                        evaluation.setUserId(Integer.valueOf(HealthApp.x.getUserId()));
                        evaluation.setGoodsId(orderItem.getGoodsId());
                        evaluation.setUserName(HealthApp.x.getUserName());
                        evaluation.setCtime(Long.valueOf(new Date().getTime()));
                        evaluation.setEvaluation(((RichEditText) holder.getView(R.id.tv_evaluation)).getText().toString());
                        evaluation.setQuality(Integer.valueOf((int) Math.ceil((double) ((RatingBar) holder.getView(R.id.eva_quality)).getRating())));
                        OrderEvaluationActivity.this.orderPresenter.addGoodsEvaluation(evaluation, mLayImages.getPaths(), new BasePresenter.ICallback<GoodsEvaluation>() {
                            public void onResult(GoodsEvaluation result) {
                                holder.setVisible(R.id.eva_panel, false);
                                holder.setText((int) R.id.show_eva_btn, OrderEvaluationActivity.this.getString(R.string.ORDER_VIEW_EVA));
                                holder.setVisible(R.id.tv_evaluation, false);
                                holder.setVisible(R.id.tv_evaluation_show, true);
                                holder.setText((int) R.id.tv_evaluation_show, evaluation.getEvaluation());
                                holder.setVisible(R.id.bt_goods_eva, false);
                            }
                        });
                    }
                });
                holder.setOnClickListener(R.id.iv_picture, new View.OnClickListener() {
                    public void onClick(View v) {
                        mLayImages.onLoadMoreClick();
                    }
                });
                holder.setOnClickListener(R.id.goods_order_item, new View.OnClickListener() {
                    public void onClick(View v) {
                        ProductsActivity.startAction(OrderEvaluationActivity.this, orderItem.getGoodsId().intValue());
                    }
                });
            }
        };
        this.recyclerView.setAdapter(this.commonAdapter);
    }

    @OnClick({2131231050, 2131231035})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_order_eva:
                evaOrder();
                return;
            case R.id.iv_back:
                finish();
                return;
            default:
                return;
        }
    }

    private void evaOrder() {
        OrderEvaluation evaluation = new OrderEvaluation();
        evaluation.setOrderId(this.order.getId());
        evaluation.setCtime(Long.valueOf(new Date().getTime()));
        evaluation.setUserId(Integer.valueOf(HealthApp.x.getUserId()));
        evaluation.setQuality(Integer.valueOf((int) Math.ceil((double) this.ratingBar.getRating())));
        this.orderPresenter.addOrderEvaluation(evaluation, new BasePresenter.ICallback<OrderEvaluation>() {
            public void onResult(OrderEvaluation result) {
                OrderEvaluationActivity.this.btOrderEva.setVisibility(8);
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        finish();
        return true;
    }

    public void updateOrderStatus(Order order2, int status) {
    }

    public void updateOrderList(PageResult<Order> pageResult, boolean refresh) {
    }
}
