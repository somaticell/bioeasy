package cn.com.bioeasy.healty.app.healthapp.modules.home.view;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.ContentDetailActivity;

public class ContentDetailActivity$$ViewBinder<T extends ContentDetailActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.webViewFood = (WebView) finder.castView((View) finder.findRequiredView(source, R.id.wb_food, "field 'webViewFood'"), R.id.wb_food, "field 'webViewFood'");
        target.tvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'tvTitle'"), R.id.tv_title, "field 'tvTitle'");
        View view = (View) finder.findRequiredView(source, R.id.iv_share, "field 'mIvShare' and method 'onClicks'");
        target.mIvShare = (ImageView) finder.castView(view, R.id.iv_share, "field 'mIvShare'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClicks(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'onClicks'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClicks(p0);
            }
        });
    }

    public void unbind(T target) {
        target.webViewFood = null;
        target.tvTitle = null;
        target.mIvShare = null;
    }
}
