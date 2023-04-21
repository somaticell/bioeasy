package cn.com.bioeasy.healty.app.healthapp.modules.user.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.app.widgets.CircleImageView;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.user.view.RegisterActivity;

public class RegisterActivity$$ViewBinder<T extends RegisterActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.mEtAccount = (EditText) finder.castView((View) finder.findRequiredView(source, R.id.edt_account, "field 'mEtAccount'"), R.id.edt_account, "field 'mEtAccount'");
        target.mEtCode = (EditText) finder.castView((View) finder.findRequiredView(source, R.id.edt_code, "field 'mEtCode'"), R.id.edt_code, "field 'mEtCode'");
        View view = (View) finder.findRequiredView(source, R.id.btn_next, "field 'mBtNext' and method 'OnClick'");
        target.mBtNext = (Button) finder.castView(view, R.id.btn_next, "field 'mBtNext'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        target.mRlOnepage = (RelativeLayout) finder.castView((View) finder.findRequiredView(source, R.id.rl_onepage, "field 'mRlOnepage'"), R.id.rl_onepage, "field 'mRlOnepage'");
        target.mRlTwopage = (RelativeLayout) finder.castView((View) finder.findRequiredView(source, R.id.rl_twopage, "field 'mRlTwopage'"), R.id.rl_twopage, "field 'mRlTwopage'");
        target.mrlHead = (RelativeLayout) finder.castView((View) finder.findRequiredView(source, R.id.rl_head, "field 'mrlHead'"), R.id.rl_head, "field 'mrlHead'");
        target.password = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.edt_pwd, "field 'password'"), R.id.edt_pwd, "field 'password'");
        target.confirmPassword = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.edt_aingin_pwd, "field 'confirmPassword'"), R.id.edt_aingin_pwd, "field 'confirmPassword'");
        target.mCiHead = (CircleImageView) finder.castView((View) finder.findRequiredView(source, R.id.iv_head, "field 'mCiHead'"), R.id.iv_head, "field 'mCiHead'");
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
        ((View) finder.findRequiredView(source, R.id.btn_rst_finsh, "method 'OnClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.mEtAccount = null;
        target.mEtCode = null;
        target.mBtNext = null;
        target.mRlOnepage = null;
        target.mRlTwopage = null;
        target.mrlHead = null;
        target.password = null;
        target.confirmPassword = null;
        target.mCiHead = null;
        target.mBtCode = null;
    }
}
