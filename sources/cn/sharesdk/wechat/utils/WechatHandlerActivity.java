package cn.sharesdk.wechat.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import cn.sharesdk.framework.utils.d;

public class WechatHandlerActivity extends Activity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        setTheme(16973839);
        super.onCreate(savedInstanceState);
        try {
            WechatHelper.a().a(this);
        } catch (Throwable th) {
            d.a().d(th);
        }
        finish();
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        try {
            WechatHelper.a().a(this);
        } catch (Throwable th) {
            d.a().d(th);
        }
        finish();
    }

    public void onGetMessageFromWXReq(WXMediaMessage msg) {
    }

    public void onShowMessageFromWXReq(WXMediaMessage msg) {
    }
}
