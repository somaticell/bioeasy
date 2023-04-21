package com.baidu.location.d;

import com.baidu.location.a.l;
import com.baidu.location.a.t;
import com.baidu.location.a.v;
import com.baidu.location.d.h;
import com.baidu.location.indoor.g;

class i implements Runnable {
    final /* synthetic */ h.a a;

    i(h.a aVar) {
        this.a = aVar;
    }

    public void run() {
        h.this.t();
        l.c().h();
        if (g.a().f()) {
            g.a().c.obtainMessage(41).sendToTarget();
        }
        h.this.r();
        if (System.currentTimeMillis() - t.b() <= 5000) {
            v.a(t.c(), h.this.o(), t.d(), t.a());
        }
    }
}
