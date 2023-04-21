package cn.sharesdk.framework.utils;

import android.content.Context;
import cn.sharesdk.framework.ShareSDK;
import com.mob.commons.logcollector.LogsCollector;
import com.mob.tools.log.NLog;

/* compiled from: SSDKLog */
public class d extends NLog {
    private d(Context context, final int i, final String str) {
        setCollector(ShareSDK.SDK_TAG, new LogsCollector(context) {
            /* access modifiers changed from: protected */
            public int getSDKVersion() {
                return i;
            }

            /* access modifiers changed from: protected */
            public String getSDKTag() {
                return ShareSDK.SDK_TAG;
            }

            /* access modifiers changed from: protected */
            public String getAppkey() {
                return str;
            }
        });
    }

    /* access modifiers changed from: protected */
    public String getSDKTag() {
        return ShareSDK.SDK_TAG;
    }

    public static NLog a(Context context, int i, String str) {
        return new d(context, i, str);
    }

    public static NLog a() {
        return getInstanceForSDK(ShareSDK.SDK_TAG, true);
    }
}
