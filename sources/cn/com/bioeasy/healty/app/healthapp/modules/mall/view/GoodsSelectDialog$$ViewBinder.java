package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.GoodsSelectDialog;

public class GoodsSelectDialog$$ViewBinder<T extends GoodsSelectDialog> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        View view = (View) finder.findRequiredView(source, R.id.cart_minus_btn, "field 'btnMinus' and method 'onClick'");
        target.btnMinus = (Button) finder.castView(view, R.id.cart_minus_btn, "field 'btnMinus'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        View view2 = (View) finder.findRequiredView(source, R.id.cart_plus_btn, "field 'btnPlus' and method 'onClick'");
        target.btnPlus = (Button) finder.castView(view2, R.id.cart_plus_btn, "field 'btnPlus'");
        view2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        target.edtCount = (EditText) finder.castView((View) finder.findRequiredView(source, R.id.cart_count_dtxtv, "field 'edtCount'"), R.id.cart_count_dtxtv, "field 'edtCount'");
        target.goodIcon = (ImageView) finder.castView((View) finder.findRequiredView(source, R.id.iv_img, "field 'goodIcon'"), R.id.iv_img, "field 'goodIcon'");
        target.listview = (ListView) finder.castView((View) finder.findRequiredView(source, R.id.product_spec_lstv, "field 'listview'"), R.id.product_spec_lstv, "field 'listview'");
        target.tvGoodPrice = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_price, "field 'tvGoodPrice'"), R.id.tv_price, "field 'tvGoodPrice'");
        target.mtvType = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_type, "field 'mtvType'"), R.id.tv_type, "field 'mtvType'");
        ((View) finder.findRequiredView(source, R.id.iv_close, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.bt_addcart, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.btnMinus = null;
        target.btnPlus = null;
        target.edtCount = null;
        target.goodIcon = null;
        target.listview = null;
        target.tvGoodPrice = null;
        target.mtvType = null;
    }
}
