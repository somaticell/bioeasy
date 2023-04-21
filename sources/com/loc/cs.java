package com.loc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

final class cs extends BroadcastReceiver {
    cs(cb cbVar) {
    }

    public final void onReceive(Context context, Intent intent) {
        if (intent != null) {
            try {
                String action = intent.getAction();
                if (action.equals("android.intent.action.MEDIA_MOUNTED")) {
                    cl.c = false;
                }
                if (action.equals("android.intent.action.MEDIA_UNMOUNTED")) {
                    cl.c = true;
                }
                if (action.equals("android.intent.action.MEDIA_EJECT")) {
                    cl.c = true;
                }
            } catch (Exception e) {
            }
        }
    }
}
