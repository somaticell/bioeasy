package cn.sharesdk.wechat.utils;

import android.os.Bundle;
import cn.sharesdk.wechat.utils.WXMediaMessage;

/* compiled from: ShowMessageFromWechatResp */
public class f extends WechatResp {
    public WXMediaMessage a;

    public f(Bundle bundle) {
        super(bundle);
    }

    public int a() {
        return 4;
    }

    public void a(Bundle bundle) {
        super.a(bundle);
        this.a = WXMediaMessage.a.a(bundle);
    }

    public void b(Bundle bundle) {
        super.b(bundle);
        bundle.putAll(WXMediaMessage.a.a(this.a));
    }
}
