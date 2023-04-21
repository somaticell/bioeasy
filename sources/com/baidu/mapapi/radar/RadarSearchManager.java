package com.baidu.mapapi.radar;

import android.os.Handler;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.platform.comapi.radar.a;
import java.util.Timer;
import java.util.TimerTask;

public class RadarSearchManager {
    /* access modifiers changed from: private */
    public static RadarSearchManager a;
    private static String b = "";
    private Timer c = new Timer();
    private TimerTask d;
    private boolean e = false;
    /* access modifiers changed from: private */
    public Handler f;
    /* access modifiers changed from: private */
    public RadarUploadInfoCallback g;
    /* access modifiers changed from: private */
    public RadarUploadInfo h;
    private long i;

    private RadarSearchManager() {
        BMapManager.init();
    }

    /* access modifiers changed from: private */
    public boolean a(RadarUploadInfo radarUploadInfo) {
        if (radarUploadInfo == null || b == null || b.equals("") || System.currentTimeMillis() - this.i < 5000) {
            return false;
        }
        this.h = radarUploadInfo;
        this.i = System.currentTimeMillis();
        return a.a().a(radarUploadInfo, b);
    }

    public static RadarSearchManager getInstance() {
        if (a == null) {
            a = new RadarSearchManager();
        }
        return a;
    }

    public void addNearbyInfoListener(RadarSearchListener radarSearchListener) {
        if (a != null && radarSearchListener != null) {
            a.a().a(radarSearchListener);
        }
    }

    public void clearUserInfo() {
        if (a != null && b != null && !b.equals("")) {
            a.a().a(b);
        }
    }

    public void destroy() {
        if (a != null) {
            if (this.e) {
                stopUploadAuto();
                this.c.cancel();
            }
            a.a().b();
            BMapManager.destroy();
            a = null;
        }
    }

    public boolean nearbyInfoRequest(RadarNearbySearchOption radarNearbySearchOption) {
        if (a == null || radarNearbySearchOption == null) {
            return false;
        }
        return this.h != null ? a.a().a(radarNearbySearchOption, b, this.h.pt) : a.a().a(radarNearbySearchOption, b, (LatLng) null);
    }

    public void removeNearbyInfoListener(RadarSearchListener radarSearchListener) {
        if (a != null) {
            a.a().b(radarSearchListener);
        }
    }

    public void setUserID(String str) {
        if (a != null) {
            if (str == null || str.equals("")) {
                b = SysOSUtil.getDeviceID();
            } else {
                b = str;
            }
            this.h = null;
        }
    }

    public void startUploadAuto(RadarUploadInfoCallback radarUploadInfoCallback, int i2) {
        if (a != null && i2 >= 5000 && radarUploadInfoCallback != null && !this.e) {
            this.e = true;
            this.g = radarUploadInfoCallback;
            this.f = new a(this);
            this.d = new b(this);
            this.c.schedule(this.d, 1000, (long) i2);
        }
    }

    public void stopUploadAuto() {
        if (a != null && this.e) {
            this.e = false;
            this.g = null;
            this.d.cancel();
            this.f = null;
        }
    }

    public boolean uploadInfoRequest(RadarUploadInfo radarUploadInfo) {
        if (a == null) {
            return false;
        }
        return a(radarUploadInfo);
    }
}
