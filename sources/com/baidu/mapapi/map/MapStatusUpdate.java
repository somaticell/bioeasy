package com.baidu.mapapi.map;

import android.graphics.Point;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.e;

public final class MapStatusUpdate {
    private static final String l = MapStatusUpdate.class.getSimpleName();
    int a;
    MapStatus b;
    LatLng c;
    LatLngBounds d;
    int e;
    int f;
    float g;
    int h;
    int i;
    float j;
    Point k;

    MapStatusUpdate() {
    }

    MapStatusUpdate(int i2) {
        this.a = i2;
    }

    /* access modifiers changed from: package-private */
    public MapStatus a(e eVar, MapStatus mapStatus) {
        if (eVar == null || mapStatus == null) {
            return null;
        }
        switch (this.a) {
            case 1:
                return this.b;
            case 2:
                return new MapStatus(mapStatus.rotate, this.c, mapStatus.overlook, mapStatus.zoom, mapStatus.targetScreen, (LatLngBounds) null);
            case 3:
                GeoPoint ll2mc = CoordUtil.ll2mc(this.d.southwest);
                GeoPoint ll2mc2 = CoordUtil.ll2mc(this.d.northeast);
                double longitudeE6 = ll2mc.getLongitudeE6();
                double latitudeE6 = ll2mc2.getLatitudeE6();
                double longitudeE62 = ll2mc2.getLongitudeE6();
                e eVar2 = eVar;
                float a2 = eVar2.a((int) longitudeE6, (int) latitudeE6, (int) longitudeE62, (int) ll2mc.getLatitudeE6(), mapStatus.a.j.right - mapStatus.a.j.left, mapStatus.a.j.bottom - mapStatus.a.j.top);
                return new MapStatus(mapStatus.rotate, this.d.getCenter(), mapStatus.overlook, a2, mapStatus.targetScreen, (LatLngBounds) null);
            case 4:
                return new MapStatus(mapStatus.rotate, this.c, mapStatus.overlook, this.g, mapStatus.targetScreen, (LatLngBounds) null);
            case 5:
                eVar.G();
                GeoPoint b2 = eVar.b((eVar.G() / 2) + this.h, (eVar.H() / 2) + this.i);
                return new MapStatus(mapStatus.rotate, CoordUtil.mc2ll(b2), mapStatus.overlook, mapStatus.zoom, mapStatus.targetScreen, b2.getLongitudeE6(), b2.getLatitudeE6(), (LatLngBounds) null);
            case 6:
                return new MapStatus(mapStatus.rotate, mapStatus.target, mapStatus.overlook, mapStatus.zoom + this.j, mapStatus.targetScreen, mapStatus.a(), mapStatus.b(), (LatLngBounds) null);
            case 7:
                return new MapStatus(mapStatus.rotate, CoordUtil.mc2ll(eVar.b(this.k.x, this.k.y)), mapStatus.overlook, mapStatus.zoom + this.j, this.k, (LatLngBounds) null);
            case 8:
                return new MapStatus(mapStatus.rotate, mapStatus.target, mapStatus.overlook, this.g, mapStatus.targetScreen, mapStatus.a(), mapStatus.b(), (LatLngBounds) null);
            case 9:
                GeoPoint ll2mc3 = CoordUtil.ll2mc(this.d.southwest);
                GeoPoint ll2mc4 = CoordUtil.ll2mc(this.d.northeast);
                e eVar3 = eVar;
                float a3 = eVar3.a((int) ll2mc3.getLongitudeE6(), (int) ll2mc4.getLatitudeE6(), (int) ll2mc4.getLongitudeE6(), (int) ll2mc3.getLatitudeE6(), this.e, this.f);
                return new MapStatus(mapStatus.rotate, this.d.getCenter(), mapStatus.overlook, a3, mapStatus.targetScreen, (LatLngBounds) null);
            default:
                return null;
        }
    }
}
