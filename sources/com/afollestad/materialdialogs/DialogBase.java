package com.afollestad.materialdialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import com.afollestad.materialdialogs.internal.MDRootLayout;

class DialogBase extends Dialog implements DialogInterface.OnShowListener {
    private DialogInterface.OnShowListener mShowListener;
    protected MDRootLayout view;

    protected DialogBase(Context context, int theme) {
        super(context, theme);
    }

    public View findViewById(int id) {
        return this.view.findViewById(id);
    }

    public final void setOnShowListener(DialogInterface.OnShowListener listener) {
        this.mShowListener = listener;
    }

    /* access modifiers changed from: protected */
    public final void setOnShowListenerInternal() {
        super.setOnShowListener(this);
    }

    /* access modifiers changed from: protected */
    public final void setViewInternal(View view2) {
        super.setContentView(view2);
    }

    public void onShow(DialogInterface dialog) {
        if (this.mShowListener != null) {
            this.mShowListener.onShow(dialog);
        }
    }

    @Deprecated
    public void setContentView(int layoutResID) throws IllegalAccessError {
        throw new IllegalAccessError("setContentView() is not supported in MaterialDialog. Specify a custom view in the Builder instead.");
    }

    @Deprecated
    public void setContentView(View view2) throws IllegalAccessError {
        throw new IllegalAccessError("setContentView() is not supported in MaterialDialog. Specify a custom view in the Builder instead.");
    }

    @Deprecated
    public void setContentView(View view2, ViewGroup.LayoutParams params) throws IllegalAccessError {
        throw new IllegalAccessError("setContentView() is not supported in MaterialDialog. Specify a custom view in the Builder instead.");
    }
}
