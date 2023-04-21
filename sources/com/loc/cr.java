package com.loc;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import java.text.SimpleDateFormat;

final class cr implements LocationListener {
    private /* synthetic */ cb a;

    cr(cb cbVar) {
        this.a = cbVar;
    }

    private static boolean a(Location location) {
        return location != null && "gps".equalsIgnoreCase(location.getProvider()) && location.getLatitude() > -90.0d && location.getLatitude() < 90.0d && location.getLongitude() > -180.0d && location.getLongitude() < 180.0d;
    }

    public final void onLocationChanged(Location location) {
        try {
            long time = location.getTime();
            long currentTimeMillis = System.currentTimeMillis();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleDateFormat.format(Long.valueOf(time));
            simpleDateFormat.format(Long.valueOf(currentTimeMillis));
            if (time > 0) {
                currentTimeMillis = time;
            }
            if (location != null && a(location)) {
                if (location.getSpeed() > ((float) cb.e)) {
                    cz.a(cb.h);
                    cz.b(cb.h * 10);
                } else if (location.getSpeed() > ((float) cb.d)) {
                    cz.a(cb.g);
                    cz.b(cb.g * 10);
                } else {
                    cz.a(cb.f);
                    cz.b(cb.f * 10);
                }
                this.a.v.a();
                a(location);
                if (this.a.v.a() && a(location)) {
                    location.setTime(System.currentTimeMillis());
                    cb.a(this.a, location, 0, currentTimeMillis);
                }
            }
        } catch (Exception e) {
        }
    }

    public final void onProviderDisabled(String str) {
    }

    public final void onProviderEnabled(String str) {
    }

    public final void onStatusChanged(String str, int i, Bundle bundle) {
    }
}
