package com.loc;

import android.os.Handler;
import android.os.Message;

final class cv extends Handler {
    private /* synthetic */ cu a;

    cv(cu cuVar) {
        this.a = cuVar;
    }

    public final void handleMessage(Message message) {
        try {
            switch (message.what) {
                case 1:
                    if (this.a.a.z != null) {
                        this.a.a.z.a((String) message.obj);
                        return;
                    }
                    return;
                default:
                    return;
            }
        } catch (Exception e) {
        }
    }
}
