package cn.com.bioeasy.healty.app.healthapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.bean.PresentBean;
import java.util.List;

public class PresentAdapter extends RecyclerView.Adapter<MyviewHolder> {
    private Context cxt;
    public ItemsOnclickListener listener;
    /* access modifiers changed from: private */
    public List<PresentBean> lists;

    public interface ItemsOnclickListener {
        void onClick(PresentBean presentBean, String str, int i);
    }

    public void setOnclick(ItemsOnclickListener listener2) {
        this.listener = listener2;
    }

    public PresentAdapter(Context cxt2, List<PresentBean> lists2) {
        this.cxt = cxt2;
        this.lists = lists2;
    }

    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyviewHolder(LayoutInflater.from(this.cxt).inflate(R.layout.measur_items, parent, false));
    }

    public void onBindViewHolder(MyviewHolder holder, final int position) {
        if (holder != null) {
            holder.mProductName.setText(this.lists.get(position).getProductName());
            String priceFormat = this.cxt.getResources().getString(R.string.product_price);
            String countsFormat = this.cxt.getResources().getString(R.string.product_salecounts);
            String price = String.format(priceFormat, new Object[]{this.lists.get(position).getScores() + ""});
            String counts = String.format(countsFormat, new Object[]{this.lists.get(position).getCounts() + ""});
            holder.mPrice.setText(price);
            holder.mCount.setText(counts);
            holder.mView.setBackgroundResource(R.drawable.product_img);
            if (this.listener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        PresentAdapter.this.listener.onClick((PresentBean) PresentAdapter.this.lists.get(position), "", position);
                    }
                });
            }
        }
    }

    public int getItemCount() {
        if (this.lists == null) {
            return 0;
        }
        return this.lists.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView mCount;
        TextView mPrice;
        TextView mProductName;
        ImageView mView;

        public MyviewHolder(View itemView) {
            super(itemView);
            this.mProductName = (TextView) itemView.findViewById(R.id.tv_productName);
            this.mPrice = (TextView) itemView.findViewById(R.id.tv_productPrice);
            this.mCount = (TextView) itemView.findViewById(R.id.count);
            this.mView = (ImageView) itemView.findViewById(R.id.measur_img);
        }
    }
}
