package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.OrderExpressActivity;

public class OrderExpressActivity$$ViewBinder<T extends OrderExpressActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.tvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'tvTitle'"), R.id.tv_title, "field 'tvTitle'");
        target.recyclerView = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.lv_express_list, "field 'recyclerView'"), R.id.lv_express_list, "field 'recyclerView'");
        target.mOrderSnText = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_order_sn, "field 'mOrderSnText'"), R.id.tv_order_sn, "field 'mOrderSnText'");
        target.mOrderExpressText = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_order_express, "field 'mOrderExpressText'"), R.id.tv_order_express, "field 'mOrderExpressText'");
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.tvTitle = null;
        target.recyclerView = null;
        target.mOrderSnText = null;
        target.mOrderExpressText = null;
    }
}
