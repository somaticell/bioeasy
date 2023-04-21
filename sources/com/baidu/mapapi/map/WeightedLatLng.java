package com.baidu.mapapi.map;

import android.graphics.Point;
import com.baidu.mapapi.map.l;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;

public class WeightedLatLng extends l.a {
    public static final double DEFAULT_INTENSITY = 1.0d;
    private Point a;
    public final double intensity;
    public final LatLng latLng;

    public WeightedLatLng(LatLng latLng2) {
        this(latLng2, 1.0d);
    }

    public WeightedLatLng(LatLng latLng2, double d) {
        if (latLng2 == null) {
            throw new IllegalArgumentException("latLng can not be null");
        }
        this.latLng = latLng2;
        GeoPoint ll2mc = CoordUtil.ll2mc(latLng2);
        this.a = new Point((int) ll2mc.getLongitudeE6(), (int) ll2mc.getLatitudeE6());
        if (d > 0.0d) {
            this.intensity = d;
        } else {
            this.intensity = 1.0d;
        }
    }

    /* access modifiers changed from: package-private */
    public Point a() {
        return this.a;
    }
}
