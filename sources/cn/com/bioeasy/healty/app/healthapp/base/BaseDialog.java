package cn.com.bioeasy.healty.app.healthapp.base;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import butterknife.ButterKnife;
import cn.com.bioeasy.healty.app.healthapp.R;

public abstract class BaseDialog extends Dialog {
    protected View view;

    /* access modifiers changed from: protected */
    public abstract int getLayoutId();

    /* access modifiers changed from: protected */
    public abstract void initView(View view2, Context context);

    public BaseDialog(Context context, int themeResId) {
        this(context, themeResId, 80);
    }

    public BaseDialog(Context context, int themeResId, int gravity) {
        super(context, themeResId);
        this.view = LayoutInflater.from(context).inflate(getLayoutId(), (ViewGroup) null);
        setContentView(this.view);
        ButterKnife.bind((Dialog) this);
        initView(this.view, context);
        initWindow(context, gravity);
    }

    /* access modifiers changed from: protected */
    public void initWindow(Context context, int gravity) {
        Window dialogWindow = getWindow();
        dialogWindow.addFlags(67108864);
        dialogWindow.setGravity(gravity);
        dialogWindow.setWindowAnimations(R.style.dialogstyle);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = -20;
        lp.width = context.getResources().getDisplayMetrics().widthPixels;
        this.view.measure(0, 0);
        lp.height = this.view.getMeasuredHeight();
        lp.alpha = 9.0f;
        dialogWindow.setAttributes(lp);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    public void showDialog() {
        if (isShowing()) {
            hide();
        }
        show();
    }
}
