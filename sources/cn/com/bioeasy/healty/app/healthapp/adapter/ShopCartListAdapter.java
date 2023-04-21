package cn.com.bioeasy.healty.app.healthapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.app.widgets.util.ToastUtils;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.ShopCardItem;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import org.litepal.crud.DataSupport;

public class ShopCartListAdapter extends BaseAdapter {
    public static final String EDITING = "编辑";
    public static final String FINISH_EDITING = "完成";
    private static final String TAG = "ShopCartListAdapter";
    /* access modifiers changed from: private */
    public List<ShopCardItem> childMapList_list = new ArrayList();
    private Context context;
    private OnAllCheckedBoxNeedChangeListener onAllCheckedBoxNeedChangeListener;
    private OnCheckHasGoodsListener onCheckHasGoodsListener;
    private OnEditingTvChangeListener onEditingTvChangeListener;
    private OnGoodsCheckedChangeListener onGoodsCheckedChangeListener;
    private OnSelectedProductListener onSelectedProductListener;
    private int totalCount = 0;
    private double totalPrice = 0.0d;

    public interface OnAllCheckedBoxNeedChangeListener {
        void onCheckedBoxNeedChange(boolean z);
    }

    public interface OnCheckHasGoodsListener {
        void onCheckHasGoods(boolean z);
    }

    public interface OnEditingTvChangeListener {
        void onEditingTvChange(boolean z);
    }

    public interface OnGoodsCheckedChangeListener {
        void onGoodsCheckedChange(int i, double d);
    }

    public interface OnSelectedProductListener {
        void selectEdPProductListener(List<ShopCardItem> list);
    }

    public void setOnCheckProductListener(OnSelectedProductListener onCheckHasGoodsListener2) {
        this.onSelectedProductListener = onCheckHasGoodsListener2;
    }

    public void setOnCheckHasGoodsListener(OnCheckHasGoodsListener onCheckHasGoodsListener2) {
        this.onCheckHasGoodsListener = onCheckHasGoodsListener2;
    }

    public void setOnEditingTvChangeListener(OnEditingTvChangeListener onEditingTvChangeListener2) {
        this.onEditingTvChangeListener = onEditingTvChangeListener2;
    }

    public void setOnGoodsCheckedChangeListener(OnGoodsCheckedChangeListener onGoodsCheckedChangeListener2) {
        this.onGoodsCheckedChangeListener = onGoodsCheckedChangeListener2;
    }

    public void setOnAllCheckedBoxNeedChangeListener(OnAllCheckedBoxNeedChangeListener onAllCheckedBoxNeedChangeListener2) {
        this.onAllCheckedBoxNeedChangeListener = onAllCheckedBoxNeedChangeListener2;
    }

    public ShopCartListAdapter(Context context2) {
        this.context = context2;
    }

    public void dealAdd(ShopCardItem goodsBean, EditText editTextCount) {
        int count = goodsBean.getAmount() + 1;
        if (count <= goodsBean.getGoods().getStockCount()) {
            goodsBean.setAmount(count);
        } else {
            ToastUtils.showToast("库存不足");
        }
        editTextCount.setText(goodsBean.getAmount() + "");
        notifyDataSetChanged();
        dealPrice();
    }

    public void dealReduce(ShopCardItem goodsBean, EditText editTextCount) {
        int count = goodsBean.getAmount();
        if (count != 1) {
            int count2 = count - 1;
            goodsBean.setAmount(count2);
            editTextCount.setText(count2 + "");
            notifyDataSetChanged();
            dealPrice();
        }
    }

    public void setupEditingAll(boolean isEditingAll) {
        for (int i = 0; i < this.childMapList_list.size(); i++) {
            this.childMapList_list.get(i).setEditing(isEditingAll);
        }
        notifyDataSetChanged();
    }

    public void dealPrice() {
        double discountPrice;
        this.totalCount = 0;
        this.totalPrice = 0.0d;
        List<ShopCardItem> listSelectedProductList = new ArrayList<>();
        for (int j = 0; j < this.childMapList_list.size(); j++) {
            ShopCardItem goodsBean = this.childMapList_list.get(j);
            int count = goodsBean.getAmount();
            if (!StringUtil.isNullOrEmpty(goodsBean.getPrice())) {
                discountPrice = Double.parseDouble(goodsBean.getPrice());
            } else {
                discountPrice = 0.0d;
            }
            if (goodsBean.isChecked()) {
                this.totalCount++;
                this.totalPrice += ((double) count) * discountPrice;
                listSelectedProductList.add(goodsBean);
                this.onSelectedProductListener.selectEdPProductListener(listSelectedProductList);
            }
        }
        if (this.totalCount == this.childMapList_list.size()) {
            this.onAllCheckedBoxNeedChangeListener.onCheckedBoxNeedChange(true);
        } else {
            this.onAllCheckedBoxNeedChangeListener.onCheckedBoxNeedChange(false);
        }
        this.onGoodsCheckedChangeListener.onGoodsCheckedChange(this.totalCount, this.totalPrice);
    }

    public int getCount() {
        if (this.childMapList_list == null) {
            return 0;
        }
        return this.childMapList_list.size();
    }

    public Object getItem(int position) {
        return this.childMapList_list.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.shopcart_item, (ViewGroup) null);
            childViewHolder = new ChildViewHolder();
            childViewHolder.id_cb_select_child = (CheckBox) convertView.findViewById(R.id.id_cb_select_child);
            childViewHolder.imageView = (ImageView) convertView.findViewById(R.id.id_iv_logo);
            childViewHolder.tv_items_child = (TextView) convertView.findViewById(R.id.tv_goods_name);
            childViewHolder.tv_items_child_desc = (TextView) convertView.findViewById(R.id.tv_items_child_desc);
            childViewHolder.id_tv_price = (TextView) convertView.findViewById(R.id.id_tv_price);
            childViewHolder.id_tv_discount_price = (TextView) convertView.findViewById(R.id.id_tv_discount_price);
            childViewHolder.rl_change_num = (RelativeLayout) convertView.findViewById(R.id.rl_change_num);
            childViewHolder.btnMinus = (Button) convertView.findViewById(R.id.cart_minus_btn);
            childViewHolder.btnPlus = (Button) convertView.findViewById(R.id.cart_plus_btn);
            childViewHolder.editTextCount = (EditText) convertView.findViewById(R.id.cart_count_dtxtv);
            childViewHolder.id_ll_edtoring = (RelativeLayout) convertView.findViewById(R.id.id_ll_edtoring);
            childViewHolder.id_tv_count = (TextView) convertView.findViewById(R.id.id_tv_count);
            childViewHolder.id_tv_goods_delete = (TextView) convertView.findViewById(R.id.id_tv_goods_delete);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        final ShopCardItem goodsBean = this.childMapList_list.get(position);
        final ChildViewHolder finalChildViewHolder1 = childViewHolder;
        childViewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ShopCartListAdapter.this.dealReduce((ShopCardItem) ShopCartListAdapter.this.childMapList_list.get(position), finalChildViewHolder1.editTextCount);
            }
        });
        childViewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ShopCartListAdapter.this.dealAdd((ShopCardItem) ShopCartListAdapter.this.childMapList_list.get(position), finalChildViewHolder1.editTextCount);
            }
        });
        childViewHolder.tv_items_child.setText(goodsBean.getGoods().getName());
        childViewHolder.id_tv_price.setText(String.format(this.context.getString(R.string.product_price), new Object[]{goodsBean.getGoods().getMarketPrice()}));
        childViewHolder.id_tv_price.getPaint().setFlags(17);
        childViewHolder.id_tv_discount_price.setText(String.format(this.context.getString(R.string.product_price), new Object[]{goodsBean.getPrice()}));
        childViewHolder.tv_items_child_desc.setText(String.valueOf(goodsBean.getGoods().getBrandId()));
        childViewHolder.id_tv_count.setText(String.format(this.context.getString(R.string.product_num), new Object[]{String.valueOf(goodsBean.getAmount())}));
        if (!StringUtil.isNullOrEmpty(goodsBean.getGoods().getShopPrice())) {
            double priceNow = ((double) goodsBean.getAmount()) * Double.parseDouble(goodsBean.getPrice());
        }
        Glide.with(this.context).load(goodsBean.getGoods().getIcon()).centerCrop().placeholder((int) R.drawable.ic_split_graph).override(50, 50).error((int) R.drawable.ic_split_graph).into(childViewHolder.imageView);
        childViewHolder.editTextCount.setText(goodsBean.getAmount() + "");
        childViewHolder.id_cb_select_child.setChecked(goodsBean.isChecked());
        childViewHolder.id_cb_select_child.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ShopCartListAdapter.this.isCheck(goodsBean, position);
            }
        });
        if (goodsBean.isEditing()) {
            childViewHolder.rl_change_num.setVisibility(8);
            childViewHolder.id_ll_edtoring.setVisibility(0);
        } else {
            childViewHolder.rl_change_num.setVisibility(0);
            childViewHolder.id_ll_edtoring.setVisibility(8);
        }
        childViewHolder.id_tv_goods_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ShopCartListAdapter.this.removeOneGood(position);
            }
        });
        return convertView;
    }

    /* access modifiers changed from: private */
    public void isCheck(ShopCardItem goodsBean, int position) {
        goodsBean.setChecked(!goodsBean.isChecked());
        Log.d(TAG, "getChildView:onClick:isOneParentAllChildIsChecked:" + dealOneParentAllChildIsChecked(position));
        notifyDataSetChanged();
        dealPrice();
    }

    /* JADX WARNING: Removed duplicated region for block: B:1:0x001f A[LOOP:0: B:1:0x001f->B:4:0x002f, LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean dealOneParentAllChildIsChecked(int r6) {
        /*
            r5 = this;
            r4 = 1
            java.lang.String r1 = "ShopCartListAdapter"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "dealOneParentAllChildIsChecked: groupPosition："
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r6)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r1, r2)
            java.util.List<cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.ShopCardItem> r1 = r5.childMapList_list
            java.util.Iterator r1 = r1.iterator()
        L_0x001f:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0031
            java.lang.Object r0 = r1.next()
            cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.ShopCardItem r0 = (cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.ShopCardItem) r0
            boolean r2 = r0.isChecked()
            if (r2 == 0) goto L_0x001f
        L_0x0031:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.com.bioeasy.healty.app.healthapp.adapter.ShopCartListAdapter.dealOneParentAllChildIsChecked(int):boolean");
    }

    public void addDataList(List<ShopCardItem> goodsList) {
        this.childMapList_list.addAll(goodsList);
        notifyDataSetChanged();
    }

    public void removeOneGood(int childPosition) {
        int delete = DataSupport.delete(ShopCardItem.class, (long) this.childMapList_list.get(childPosition).getId());
        this.childMapList_list.remove(childPosition);
        if (delete == 1) {
            ToastUtils.showToast("移除成功");
        }
        notifyDataSetChanged();
        dealPrice();
        if (this.childMapList_list == null || this.childMapList_list.size() <= 0) {
            this.onCheckHasGoodsListener.onCheckHasGoods(false);
        } else {
            this.onCheckHasGoodsListener.onCheckHasGoods(true);
        }
    }

    public void setupAllChecked(boolean isChecked) {
        Log.d(TAG, "setupAllChecked: ============");
        Log.d(TAG, "setupAllChecked: isChecked：" + isChecked);
        List<ShopCardItem> childMapList = this.childMapList_list;
        for (int j = 0; j < childMapList.size(); j++) {
            childMapList.get(j).setChecked(isChecked);
        }
        notifyDataSetChanged();
        dealPrice();
    }

    class ChildViewHolder {
        Button btnMinus;
        Button btnPlus;
        EditText editTextCount;
        CheckBox id_cb_select_child;
        RelativeLayout id_ll_edtoring;
        TextView id_tv_count;
        TextView id_tv_discount_price;
        TextView id_tv_goods_delete;
        TextView id_tv_price;
        ImageView imageView;
        RelativeLayout rl_change_num;
        TextView tv_items_child;
        TextView tv_items_child_desc;

        ChildViewHolder() {
        }
    }
}
