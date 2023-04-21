package com.alipay.sdk.app;

import android.content.DialogInterface;

final class d implements DialogInterface.OnClickListener {
    final /* synthetic */ c a;

    d(c cVar) {
        this.a = cVar;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        boolean unused = this.a.b.d = true;
        this.a.a.proceed();
        dialogInterface.dismiss();
    }
}
