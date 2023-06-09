package com.baidu.mapapi.map;

import android.os.Bundle;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.h;

public final class GroundOverlay extends Overlay {
    private static final String j = GroundOverlay.class.getSimpleName();
    int a;
    BitmapDescriptor b;
    LatLng c;
    double d;
    double e;
    float f;
    float g;
    LatLngBounds h;
    float i;

    GroundOverlay() {
        this.type = h.ground;
    }

    /* access modifiers changed from: package-private */
    public Bundle a(Bundle bundle) {
        super.a(bundle);
        bundle.putBundle("image_info", this.b.b());
        if (this.a == 1) {
            GeoPoint ll2mc = CoordUtil.ll2mc(this.h.southwest);
            double longitudeE6 = ll2mc.getLongitudeE6();
            double latitudeE6 = ll2mc.getLatitudeE6();
            GeoPoint ll2mc2 = CoordUtil.ll2mc(this.h.northeast);
            double longitudeE62 = ll2mc2.getLongitudeE6();
            double latitudeE62 = ll2mc2.getLatitudeE6();
            this.d = longitudeE62 - longitudeE6;
            this.e = latitudeE62 - latitudeE6;
            this.c = CoordUtil.mc2ll(new GeoPoint(latitudeE6 + (this.e / 2.0d), longitudeE6 + (this.d / 2.0d)));
            this.f = 0.5f;
            this.g = 0.5f;
        }
        if (this.d <= 0.0d || this.e <= 0.0d) {
            throw new IllegalStateException("when you add ground overlay, the width and height must greater than 0");
        }
        bundle.putDouble("x_distance", this.d);
        if (this.e == 2.147483647E9d) {
            this.e = (double) ((int) ((this.d * ((double) this.b.a.getHeight())) / ((double) ((float) this.b.a.getWidth()))));
        }
        bundle.putDouble("y_distance", this.e);
        GeoPoint ll2mc3 = CoordUtil.ll2mc(this.c);
        bundle.putDouble("location_x", ll2mc3.getLongitudeE6());
        bundle.putDouble("location_y", ll2mc3.getLatitudeE6());
        bundle.putFloat("anchor_x", this.f);
        bundle.putFloat("anchor_y", this.g);
        bundle.putFloat("transparency", this.i);
        return bundle;
    }

    public float getAnchorX() {
        return this.f;
    }

    public float getAnchorY() {
        return this.g;
    }

    public LatLngBounds getBounds() {
        return this.h;
    }

    public double getHeight() {
        return this.e;
    }

    public BitmapDescriptor getImage() {
        return this.b;
    }

    public LatLng getPosition() {
        return this.c;
    }

    public float getTransparency() {
        return this.i;
    }

    public double getWidth() {
        return this.d;
    }

    public void setAnchor(float f2, float f3) {
        if (f2 >= 0.0f && f2 <= 1.0f && f3 >= 0.0f && f3 <= 1.0f) {
            this.f = f2;
            this.g = f3;
            this.listener.b(this);
        }
    }

    public void setDimensions(int i2) {
        this.d = (double) i2;
        this.e = 2.147483647E9d;
        this.listener.b(this);
    }

    public void setDimensions(int i2, int i3) {
        this.d = (double) i2;
        this.e = (double) i3;
        this.listener.b(this);
    }

    public void setImage(BitmapDescriptor bitmapDescriptor) {
        if (bitmapDescriptor == null) {
            throw new IllegalArgumentException("image can not be null");
        }
        this.b = bitmapDescriptor;
        this.listener.b(this);
    }

    public void setPosition(LatLng latLng) {
        if (latLng == null) {
            throw new IllegalArgumentException("position can not be null");
        }
        this.a = 2;
        this.c = latLng;
        this.listener.b(this);
    }

    public void setPositionFromBounds(LatLngBounds latLngBounds) {
        if (latLngBounds == null) {
            throw new IllegalArgumentException("bounds can not be null");
        }
        this.a = 1;
        this.h = latLngBounds;
        this.listener.b(this);
    }

    public void setTransparency(float f2) {
        if (f2 <= 1.0f && f2 >= 0.0f) {
            this.i = f2;
            this.listener.b(this);
        }
    }
}
