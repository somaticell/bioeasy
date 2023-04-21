package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.AboutMeActivity;

public class AboutMeActivity$$ViewBinder<T extends AboutMeActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.mtvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'mtvTitle'"), R.id.tv_title, "field 'mtvTitle'");
        target.mtBiName = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_appname, "field 'mtBiName'"), R.id.tv_appname, "field 'mtBiName'");
        target.mtBiVersion = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_version, "field 'mtBiVersion'"), R.id.tv_version, "field 'mtBiVersion'");
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'OnClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.rl_version_wrap, "method 'OnClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.rl_protocol_wrap, "method 'OnClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.mtvTitle = null;
        target.mtBiName = null;
        target.mtBiVersion = null;
    }
}
