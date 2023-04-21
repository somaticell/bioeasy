package cn.com.bioeasy.app.base;

import dagger.internal.Factory;

public enum AppManager_Factory implements Factory<AppManager> {
    INSTANCE;

    public AppManager get() {
        return new AppManager();
    }

    public static Factory<AppManager> create() {
        return INSTANCE;
    }
}
