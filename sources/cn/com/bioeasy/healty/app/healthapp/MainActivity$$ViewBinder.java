package cn.com.bioeasy.healty.app.healthapp;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import butterknife.ButterKnife;
import cn.com.bioeasy.healty.app.healthapp.MainActivity;

public class MainActivity$$ViewBinder<T extends MainActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, T target, Object source) {
        target.mToolbar = (Toolbar) finder.castView((View) finder.findRequiredView(source, R.id.toolbar, "field 'mToolbar'"), R.id.toolbar, "field 'mToolbar'");
        target.mIvBack = (ImageView) finder.castView((View) finder.findRequiredView(source, R.id.iv_back, "field 'mIvBack'"), R.id.iv_back, "field 'mIvBack'");
    }

    public void unbind(T target) {
        target.mToolbar = null;
        target.mIvBack = null;
    }
}
