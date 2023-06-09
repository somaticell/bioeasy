package com.baidu.mapapi.map;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.h;
import vi.com.gdi.bgl.android.java.EnvDrawText;

public final class Text extends Overlay {
    private static final String k = Text.class.getSimpleName();
    String a;
    LatLng b;
    int c;
    int d;
    int e;
    Typeface f;
    int g;
    int h;
    float i;
    int j;

    Text() {
        this.type = h.text;
    }

    /* access modifiers changed from: package-private */
    public Bundle a() {
        if (this.f != null) {
            EnvDrawText.removeFontCache(this.f.hashCode());
        }
        return super.a();
    }

    /* access modifiers changed from: package-private */
    public Bundle a(Bundle bundle) {
        float f2;
        float f3 = 0.5f;
        super.a(bundle);
        if (this.b == null) {
            throw new IllegalStateException("when you add a text overlay, you must provide text and the position info.");
        }
        bundle.putString("text", this.a);
        GeoPoint ll2mc = CoordUtil.ll2mc(this.b);
        bundle.putDouble("location_x", ll2mc.getLongitudeE6());
        bundle.putDouble("location_y", ll2mc.getLatitudeE6());
        bundle.putInt("font_color", Color.argb(this.d >>> 24, this.d & 255, (this.d >> 8) & 255, (this.d >> 16) & 255));
        bundle.putInt("bg_color", Color.argb(this.c >>> 24, this.c & 255, (this.c >> 8) & 255, (this.c >> 16) & 255));
        bundle.putInt("font_size", this.e);
        if (this.f != null) {
            EnvDrawText.registFontCache(this.f.hashCode(), this.f);
            bundle.putInt("type_face", this.f.hashCode());
        }
        switch (this.g) {
            case 1:
                f2 = 0.0f;
                break;
            case 2:
                f2 = 1.0f;
                break;
            case 4:
                f2 = 0.5f;
                break;
            default:
                f2 = 0.5f;
                break;
        }
        bundle.putFloat("align_x", f2);
        switch (this.h) {
            case 8:
                f3 = 0.0f;
                break;
            case 16:
                f3 = 1.0f;
                break;
        }
        bundle.putFloat("align_y", f3);
        bundle.putFloat("rotate", this.i);
        bundle.putInt("update", this.j);
        return bundle;
    }

    public float getAlignX() {
        return (float) this.g;
    }

    public float getAlignY() {
        return (float) this.h;
    }

    public int getBgColor() {
        return this.c;
    }

    public int getFontColor() {
        return this.d;
    }

    public int getFontSize() {
        return this.e;
    }

    public LatLng getPosition() {
        return this.b;
    }

    public float getRotate() {
        return this.i;
    }

    public String getText() {
        return this.a;
    }

    public Typeface getTypeface() {
        return this.f;
    }

    public void setAlign(int i2, int i3) {
        this.g = i2;
        this.h = i3;
        this.j = 1;
        this.listener.b(this);
    }

    public void setBgColor(int i2) {
        this.c = i2;
        this.j = 1;
        this.listener.b(this);
    }

    public void setFontColor(int i2) {
        this.d = i2;
        this.j = 1;
        this.listener.b(this);
    }

    public void setFontSize(int i2) {
        this.e = i2;
        this.j = 1;
        this.listener.b(this);
    }

    public void setPosition(LatLng latLng) {
        if (latLng == null) {
            throw new IllegalArgumentException("position can not be null");
        }
        this.b = latLng;
        this.j = 1;
        this.listener.b(this);
    }

    public void setRotate(float f2) {
        this.i = f2;
        this.j = 1;
        this.listener.b(this);
    }

    public void setText(String str) {
        if (str == null || str.equals("")) {
            throw new IllegalArgumentException("text can not be null or empty");
        }
        this.a = str;
        this.j = 1;
        this.listener.b(this);
    }

    public void setTypeface(Typeface typeface) {
        this.f = typeface;
        this.j = 1;
        this.listener.b(this);
    }
}
