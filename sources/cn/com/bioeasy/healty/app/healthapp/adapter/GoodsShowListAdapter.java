package cn.com.bioeasy.healty.app.healthapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.Goods;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.ShopCardItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.List;

public class GoodsShowListAdapter extends BaseAdapter {
    private Context mContext;
    private List<ShopCardItem> mProductLst;

    public GoodsShowListAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<ShopCardItem> products) {
        if (products != null) {
            this.mProductLst = products;
            notifyDataSetChanged();
        }
    }

    public int getCount() {
        if (this.mProductLst == null) {
            return 0;
        }
        return this.mProductLst.size();
    }

    public Object getItem(int position) {
        if (this.mProductLst == null) {
            return null;
        }
        return this.mProductLst.get(position);
    }

    public long getItemId(int position) {
        if (this.mProductLst == null) {
            return -1;
        }
        return Long.valueOf((long) this.mProductLst.get(position).getId()).longValue();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.goods_show_list_item, parent, false);
            holder = new ViewHolder();
            holder.picIngv = (ImageView) convertView.findViewById(R.id.product_pic_imgv);
            holder.nameTxtv = (TextView) convertView.findViewById(R.id.product_name_txtv);
            holder.specTxtv = (TextView) convertView.findViewById(R.id.product_spec_txtv);
            holder.countTxtv = (TextView) convertView.findViewById(R.id.product_count_txtv);
            holder.priceTxtv = (TextView) convertView.findViewById(R.id.product_price_txtv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ShopCardItem cardItem = this.mProductLst.get(position);
        Goods product = cardItem.getGoods();
        Glide.with(this.mContext).load(product.getIcon()).placeholder((int) R.drawable.product_default).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.picIngv);
        if (!TextUtils.isEmpty(product.getName())) {
            holder.nameTxtv.setText(product.getName());
        }
        if (!StringUtil.isNullOrEmpty(cardItem.getGoodSpecKeyNames())) {
            holder.specTxtv.setText(cardItem.getGoodSpecKeyNames());
        } else {
            holder.specTxtv.setVisibility(8);
        }
        if (cardItem.getAmount() != 0) {
            holder.countTxtv.setText("x" + cardItem.getAmount());
        }
        holder.priceTxtv.setText("¥" + (StringUtil.isNullOrEmpty(product.getShopPrice()) ? "0.0" : product.getShopPrice()));
        return convertView;
    }

    class ViewHolder {
        TextView countTxtv;
        TextView nameTxtv;
        ImageView picIngv;
        TextView priceTxtv;
        TextView specTxtv;

        ViewHolder() {
        }
    }
}
