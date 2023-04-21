package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.view.View;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.app.widgets.wheel.WheelView;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.SelectAreaDialog;

public class SelectAreaDialog$$ViewBinder<T extends SelectAreaDialog> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.mProvince = (WheelView) finder.castView((View) finder.findRequiredView(source, R.id.id_province, "field 'mProvince'"), R.id.id_province, "field 'mProvince'");
        target.mCity = (WheelView) finder.castView((View) finder.findRequiredView(source, R.id.id_city, "field 'mCity'"), R.id.id_city, "field 'mCity'");
        target.mDistrict = (WheelView) finder.castView((View) finder.findRequiredView(source, R.id.id_district, "field 'mDistrict'"), R.id.id_district, "field 'mDistrict'");
        ((View) finder.findRequiredView(source, R.id.btn_cancle, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.btn_confire, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.mProvince = null;
        target.mCity = null;
        target.mDistrict = null;
    }
}
