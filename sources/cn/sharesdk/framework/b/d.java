package cn.sharesdk.framework.b;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import cn.sharesdk.framework.b.b.c;
import cn.sharesdk.framework.b.b.e;
import cn.sharesdk.framework.b.b.g;
import com.mob.commons.SHARESDK;
import com.mob.commons.clt.DvcClt;
import com.mob.commons.clt.PkgClt;
import com.mob.commons.clt.RtClt;
import com.mob.commons.iosbridge.UDPServer;
import com.mob.tools.SSDKHandlerThread;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.FileLocker;
import java.io.File;
import java.util.Calendar;

/* compiled from: StatisticsLogger */
public class d extends SSDKHandlerThread {
    private static d a;
    private Context b;
    private DeviceHelper c;
    private a d;
    private String e;
    private Handler f;
    private boolean g;
    private int h;
    private boolean i;
    private long j;
    private boolean k;
    private File l;
    private FileLocker m = new FileLocker();

    public static synchronized d a(Context context, String str) {
        d dVar = null;
        synchronized (d.class) {
            if (a == null) {
                if (context != null) {
                    if (!TextUtils.isEmpty(str)) {
                        a = new d(context.getApplicationContext(), str);
                    }
                }
            }
            dVar = a;
        }
        return dVar;
    }

    private d(Context context, String str) {
        this.b = context;
        this.e = str;
        this.c = DeviceHelper.getInstance(context);
        this.d = a.a(context, str);
        this.l = new File(context.getFilesDir(), ".statistics");
        if (!this.l.exists()) {
            try {
                this.l.createNewFile();
            } catch (Exception e2) {
                cn.sharesdk.framework.utils.d.a().d(e2);
            }
        }
    }

    public void a(Handler handler) {
        this.f = handler;
    }

    public void a(boolean z) {
        this.g = z;
    }

    public void a(int i2) {
        this.h = i2;
    }

    /* access modifiers changed from: protected */
    public void onStart(Message msg) {
        if (!this.i) {
            this.i = true;
            try {
                this.m.setLockFile(this.l.getAbsolutePath());
                if (this.m.lock(false)) {
                    this.d.a();
                    this.d.b();
                    SHARESDK.setAppKey(this.e);
                    new SHARESDK().getDuid(this.b);
                    DvcClt.startCollector(this.b);
                    PkgClt.startCollector(this.b);
                    RtClt.startCollector(this.b);
                    UDPServer.start(this.b);
                    this.handler.sendEmptyMessageDelayed(4, 3600000);
                    this.d.a(this.g);
                    this.handler.sendEmptyMessage(1);
                    this.handler.sendEmptyMessage(2);
                }
            } catch (Throwable th) {
                cn.sharesdk.framework.utils.d.a().d(th);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onStop(Message msg) {
        if (this.i) {
            long currentTimeMillis = System.currentTimeMillis() - this.j;
            e eVar = new e();
            eVar.a = currentTimeMillis;
            a((c) eVar);
            this.i = false;
            try {
                this.f.sendEmptyMessage(1);
            } catch (Throwable th) {
                cn.sharesdk.framework.utils.d.a().d(th);
            }
            a = null;
            this.handler.getLooper().quit();
        }
    }

    public void a(c cVar) {
        if (this.i) {
            b(cVar);
            if (cVar.a(this.b)) {
                Message message = new Message();
                message.what = 3;
                message.obj = cVar;
                try {
                    this.handler.sendMessage(message);
                } catch (Throwable th) {
                    cn.sharesdk.framework.utils.d.a().d(th);
                }
            } else {
                cn.sharesdk.framework.utils.d.a().d("Drop event: " + cVar.toString(), new Object[0]);
            }
        }
    }

    private void b(c cVar) {
        cVar.f = this.c.getDeviceKey();
        cVar.g = this.e;
        cVar.h = this.c.getPackageName();
        cVar.i = this.c.getAppVersion();
        cVar.j = String.valueOf(60000 + this.h);
        cVar.k = this.c.getPlatformCode();
        cVar.l = this.c.getDetailNetworkTypeForStatic();
        if (TextUtils.isEmpty(this.e)) {
            Log.w("ShareSDKCore", "Your appKey of ShareSDK is null , this will cause its data won't be count!");
        } else if (!"cn.sharesdk.demo".equals(cVar.h) && ("api20".equals(this.e) || "androidv1101".equals(this.e))) {
            Log.w("ShareSDKCore", "Your app is using the appkey of ShareSDK Demo, this will cause its data won't be count!");
        }
        cVar.m = this.c.getDeviceData();
    }

    /* access modifiers changed from: protected */
    public void onMessage(Message msg) {
        switch (msg.what) {
            case 1:
                a();
                try {
                    this.handler.sendEmptyMessageDelayed(1, 5000);
                    return;
                } catch (Throwable th) {
                    cn.sharesdk.framework.utils.d.a().d(th);
                    return;
                }
            case 2:
                try {
                    this.d.c();
                    return;
                } catch (Throwable th2) {
                    cn.sharesdk.framework.utils.d.a().d(th2);
                    return;
                }
            case 3:
                if (msg.obj != null) {
                    c((c) msg.obj);
                    this.handler.removeMessages(2);
                    this.handler.sendEmptyMessageDelayed(2, 10000);
                    return;
                }
                return;
            case 4:
                long longValue = cn.sharesdk.framework.b.a.e.a(this.b).f().longValue();
                Calendar instance = Calendar.getInstance();
                instance.setTimeInMillis(longValue);
                int i2 = instance.get(1);
                int i3 = instance.get(2);
                int i4 = instance.get(5);
                instance.setTimeInMillis(System.currentTimeMillis());
                int i5 = instance.get(1);
                int i6 = instance.get(2);
                int i7 = instance.get(5);
                if (!(i2 == i5 && i3 == i6 && i4 == i7)) {
                    this.d.b();
                }
                this.handler.sendEmptyMessageDelayed(4, 3600000);
                return;
            default:
                return;
        }
    }

    private void c(c cVar) {
        try {
            this.d.a(cVar);
            cVar.b(this.b);
        } catch (Throwable th) {
            cn.sharesdk.framework.utils.d.a().d(th);
            cn.sharesdk.framework.utils.d.a().d(cVar.toString(), new Object[0]);
        }
    }

    private void a() {
        boolean b2 = b();
        if (b2) {
            if (!this.k) {
                this.k = b2;
                this.j = System.currentTimeMillis();
                a((c) new g());
            }
        } else if (this.k) {
            this.k = b2;
            long currentTimeMillis = System.currentTimeMillis() - this.j;
            e eVar = new e();
            eVar.a = currentTimeMillis;
            a((c) eVar);
        }
    }

    private boolean b() {
        return DeviceHelper.getInstance(this.b).amIOnForeground();
    }
}
