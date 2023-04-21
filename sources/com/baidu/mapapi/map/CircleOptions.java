package com.baidu.mapapi.map;

import android.os.Bundle;
import com.baidu.mapapi.model.LatLng;

public final class CircleOptions extends OverlayOptions {
    private static final String d = CircleOptions.class.getSimpleName();
    int a;
    boolean b = true;
    Bundle c;
    private LatLng e;
    private int f = -16777216;
    private int g;
    private Stroke h;

    /* access modifiers changed from: package-private */
    public Overlay a() {
        Circle circle = new Circle();
        circle.r = this.b;
        circle.q = this.a;
        circle.s = this.c;
        circle.b = this.f;
        circle.a = this.e;
        circle.c = this.g;
        circle.d = this.h;
        return circle;
    }

    public CircleOptions center(LatLng latLng) {
        if (latLng == null) {
            throw new IllegalArgumentException("circle center can not be null");
        }
        this.e = latLng;
        return this;
    }

    public CircleOptions extraInfo(Bundle bundle) {
        this.c = bundle;
        return this;
    }

    public CircleOptions fillColor(int i) {
        this.f = i;
        return this;
    }

    public LatLng getCenter() {
        return this.e;
    }

    public Bundle getExtraInfo() {
        return this.c;
    }

    public int getFillColor() {
        return this.f;
    }

    public int getRadius() {
        return this.g;
    }

    public Stroke getStroke() {
        return this.h;
    }

    public int getZIndex() {
        return this.a;
    }

    public boolean isVisible() {
        return this.b;
    }

    public CircleOptions radius(int i) {
        this.g = i;
        return this;
    }

    public CircleOptions stroke(Stroke stroke) {
        this.h = stroke;
        return this;
    }

    public CircleOptions visible(boolean z) {
        this.b = z;
        return this;
    }

    public CircleOptions zIndex(int i) {
        this.a = i;
        return this;
    }
}
