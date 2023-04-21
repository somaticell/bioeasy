package cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view.SelectFragment;

public class SelectFragment$$ViewBinder<T extends SelectFragment> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.mContentView = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.rv_image, "field 'mContentView'"), R.id.rv_image, "field 'mContentView'");
        View view = (View) finder.findRequiredView(source, R.id.btn_title_select, "field 'mSelectFolderView' and method 'onClick'");
        target.mSelectFolderView = (Button) finder.castView(view, R.id.btn_title_select, "field 'mSelectFolderView'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        target.mSelectFolderIcon = (ImageView) finder.castView((View) finder.findRequiredView(source, R.id.iv_title_select, "field 'mSelectFolderIcon'"), R.id.iv_title_select, "field 'mSelectFolderIcon'");
        target.mToolbar = (View) finder.findRequiredView(source, R.id.toolbar, "field 'mToolbar'");
        View view2 = (View) finder.findRequiredView(source, R.id.btn_done, "field 'mDoneView' and method 'onClick'");
        target.mDoneView = (Button) finder.castView(view2, R.id.btn_done, "field 'mDoneView'");
        view2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        View view3 = (View) finder.findRequiredView(source, R.id.btn_preview, "field 'mPreviewView' and method 'onClick'");
        target.mPreviewView = (Button) finder.castView(view3, R.id.btn_preview, "field 'mPreviewView'");
        view3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.icon_back, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.mContentView = null;
        target.mSelectFolderView = null;
        target.mSelectFolderIcon = null;
        target.mToolbar = null;
        target.mDoneView = null;
        target.mPreviewView = null;
    }
}
