package com.alipay.sdk.widget;

import android.content.DialogInterface;
import android.view.KeyEvent;

final class e implements DialogInterface.OnKeyListener {
    e() {
    }

    public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i == 4) {
            return true;
        }
        return false;
    }
}
