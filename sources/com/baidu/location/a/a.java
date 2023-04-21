package com.baidu.location.a;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.widget.ActivityChooserView;
import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.Jni;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.location.b.i;
import com.baidu.location.d.e;
import com.baidu.location.d.h;
import com.baidu.location.f;
import com.baidu.location.f.j;
import com.baidu.location.indoor.g;
import com.baidu.platform.comapi.location.CoordinateType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class a {
    public static long c = 0;
    private static a e = null;
    public boolean a;
    boolean b;
    int d;
    private ArrayList<C0004a> f;
    private boolean g;
    private BDLocation h;
    private BDLocation i;
    private BDLocation j;
    private BDLocation k;
    private boolean l;
    /* access modifiers changed from: private */
    public boolean m;
    private b n;

    /* renamed from: com.baidu.location.a.a$a  reason: collision with other inner class name */
    private class C0004a {
        public String a = null;
        public Messenger b = null;
        public LocationClientOption c = new LocationClientOption();
        public int d = 0;
        final /* synthetic */ a e;

        public C0004a(a aVar, Message message) {
            boolean z = true;
            this.e = aVar;
            this.b = message.replyTo;
            this.a = message.getData().getString("packName");
            this.c.prodName = message.getData().getString("prodName");
            com.baidu.location.f.b.a().a(this.c.prodName, this.a);
            this.c.coorType = message.getData().getString("coorType");
            this.c.addrType = message.getData().getString("addrType");
            this.c.enableSimulateGps = message.getData().getBoolean("enableSimulateGps", false);
            j.l = j.l || this.c.enableSimulateGps;
            if (!j.g.equals("all")) {
                j.g = this.c.addrType;
            }
            this.c.openGps = message.getData().getBoolean("openGPS");
            this.c.scanSpan = message.getData().getInt("scanSpan");
            this.c.timeOut = message.getData().getInt("timeOut");
            this.c.priority = message.getData().getInt("priority");
            this.c.location_change_notify = message.getData().getBoolean("location_change_notify");
            this.c.mIsNeedDeviceDirect = message.getData().getBoolean("needDirect", false);
            this.c.isNeedAltitude = message.getData().getBoolean("isneedaltitude", false);
            j.h = j.h || message.getData().getBoolean("isneedaptag", false);
            if (!j.i && !message.getData().getBoolean("isneedaptagd", false)) {
                z = false;
            }
            j.i = z;
            j.Q = message.getData().getFloat("autoNotifyLocSensitivity", 0.5f);
            int i = message.getData().getInt("wifitimeout", ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
            if (i < j.ae) {
                j.ae = i;
            }
            int i2 = message.getData().getInt("autoNotifyMaxInterval", 0);
            if (i2 >= j.V) {
                j.V = i2;
            }
            int i3 = message.getData().getInt("autoNotifyMinDistance", 0);
            if (i3 >= j.X) {
                j.X = i3;
            }
            int i4 = message.getData().getInt("autoNotifyMinTimeInterval", 0);
            if (i4 >= j.W) {
                j.W = i4;
            }
            if (this.c.scanSpan >= 1000) {
                i.a().b();
            }
            if (this.c.mIsNeedDeviceDirect || this.c.isNeedAltitude) {
                n.a().a(this.c.mIsNeedDeviceDirect);
                n.a().b();
            }
            aVar.b |= this.c.isNeedAltitude;
        }

        /* access modifiers changed from: private */
        public void a(int i) {
            Message obtain = Message.obtain((Handler) null, i);
            try {
                if (this.b != null) {
                    this.b.send(obtain);
                }
                this.d = 0;
            } catch (Exception e2) {
                if (e2 instanceof DeadObjectException) {
                    this.d++;
                }
            }
        }

        /* access modifiers changed from: private */
        public void a(int i, Bundle bundle) {
            Message obtain = Message.obtain((Handler) null, i);
            obtain.setData(bundle);
            try {
                if (this.b != null) {
                    this.b.send(obtain);
                }
                this.d = 0;
            } catch (Exception e2) {
                if (e2 instanceof DeadObjectException) {
                    this.d++;
                }
                e2.printStackTrace();
            }
        }

        private void a(int i, String str, BDLocation bDLocation) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(str, bDLocation);
            bundle.setClassLoader(BDLocation.class.getClassLoader());
            Message obtain = Message.obtain((Handler) null, i);
            obtain.setData(bundle);
            try {
                if (this.b != null) {
                    this.b.send(obtain);
                }
                this.d = 0;
            } catch (Exception e2) {
                if (e2 instanceof DeadObjectException) {
                    this.d++;
                }
            }
        }

        public void a() {
            a(111);
        }

        public void a(BDLocation bDLocation) {
            a(bDLocation, 21);
        }

        public void a(BDLocation bDLocation, int i) {
            BDLocation bDLocation2 = new BDLocation(bDLocation);
            if (g.a().f()) {
                bDLocation2.setIndoorLocMode(true);
            }
            if (i == 21) {
                a(27, "locStr", bDLocation2);
            }
            if (this.c.coorType != null && !this.c.coorType.equals(CoordinateType.GCJ02)) {
                double longitude = bDLocation2.getLongitude();
                double latitude = bDLocation2.getLatitude();
                if (!(longitude == Double.MIN_VALUE || latitude == Double.MIN_VALUE)) {
                    if ((bDLocation2.getCoorType() != null && bDLocation2.getCoorType().equals(CoordinateType.GCJ02)) || bDLocation2.getCoorType() == null) {
                        double[] coorEncrypt = Jni.coorEncrypt(longitude, latitude, this.c.coorType);
                        bDLocation2.setLongitude(coorEncrypt[0]);
                        bDLocation2.setLatitude(coorEncrypt[1]);
                        bDLocation2.setCoorType(this.c.coorType);
                    } else if (bDLocation2.getCoorType() != null && bDLocation2.getCoorType().equals(CoordinateType.WGS84) && !this.c.coorType.equals("bd09ll")) {
                        double[] coorEncrypt2 = Jni.coorEncrypt(longitude, latitude, "wgs842mc");
                        bDLocation2.setLongitude(coorEncrypt2[0]);
                        bDLocation2.setLatitude(coorEncrypt2[1]);
                        bDLocation2.setCoorType("wgs84mc");
                    }
                }
            }
            a(i, "locStr", bDLocation2);
        }

        public void b() {
            if (!this.c.location_change_notify) {
                return;
            }
            if (j.b) {
                a(54);
            } else {
                a(55);
            }
        }
    }

    private class b implements Runnable {
        final /* synthetic */ a a;
        private int b;
        private boolean c;

        public void run() {
            if (!this.c) {
                this.b++;
                boolean unused = this.a.m = false;
            }
        }
    }

    private a() {
        this.f = null;
        this.g = false;
        this.a = false;
        this.b = false;
        this.h = null;
        this.i = null;
        this.j = null;
        this.d = 0;
        this.k = null;
        this.l = false;
        this.m = false;
        this.n = null;
        this.f = new ArrayList<>();
    }

    private C0004a a(Messenger messenger) {
        if (this.f == null) {
            return null;
        }
        Iterator<C0004a> it = this.f.iterator();
        while (it.hasNext()) {
            C0004a next = it.next();
            if (next.b.equals(messenger)) {
                return next;
            }
        }
        return null;
    }

    public static a a() {
        if (e == null) {
            e = new a();
        }
        return e;
    }

    private void a(C0004a aVar) {
        if (aVar != null) {
            if (a(aVar.b) != null) {
                aVar.a(14);
                return;
            }
            this.f.add(aVar);
            aVar.a(13);
        }
    }

    private void b(String str) {
        Intent intent = new Intent("com.baidu.location.flp.log");
        intent.setPackage("com.baidu.baidulocationdemo");
        intent.putExtra("data", str);
        intent.putExtra("pack", com.baidu.location.f.b.d);
        intent.putExtra("tag", "state");
        f.getServiceContext().sendBroadcast(intent);
    }

    private void h() {
        i();
        g();
    }

    private void i() {
        Iterator<C0004a> it = this.f.iterator();
        boolean z = false;
        boolean z2 = false;
        while (it.hasNext()) {
            C0004a next = it.next();
            if (next.c.openGps) {
                z2 = true;
            }
            z = next.c.location_change_notify ? true : z;
        }
        j.a = z;
        if (this.g != z2) {
            this.g = z2;
            e.a().a(this.g);
        }
    }

    public void a(Bundle bundle, int i2) {
        Iterator<C0004a> it = this.f.iterator();
        while (it.hasNext()) {
            try {
                C0004a next = it.next();
                next.a(i2, bundle);
                if (next.d > 4) {
                    it.remove();
                }
            } catch (Exception e2) {
                return;
            }
        }
    }

    public void a(Message message) {
        if (message != null && message.replyTo != null) {
            c = System.currentTimeMillis();
            this.a = true;
            h.a().b();
            a(new C0004a(this, message));
            h();
            if (this.l) {
                b("start");
                this.d = 0;
            }
        }
    }

    public void a(BDLocation bDLocation) {
        b(bDLocation);
    }

    public void a(String str) {
        c(new BDLocation(str));
    }

    public void b() {
        this.f.clear();
        this.h = null;
        h();
    }

    public void b(Message message) {
        C0004a a2 = a(message.replyTo);
        if (a2 != null) {
            this.f.remove(a2);
        }
        i.a().c();
        n.a().c();
        h();
        if (this.l) {
            b("stop");
            this.d = 0;
        }
    }

    public void b(BDLocation bDLocation) {
        if (g.a().g() && bDLocation.getFloor() != null) {
            this.j = null;
            this.j = new BDLocation(bDLocation);
        }
        if (bDLocation.getFloor() == null) {
            this.j = null;
        }
        boolean z = l.h;
        if (z) {
            l.h = false;
        }
        if (j.V >= 10000 && (bDLocation.getLocType() == 61 || bDLocation.getLocType() == 161 || bDLocation.getLocType() == 66)) {
            if (this.h != null) {
                float[] fArr = new float[1];
                Location.distanceBetween(this.h.getLatitude(), this.h.getLongitude(), bDLocation.getLatitude(), bDLocation.getLongitude(), fArr);
                if (fArr[0] > ((float) j.X) || z) {
                    this.h = null;
                    this.h = new BDLocation(bDLocation);
                } else {
                    return;
                }
            } else {
                this.h = new BDLocation(bDLocation);
            }
        }
        if (bDLocation == null || bDLocation.getLocType() != 161 || j.a().b()) {
            if (!bDLocation.hasAltitude() && this.b && (bDLocation.getLocType() == 161 || bDLocation.getLocType() == 66)) {
                double a2 = com.baidu.location.b.a.a().a(bDLocation.getLongitude(), bDLocation.getLatitude());
                if (a2 != Double.MAX_VALUE) {
                    bDLocation.setAltitude(a2);
                }
            }
            if (bDLocation.getLocType() == 61) {
                bDLocation.setGpsAccuracyStatus(com.baidu.location.b.a.a().a(bDLocation));
            }
            Iterator<C0004a> it = this.f.iterator();
            while (it.hasNext()) {
                try {
                    C0004a next = it.next();
                    next.a(bDLocation);
                    if (next.d > 4) {
                        it.remove();
                    }
                } catch (Exception e2) {
                    return;
                }
            }
            return;
        }
        if (this.i == null) {
            this.i = new BDLocation();
            this.i.setLocType(505);
        }
        Iterator<C0004a> it2 = this.f.iterator();
        while (it2.hasNext()) {
            try {
                C0004a next2 = it2.next();
                next2.a(this.i);
                if (next2.d > 4) {
                    it2.remove();
                }
            } catch (Exception e3) {
                return;
            }
        }
    }

    public void c() {
        if (this.j != null) {
            a(this.j);
        }
    }

    public void c(BDLocation bDLocation) {
        Address a2 = l.c().a(bDLocation);
        String f2 = l.c().f();
        List<Poi> g2 = l.c().g();
        if (a2 != null) {
            bDLocation.setAddr(a2);
        }
        if (f2 != null) {
            bDLocation.setLocationDescribe(f2);
        }
        if (g2 != null) {
            bDLocation.setPoiList(g2);
        }
        if (g.a().g() && g.a().h() != null) {
            bDLocation.setFloor(g.a().h());
            bDLocation.setIndoorLocMode(true);
            if (g.a().i() != null) {
                bDLocation.setBuildingID(g.a().i());
            }
        }
        l.c().d(bDLocation);
        a(bDLocation);
    }

    public boolean c(Message message) {
        boolean z = true;
        C0004a a2 = a(message.replyTo);
        if (a2 == null) {
            return false;
        }
        int i2 = a2.c.scanSpan;
        a2.c.scanSpan = message.getData().getInt("scanSpan", a2.c.scanSpan);
        if (a2.c.scanSpan < 1000) {
            i.a().e();
            n.a().c();
            this.a = false;
        } else {
            i.a().d();
            this.a = true;
        }
        if (a2.c.scanSpan <= 999 || i2 >= 1000) {
            z = false;
        } else {
            if (a2.c.mIsNeedDeviceDirect || a2.c.isNeedAltitude) {
                n.a().a(a2.c.mIsNeedDeviceDirect);
                n.a().b();
            }
            this.b |= a2.c.isNeedAltitude;
        }
        a2.c.openGps = message.getData().getBoolean("openGPS", a2.c.openGps);
        String string = message.getData().getString("coorType");
        LocationClientOption locationClientOption = a2.c;
        if (string == null || string.equals("")) {
            string = a2.c.coorType;
        }
        locationClientOption.coorType = string;
        String string2 = message.getData().getString("addrType");
        LocationClientOption locationClientOption2 = a2.c;
        if (string2 == null || string2.equals("")) {
            string2 = a2.c.addrType;
        }
        locationClientOption2.addrType = string2;
        if (!j.g.equals(a2.c.addrType)) {
            l.c().i();
        }
        a2.c.timeOut = message.getData().getInt("timeOut", a2.c.timeOut);
        a2.c.location_change_notify = message.getData().getBoolean("location_change_notify", a2.c.location_change_notify);
        a2.c.priority = message.getData().getInt("priority", a2.c.priority);
        int i3 = message.getData().getInt("wifitimeout", ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        if (i3 < j.ae) {
            j.ae = i3;
        }
        h();
        return z;
    }

    public int d(Message message) {
        C0004a a2;
        if (message == null || message.replyTo == null || (a2 = a(message.replyTo)) == null || a2.c == null) {
            return 1;
        }
        return a2.c.priority;
    }

    public void d() {
        if (this.j != null) {
            this.j = null;
        }
    }

    public int e(Message message) {
        C0004a a2;
        if (message == null || message.replyTo == null || (a2 = a(message.replyTo)) == null || a2.c == null) {
            return 1000;
        }
        return a2.c.scanSpan;
    }

    public void e() {
        if (this.j != null) {
            this.j = null;
        }
        Iterator<C0004a> it = this.f.iterator();
        while (it.hasNext()) {
            it.next().a();
        }
    }

    public String f() {
        StringBuffer stringBuffer = new StringBuffer(256);
        if (this.f.isEmpty()) {
            return "&prod=" + com.baidu.location.f.b.e + ":" + com.baidu.location.f.b.d;
        }
        C0004a aVar = this.f.get(0);
        if (aVar.c.prodName != null) {
            stringBuffer.append(aVar.c.prodName);
        }
        if (aVar.a != null) {
            stringBuffer.append(":");
            stringBuffer.append(aVar.a);
            stringBuffer.append("|");
        }
        String stringBuffer2 = stringBuffer.toString();
        if (stringBuffer2 == null || stringBuffer2.equals("")) {
            return null;
        }
        return "&prod=" + stringBuffer2;
    }

    public void g() {
        Iterator<C0004a> it = this.f.iterator();
        while (it.hasNext()) {
            it.next().b();
        }
    }
}
