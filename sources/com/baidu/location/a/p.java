package com.baidu.location.a;

import com.baidu.location.d.h;
import com.baidu.location.f;

class p implements Runnable {
    final /* synthetic */ o a;

    p(o oVar) {
        this.a = oVar;
    }

    public void run() {
        if (h.j() || this.a.a(f.getServiceContext())) {
            this.a.d();
        }
    }
}
