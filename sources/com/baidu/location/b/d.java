package com.baidu.location.b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.baidu.location.f;

public class d {
    private static d d = null;
    /* access modifiers changed from: private */
    public boolean a = false;
    /* access modifiers changed from: private */
    public String b = null;
    private a c = null;
    /* access modifiers changed from: private */
    public int e = -1;

    public class a extends BroadcastReceiver {
        public a() {
        }

        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getAction().equals("android.intent.action.BATTERY_CHANGED")) {
                    boolean unused = d.this.a = false;
                    int intExtra = intent.getIntExtra("status", 0);
                    int intExtra2 = intent.getIntExtra("plugged", 0);
                    int intExtra3 = intent.getIntExtra("level", -1);
                    int intExtra4 = intent.getIntExtra("scale", -1);
                    if (intExtra3 <= 0 || intExtra4 <= 0) {
                        int unused2 = d.this.e = -1;
                    } else {
                        int unused3 = d.this.e = (intExtra3 * 100) / intExtra4;
                    }
                    switch (intExtra) {
                        case 2:
                            String unused4 = d.this.b = "4";
                            break;
                        case 3:
                        case 4:
                            String unused5 = d.this.b = "3";
                            break;
                        default:
                            String unused6 = d.this.b = null;
                            break;
                    }
                    switch (intExtra2) {
                        case 1:
                            String unused7 = d.this.b = "6";
                            boolean unused8 = d.this.a = true;
                            return;
                        case 2:
                            String unused9 = d.this.b = "5";
                            boolean unused10 = d.this.a = true;
                            return;
                        default:
                            return;
                    }
                }
            } catch (Exception e) {
                String unused11 = d.this.b = null;
            }
        }
    }

    private d() {
    }

    public static synchronized d a() {
        d dVar;
        synchronized (d.class) {
            if (d == null) {
                d = new d();
            }
            dVar = d;
        }
        return dVar;
    }

    public void b() {
        this.c = new a();
        try {
            f.getServiceContext().registerReceiver(this.c, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        } catch (Exception e2) {
        }
    }

    public void c() {
        if (this.c != null) {
            try {
                f.getServiceContext().unregisterReceiver(this.c);
            } catch (Exception e2) {
            }
        }
        this.c = null;
    }

    public String d() {
        return this.b;
    }

    public int e() {
        return this.e;
    }
}
