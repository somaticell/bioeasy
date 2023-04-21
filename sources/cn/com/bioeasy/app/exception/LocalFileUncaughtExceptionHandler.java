package cn.com.bioeasy.app.exception;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.Thread;
import java.util.Calendar;
import java.util.Locale;

public class LocalFileUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    public LocalFileUncaughtExceptionHandler(Context context, Thread.UncaughtExceptionHandler defaultHandler) {
        this.mDefaultHandler = defaultHandler;
        this.mContext = context;
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e("Crash", "Application crash", ex);
        writeFile(thread, ex);
        this.mDefaultHandler.uncaughtException(thread, ex);
    }

    private void writeFile(Thread thread, Throwable ex) {
        try {
            OutputStream os = getLogStream();
            os.write(getExceptionInformation(thread, ex).getBytes("utf-8"));
            os.flush();
            os.close();
            Process.killProcess(Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private OutputStream getLogStream() throws IOException {
        File file = new File(Environment.getExternalStorageDirectory(), String.format("crash_%s.log", new Object[]{this.mContext.getPackageName()}));
        if (!file.exists()) {
            file.createNewFile();
        }
        return new FileOutputStream(file, true);
    }

    private String getExceptionInformation(Thread thread, Throwable ex) {
        long current = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder().append(10);
        sb.append("THREAD: ").append(thread).append(10);
        sb.append("BOARD: ").append(Build.BOARD).append(10);
        sb.append("BOOTLOADER: ").append(Build.BOOTLOADER).append(10);
        sb.append("BRAND: ").append(Build.BRAND).append(10);
        sb.append("CPU_ABI: ").append(Build.CPU_ABI).append(10);
        sb.append("CPU_ABI2: ").append(Build.CPU_ABI2).append(10);
        sb.append("DEVICE: ").append(Build.DEVICE).append(10);
        sb.append("DISPLAY: ").append(Build.DISPLAY).append(10);
        sb.append("FINGERPRINT: ").append(Build.FINGERPRINT).append(10);
        sb.append("HARDWARE: ").append(Build.HARDWARE).append(10);
        sb.append("HOST: ").append(Build.HOST).append(10);
        sb.append("ID: ").append(Build.ID).append(10);
        sb.append("MANUFACTURER: ").append(Build.MANUFACTURER).append(10);
        sb.append("MODEL: ").append(Build.MODEL).append(10);
        sb.append("PRODUCT: ").append(Build.PRODUCT).append(10);
        sb.append("SERIAL: ").append(Build.SERIAL).append(10);
        sb.append("TAGS: ").append(Build.TAGS).append(10);
        sb.append("TIME: ").append(Build.TIME).append(' ').append(toDateString(Build.TIME)).append(10);
        sb.append("TYPE: ").append(Build.TYPE).append(10);
        sb.append("USER: ").append(Build.USER).append(10);
        sb.append("VERSION.CODENAME: ").append(Build.VERSION.CODENAME).append(10);
        sb.append("VERSION.INCREMENTAL: ").append(Build.VERSION.INCREMENTAL).append(10);
        sb.append("VERSION.RELEASE: ").append(Build.VERSION.RELEASE).append(10);
        sb.append("VERSION.SDK_INT: ").append(Build.VERSION.SDK_INT).append(10);
        sb.append("LANG: ").append(this.mContext.getResources().getConfiguration().locale.getLanguage()).append(10);
        sb.append("APP.VERSION.NAME: ").append(getVersionName()).append(10);
        sb.append("APP.VERSION.CODE: ").append(getVersionCode()).append(10);
        sb.append("CURRENT: ").append(current).append(' ').append(toDateString(current)).append(10);
        sb.append(getErrorInformation(ex));
        return sb.toString();
    }

    private String getVersionName() {
        try {
            return this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int getVersionCode() {
        try {
            return this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private String getErrorInformation(Throwable t) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(baos);
        t.printStackTrace(writer);
        writer.flush();
        String result = new String(baos.toByteArray());
        writer.close();
        return result;
    }

    private String toDateString(long timeMilli) {
        Calendar calc = Calendar.getInstance();
        calc.setTimeInMillis(timeMilli);
        return String.format(Locale.CHINESE, "%04d.%02d.%02d %02d:%02d:%02d:%03d", new Object[]{Integer.valueOf(calc.get(1)), Integer.valueOf(calc.get(2) + 1), Integer.valueOf(calc.get(5)), Integer.valueOf(calc.get(11)), Integer.valueOf(calc.get(12)), Integer.valueOf(calc.get(13)), Integer.valueOf(calc.get(14))});
    }
}
