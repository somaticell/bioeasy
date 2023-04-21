package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.app.widgets.dialog.DialogManager;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.constant.ActionConstant;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.OrderPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.AddressBean;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.Order;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.OrderItem;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.ShopCardItem;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.bean.OrderButton;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kyleduo.switchbutton.SwitchButton;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class OrderDetailActivity extends BIBaseActivity implements OrderView {
    private static final String ORDER_INFO = "ORDER_INFO";
    @Bind({2131231111})
    LinearLayout buttonLayout;
    @Bind({2131231121})
    TextView invoiceTitle;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Order order = (Order) msg.obj;
            switch (msg.what) {
                case 666:
                    OrderDetailActivity.this.cancelOrder(order);
                    return;
                case ActionConstant.tagPay:
                    OrderDetailActivity.this.gotoPay(order);
                    return;
                case ActionConstant.tagReceive:
                    OrderDetailActivity.this.confirmReceive(order);
                    return;
                case ActionConstant.tagComment:
                    OrderDetailActivity.this.commentOrder(order);
                    return;
                case ActionConstant.tagBuyAgain:
                    OrderDetailActivity.this.againBuy(order);
                    return;
                default:
                    return;
            }
        }
    };
    @Bind({2131231120})
    RelativeLayout mRlTaitouWrap;
    @Bind({2131231119})
    SwitchButton mSbFapiao;
    /* access modifiers changed from: private */
    public Order order;
    private List<OrderItem> orderItems;
    @Inject
    OrderPresenter orderPresenter;
    @Bind({2131231118})
    RecyclerView recyclerView;
    @Bind({2131231116})
    RelativeLayout rlExitDefaultAddress;
    @Bind({2131231115})
    ImageView selectAddressArrow;
    @Bind({2131231108})
    LinearLayout submitOrderLayout;
    @Bind({2131231070})
    TextView tvAddress;
    @Bind({2131231123})
    TextView tvFarePrice;
    @Bind({2131231122})
    TextView tvGoodTotalPrice;
    @Bind({2131231117})
    TextView tvName;
    @Bind({2131231114})
    TextView tvNoAddressTitle;
    @Bind({2131230896})
    TextView tvPhone;
    @Bind({2131231109})
    TextView tvRealTotalPrice;
    @Bind({2131231071})
    EditText tvRemark;
    @Bind({2131231008})
    TextView tvTitle;

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject((BIBaseActivity) this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return this.orderPresenter;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_shop_order;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.tvTitle.setText(getString(R.string.ORDER_DETAIL));
        this.order = (Order) getIntent().getSerializableExtra("ORDER_INFO");
        initOrderAddress();
        initOrderGoodsInfo();
    }

    private void initOrderGoodsInfo() {
        this.orderItems = JSONArray.parseArray(JSON.parseObject(this.order.getProps()).getJSONArray("items").toJSONString(), OrderItem.class);
        if (this.orderItems != null) {
            this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            this.recyclerView.setAdapter(new CommonAdapter<OrderItem>(this, this.orderItems, R.layout.goods_show_list_item) {
                /* access modifiers changed from: protected */
                public void convert(ViewHolder holder, final OrderItem shopCardItem, int position) {
                    holder.setText((int) R.id.product_name_txtv, shopCardItem.getGoods().getName());
                    holder.setText((int) R.id.product_spec_txtv, shopCardItem.getGetGoodsSpecName());
                    holder.setText((int) R.id.product_price_txtv, "¥" + (StringUtil.isNullOrEmpty(shopCardItem.getTotalPrice()) ? "0.0" : shopCardItem.getTotalPrice()));
                    holder.setText((int) R.id.product_count_txtv, "X" + String.valueOf(shopCardItem.getAmount()));
                    Glide.with((FragmentActivity) OrderDetailActivity.this).load(shopCardItem.getGoods().getIcon()).placeholder((int) R.drawable.product_default).diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) holder.getView(R.id.product_pic_imgv));
                    holder.setOnClickListener(R.id.goods_order_item, new View.OnClickListener() {
                        public void onClick(View v) {
                            ProductsActivity.startAction(OrderDetailActivity.this, shopCardItem.getGoodsId().intValue());
                        }
                    });
                }
            });
            this.tvGoodTotalPrice.setText(String.format(getResources().getString(R.string.product_price), new Object[]{String.valueOf(this.order.getTotalPrice())}));
            String totalFeeFmt = getResources().getString(R.string.shopping_payment) + this.order.getTotalPrice();
            SpannableString totalFeeSpanStr = new SpannableString(totalFeeFmt);
            totalFeeSpanStr.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.light_red)), 4, totalFeeFmt.length(), 33);
            this.tvRealTotalPrice.setText(totalFeeSpanStr);
            if (this.order.getInvoiceType() == null || this.order.getInvoiceType().intValue() != 1) {
                this.mRlTaitouWrap.setVisibility(8);
            } else {
                this.mRlTaitouWrap.setVisibility(0);
                this.invoiceTitle.setText(this.order.getInvoiceTitle());
            }
            this.tvRemark.setText(this.order.getRemark());
            this.tvRemark.setEnabled(false);
            this.mSbFapiao.setVisibility(8);
            this.submitOrderLayout.setVisibility(8);
            buildProductButtonGallery(this.buttonLayout, getOrderButtonByOrder(this.order));
        }
    }

    @OnClick({2131231050})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                return;
            default:
                return;
        }
    }

    private void initOrderAddress() {
        AddressBean addressBean = (AddressBean) JSON.parseObject(JSON.parseObject(this.order.getProps()).getJSONObject(ActionConstant.ORDER_ADDRESS).toJSONString(), AddressBean.class);
        if (addressBean != null) {
            this.tvName.setText(addressBean.getConsignee());
            this.tvPhone.setText(addressBean.getMobile());
            this.tvAddress.setText(addressBean.getProvince() + addressBean.getCity() + addressBean.getDistrict() + addressBean.getAddress());
        }
        this.tvNoAddressTitle.setVisibility(8);
        this.selectAddressArrow.setVisibility(8);
        this.rlExitDefaultAddress.setVisibility(0);
    }

    /* access modifiers changed from: protected */
    public void onPostResume() {
        super.onPostResume();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        finish();
        return true;
    }

    public static void startAction(Activity activity, Order order2) {
        Intent intent = new Intent(activity, OrderDetailActivity.class);
        intent.putExtra("ORDER_INFO", order2);
        activity.startActivity(intent);
    }

    public void updateOrderStatus(Order order2, int status) {
        PayActivity.startAction(this, order2);
    }

    public void updateOrderList(PageResult<Order> pageResult, boolean refresh) {
    }

    private void buildProductButtonGallery(LinearLayout gallery, List<OrderButton> buttonModels) {
        gallery.removeAllViews();
        if (buttonModels == null || buttonModels.size() <= 0) {
            View view = LayoutInflater.from(this.mContext).inflate(R.layout.order_button_gallery_item, gallery, false);
            Button button = (Button) view.findViewById(R.id.id_index_gallery_item_button);
            button.setText(getString(R.string.ORDER_AGAIN));
            button.setFocusable(false);
            button.setTag(R.id.key_tag_index1, Integer.valueOf(ActionConstant.tagBuyAgain));
            button.setBackgroundResource(R.drawable.tag_button_bg_unchecked);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (OrderDetailActivity.this.mHandler != null) {
                        int tag = Integer.valueOf(v.getTag(R.id.key_tag_index1).toString()).intValue();
                        Message msg = OrderDetailActivity.this.mHandler.obtainMessage(11);
                        msg.obj = OrderDetailActivity.this.order;
                        msg.what = tag;
                        OrderDetailActivity.this.mHandler.sendMessage(msg);
                    }
                }
            });
            gallery.addView(view);
            return;
        }
        for (int i = 0; i < buttonModels.size(); i++) {
            OrderButton buttonModel = buttonModels.get(i);
            View view2 = LayoutInflater.from(this.mContext).inflate(R.layout.order_button_gallery_item, gallery, false);
            Button button2 = (Button) view2.findViewById(R.id.id_index_gallery_item_button);
            button2.setText(buttonModel.getText());
            button2.setTag(R.id.key_tag_index1, Integer.valueOf(buttonModel.getTag()));
            button2.setFocusable(false);
            if (buttonModel.isLight()) {
                button2.setBackgroundResource(R.drawable.tag_button_bg_checked);
            } else {
                button2.setBackgroundResource(R.drawable.tag_button_bg_unchecked);
            }
            button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (OrderDetailActivity.this.mHandler != null) {
                        int tag = Integer.valueOf(v.getTag(R.id.key_tag_index1).toString()).intValue();
                        Message msg = OrderDetailActivity.this.mHandler.obtainMessage(11);
                        msg.obj = OrderDetailActivity.this.order;
                        msg.what = tag;
                        OrderDetailActivity.this.mHandler.sendMessage(msg);
                    }
                }
            });
            gallery.addView(view2);
        }
    }

    public List<OrderButton> getOrderButtonByOrder(Order order2) {
        List<OrderButton> buttons = new ArrayList<>();
        if (order2.getStatus().byteValue() == 0) {
            buttons.add(new OrderButton(getString(R.string.ORDER_BUY), ActionConstant.tagPay, true));
        }
        if (order2.getStatus().byteValue() == 0) {
            buttons.add(new OrderButton(getString(R.string.ORDER_CANCEL), 666, false));
        }
        if (order2.getStatus().byteValue() == 2) {
            buttons.add(new OrderButton(getString(R.string.ORDER_CONFIRM), ActionConstant.tagReceive, true));
        }
        if (order2.getStatus().byteValue() == 2) {
            buttons.add(new OrderButton(getString(R.string.ORDER_VIEW_EXPRESS), ActionConstant.tagShopping, true));
        }
        if (order2.getStatus().byteValue() == 3) {
            buttons.add(new OrderButton(getString(R.string.ORDER_BUTTON_EVA), ActionConstant.tagComment, true));
        }
        if (order2.getStatus().byteValue() == 3) {
            buttons.add(new OrderButton(getString(R.string.ORDER_AGAIN), ActionConstant.tagBuyAgain, true));
        }
        return buttons;
    }

    /* access modifiers changed from: private */
    public void commentOrder(Order order2) {
        Intent intent = new Intent(this, OrderEvaluationActivity.class);
        intent.putExtra("ORDER_INFO", order2);
        startActivity(intent);
    }

    public void confirmReceive(Order order2) {
        this.orderPresenter.confirmOrder(order2);
    }

    /* access modifiers changed from: private */
    public void againBuy(Order order2) {
        List<OrderItem> orderItems2 = JSONArray.parseArray(JSON.parseObject(order2.getProps()).getJSONArray("items").toJSONString(), OrderItem.class);
        if (orderItems2 != null) {
            List<ShopCardItem> items = new ArrayList<>();
            for (int i = 0; i < orderItems2.size(); i++) {
                OrderItem item = orderItems2.get(i);
                ShopCardItem cardItem = new ShopCardItem();
                cardItem.setAmount(item.getAmount().intValue());
                cardItem.setGoods(item.getGoods());
                cardItem.setGoodId(item.getGoodsId().intValue());
                cardItem.setGoodSpecKeyNames(item.getGetGoodsSpecName());
                items.add(cardItem);
            }
            OrderActivity.startAction(this, items, Double.parseDouble(order2.getTotalPrice()));
        }
    }

    /* access modifiers changed from: private */
    public void gotoPay(Order order2) {
        PayActivity.startAction(this, order2);
    }

    public void cancelOrder(Order order2) {
        showWarnMessage(getString(R.string.ORDER_TIP_TITLE), getString(R.string.ORDER_TIP_CANCEL), new DialogManager.ConfirmDialogListener() {
            public void clickOk(int actionType) {
            }
        }, 666);
    }
}
