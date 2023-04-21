package cn.sharesdk.wechat.utils;

import android.os.Bundle;

/* compiled from: WechatReq */
public abstract class j {
    public String c;

    public abstract int a();

    public abstract boolean b();

    public void a(Bundle bundle) {
        bundle.putInt("_wxapi_command_type", a());
        bundle.putString("_wxapi_basereq_transaction", this.c);
    }
}
