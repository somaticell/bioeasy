package com.alipay.sdk.auth;

import android.app.Activity;
import android.content.DialogInterface;

final class g implements DialogInterface.OnClickListener {
    final /* synthetic */ e a;

    g(e eVar) {
        this.a = eVar;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.a.a.cancel();
        boolean unused = AuthActivity.this.g = false;
        h.a((Activity) AuthActivity.this, AuthActivity.this.d + "?resultCode=150");
        AuthActivity.this.finish();
    }
}
