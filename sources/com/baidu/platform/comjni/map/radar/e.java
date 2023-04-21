package com.baidu.platform.comjni.map.radar;

class e implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ d b;

    e(d dVar, String str) {
        this.b = dVar;
        this.a = str;
    }

    public void run() {
        if (this.b.a.a != null) {
            this.b.a.a.b(this.a);
        }
    }
}
