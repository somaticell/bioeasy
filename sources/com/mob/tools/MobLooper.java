package com.mob.tools;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;

public class MobLooper {
    private Context context;
    /* access modifiers changed from: private */
    public boolean goingToStop;

    public MobLooper(Context context2) {
        this.context = context2.getApplicationContext();
    }

    public void start(Runnable task, long interval) {
        start(task, interval, 0);
    }

    public synchronized void start(Runnable task, long interval, long delay) {
        this.goingToStop = false;
        final Object lock = new Object();
        Intent intent = new Intent(getClass().getName() + "." + SystemClock.elapsedRealtime());
        final PendingIntent sender = PendingIntent.getBroadcast(this.context, 0, intent, 0);
        final AlarmManager am = (AlarmManager) this.context.getSystemService("alarm");
        am.set(3, SystemClock.elapsedRealtime() + delay, sender);
        final Runnable runnable = task;
        final long j = interval;
        this.context.registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (!MobLooper.this.goingToStop) {
                    new Thread() {
                        public void run() {
                            synchronized (lock) {
                                runnable.run();
                            }
                        }
                    }.start();
                    am.set(3, SystemClock.elapsedRealtime() + j, sender);
                }
            }
        }, new IntentFilter(intent.getAction()));
    }

    public synchronized void stop() {
        this.goingToStop = true;
    }
}
