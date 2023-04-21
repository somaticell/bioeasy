package com.loc;

import com.amap.api.location.AMapLocationClientOption;

/* compiled from: AMapLocationManager */
/* synthetic */ class c {
    static final /* synthetic */ int[] a = new int[AMapLocationClientOption.AMapLocationMode.values().length];

    static {
        try {
            a[AMapLocationClientOption.AMapLocationMode.Battery_Saving.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            a[AMapLocationClientOption.AMapLocationMode.Device_Sensors.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            a[AMapLocationClientOption.AMapLocationMode.Hight_Accuracy.ordinal()] = 3;
        } catch (NoSuchFieldError e3) {
        }
    }
}
