package cn.com.bioeasy.healty.app.healthapp.modules.user.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.user.view.ForgetPwdActivity;

public class ForgetPwdActivity$$ViewBinder<T extends ForgetPwdActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        View view = (View) finder.findRequiredView(source, R.id.btn_next, "field 'mBtNext' and method 'OnClick'");
        target.mBtNext = (Button) finder.castView(view, R.id.btn_next, "field 'mBtNext'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        target.mRlOnepage = (RelativeLayout) finder.castView((View) finder.findRequiredView(source, R.id.rl_one, "field 'mRlOnepage'"), R.id.rl_one, "field 'mRlOnepage'");
        target.mRlTwopage = (RelativeLayout) finder.castView((View) finder.findRequiredView(source, R.id.rl_two, "field 'mRlTwopage'"), R.id.rl_two, "field 'mRlTwopage'");
        target.mRlThreepage = (RelativeLayout) finder.castView((View) finder.findRequiredView(source, R.id.rl_three, "field 'mRlThreepage'"), R.id.rl_three, "field 'mRlThreepage'");
        View view2 = (View) finder.findRequiredView(source, R.id.bt_code, "field 'mBtCode' and method 'OnClick'");
        target.mBtCode = (Button) finder.castView(view2, R.id.bt_code, "field 'mBtCode'");
        view2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        target.mEtAccount = (EditText) finder.castView((View) finder.findRequiredView(source, R.id.et_phone, "field 'mEtAccount'"), R.id.et_phone, "field 'mEtAccount'");
        target.mEtCode = (EditText) finder.castView((View) finder.findRequiredView(source, R.id.et_code, "field 'mEtCode'"), R.id.et_code, "field 'mEtCode'");
        target.mTvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'mTvTitle'"), R.id.tv_title, "field 'mTvTitle'");
        target.mEtnewPwd = (EditText) finder.castView((View) finder.findRequiredView(source, R.id.et_new_pwd, "field 'mEtnewPwd'"), R.id.et_new_pwd, "field 'mEtnewPwd'");
        target.mEtagainPwd = (EditText) finder.castView((View) finder.findRequiredView(source, R.id.et_agin_pwd, "field 'mEtagainPwd'"), R.id.et_agin_pwd, "field 'mEtagainPwd'");
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'OnClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.btn_finsh, "method 'OnClick'")).setOnClickListener(new DebouncingOnClickListener() {
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
        target.mBtNext = null;
        target.mRlOnepage = null;
        target.mRlTwopage = null;
        target.mRlThreepage = null;
        target.mBtCode = null;
        target.mEtAccount = null;
        target.mEtCode = null;
        target.mTvTitle = null;
        target.mEtnewPwd = null;
        target.mEtagainPwd = null;
    }
}
