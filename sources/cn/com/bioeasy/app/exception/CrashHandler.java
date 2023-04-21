package cn.com.bioeasy.app.exception;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import cn.com.bioeasy.app.base.AppManager;
import cn.com.bioeasy.app.common.AppUtils;
import cn.com.bioeasy.app.log.LogUtil;
import cn.com.bioeasy.app.storage.ext.SDCardUtil;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private String TAG = "CrashHandler";
    private AppManager activityManager = null;
    private Context context;
    private Map<String, String> infos;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private String path;

    @Inject
    public CrashHandler(Context app, AppManager activityManager2) {
        this.context = app;
        this.activityManager = activityManager2;
    }

    public void init() {
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.infos = new HashMap();
        this.path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/crash/";
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && this.mDefaultHandler != null) {
            this.mDefaultHandler.uncaughtException(thread, ex);
        }
    }

    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }
        ex.printStackTrace();
        System.out.println("=========:" + ex);
        new Thread() {
            public void run() {
                Looper.prepare();
                CrashHandler.this.collectDeviceInfo();
                String unused = CrashHandler.this.saveCrashInfo2File(ex);
                Looper.loop();
            }
        }.start();
        return true;
    }

    public void collectDeviceInfo() {
        this.infos.put("versionName", AppUtils.getAppVersionName(this.context));
        this.infos.put("versionCode", AppUtils.getAppVersionCode(this.context) + "");
        for (Field field : Build.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                this.infos.put(field.getName(), field.get((Object) null).toString());
                LogUtil.d(this.TAG, field.getName() + " : " + field.get((Object) null));
            } catch (Exception e) {
                LogUtil.e(this.TAG, "an error occured when collect crash info");
            }
        }
    }

    /* access modifiers changed from: private */
    public String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : this.infos.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue() + "\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        for (Throwable cause = ex.getCause(); cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        printWriter.close();
        sb.append(writer.toString());
        LogUtil.e(this.TAG, sb.toString());
        try {
            String fileName = "crash-" + System.currentTimeMillis() + ".log";
            if (!SDCardUtil.checkSDCardAvailable()) {
                return fileName;
            }
            SDCardUtil.saveFileToSDCard(this.path, fileName, sb.toString());
            return fileName;
        } catch (Throwable e) {
            LogUtil.e(this.TAG, "an error occured while writing file...\npath" + this.path);
            e.printStackTrace();
            return null;
        }
    }
}
