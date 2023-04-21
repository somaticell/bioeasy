package cn.com.bioeasy.app;

import dagger.internal.Factory;

public enum RxBus_Factory implements Factory<RxBus> {
    INSTANCE;

    public RxBus get() {
        return new RxBus();
    }

    public static Factory<RxBus> create() {
        return INSTANCE;
    }
}
