package com.baidu.mapapi.map;

import android.os.Bundle;
import android.support.v7.widget.ActivityChooserView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

public final class GroundOverlayOptions extends OverlayOptions {
    int a;
    boolean b = true;
    Bundle c;
    private BitmapDescriptor d;
    private LatLng e;
    private int f;
    private int g;
    private float h = 0.5f;
    private float i = 0.5f;
    private LatLngBounds j;
    private float k = 1.0f;

    /* access modifiers changed from: package-private */
    public Overlay a() {
        GroundOverlay groundOverlay = new GroundOverlay();
        groundOverlay.r = this.b;
        groundOverlay.q = this.a;
        groundOverlay.s = this.c;
        if (this.d == null) {
            throw new IllegalStateException("when you add ground overlay, you must set the image");
        }
        groundOverlay.b = this.d;
        if (this.j != null || this.e == null) {
            if (this.e != null || this.j == null) {
                throw new IllegalStateException("when you add ground overlay, you must set one of position or bounds");
            }
            groundOverlay.h = this.j;
            groundOverlay.a = 1;
        } else if (this.f <= 0 || this.g <= 0) {
            throw new IllegalArgumentException("when you add ground overlay, the width and height must greater than 0");
        } else {
            groundOverlay.c = this.e;
            groundOverlay.f = this.h;
            groundOverlay.g = this.i;
            groundOverlay.d = (double) this.f;
            groundOverlay.e = (double) this.g;
            groundOverlay.a = 2;
        }
        groundOverlay.i = this.k;
        return groundOverlay;
    }

    public GroundOverlayOptions anchor(float f2, float f3) {
        if (f2 >= 0.0f && f2 <= 1.0f && f3 >= 0.0f && f3 <= 1.0f) {
            this.h = f2;
            this.i = f3;
        }
        return this;
    }

    public GroundOverlayOptions dimensions(int i2) {
        this.f = i2;
        this.g = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        return this;
    }

    public GroundOverlayOptions dimensions(int i2, int i3) {
        this.f = i2;
        this.g = i3;
        return this;
    }

    public GroundOverlayOptions extraInfo(Bundle bundle) {
        this.c = bundle;
        return this;
    }

    public float getAnchorX() {
        return this.h;
    }

    public float getAnchorY() {
        return this.i;
    }

    public LatLngBounds getBounds() {
        return this.j;
    }

    public Bundle getExtraInfo() {
        return this.c;
    }

    public int getHeight() {
        return this.g == Integer.MAX_VALUE ? (int) (((float) (this.f * this.d.a.getHeight())) / ((float) this.d.a.getWidth())) : this.g;
    }

    public BitmapDescriptor getImage() {
        return this.d;
    }

    public LatLng getPosition() {
        return this.e;
    }

    public float getTransparency() {
        return this.k;
    }

    public int getWidth() {
        return this.f;
    }

    public int getZIndex() {
        return this.a;
    }

    public GroundOverlayOptions image(BitmapDescriptor bitmapDescriptor) {
        if (bitmapDescriptor == null) {
            throw new IllegalArgumentException("image can not be null");
        }
        this.d = bitmapDescriptor;
        return this;
    }

    public boolean isVisible() {
        return this.b;
    }

    public GroundOverlayOptions position(LatLng latLng) {
        if (latLng == null) {
            throw new IllegalArgumentException("position can not be null");
        }
        this.e = latLng;
        return this;
    }

    public GroundOverlayOptions positionFromBounds(LatLngBounds latLngBounds) {
        if (latLngBounds == null) {
            throw new IllegalArgumentException("bounds can not be null");
        }
        this.j = latLngBounds;
        return this;
    }

    public GroundOverlayOptions transparency(float f2) {
        if (f2 <= 1.0f && f2 >= 0.0f) {
            this.k = f2;
        }
        return this;
    }

    public GroundOverlayOptions visible(boolean z) {
        this.b = z;
        return this;
    }

    public GroundOverlayOptions zIndex(int i2) {
        this.a = i2;
        return this;
    }
}
