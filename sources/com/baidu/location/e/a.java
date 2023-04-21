package com.baidu.location.e;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.util.Log;
import com.baidu.location.LLSInterface;
import com.baidu.location.a.h;
import com.baidu.location.a.j;
import com.baidu.location.a.l;
import com.baidu.location.a.m;
import com.baidu.location.a.o;
import com.baidu.location.a.u;
import com.baidu.location.a.v;
import com.baidu.location.b.d;
import com.baidu.location.b.e;
import com.baidu.location.b.i;
import com.baidu.location.d.b;
import com.baidu.location.f;
import com.baidu.location.indoor.g;
import java.lang.ref.WeakReference;

public class a extends Service implements LLSInterface {
    static C0008a a = null;
    private static long f = 0;
    Messenger b = null;
    private Looper c;
    private HandlerThread d;
    private boolean e = false;

    /* renamed from: com.baidu.location.e.a$a  reason: collision with other inner class name */
    public static class C0008a extends Handler {
        private final WeakReference<a> a;

        public C0008a(Looper looper, a aVar) {
            super(looper);
            this.a = new WeakReference<>(aVar);
        }

        public void handleMessage(Message message) {
            a aVar = (a) this.a.get();
            if (aVar != null) {
                if (f.isServing) {
                    switch (message.what) {
                        case 11:
                            aVar.a(message);
                            break;
                        case 12:
                            aVar.b(message);
                            break;
                        case 15:
                            aVar.c(message);
                            break;
                        case 22:
                            l.c().b(message);
                            break;
                        case 41:
                            l.c().h();
                            break;
                        case 110:
                            g.a().c();
                            break;
                        case 111:
                            g.a().d();
                            break;
                        case 112:
                            g.a().b();
                            break;
                        case 302:
                            g.a().e();
                            break;
                        case 401:
                            try {
                                message.getData();
                                break;
                            } catch (Exception e) {
                                break;
                            }
                        case 405:
                            byte[] byteArray = message.getData().getByteArray("errorid");
                            if (byteArray != null && byteArray.length > 0) {
                                new String(byteArray);
                                break;
                            }
                        case 406:
                            h.a().e();
                            break;
                    }
                }
                if (message.what == 1) {
                    aVar.c();
                }
                if (message.what == 0) {
                    aVar.b();
                }
                super.handleMessage(message);
            }
        }
    }

    public static long a() {
        return f;
    }

    /* access modifiers changed from: private */
    public void a(Message message) {
        Log.d("baidu_location_service", "baidu location service register ...");
        com.baidu.location.a.a.a().a(message);
        e.a().d();
        o.b().c();
    }

    /* access modifiers changed from: private */
    public void b() {
        j.a().a(f.getServiceContext());
        m.a().b();
        h.a().b();
        com.baidu.location.d.e.a().b();
        b.a().b();
        com.baidu.location.f.b.a();
        l.c().d();
        d.a().b();
        e.a().b();
        com.baidu.location.b.f.a().b();
        com.baidu.location.b.a.a().b();
        com.baidu.location.b.g.a().b();
        com.baidu.location.d.h.a().c();
    }

    /* access modifiers changed from: private */
    public void b(Message message) {
        com.baidu.location.a.a.a().b(message);
    }

    /* access modifiers changed from: private */
    public void c() {
        com.baidu.location.d.h.a().e();
        com.baidu.location.d.e.a().e();
        i.a().c();
        e.a().c();
        d.a().c();
        com.baidu.location.b.b.a().c();
        com.baidu.location.b.a.a().c();
        com.baidu.location.a.b.a().b();
        b.a().c();
        l.c().e();
        g.a().d();
        h.a().c();
        v.e();
        com.baidu.location.a.a.a().b();
        com.baidu.location.a.d.a().b();
        m.a().c();
        Log.d("baidu_location_service", "baidu location service has stoped ...");
        if (!this.e) {
            Process.killProcess(Process.myPid());
        }
    }

    /* access modifiers changed from: private */
    public void c(Message message) {
        com.baidu.location.a.a.a().c(message);
    }

    public double getVersion() {
        return 7.53000020980835d;
    }

    public IBinder onBind(Intent intent) {
        Bundle extras = intent.getExtras();
        boolean z = false;
        if (extras != null) {
            com.baidu.location.f.b.g = extras.getString("key");
            com.baidu.location.f.b.f = extras.getString("sign");
            this.e = extras.getBoolean("kill_process");
            z = extras.getBoolean("cache_exception");
        }
        if (!z) {
            Thread.setDefaultUncaughtExceptionHandler(com.baidu.location.b.f.a());
        }
        return this.b.getBinder();
    }

    public void onCreate(Context context) {
        f = System.currentTimeMillis();
        this.d = u.a();
        this.c = this.d.getLooper();
        if (this.c == null) {
            a = new C0008a(Looper.getMainLooper(), this);
        } else {
            a = new C0008a(this.c, this);
        }
        this.b = new Messenger(a);
        a.sendEmptyMessage(0);
        Log.d("baidu_location_service", "baidu location service start1 ...20171027..." + Process.myPid());
    }

    public void onDestroy() {
        try {
            a.sendEmptyMessage(1);
        } catch (Exception e2) {
            Log.d("baidu_location_service", "baidu location service stop exception...");
            c();
            Process.killProcess(Process.myPid());
        }
        Log.d("baidu_location_service", "baidu location service stop ...");
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        return 1;
    }

    public void onTaskRemoved(Intent intent) {
        Log.d("baidu_location_service", "baidu location service remove task...");
    }

    public boolean onUnBind(Intent intent) {
        return false;
    }
}
