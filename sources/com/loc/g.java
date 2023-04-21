package com.loc;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.alipay.android.phone.mrpc.core.Headers;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.loc.a;

/* compiled from: GPSLocation */
public class g {
    Handler a;
    Context b;
    LocationManager c;
    AMapLocationClientOption d;
    long e = 1000;
    long f = 0;
    LocationListener g = new h(this);

    public g(Context context, a.b bVar) {
        this.b = context;
        this.a = bVar;
        this.c = (LocationManager) this.b.getSystemService(Headers.LOCATION);
    }

    public void a(AMapLocationClientOption aMapLocationClientOption) {
        this.d = aMapLocationClientOption;
        a(this.d.getInterval(), 0.0f);
    }

    public void a() {
        if (this.c != null && this.g != null) {
            this.c.removeUpdates(this.g);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(long j, float f2) {
        try {
            Looper myLooper = Looper.myLooper();
            if (myLooper == null) {
                myLooper = this.b.getMainLooper();
            }
            this.e = j;
            this.c.requestLocationUpdates("gps", 1000, f2, this.g, myLooper);
        } catch (SecurityException e2) {
            e2.printStackTrace();
            Message obtain = Message.obtain();
            AMapLocation aMapLocation = new AMapLocation("");
            aMapLocation.setProvider("gps");
            aMapLocation.setErrorCode(12);
            aMapLocation.setLocationType(1);
            obtain.what = 2;
            obtain.obj = aMapLocation;
            if (this.a != null) {
                this.a.sendMessage(obtain);
            }
        } catch (Throwable th) {
        }
    }
}
