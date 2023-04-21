package com.mob;

import android.app.Application;

public class MobApplication extends Application {
    /* access modifiers changed from: protected */
    public String a() {
        return null;
    }

    /* access modifiers changed from: protected */
    public String b() {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        MobSDK.init(this, a(), b());
    }
}
