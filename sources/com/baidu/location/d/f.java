package com.baidu.location.d;

import android.location.Location;
import android.os.Handler;
import android.os.Message;

class f extends Handler {
    final /* synthetic */ e a;

    f(e eVar) {
        this.a = eVar;
    }

    public void handleMessage(Message message) {
        if (com.baidu.location.f.isServing) {
            switch (message.what) {
                case 1:
                    this.a.e((Location) message.obj);
                    return;
                case 3:
                    this.a.a("&og=1", (Location) message.obj);
                    return;
                case 4:
                    this.a.a("&og=2", (Location) message.obj);
                    return;
                default:
                    return;
            }
        }
    }
}
