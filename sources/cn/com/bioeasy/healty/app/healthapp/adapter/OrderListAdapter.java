package cn.com.bioeasy.healty.app.healthapp.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.constant.ActionConstant;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.Order;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.OrderItem;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.bean.OrderButton;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends BaseAdapter implements View.OnClickListener {
    private Context mContext;
    /* access modifiers changed from: private */
    public Handler mHandler;
    /* access modifiers changed from: private */
    public List<Order> mOrders;
    public View.OnClickListener orderButtonClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            int position = Integer.valueOf(v.getTag().toString()).intValue();
            if (OrderListAdapter.this.mHandler != null) {
                Message msg = OrderListAdapter.this.mHandler.obtainMessage(11);
                msg.obj = (Order) OrderListAdapter.this.mOrders.get(position);
                OrderListAdapter.this.mHandler.sendMessage(msg);
            }
        }
    };
    private int selectIndex;

    public OrderListAdapter(Context context, Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
    }

    public void setData(List<Order> orders) {
        if (orders != null) {
            this.mOrders = orders;
            notifyDataSetChanged();
        }
    }

    public int getCount() {
        if (this.mOrders == null) {
            return 0;
        }
        return this.mOrders.size();
    }

    public Object getItem(int position) {
        if (this.mOrders == null) {
            return null;
        }
        return this.mOrders.get(position);
    }

    public long getItemId(int position) {
        if (this.mOrders == null) {
            return -1;
        }
        return this.mOrders.get(position).getId().longValue();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.order_list_item, parent, false);
            holder = new ViewHolder();
            holder.orderProductScrollv = (HorizontalScrollView) convertView.findViewById(R.id.order_product_scrollv);
            holder.productListGalleryLyaout = (LinearLayout) convertView.findViewById(R.id.product_list_gallery_lyaout);
            holder.orderButtonScrollv = (HorizontalScrollView) convertView.findViewById(R.id.order_button_scrollv);
            holder.orderButtonGalleryLyaout = (LinearLayout) convertView.findViewById(R.id.order_button_gallery_lyaout);
            holder.orderProductRlayout = (RelativeLayout) convertView.findViewById(R.id.order_product_rlayout);
            holder.orderPicImgv = (ImageView) convertView.findViewById(R.id.order_pic_imgv);
            holder.orderProductNameTxtv = (TextView) convertView.findViewById(R.id.order_product_name_txtv);
            holder.orderProductDetailTxtv = (TextView) convertView.findViewById(R.id.order_product_detail_txtv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Order order = this.mOrders.get(position);
        String orderAmout = order.getTotalPrice();
        int count = 0;
        if (order.getItems() == null || order.getItems().size() <= 1) {
            if (order.getItems() != null && order.getItems().size() == 1) {
                OrderItem product = order.getItems().get(0);
                holder.orderProductNameTxtv.setText(product.getGoods().getName());
                Glide.with(this.mContext).load(product.getGoods().getIcon()).placeholder((int) R.drawable.product_default).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.orderPicImgv);
                count = 1;
            }
            holder.orderProductScrollv.setVisibility(8);
            holder.orderProductRlayout.setVisibility(0);
        } else {
            buildProductPreviewGallery(holder.productListGalleryLyaout, order.getItems(), position);
            count = order.getItems().size();
            holder.orderProductScrollv.setVisibility(0);
            holder.orderProductRlayout.setVisibility(8);
        }
        holder.orderProductRlayout.setOnClickListener(this.orderButtonClickListener);
        holder.orderProductRlayout.setTag(Integer.valueOf(position));
        holder.orderButtonScrollv.setOnClickListener(this.orderButtonClickListener);
        holder.orderButtonScrollv.setTag(Integer.valueOf(position));
        holder.orderProductScrollv.setOnClickListener(this.orderButtonClickListener);
        holder.orderProductScrollv.setTag(Integer.valueOf(position));
        buildProductButtonGallery(holder.orderButtonGalleryLyaout, getOrderButtonByOrder(order), position);
        holder.orderProductDetailTxtv.setText("共" + count + "件商品 实付款:¥" + orderAmout);
        return convertView;
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

    class ViewHolder {
        LinearLayout orderButtonGalleryLyaout;
        LinearLayout orderButtonLayout;
        HorizontalScrollView orderButtonScrollv;
        ImageView orderPicImgv;
        TextView orderProductDetailTxtv;
        TextView orderProductNameTxtv;
        RelativeLayout orderProductRlayout;
        HorizontalScrollView orderProductScrollv;
        LinearLayout productListGalleryLyaout;

        ViewHolder() {
        }
    }

    private void buildProductPreviewGallery(LinearLayout gallery, List<OrderItem> products, int position) {
        gallery.removeAllViews();
        for (int i = 0; i < products.size(); i++) {
            String url = products.get(i).getGoods().getIcon();
            View view = LayoutInflater.from(this.mContext).inflate(R.layout.activity_index_gallery_item, gallery, false);
            view.setOnClickListener(this.orderButtonClickListener);
            view.setTag(Integer.valueOf(position));
            Glide.with(this.mContext).load(url).placeholder((int) R.drawable.product_default).diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) view.findViewById(R.id.id_index_gallery_item_image));
            gallery.addView(view);
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
            button.setBackgroundResource(R.drawable.tag_button_bg_unchecked);
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
                button2.setBackgroundResource(R.drawable.tag_button_bg_unchecked);
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
            buttons.add(new OrderButton("查看物流", ActionConstant.tagShopping, true));
        }
        return buttons;
    }
}
