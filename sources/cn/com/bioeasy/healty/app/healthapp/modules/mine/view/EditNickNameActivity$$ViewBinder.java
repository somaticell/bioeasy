package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.EditNickNameActivity;

public class EditNickNameActivity$$ViewBinder<T extends EditNickNameActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.mTvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'mTvTitle'"), R.id.tv_title, "field 'mTvTitle'");
        View view = (View) finder.findRequiredView(source, R.id.id_tv_add_all, "field 'mTvFinish' and method 'OnClick'");
        target.mTvFinish = (TextView) finder.castView(view, R.id.id_tv_add_all, "field 'mTvFinish'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        target.mEtNickName = (EditText) finder.castView((View) finder.findRequiredView(source, R.id.edt_nickname, "field 'mEtNickName'"), R.id.edt_nickname, "field 'mEtNickName'");
        View view2 = (View) finder.findRequiredView(source, R.id.iv_delete, "field 'mIvDelete' and method 'OnClick'");
        target.mIvDelete = (ImageView) finder.castView(view2, R.id.iv_delete, "field 'mIvDelete'");
        view2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'OnClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.mTvTitle = null;
        target.mTvFinish = null;
        target.mEtNickName = null;
        target.mIvDelete = null;
    }
}
