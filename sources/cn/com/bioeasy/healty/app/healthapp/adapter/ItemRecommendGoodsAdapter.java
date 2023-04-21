package cn.com.bioeasy.healty.app.healthapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.RecommendGoodsBean;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.List;

public class ItemRecommendGoodsAdapter extends BaseAdapter {
    private Context context;
    private List<RecommendGoodsBean> data;
    private LayoutInflater inflater;

    public ItemRecommendGoodsAdapter(Context context2, List<RecommendGoodsBean> data2) {
        this.context = context2;
        this.data = data2;
        this.inflater = (LayoutInflater) context2.getSystemService("layout_inflater");
    }

    public void setData(List<RecommendGoodsBean> data2) {
        this.data = data2;
        notifyDataSetChanged();
    }

    public void addData(List<RecommendGoodsBean> data2) {
        this.data.addAll(data2);
        notifyDataSetChanged();
    }

    public List<RecommendGoodsBean> getData() {
        return this.data;
    }

    public void clearData() {
        this.data.clear();
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.data.size();
    }

    public Object getItem(int position) {
        return this.data.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.item_recommend_goods_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RecommendGoodsBean recommendGoods = this.data.get(position);
        holder.tv_goods_name.setText(recommendGoods.getTitle());
        holder.tv_goods_price.setText("￥" + recommendGoods.getCurrentPrice());
        if (!StringUtil.isNullOrEmpty(recommendGoods.getImage())) {
            holder.sdv_goods.setImageURI(Uri.parse(recommendGoods.getImage()));
        }
        holder.tv_goods_old_price.setText("￥" + recommendGoods.getPrice());
        return convertView;
    }

    class ViewHolder {
        /* access modifiers changed from: private */
        public SimpleDraweeView sdv_goods;
        /* access modifiers changed from: private */
        public TextView tv_goods_name;
        /* access modifiers changed from: private */
        public TextView tv_goods_old_price;
        /* access modifiers changed from: private */
        public TextView tv_goods_price;

        public ViewHolder(View convertView) {
            this.sdv_goods = (SimpleDraweeView) convertView.findViewById(R.id.sdv_goods);
            this.tv_goods_name = (TextView) convertView.findViewById(R.id.tv_goods_name);
            this.tv_goods_price = (TextView) convertView.findViewById(R.id.tv_goods_price);
            this.tv_goods_old_price = (TextView) convertView.findViewById(R.id.tv_goods_old_price);
            this.tv_goods_old_price.getPaint().setFlags(16);
        }
    }
}
