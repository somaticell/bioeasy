package com.baidu.location.indoor;

import java.util.TimerTask;

class q extends TimerTask {
    final /* synthetic */ o a;

    q(o oVar) {
        this.a = oVar;
    }

    public void run() {
        try {
            this.a.l();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
