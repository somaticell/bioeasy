package cn.com.bioeasy.healty.app.healthapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.RecommendGoodsBean;
import com.bigkoo.convenientbanner.holder.Holder;
import java.util.List;

public class ItemRecommendAdapter implements Holder<List<RecommendGoodsBean>> {
    private GridView gv_recommend_goods;
    /* access modifiers changed from: private */
    public List<RecommendGoodsBean> itemDatas;
    /* access modifiers changed from: private */
    public OnItemClickListener onItemClickListener;
    private View rootview;

    public interface OnItemClickListener {
        void onClick(RecommendGoodsBean recommendGoodsBean);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public View createView(Context context) {
        this.rootview = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.item_recommend, (ViewGroup) null);
        this.gv_recommend_goods = (GridView) this.rootview.findViewById(R.id.gv_recommend_goods);
        this.gv_recommend_goods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RecommendGoodsBean goodsBean = (RecommendGoodsBean) ItemRecommendAdapter.this.itemDatas.get(i);
                if (ItemRecommendAdapter.this.onItemClickListener != null) {
                    ItemRecommendAdapter.this.onItemClickListener.onClick(goodsBean);
                }
            }
        });
        return this.rootview;
    }

    public void UpdateUI(Context context, int position, List<RecommendGoodsBean> data) {
        this.itemDatas = data;
        this.gv_recommend_goods.setAdapter(new ItemRecommendGoodsAdapter(context, this.itemDatas));
    }
}
