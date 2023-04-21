package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.MyScoreActivity;
import com.flyco.tablayout.SegmentTabLayout;

public class MyScoreActivity$$ViewBinder<T extends MyScoreActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.mTvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'mTvTitle'"), R.id.tv_title, "field 'mTvTitle'");
        target.mTb = (SegmentTabLayout) finder.castView((View) finder.findRequiredView(source, R.id.tl_1, "field 'mTb'"), R.id.tl_1, "field 'mTb'");
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'OnClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.bt_exchange, "method 'OnClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.rl_history, "method 'OnClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.mTvTitle = null;
        target.mTb = null;
    }
}
