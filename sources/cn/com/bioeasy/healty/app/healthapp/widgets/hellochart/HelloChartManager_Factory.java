package cn.com.bioeasy.healty.app.healthapp.widgets.hellochart;

import dagger.internal.Factory;

public enum HelloChartManager_Factory implements Factory<HelloChartManager> {
    INSTANCE;

    public HelloChartManager get() {
        return new HelloChartManager();
    }

    public static Factory<HelloChartManager> create() {
        return INSTANCE;
    }
}
