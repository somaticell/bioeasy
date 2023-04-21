package com.loc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

final class ct extends BroadcastReceiver {
    ct(cb cbVar) {
    }

    public final void onReceive(Context context, Intent intent) {
        if (intent != null) {
            try {
                if (intent.getAction().equals("android.location.GPS_FIX_CHANGE")) {
                    cb.b = false;
                }
            } catch (Exception e) {
            }
        }
    }
}
