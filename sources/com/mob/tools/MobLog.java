package com.mob.tools;

import com.mob.tools.log.NLog;

public class MobLog extends NLog {
    private MobLog() {
    }

    /* access modifiers changed from: protected */
    public String getSDKTag() {
        return "MOBTOOLS";
    }

    public static NLog getInstance() {
        return getInstanceForSDK("MOBTOOLS", true);
    }
}
