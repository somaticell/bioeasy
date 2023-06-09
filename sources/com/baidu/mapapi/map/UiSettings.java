package com.baidu.mapapi.map;

import com.baidu.platform.comapi.map.e;

public final class UiSettings {
    private e a;

    UiSettings(e eVar) {
        this.a = eVar;
    }

    public boolean isCompassEnabled() {
        return this.a.r();
    }

    public boolean isOverlookingGesturesEnabled() {
        return this.a.z();
    }

    public boolean isRotateGesturesEnabled() {
        return this.a.y();
    }

    public boolean isScrollGesturesEnabled() {
        return this.a.w();
    }

    public boolean isZoomGesturesEnabled() {
        return this.a.x();
    }

    public void setAllGesturesEnabled(boolean z) {
        setRotateGesturesEnabled(z);
        setScrollGesturesEnabled(z);
        setOverlookingGesturesEnabled(z);
        setZoomGesturesEnabled(z);
    }

    public void setCompassEnabled(boolean z) {
        this.a.i(z);
    }

    public void setOverlookingGesturesEnabled(boolean z) {
        this.a.q(z);
    }

    public void setRotateGesturesEnabled(boolean z) {
        this.a.p(z);
    }

    public void setScrollGesturesEnabled(boolean z) {
        this.a.n(z);
    }

    public void setZoomGesturesEnabled(boolean z) {
        this.a.o(z);
    }
}
