package com.baidu.location;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import com.baidu.location.e.a;
import com.baidu.location.f.j;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.RandomAccessFile;

public class f extends Service {
    public static boolean isServing = false;
    public static boolean isStartedServing = false;
    public static Context mC = null;
    public static String replaceFileName = "repll.jar";
    LLSInterface a = null;
    LLSInterface b = null;
    LLSInterface c = null;

    private boolean a(File file) {
        int readInt;
        boolean z = false;
        try {
            File file2 = new File(j.h() + "/grtcfrsa.dat");
            if (file2.exists()) {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file2, "rw");
                randomAccessFile.seek(200);
                if (randomAccessFile.readBoolean() && randomAccessFile.readBoolean() && (readInt = randomAccessFile.readInt()) != 0) {
                    byte[] bArr = new byte[readInt];
                    randomAccessFile.read(bArr, 0, readInt);
                    String str = new String(bArr);
                    String a2 = j.a(file, "SHA-256");
                    if (!(str == null || a2 == null || !j.b(a2, str, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiP7BS5IjEOzrKGR9/Ww9oSDhdX1ir26VOsYjT1T6tk2XumRpkHRwZbrucDcNnvSB4QsqiEJnvTSRi7YMbh2H9sLMkcvHlMV5jAErNvnuskWfcvf7T2mq7EUZI/Hf4oVZhHV0hQJRFVdTcjWI6q2uaaKM3VMh+roDesiE7CR2biQIDAQAB"))) {
                        z = true;
                    }
                }
                randomAccessFile.close();
            }
        } catch (Exception e) {
        }
        return z;
    }

    public static float getFrameVersion() {
        return 7.53f;
    }

    public static String getJarFileName() {
        return "app.jar";
    }

    public static Context getServiceContext() {
        return mC;
    }

    public IBinder onBind(Intent intent) {
        return this.c.onBind(intent);
    }

    @SuppressLint({"NewApi"})
    public void onCreate() {
        mC = getApplicationContext();
        System.currentTimeMillis();
        this.b = new a();
        try {
            File file = new File(j.h() + File.separator + replaceFileName);
            File file2 = new File(j.h() + File.separator + "app.jar");
            if (file.exists()) {
                if (file2.exists()) {
                    file2.delete();
                }
                file.renameTo(file2);
            }
            if (file2.exists() && a(new File(j.h() + File.separator + "app.jar"))) {
                this.a = (LLSInterface) new DexClassLoader(j.h() + File.separator + "app.jar", j.h(), (String) null, getClassLoader()).loadClass("com.baidu.serverLoc.LocationService").newInstance();
            }
        } catch (Exception e) {
            this.a = null;
        }
        if (this.a == null || this.a.getVersion() < this.b.getVersion()) {
            this.c = this.b;
            this.a = null;
        } else {
            this.c = this.a;
            this.b = null;
        }
        isServing = true;
        this.c.onCreate(this);
    }

    public void onDestroy() {
        isServing = false;
        this.c.onDestroy();
        if (isStartedServing) {
            stopForeground(true);
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent != null) {
            try {
                int intExtra = intent.getIntExtra("command", 0);
                if (intExtra == 1) {
                    startForeground(intent.getIntExtra("id", 0), (Notification) intent.getParcelableExtra("notification"));
                    isStartedServing = true;
                } else if (intExtra == 2) {
                    stopForeground(intent.getBooleanExtra("removenotify", true));
                    isStartedServing = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.c.onStartCommand(intent, i, i2);
    }

    public void onTaskRemoved(Intent intent) {
        this.c.onTaskRemoved(intent);
    }

    public boolean onUnbind(Intent intent) {
        return this.c.onUnBind(intent);
    }
}
