package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import cn.com.bioeasy.healty.app.healthapp.R;

public class BindDialog extends Dialog {
    public BindDialog(Context context) {
        super(context, R.style.hd_dialog_dim);
        init(context);
    }

    public BindDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = -2;
        params.height = -2;
        getWindow().setAttributes(params);
    }

    private void init(Context context) {
        View dialogContainer = View.inflate(context, R.layout.dialog_bind, (ViewGroup) null);
        setCancelable(true);
        setContentView(dialogContainer);
        setCanceledOnTouchOutside(true);
    }

    public BindDialog cancelable(boolean cancelable) {
        setCancelable(cancelable);
        return this;
    }

    public BindDialog outsideCancelable(boolean outsideCancelable) {
        setCanceledOnTouchOutside(outsideCancelable);
        return this;
    }
}
