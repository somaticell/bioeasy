package cn.sharesdk.tencent.qq;

import android.content.Intent;
import android.os.Bundle;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import com.alipay.sdk.util.j;
import com.mob.tools.FakeActivity;
import com.mob.tools.MobUIShell;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.ResHelper;

/* compiled from: ResultReceiver */
public class d extends FakeActivity {
    private String a;
    private Platform b;
    private PlatformActionListener c;

    public void a(String str) {
        this.a = str;
    }

    public void a(Platform platform, PlatformActionListener platformActionListener) {
        this.b = platform;
        this.c = platformActionListener;
    }

    public void onCreate() {
        try {
            Intent intent = this.activity.getIntent();
            String scheme = intent.getScheme();
            finish();
            if (scheme != null && scheme.startsWith(this.a)) {
                Bundle urlToBundle = ResHelper.urlToBundle(intent.getDataString());
                String valueOf = String.valueOf(urlToBundle.get(j.c));
                String valueOf2 = String.valueOf(urlToBundle.get(com.alipay.sdk.packet.d.o));
                if ("shareToQQ".equals(valueOf2) || "shareToQzone".equals(valueOf2)) {
                    if ("complete".equals(valueOf)) {
                        if (this.c != null) {
                            this.c.onComplete(this.b, 9, new Hashon().fromJson(String.valueOf(urlToBundle.get("response"))));
                        }
                    } else if ("error".equals(valueOf)) {
                        if (this.c != null) {
                            this.c.onError(this.b, 9, new Throwable(String.valueOf(urlToBundle.get("response"))));
                        }
                    } else if (this.c != null) {
                        this.c.onCancel(this.b, 9);
                    }
                }
                Intent intent2 = new Intent("android.intent.action.VIEW");
                intent2.setClass(this.activity, MobUIShell.class);
                intent2.setFlags(335544320);
                startActivity(intent2);
            }
        } catch (Throwable th) {
            cn.sharesdk.framework.utils.d.a().d(th);
        }
    }
}
