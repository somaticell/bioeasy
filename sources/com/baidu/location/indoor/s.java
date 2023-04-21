package com.baidu.location.indoor;

import com.baidu.location.BDLocation;
import com.baidu.location.indoor.r;

class s implements Runnable {
    final /* synthetic */ r a;

    s(r rVar) {
        this.a = rVar;
    }

    public void run() {
        r.b a2 = this.a.a(this.a.e);
        if (!(a2 == null || this.a.a == null)) {
            r.b unused = this.a.e = this.a.e.b(a2);
            long currentTimeMillis = System.currentTimeMillis();
            if (!a2.b(2.0E-6d) && currentTimeMillis - this.a.j > this.a.b) {
                BDLocation bDLocation = new BDLocation(this.a.c);
                bDLocation.setLatitude(this.a.e.a);
                bDLocation.setLongitude(this.a.e.b);
                this.a.a.a(bDLocation);
                long unused2 = this.a.j = currentTimeMillis;
            }
        }
        this.a.l.postDelayed(this.a.m, 450);
    }
}
