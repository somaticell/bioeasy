package com.mob;

import android.content.Context;
import com.alipay.android.phone.mrpc.core.gwprotocol.JsonSerializer;
import com.mob.commons.logcollector.LogsCollector;
import com.mob.tools.log.NLog;

/* compiled from: MobSDKLog */
public class a extends NLog {
    private a(Context context, String str, String str2) {
        setCollector("MOBSDK", new LogsCollector(context) {
            /* access modifiers changed from: protected */
            public int getSDKVersion() {
                return 1;
            }

            /* access modifiers changed from: protected */
            public String getSDKTag() {
                return "MOBSDK";
            }

            /* access modifiers changed from: protected */
            public String getAppkey() {
                return JsonSerializer.VERSION;
            }
        });
    }

    /* access modifiers changed from: protected */
    public String getSDKTag() {
        return "MOBSDK";
    }

    public static NLog a(Context context, String str, String str2) {
        return new a(context, str, str2);
    }
}
