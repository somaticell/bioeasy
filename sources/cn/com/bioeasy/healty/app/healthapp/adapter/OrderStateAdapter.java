package cn.com.bioeasy.healty.app.healthapp.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.constant.ActionConstant;
import cn.com.bioeasy.healty.app.healthapp.constant.OrderStatus;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.Order;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.OrderItem;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.bean.OrderButton;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.ArrayList;
import java.util.List;

public class OrderStateAdapter extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener {
    private Context mContext;
    /* access modifiers changed from: private */
    public Handler mHandler;
    /* access modifiers changed from: private */
    public List<Order> mOrders;
    public View.OnClickListener orderButtonClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            int position = Integer.valueOf(v.getTag().toString()).intValue();
            if (OrderStateAdapter.this.mHandler != null) {
                Message msg = OrderStateAdapter.this.mHandler.obtainMessage(11);
                msg.obj = (Order) OrderStateAdapter.this.mOrders.get(position);
                OrderStateAdapter.this.mHandler.sendMessage(msg);
            }
        }
    };

    public OrderStateAdapter(Context context, Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
    }

    public void addOrders(List<Order> orderList) {
        this.mOrders = orderList;
        notifyDataSetChanged();
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.order_list_item, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        Order order = this.mOrders.get(position);
        String orderAmout = order.getTotalPrice();
        List<OrderItem> orderItems = JSONArray.parseArray(JSON.parseObject(order.getProps()).getJSONArray("items").toJSONString(), OrderItem.class);
        int count = orderItems != null ? orderItems.size() : 0;
        if (orderItems == null || orderItems.size() <= 1) {
            if (orderItems != null && orderItems.size() == 1) {
                OrderItem product = orderItems.get(0);
                holder.orderProductNameTxtv.setText(product.getGoods().getName());
                holder.orderProductSpec.setText(product.getGoods().getSummary());
                holder.orderProductPrice.setText(product.getGoods().getShopPrice());
                Glide.with(this.mContext).load(product.getGoods().getIcon()).placeholder((int) R.drawable.product_default).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.orderPicImgv);
            }
            holder.orderProductScrollv.setVisibility(8);
            holder.orderProductRlayout.setVisibility(0);
        } else {
            buildProductPreviewGallery(holder.productListGalleryLyaout, orderItems, position);
            holder.orderProductScrollv.setVisibility(0);
            holder.orderProductRlayout.setVisibility(8);
        }
        holder.productListGalleryLyaout.setOnClickListener(this.orderButtonClickListener);
        holder.orderProductRlayout.setOnClickListener(this.orderButtonClickListener);
        holder.orderProductRlayout.setTag(Integer.valueOf(position));
        holder.orderButtonScrollv.setOnClickListener(this.orderButtonClickListener);
        holder.orderButtonScrollv.setTag(Integer.valueOf(position));
        holder.orderProductScrollv.setOnClickListener(this.orderButtonClickListener);
        holder.orderProductScrollv.setTag(Integer.valueOf(position));
        buildProductButtonGallery(holder.orderButtonGalleryLyaout, getOrderButtonByOrder(order), position);
        holder.orderProductDetailTxtv.setText("共" + count + "件商品 实付款:¥" + orderAmout);
        holder.orderSnText.setText("订单号：" + String.valueOf(order.getId()));
        holder.orderStatusText.setText(OrderStatus.ORDER_NAMES[order.getStatus().byteValue()]);
    }

    private void buildProductPreviewGallery(LinearLayout gallery, List<OrderItem> products, int position) {
        gallery.removeAllViews();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getGoods() != null) {
                String url = products.get(i).getGoods().getIcon();
                View view = LayoutInflater.from(this.mContext).inflate(R.layout.activity_index_gallery_item, gallery, false);
                view.setOnClickListener(this.orderButtonClickListener);
                view.setTag(Integer.valueOf(position));
                Glide.with(this.mContext).load(url).placeholder((int) R.drawable.product_default).diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) view.findViewById(R.id.id_index_gallery_item_image));
                gallery.addView(view);
            }
        }
    }

    private void buildProductButtonGallery(LinearLayout gallery, List<OrderButton> buttonModels, int position) {
        gallery.removeAllViews();
        if (buttonModels == null || buttonModels.size() <= 0) {
            View view = LayoutInflater.from(this.mContext).inflate(R.layout.order_button_gallery_item, gallery, false);
            Button button = (Button) view.findViewById(R.id.id_index_gallery_item_button);
            button.setText("再次购买");
            button.setFocusable(false);
            button.setTag(R.id.key_tag_index1, Integer.valueOf(ActionConstant.tagBuyAgain));
            button.setTag(R.id.key_tag_index2, Integer.valueOf(position));
            button.setBackgroundResource(R.drawable.order_btn_normal);
            button.setOnClickListener(this);
            gallery.addView(view);
            return;
        }
        for (int i = 0; i < buttonModels.size(); i++) {
            OrderButton buttonModel = buttonModels.get(i);
            View view2 = LayoutInflater.from(this.mContext).inflate(R.layout.order_button_gallery_item, gallery, false);
            Button button2 = (Button) view2.findViewById(R.id.id_index_gallery_item_button);
            button2.setText(buttonModel.getText());
            button2.setFocusable(false);
            button2.setTag(R.id.key_tag_index1, Integer.valueOf(buttonModel.getTag()));
            button2.setTag(R.id.key_tag_index2, Integer.valueOf(position));
            if (buttonModel.isLight()) {
                button2.setBackgroundResource(R.drawable.tag_button_bg_checked);
            } else {
                button2.setBackgroundResource(R.drawable.order_btn_normal);
            }
            button2.setOnClickListener(this);
            gallery.addView(view2);
        }
    }

    public List<OrderButton> getOrderButtonByOrder(Order order) {
        List<OrderButton> buttons = new ArrayList<>();
        if (order.getStatus().byteValue() == 0) {
            buttons.add(new OrderButton("支付", ActionConstant.tagPay, true));
        }
        if (order.getStatus().byteValue() == 0) {
            buttons.add(new OrderButton("取消订单", 666, false));
        }
        if (order.getStatus().byteValue() == 2) {
            buttons.add(new OrderButton("确认收货", ActionConstant.tagReceive, true));
        }
        if (order.getStatus().byteValue() == 2) {
            buttons.add(new OrderButton("查看物流", ActionConstant.tagShopping, false));
        }
        if (order.getStatus().byteValue() == 3) {
            buttons.add(new OrderButton("评价", ActionConstant.tagComment, false));
        }
        if (order.getStatus().byteValue() == 3) {
            buttons.add(new OrderButton("再次购买", ActionConstant.tagBuyAgain, false));
        }
        return buttons;
    }

    public void onClick(View v) {
        int tag = Integer.valueOf(v.getTag(R.id.key_tag_index1).toString()).intValue();
        int position = Integer.valueOf(v.getTag(R.id.key_tag_index2).toString()).intValue();
        if (this.mHandler != null) {
            Message msg = this.mHandler.obtainMessage(10);
            msg.obj = this.mOrders.get(position);
            msg.what = tag;
            this.mHandler.sendMessage(msg);
        }
    }

    public int getItemCount() {
        if (this.mOrders != null) {
            return this.mOrders.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout orderButtonGalleryLyaout;
        HorizontalScrollView orderButtonScrollv;
        ImageView orderPicImgv;
        TextView orderProductDetailTxtv;
        TextView orderProductNameTxtv;
        TextView orderProductPrice;
        RelativeLayout orderProductRlayout;
        HorizontalScrollView orderProductScrollv;
        TextView orderProductSpec;
        TextView orderSnText;
        TextView orderStatusText;
        LinearLayout productListGalleryLyaout;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.orderSnText = (TextView) itemView.findViewById(R.id.tv_order_sn);
            this.orderStatusText = (TextView) itemView.findViewById(R.id.tv_order_status);
            this.orderProductScrollv = (HorizontalScrollView) itemView.findViewById(R.id.order_product_scrollv);
            this.productListGalleryLyaout = (LinearLayout) itemView.findViewById(R.id.product_list_gallery_lyaout);
            this.orderButtonScrollv = (HorizontalScrollView) itemView.findViewById(R.id.order_button_scrollv);
            this.orderButtonGalleryLyaout = (LinearLayout) itemView.findViewById(R.id.order_button_gallery_lyaout);
            this.orderProductRlayout = (RelativeLayout) itemView.findViewById(R.id.order_product_rlayout);
            this.orderPicImgv = (ImageView) itemView.findViewById(R.id.order_pic_imgv);
            this.orderProductNameTxtv = (TextView) itemView.findViewById(R.id.order_product_name_txtv);
            this.orderProductSpec = (TextView) itemView.findViewById(R.id.product_spec_txtv);
            this.orderProductPrice = (TextView) itemView.findViewById(R.id.product_price_txtv);
            this.orderProductDetailTxtv = (TextView) itemView.findViewById(R.id.order_product_detail_txtv);
        }
    }
}
