package cn.com.bioeasy.healty.app.healthapp.modules.user.view;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.user.view.LoginActivity;

public class LoginActivity$$ViewBinder<T extends LoginActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.edtAccount = (EditText) finder.castView((View) finder.findRequiredView(source, R.id.edt_account, "field 'edtAccount'"), R.id.edt_account, "field 'edtAccount'");
        target.edtPsw = (EditText) finder.castView((View) finder.findRequiredView(source, R.id.edt_psw, "field 'edtPsw'"), R.id.edt_psw, "field 'edtPsw'");
        View view = (View) finder.findRequiredView(source, R.id.tv_forgetpsw, "field 'tvForgetpsw' and method 'onClick'");
        target.tvForgetpsw = (TextView) finder.castView(view, R.id.tv_forgetpsw, "field 'tvForgetpsw'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        View view2 = (View) finder.findRequiredView(source, R.id.cb_pwd, "field 'cbPwd' and method 'onClick'");
        target.cbPwd = (CheckBox) finder.castView(view2, R.id.cb_pwd, "field 'cbPwd'");
        view2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.login, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.tv_register, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.tv_close, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.iv_wechat_login, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.edtAccount = null;
        target.edtPsw = null;
        target.tvForgetpsw = null;
        target.cbPwd = null;
    }
}
