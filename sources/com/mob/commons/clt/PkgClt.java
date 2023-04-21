package com.mob.commons.clt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.mob.commons.LockAction;
import com.mob.commons.a;
import com.mob.commons.b;
import com.mob.commons.d;
import com.mob.tools.MobHandlerThread;
import com.mob.tools.MobLog;
import com.mob.tools.utils.FileLocker;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.ReflectHelper;
import com.mob.tools.utils.ResHelper;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class PkgClt {
    private static final String[] a = {"android.intent.action.PACKAGE_ADDED", "android.intent.action.PACKAGE_CHANGED", "android.intent.action.PACKAGE_REMOVED", "android.intent.action.PACKAGE_REPLACED"};
    private static PkgClt b;
    private BroadcastReceiver c;
    /* access modifiers changed from: private */
    public Context d;
    private Hashon e = new Hashon();
    /* access modifiers changed from: private */
    public Handler f;

    public static synchronized boolean register(Context context, String str) {
        synchronized (PkgClt.class) {
            startCollector(context);
        }
        return true;
    }

    public static synchronized void startCollector(Context context) {
        synchronized (PkgClt.class) {
            if (b == null) {
                b = new PkgClt(context);
                b.a();
            }
        }
    }

    private PkgClt(Context context) {
        this.d = context.getApplicationContext();
    }

    private void a() {
        AnonymousClass1 r0 = new MobHandlerThread() {
            public void run() {
                d.a(new File(ResHelper.getCacheRoot(PkgClt.this.d), "comm/locks/.pkg_lock"), new LockAction() {
                    public boolean run(FileLocker fileLocker) {
                        if (a.e(PkgClt.this.d)) {
                            PkgClt.this.b();
                        }
                        AnonymousClass1.this.a();
                        return false;
                    }
                });
            }

            /* access modifiers changed from: private */
            public void a() {
                super.run();
            }
        };
        r0.start();
        this.f = new Handler(r0.getLooper(), new Handler.Callback() {
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case 1:
                        PkgClt.this.f();
                        return false;
                    case 2:
                        PkgClt.this.e();
                        return false;
                    default:
                        return false;
                }
            }
        });
        this.f.sendEmptyMessage(2);
    }

    /* access modifiers changed from: private */
    public void b() {
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList<HashMap<String, String>> c2 = c();
        if (c2 == null || c2.isEmpty()) {
            try {
                arrayList = (ArrayList) ReflectHelper.invokeInstanceMethod(ReflectHelper.invokeStaticMethod("DeviceHelper", "getInstance", this.d), "getInstalledApp", false);
            } catch (Throwable th) {
                MobLog.getInstance().w(th);
                arrayList = new ArrayList();
            }
            a(a.o(this.d), "APPS_ALL", arrayList);
            a((ArrayList<HashMap<String, String>>) arrayList);
            a(a.a(this.d) + (a.g(this.d) * 1000));
            return;
        }
        long a2 = a.a(this.d);
        if (a2 >= d()) {
            try {
                arrayList2 = (ArrayList) ReflectHelper.invokeInstanceMethod(ReflectHelper.invokeStaticMethod("DeviceHelper", "getInstance", this.d), "getInstalledApp", false);
            } catch (Throwable th2) {
                MobLog.getInstance().w(th2);
                arrayList2 = new ArrayList();
            }
            a(a.o(this.d), "APPS_ALL", arrayList2);
            a((ArrayList<HashMap<String, String>>) arrayList2);
            a((a.g(this.d) * 1000) + a2);
            return;
        }
        f();
    }

    private ArrayList<HashMap<String, String>> c() {
        File file = new File(ResHelper.getCacheRoot(this.d), "comm/dbs/.al");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (file.exists()) {
            try {
                ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(file)), "utf-8"));
                for (String readLine = bufferedReader.readLine(); readLine != null; readLine = bufferedReader.readLine()) {
                    HashMap fromJson = this.e.fromJson(readLine);
                    if (fromJson != null) {
                        arrayList.add(fromJson);
                    }
                }
                bufferedReader.close();
                return arrayList;
            } catch (Throwable th) {
                MobLog.getInstance().d(th);
            }
        }
        return new ArrayList<>();
    }

    private void a(long j, String str, ArrayList<HashMap<String, String>> arrayList) {
        HashMap hashMap = new HashMap();
        hashMap.put("type", str);
        hashMap.put("list", arrayList);
        hashMap.put("datetime", Long.valueOf(a.a(this.d)));
        b.a(this.d).a(j, (HashMap<String, Object>) hashMap);
    }

    private void a(ArrayList<HashMap<String, String>> arrayList) {
        File file = new File(ResHelper.getCacheRoot(this.d), "comm/dbs/.al");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(file)), "utf-8");
            Iterator<HashMap<String, String>> it = arrayList.iterator();
            while (it.hasNext()) {
                outputStreamWriter.append(this.e.fromHashMap(it.next())).append(10);
            }
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (Throwable th) {
            MobLog.getInstance().d(th);
        }
    }

    private void a(long j) {
        File file = new File(ResHelper.getCacheRoot(this.d), "comm/dbs/.nulal");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
            dataOutputStream.writeLong(j);
            dataOutputStream.flush();
            dataOutputStream.close();
        } catch (Throwable th) {
            MobLog.getInstance().d(th);
        }
    }

    private long d() {
        File file = new File(ResHelper.getCacheRoot(this.d), "comm/dbs/.nulal");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (file.exists()) {
            try {
                DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
                long readLong = dataInputStream.readLong();
                dataInputStream.close();
                return readLong;
            } catch (Throwable th) {
                MobLog.getInstance().d(th);
            }
        }
        return 0;
    }

    /* access modifiers changed from: private */
    public void e() {
        if (a.d(this.d) && a.f(this.d)) {
            if (this.c == null) {
                this.c = new BroadcastReceiver() {
                    public void onReceive(Context context, Intent intent) {
                        String str = null;
                        if (intent != null) {
                            str = intent.getAction();
                        }
                        if (PkgClt.this.a(str) && PkgClt.this.f != null) {
                            PkgClt.this.f.removeMessages(1);
                            PkgClt.this.f.sendEmptyMessageDelayed(1, 5000);
                        }
                    }
                };
            }
            IntentFilter intentFilter = new IntentFilter();
            for (String addAction : a) {
                intentFilter.addAction(addAction);
            }
            intentFilter.addDataScheme("package");
            try {
                this.d.registerReceiver(this.c, intentFilter);
            } catch (Throwable th) {
            }
        } else if (this.c != null) {
            this.f.removeMessages(1);
            try {
                this.d.unregisterReceiver(this.c);
            } catch (Throwable th2) {
            }
            this.c = null;
        }
        this.f.sendEmptyMessageDelayed(2, 3600000);
    }

    /* access modifiers changed from: private */
    public boolean a(String str) {
        for (String equals : a) {
            if (equals.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void f() {
        ArrayList arrayList;
        ArrayList<HashMap<String, String>> c2 = c();
        try {
            arrayList = (ArrayList) ReflectHelper.invokeInstanceMethod(ReflectHelper.invokeStaticMethod("DeviceHelper", "getInstance", this.d), "getInstalledApp", false);
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
            arrayList = new ArrayList();
        }
        if (c2 == null || c2.isEmpty()) {
            a(a.o(this.d), "APPS_ALL", arrayList);
            a((ArrayList<HashMap<String, String>>) arrayList);
            a(a.a(this.d) + (a.g(this.d) * 1000));
            return;
        }
        ArrayList<HashMap<String, String>> a2 = a((ArrayList<HashMap<String, String>>) arrayList, c2);
        if (!a2.isEmpty()) {
            a(a.a(this.d), "APPS_INCR", a2);
        }
        ArrayList<HashMap<String, String>> a3 = a(c2, (ArrayList<HashMap<String, String>>) arrayList);
        if (!a3.isEmpty()) {
            a(a.a(this.d), "UNINSTALL", a3);
        }
        a((ArrayList<HashMap<String, String>>) arrayList);
        a(a.a(this.d) + (a.g(this.d) * 1000));
    }

    private ArrayList<HashMap<String, String>> a(ArrayList<HashMap<String, String>> arrayList, ArrayList<HashMap<String, String>> arrayList2) {
        boolean z;
        ArrayList<HashMap<String, String>> arrayList3 = new ArrayList<>();
        Iterator<HashMap<String, String>> it = arrayList.iterator();
        while (it.hasNext()) {
            HashMap next = it.next();
            String str = (String) next.get("pkg");
            if (!TextUtils.isEmpty(str)) {
                Iterator<HashMap<String, String>> it2 = arrayList2.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (str.equals(it2.next().get("pkg"))) {
                            z = true;
                            break;
                        }
                    } else {
                        z = false;
                        break;
                    }
                }
                if (!z) {
                    arrayList3.add(next);
                }
            }
        }
        return arrayList3;
    }
}
