package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.AddOrEditAddressActivity;
import com.kyleduo.switchbutton.SwitchButton;

public class AddOrEditAddressActivity$$ViewBinder<T extends AddOrEditAddressActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.tvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'tvTitle'"), R.id.tv_title, "field 'tvTitle'");
        View view = (View) finder.findRequiredView(source, R.id.id_tv_edit_all, "field 'mtvSave' and method 'onClick'");
        target.mtvSave = (TextView) finder.castView(view, R.id.id_tv_edit_all, "field 'mtvSave'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        target.metPerson = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.et_person, "field 'metPerson'"), R.id.et_person, "field 'metPerson'");
        target.metPhone = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.et_phone, "field 'metPhone'"), R.id.et_phone, "field 'metPhone'");
        target.metDetailAddress = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.et_detail_address, "field 'metDetailAddress'"), R.id.et_detail_address, "field 'metDetailAddress'");
        target.mArea = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_area, "field 'mArea'"), R.id.tv_area, "field 'mArea'");
        target.mSb = (SwitchButton) finder.castView((View) finder.findRequiredView(source, R.id.sbt_default_address, "field 'mSb'"), R.id.sbt_default_address, "field 'mSb'");
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.rl_select_address, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.tvTitle = null;
        target.mtvSave = null;
        target.metPerson = null;
        target.metPhone = null;
        target.metDetailAddress = null;
        target.mArea = null;
        target.mSb = null;
    }
}
