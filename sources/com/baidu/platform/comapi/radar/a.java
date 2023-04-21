package com.baidu.platform.comapi.radar;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.radar.RadarNearbyResult;
import com.baidu.mapapi.radar.RadarNearbySearchOption;
import com.baidu.mapapi.radar.RadarSearchError;
import com.baidu.mapapi.radar.RadarSearchListener;
import com.baidu.mapapi.radar.RadarUploadInfo;
import com.baidu.platform.comjni.map.radar.IRadarCenter;
import java.util.ArrayList;
import java.util.Iterator;

public class a implements b {
    private static a e = null;
    int a = 0;
    int b = 0;
    int c = 0;
    private IRadarCenter d = null;
    private ArrayList<RadarSearchListener> f = new ArrayList<>();

    public static a a() {
        if (e == null) {
            synchronized (a.class) {
                if (e == null) {
                    e = new a();
                    e.c();
                }
            }
        }
        return e;
    }

    private boolean c() {
        if (this.d != null) {
            return true;
        }
        this.d = new com.baidu.platform.comjni.map.radar.a();
        this.d.a((b) this);
        return true;
    }

    public void a(RadarSearchListener radarSearchListener) {
        if (this.f == null) {
            this.f = new ArrayList<>();
        }
        this.f.add(radarSearchListener);
    }

    public boolean a(RadarNearbySearchOption radarNearbySearchOption, String str, LatLng latLng) {
        this.a = 30002;
        this.c = radarNearbySearchOption.mPageNum;
        this.b = radarNearbySearchOption.mPageCapacity;
        return this.d.b(c.a(radarNearbySearchOption, str, latLng));
    }

    public boolean a(RadarUploadInfo radarUploadInfo, String str) {
        return this.d.a(c.a(radarUploadInfo, str));
    }

    public boolean a(String str) {
        if (str == null || str.equals("")) {
            return false;
        }
        this.a = 30003;
        return this.d.b(c.a(str));
    }

    public void b() {
        if (e != null) {
            if (e.d != null) {
                e.d = null;
            }
            e = null;
        }
    }

    public void b(RadarSearchListener radarSearchListener) {
        if (this.f.contains(radarSearchListener)) {
            this.f.remove(radarSearchListener);
        }
    }

    public void b(String str) {
        RadarSearchError a2 = d.a(str);
        switch (this.a) {
            case 30002:
                if (this.f != null && this.f.size() > 0) {
                    if (a2 != RadarSearchError.RADAR_NO_ERROR) {
                        Iterator<RadarSearchListener> it = this.f.iterator();
                        while (it.hasNext()) {
                            it.next().onGetNearbyInfoList((RadarNearbyResult) null, a2);
                        }
                        return;
                    }
                    RadarNearbyResult a3 = d.a(str, this.c, this.b);
                    if (a3 == null || a3.infoList == null || a3.infoList.size() <= 0) {
                        Iterator<RadarSearchListener> it2 = this.f.iterator();
                        while (it2.hasNext()) {
                            it2.next().onGetNearbyInfoList(a3, RadarSearchError.RADAR_NO_RESULT);
                        }
                        return;
                    }
                    Iterator<RadarSearchListener> it3 = this.f.iterator();
                    while (it3.hasNext()) {
                        it3.next().onGetNearbyInfoList(a3, a2);
                    }
                    return;
                }
                return;
            case 30003:
                if (this.f != null && this.f.size() > 0) {
                    Iterator<RadarSearchListener> it4 = this.f.iterator();
                    while (it4.hasNext()) {
                        it4.next().onGetClearInfoState(a2);
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void c(String str) {
        if (this.f != null && this.f.size() > 0) {
            RadarSearchError a2 = d.a(str);
            Iterator<RadarSearchListener> it = this.f.iterator();
            while (it.hasNext()) {
                it.next().onGetUploadState(a2);
            }
        }
    }
}
