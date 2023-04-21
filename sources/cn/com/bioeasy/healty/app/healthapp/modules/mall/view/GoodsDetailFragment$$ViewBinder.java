package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.GoodsDetailFragment;

public class GoodsDetailFragment$$ViewBinder<T extends GoodsDetailFragment> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        View view = (View) finder.findRequiredView(source, R.id.tv_goods_detail, "field 'tv_goods_detail' and method 'OnClick'");
        target.tv_goods_detail = (TextView) finder.castView(view, R.id.tv_goods_detail, "field 'tv_goods_detail'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        View view2 = (View) finder.findRequiredView(source, R.id.tv_goods_config, "field 'tv_goods_config' and method 'OnClick'");
        target.tv_goods_config = (TextView) finder.castView(view2, R.id.tv_goods_config, "field 'tv_goods_config'");
        view2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        target.v_tab_cursor = (View) finder.findRequiredView(source, R.id.v_tab_cursor, "field 'v_tab_cursor'");
    }

    public void unbind(T target) {
        target.tv_goods_detail = null;
        target.tv_goods_config = null;
        target.v_tab_cursor = null;
    }
}
