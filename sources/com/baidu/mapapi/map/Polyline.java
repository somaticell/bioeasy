package com.baidu.mapapi.map;

import android.os.Bundle;
import android.util.Log;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.h;
import java.util.List;
import org.litepal.util.Const;

public final class Polyline extends Overlay {
    int a;
    List<LatLng> b;
    int[] c;
    int[] d;
    int e;
    boolean f;
    boolean g = false;
    boolean h = true;
    BitmapDescriptor i;
    List<BitmapDescriptor> j;

    Polyline() {
        this.type = h.polyline;
    }

    private Bundle a(boolean z) {
        return z ? BitmapDescriptorFactory.fromAsset("lineDashTexture.png").b() : this.i.b();
    }

    static void a(int[] iArr, Bundle bundle) {
        if (iArr != null && iArr.length > 0) {
            bundle.putIntArray("traffic_array", iArr);
        }
    }

    private Bundle b(boolean z) {
        if (z) {
            Bundle bundle = new Bundle();
            bundle.putInt("total", 1);
            bundle.putBundle("texture_0", BitmapDescriptorFactory.fromAsset("lineDashTexture.png").b());
            return bundle;
        }
        Bundle bundle2 = new Bundle();
        int i2 = 0;
        for (int i3 = 0; i3 < this.j.size(); i3++) {
            if (this.j.get(i3) != null) {
                bundle2.putBundle("texture_" + String.valueOf(i2), this.j.get(i3).b());
                i2++;
            }
        }
        bundle2.putInt("total", i2);
        return bundle2;
    }

    static void b(int[] iArr, Bundle bundle) {
        if (iArr != null && iArr.length > 0) {
            bundle.putIntArray("color_array", iArr);
            bundle.putInt("total", 1);
        }
    }

    /* access modifiers changed from: package-private */
    public Bundle a(Bundle bundle) {
        int i2 = 1;
        super.a(bundle);
        GeoPoint ll2mc = CoordUtil.ll2mc(this.b.get(0));
        bundle.putDouble("location_x", ll2mc.getLongitudeE6());
        bundle.putDouble("location_y", ll2mc.getLatitudeE6());
        bundle.putInt("width", this.e);
        Overlay.a(this.b, bundle);
        Overlay.a(this.a, bundle);
        a(this.c, bundle);
        b(this.d, bundle);
        if (this.c != null && this.c.length > 0 && this.c.length > this.b.size() - 1) {
            Log.e("baidumapsdk", "the size of textureIndexs is larger than the size of points");
        }
        if (this.f) {
            bundle.putInt("dotline", 1);
        } else {
            bundle.putInt("dotline", 0);
        }
        bundle.putInt("focus", this.g ? 1 : 0);
        try {
            if (this.i != null) {
                bundle.putInt("custom", 1);
                bundle.putBundle("image_info", a(false));
            } else {
                if (this.f) {
                    bundle.putBundle("image_info", a(true));
                }
                bundle.putInt("custom", 0);
            }
            if (this.j != null) {
                bundle.putInt("customlist", 1);
                bundle.putBundle("image_info_list", b(false));
            } else {
                if (this.f && ((this.c != null && this.c.length > 0) || (this.d != null && this.d.length > 0))) {
                    bundle.putBundle("image_info_list", b(true));
                }
                bundle.putInt("customlist", 0);
            }
            if (!this.h) {
                i2 = 0;
            }
            bundle.putInt(Const.Config.CASES_KEEP, i2);
        } catch (Exception e2) {
            Log.e("baidumapsdk", "load texture resource failed!");
            bundle.putInt("dotline", 0);
        }
        return bundle;
    }

    public int getColor() {
        return this.a;
    }

    public List<LatLng> getPoints() {
        return this.b;
    }

    public int getWidth() {
        return this.e;
    }

    public boolean isDottedLine() {
        return this.f;
    }

    public boolean isFocus() {
        return this.g;
    }

    public void setColor(int i2) {
        this.a = i2;
        this.listener.b(this);
    }

    public void setDottedLine(boolean z) {
        this.f = z;
        this.listener.b(this);
    }

    public void setFocus(boolean z) {
        this.g = z;
        this.listener.b(this);
    }

    public void setPoints(List<LatLng> list) {
        if (list == null) {
            throw new IllegalArgumentException("points list can not be null");
        } else if (list.size() < 2) {
            throw new IllegalArgumentException("points count can not less than 2 or more than 10000");
        } else if (list.contains((Object) null)) {
            throw new IllegalArgumentException("points list can not contains null");
        } else {
            this.b = list;
            this.listener.b(this);
        }
    }

    public void setWidth(int i2) {
        if (i2 > 0) {
            this.e = i2;
            this.listener.b(this);
        }
    }
}
