package cn.com.bioeasy.healty.app.healthapp.modules.test.view;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.TestItemActivity;

public class TestItemActivity$$ViewBinder<T extends TestItemActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.tvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'tvTitle'"), R.id.tv_title, "field 'tvTitle'");
        target.mListView = (ListView) finder.castView((View) finder.findRequiredView(source, R.id.listview_blue, "field 'mListView'"), R.id.listview_blue, "field 'mListView'");
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'OnClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.tvTitle = null;
        target.mListView = null;
    }
}
