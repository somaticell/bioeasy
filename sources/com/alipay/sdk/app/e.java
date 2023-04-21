package com.alipay.sdk.app;

import android.content.DialogInterface;

final class e implements DialogInterface.OnClickListener {
    final /* synthetic */ c a;

    e(c cVar) {
        this.a = cVar;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.a.a.cancel();
        boolean unused = this.a.b.d = false;
        h.a = h.a();
        this.a.b.a.finish();
    }
}
