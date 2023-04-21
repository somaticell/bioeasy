package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
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
import cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.cache.CacheManager;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.OrderPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.AddressBean;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.Order;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.OrderItem;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.ShopCardItem;
import cn.com.bioeasy.healty.app.healthapp.modules.user.view.LoginActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kyleduo.switchbutton.SwitchButton;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.litepal.crud.DataSupport;

public class OrderActivity extends BIBaseActivity implements OrderView {
    private static final String TOTAL_PRICE = "totalGoodPrice";
    private int addressId;
    @Bind({2131231111})
    LinearLayout buttonLayout;
    @Inject
    CacheManager cacheManager;
    @Bind({2131231121})
    TextView invoiceTitle;
    @Bind({2131231120})
    RelativeLayout mRlTaitouWrap;
    @Bind({2131231119})
    SwitchButton mSbFapiao;
    @Inject
    OrderPresenter orderPresenter;
    @Bind({2131231118})
    RecyclerView recyclerView;
    @Bind({2131231116})
    RelativeLayout rlExitDefaultAddress;
    private List<ShopCardItem> shopCardItems;
    public double totalGoodPrice;
    @Bind({2131231070})
    TextView tvAddress;
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
        activityComponent.inject(this);
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
        this.tvTitle.setText(getResources().getString(R.string.shopping_order_title2));
        this.buttonLayout.setVisibility(8);
        initOrderAddress();
        initOrderGoodsInfo();
    }

    private void initOrderGoodsInfo() {
        this.shopCardItems = (List) getIntent().getSerializableExtra("listSelectedProductList");
        if (this.shopCardItems != null) {
            this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            this.recyclerView.setAdapter(new CommonAdapter<ShopCardItem>(this, this.shopCardItems, R.layout.goods_show_list_item) {
                /* access modifiers changed from: protected */
                public void convert(ViewHolder holder, final ShopCardItem shopCardItem, int position) {
                    holder.setText((int) R.id.product_name_txtv, shopCardItem.getGoods().getName());
                    holder.setText((int) R.id.product_spec_txtv, shopCardItem.getGoodSpecKeyNames());
                    holder.setText((int) R.id.product_price_txtv, "¥" + (StringUtil.isNullOrEmpty(shopCardItem.getPrice()) ? "0.0" : shopCardItem.getPrice()));
                    holder.setText((int) R.id.product_count_txtv, "X" + String.valueOf(shopCardItem.getAmount()));
                    Glide.with((FragmentActivity) OrderActivity.this).load(shopCardItem.getGoods().getIcon()).placeholder((int) R.drawable.product_default).diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) holder.getView(R.id.product_pic_imgv));
                    holder.setOnClickListener(R.id.goods_order_item, new View.OnClickListener() {
                        public void onClick(View v) {
                            ProductsActivity.startAction(OrderActivity.this, shopCardItem.getGoodId());
                        }
                    });
                }
            });
            this.totalGoodPrice = getIntent().getDoubleExtra(TOTAL_PRICE, 0.0d);
            this.tvGoodTotalPrice.setText(String.format(getResources().getString(R.string.product_price), new Object[]{String.valueOf(this.totalGoodPrice)}));
            String totalFeeFmt = getResources().getString(R.string.shopping_payment) + this.totalGoodPrice;
            SpannableString totalFeeSpanStr = new SpannableString(totalFeeFmt);
            totalFeeSpanStr.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.light_red)), 4, totalFeeFmt.length(), 33);
            this.tvRealTotalPrice.setText(totalFeeSpanStr);
        }
    }

    @OnClick({2131231112, 2131231050, 2131231110, 2131231119})
    public void onClick(View view) {
        int i = 0;
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                return;
            case R.id.rl_submit_order:
                submitOrder();
                return;
            case R.id.rl_go_address:
                startActivityForResult(new Intent(this, AddressActivity.class), 0);
                return;
            case R.id.sbt_fapiao:
                RelativeLayout relativeLayout = this.mRlTaitouWrap;
                if (!this.mSbFapiao.isChecked()) {
                    i = 8;
                }
                relativeLayout.setVisibility(i);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 1:
                AddressBean addressBean = (AddressBean) data.getSerializableExtra("ORDER_ADDRESS");
                if (addressBean != null) {
                    this.addressId = addressBean.getId();
                    this.cacheManager.saveObject("ORDER_ADDRESS", addressBean);
                    initOrderAddress();
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void submitOrder() {
        if (this.addressId == 0) {
            showMessage((int) R.string.ORDER_EXPRESS_ADDRESS);
        } else if (HealthApp.x.getUserInfo() == null) {
            switchPage(LoginActivity.class);
        } else {
            Order order = new Order();
            order.setUserId(Integer.valueOf(HealthApp.x.getUserInfo().getUserId()));
            order.setCtime(new Date().getTime());
            order.setAddressId(Integer.valueOf(this.addressId));
            order.setRemark(this.tvRemark.getText().toString());
            if (this.mSbFapiao.isChecked()) {
                order.setInvoiceType(1);
                order.setInvoiceTitle(this.invoiceTitle.getText().toString());
            } else {
                order.setInvoiceType(0);
            }
            order.setTotalPrice(String.valueOf(this.totalGoodPrice));
            List<OrderItem> orderItems = new ArrayList<>();
            for (ShopCardItem cardItem : this.shopCardItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setAmount(Integer.valueOf(cardItem.getAmount()));
                orderItem.setGoodsId(Integer.valueOf(cardItem.getGoodId()));
                orderItem.setCtime(Long.valueOf(new Date().getTime()));
                orderItem.setTotalPrice(cardItem.getPrice());
                orderItem.setGoodsSpecId(Integer.valueOf(cardItem.getGoodsSpecId()));
                orderItems.add(orderItem);
            }
            order.setItems(orderItems);
            this.orderPresenter.addOrder(order);
        }
    }

    private void initOrderAddress() {
        AddressBean addressBean = (AddressBean) this.cacheManager.getObject("ORDER_ADDRESS", AddressBean.class);
        if (addressBean != null) {
            this.addressId = addressBean.getId();
            this.tvNoAddressTitle.setVisibility(8);
            this.rlExitDefaultAddress.setVisibility(0);
            this.tvName.setText(addressBean.getConsignee());
            this.tvPhone.setText(addressBean.getMobile());
            this.tvAddress.setText(addressBean.getProvince() + addressBean.getCity() + addressBean.getDistrict() + addressBean.getAddress());
        }
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

    public static void startAction(Activity activity, List<ShopCardItem> listSelectedProductList, double totalGoodPrice2) {
        Intent intent = new Intent(activity, OrderActivity.class);
        intent.putExtra("listSelectedProductList", (Serializable) listSelectedProductList);
        intent.putExtra(TOTAL_PRICE, totalGoodPrice2);
        activity.startActivity(intent);
    }

    public void updateOrderStatus(Order order, int status) {
        for (int i = 0; i < this.shopCardItems.size(); i++) {
            DataSupport.delete(ShopCardItem.class, (long) this.shopCardItems.get(i).getId());
        }
        PayActivity.startAction(this, order);
    }

    public void updateOrderList(PageResult<Order> pageResult, boolean refresh) {
    }
}
