package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.OrderEvaluationActivity;

public class OrderEvaluationActivity$$ViewBinder<T extends OrderEvaluationActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.tvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'tvTitle'"), R.id.tv_title, "field 'tvTitle'");
        target.recyclerView = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.lv_eva_goods_list, "field 'recyclerView'"), R.id.lv_eva_goods_list, "field 'recyclerView'");
        target.ratingBar = (RatingBar) finder.castView((View) finder.findRequiredView(source, R.id.rb_order_eva, "field 'ratingBar'"), R.id.rb_order_eva, "field 'ratingBar'");
        View view = (View) finder.findRequiredView(source, R.id.bt_order_eva, "field 'btOrderEva' and method 'onClick'");
        target.btOrderEva = (Button) finder.castView(view, R.id.bt_order_eva, "field 'btOrderEva'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.tvTitle = null;
        target.recyclerView = null;
        target.ratingBar = null;
        target.btOrderEva = null;
    }
}
