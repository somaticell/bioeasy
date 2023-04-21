package com.loc;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Message;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.DPoint;

/* compiled from: GPSLocation */
class h implements LocationListener {
    final /* synthetic */ g a;

    h(g gVar) {
        this.a = gVar;
    }

    public void onLocationChanged(Location location) {
        int i;
        AMapLocation aMapLocation;
        if (location != null) {
            try {
                Bundle extras = location.getExtras();
                if (extras != null) {
                    i = extras.getInt("satellites");
                } else {
                    i = 0;
                }
                if (i > 0 || this.a.d.isMockEnable()) {
                    if (this.a.a != null) {
                        this.a.a.sendEmptyMessage(5);
                    }
                    if (bw.b() - this.a.f > this.a.e) {
                        if (!e.a(location.getLatitude(), location.getLongitude()) || !this.a.d.isOffset()) {
                            aMapLocation = new AMapLocation(location);
                            aMapLocation.setLatitude(location.getLatitude());
                            aMapLocation.setLongitude(location.getLongitude());
                            aMapLocation.setLocationType(1);
                        } else {
                            aMapLocation = new AMapLocation(location);
                            aMapLocation.setLocationType(1);
                            DPoint a2 = j.a(this.a.b, location.getLongitude(), location.getLatitude());
                            aMapLocation.setLatitude(a2.getLatitude());
                            aMapLocation.setLongitude(a2.getLongitude());
                        }
                        aMapLocation.setSatellites(i);
                        Message message = new Message();
                        message.obj = aMapLocation;
                        message.what = 2;
                        if (this.a.a != null) {
                            this.a.a.sendMessage(message);
                        }
                        this.a.f = bw.b();
                    }
                }
            } catch (Throwable th) {
            }
        }
    }

    public void onProviderDisabled(String str) {
        try {
            if ("gps".equals(str)) {
                this.a.a.sendEmptyMessage(3);
            }
        } catch (Throwable th) {
        }
    }

    public void onProviderEnabled(String str) {
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
        if (i == 0 || i == 1) {
            try {
                this.a.a.sendEmptyMessage(3);
            } catch (Throwable th) {
            }
        }
    }
}
