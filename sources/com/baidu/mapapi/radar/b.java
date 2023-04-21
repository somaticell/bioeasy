package com.baidu.mapapi.radar;

import android.os.Message;
import java.util.TimerTask;

class b extends TimerTask {
    final /* synthetic */ RadarSearchManager a;

    b(RadarSearchManager radarSearchManager) {
        this.a = radarSearchManager;
    }

    public void run() {
        Message message = new Message();
        message.what = 1;
        this.a.f.sendMessage(message);
    }
}
