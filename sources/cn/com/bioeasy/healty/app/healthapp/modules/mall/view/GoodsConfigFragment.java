package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import cn.com.bioeasy.app.widgets.abslistview.CommonAdapter;
import cn.com.bioeasy.app.widgets.abslistview.ViewHolder;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.GoodsAttr;
import java.util.ArrayList;
import java.util.List;

public class GoodsConfigFragment extends Fragment {
    private ProductsActivity activity;
    private CommonAdapter<GoodsAttr> adapter;
    private List<GoodsAttr> attrs;
    private ListView lv_config;

    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (ProductsActivity) context;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_config, (ViewGroup) null);
        this.lv_config = (ListView) view.findViewById(R.id.lv_config);
        this.lv_config.setFocusable(false);
        this.attrs = this.attrs == null ? new ArrayList<>() : this.attrs;
        this.adapter = new CommonAdapter<GoodsAttr>(this.activity, R.layout.config_listview_item, this.attrs) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder holder, GoodsAttr attrsBeanX, int position) {
                holder.setText(R.id.tv_config_key, attrsBeanX.getName());
                holder.setText(R.id.tv_config_value, attrsBeanX.getValue());
            }
        };
        this.lv_config.setAdapter(this.adapter);
        return view;
    }

    public void initConfig(List<GoodsAttr> attrs2) {
        this.attrs = attrs2;
        if (this.adapter != null) {
            this.adapter.refresItems(attrs2);
        }
    }

    public void onResume() {
        super.onResume();
    }
}
