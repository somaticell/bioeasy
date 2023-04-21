package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.BindingPhoneActivity;

public class BindingPhoneActivity$$ViewBinder<T extends BindingPhoneActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.mTvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'mTvTitle'"), R.id.tv_title, "field 'mTvTitle'");
        target.mEtCode = (EditText) finder.castView((View) finder.findRequiredView(source, R.id.edt_code, "field 'mEtCode'"), R.id.edt_code, "field 'mEtCode'");
        View view = (View) finder.findRequiredView(source, R.id.btn_bind, "field 'mBtBind' and method 'OnClick'");
        target.mBtBind = (Button) finder.castView(view, R.id.btn_bind, "field 'mBtBind'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        target.mCurrentPhone = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_phone, "field 'mCurrentPhone'"), R.id.tv_phone, "field 'mCurrentPhone'");
        target.mEtPhone = (EditText) finder.castView((View) finder.findRequiredView(source, R.id.edt_account, "field 'mEtPhone'"), R.id.edt_account, "field 'mEtPhone'");
        target.mRlOnepage = (RelativeLayout) finder.castView((View) finder.findRequiredView(source, R.id.rl_onepage, "field 'mRlOnepage'"), R.id.rl_onepage, "field 'mRlOnepage'");
        target.mRlTwopage = (RelativeLayout) finder.castView((View) finder.findRequiredView(source, R.id.rl_twopage, "field 'mRlTwopage'"), R.id.rl_twopage, "field 'mRlTwopage'");
        target.mRlThreepage = (RelativeLayout) finder.castView((View) finder.findRequiredView(source, R.id.rl_threepage, "field 'mRlThreepage'"), R.id.rl_threepage, "field 'mRlThreepage'");
        View view2 = (View) finder.findRequiredView(source, R.id.bt_code, "field 'mBtCode' and method 'OnClick'");
        target.mBtCode = (Button) finder.castView(view2, R.id.bt_code, "field 'mBtCode'");
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
        ((View) finder.findRequiredView(source, R.id.btn_change_phone, "method 'OnClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.btn_back_main, "method 'OnClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.mTvTitle = null;
        target.mEtCode = null;
        target.mBtBind = null;
        target.mCurrentPhone = null;
        target.mEtPhone = null;
        target.mRlOnepage = null;
        target.mRlTwopage = null;
        target.mRlThreepage = null;
        target.mBtCode = null;
    }
}
