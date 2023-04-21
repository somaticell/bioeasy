package cn.com.bioeasy.app.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.os.Process;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import cn.com.bioeasy.app.utils.ListUtils;
import cn.com.bioeasy.app.utils.ObjectUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class AppUtils {
    public static final int NETWORKTYPE_2G = 2;
    public static final int NETWORKTYPE_3G = 3;
    public static final int NETWORKTYPE_INVALID = 0;
    public static final int NETWORKTYPE_WAP = 1;
    public static final int NETWORKTYPE_WIFI = 4;

    private AppUtils() {
        throw new AssertionError();
    }

    public static boolean isNamedProcess(Context context, String processName) {
        if (context == null) {
            return false;
        }
        int pid = Process.myPid();
        List<ActivityManager.RunningAppProcessInfo> processInfoList = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (ListUtils.isEmpty(processInfoList)) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfoList) {
            if (processInfo != null && processInfo.pid == pid && ObjectUtils.isEquals(processName, processInfo.processName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isApplicationInBackground(Context context) {
        ComponentName topActivity;
        List<ActivityManager.RunningTaskInfo> taskList = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1);
        if (taskList == null || taskList.isEmpty() || (topActivity = taskList.get(0).topActivity) == null || topActivity.getPackageName().equals(context.getPackageName())) {
            return false;
        }
        return true;
    }

    public static int getWidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static String getUrlFileName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public static String getUrlPath(String url) {
        return url.substring(0, url.lastIndexOf("/") + 1);
    }

    public static int getHeight(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static int dip2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        return (int) ((spValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null) {
            return new PackageInfo();
        }
        return info;
    }

    public static boolean isStorageExists() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return true;
        }
        return false;
    }

    public static void hidekeyboard(Activity activity) {
        try {
            ((InputMethodManager) activity.getSystemService("input_method")).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager;
        NetworkInfo info;
        if (context == null || (connectivityManager = (ConnectivityManager) context.getSystemService("connectivity")) == null || (info = connectivityManager.getActiveNetworkInfo()) == null || !info.isConnected() || info.getState() != NetworkInfo.State.CONNECTED) {
            return false;
        }
        return true;
    }

    public static int getNetWorkType(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return 0;
        }
        String type = networkInfo.getTypeName();
        if (type.equalsIgnoreCase("WIFI")) {
            return 4;
        }
        if (!type.equalsIgnoreCase("MOBILE")) {
            return 0;
        }
        if (TextUtils.isEmpty(Proxy.getDefaultHost())) {
            return isFastMobileNetwork(context) ? 3 : 2;
        }
        return 1;
    }

    private static boolean isFastMobileNetwork(Context context) {
        switch (((TelephonyManager) context.getSystemService("phone")).getNetworkType()) {
            case 3:
                return true;
            case 5:
                return true;
            case 6:
                return true;
            case 8:
                return true;
            case 9:
                return true;
            case 10:
                return true;
            case 12:
                return true;
            case 13:
                return true;
            case 14:
                return true;
            case 15:
                return true;
            default:
                return false;
        }
    }

    public static void setNetworkMethod(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        try {
            builder.setTitle("网络设置提示").setMessage("网络连接不可用,是否进行设置?").setPositiveButton("设置", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent;
                    if (Build.VERSION.SDK_INT > 10) {
                        intent = new Intent("android.settings.WIRELESS_SETTINGS");
                    } else {
                        intent = new Intent();
                        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.WirelessSettings"));
                        intent.setAction("android.intent.action.VIEW");
                    }
                    context.startActivity(intent);
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0 || str.equalsIgnoreCase("null") || str.isEmpty() || str.equals("")) {
            return true;
        }
        return false;
    }

    public static String getFormatDate(long timemillis) {
        return new SimpleDateFormat("yyyy年MM月dd日").format(new Date(timemillis));
    }

    public static String decodeUnicodeStr(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        char[] chars = s.toCharArray();
        int i = 0;
        while (i < chars.length) {
            char c = chars[i];
            if (c == '\\' && chars[i + 1] == 'u') {
                char cc = 0;
                int j = 0;
                while (true) {
                    if (j >= 4) {
                        break;
                    }
                    char ch = Character.toLowerCase(chars[i + 2 + j]);
                    if (('0' > ch || ch > '9') && ('a' > ch || ch > 'f')) {
                        cc = 0;
                    } else {
                        cc = (char) ((Character.digit(ch, 16) << ((3 - j) * 4)) | cc);
                        j++;
                    }
                }
                cc = 0;
                if (cc > 0) {
                    i += 5;
                    sb.append(cc);
                    i++;
                }
            }
            sb.append(c);
            i++;
        }
        return sb.toString();
    }

    public static String encodeUnicodeStr(String s) {
        StringBuilder sb = new StringBuilder(s.length() * 3);
        for (char c : s.toCharArray()) {
            if (c < 256) {
                sb.append(c);
            } else {
                sb.append("\\u");
                sb.append(Character.forDigit((c >>> 12) & 15, 16));
                sb.append(Character.forDigit((c >>> 8) & 15, 16));
                sb.append(Character.forDigit((c >>> 4) & 15, 16));
                sb.append(Character.forDigit(c & 15, 16));
            }
        }
        return sb.toString();
    }

    public static String convertTime(int time) {
        int time2 = time / 1000;
        return String.format("%02d:%02d", new Object[]{Integer.valueOf((time2 / 60) % 60), Integer.valueOf(time2 % 60)});
    }

    /* JADX WARNING: type inference failed for: r6v5, types: [java.net.URLConnection] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isUrlUsable(java.lang.String r8) {
        /*
            r7 = 0
            boolean r6 = isEmpty(r8)
            if (r6 == 0) goto L_0x0009
            r6 = r7
        L_0x0008:
            return r6
        L_0x0009:
            r4 = 0
            r1 = 0
            java.net.URL r5 = new java.net.URL     // Catch:{ Exception -> 0x002f, all -> 0x0035 }
            r5.<init>(r8)     // Catch:{ Exception -> 0x002f, all -> 0x0035 }
            java.net.URLConnection r6 = r5.openConnection()     // Catch:{ Exception -> 0x003d, all -> 0x003a }
            r0 = r6
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ Exception -> 0x003d, all -> 0x003a }
            r1 = r0
            java.lang.String r6 = "HEAD"
            r1.setRequestMethod(r6)     // Catch:{ Exception -> 0x003d, all -> 0x003a }
            int r3 = r1.getResponseCode()     // Catch:{ Exception -> 0x003d, all -> 0x003a }
            r6 = 200(0xc8, float:2.8E-43)
            if (r3 != r6) goto L_0x002a
            r6 = 1
            r1.disconnect()
            goto L_0x0008
        L_0x002a:
            r1.disconnect()
            r6 = r7
            goto L_0x0008
        L_0x002f:
            r2 = move-exception
        L_0x0030:
            r1.disconnect()
            r6 = r7
            goto L_0x0008
        L_0x0035:
            r6 = move-exception
        L_0x0036:
            r1.disconnect()
            throw r6
        L_0x003a:
            r6 = move-exception
            r4 = r5
            goto L_0x0036
        L_0x003d:
            r2 = move-exception
            r4 = r5
            goto L_0x0030
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.com.bioeasy.app.common.AppUtils.isUrlUsable(java.lang.String):boolean");
    }

    public static boolean isUrl(String url) {
        return Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$").matcher(url).matches();
    }

    public static int getToolbarHeight(Context context) {
        TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(new int[]{16843499});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0.0f);
        styledAttributes.recycle();
        return toolbarHeight;
    }

    public static String convertPicStartWithW(String originalStr) {
        StringBuilder newStr = new StringBuilder();
        newStr.append("http://m.gzgov.gov.cn/webpic/" + originalStr.substring(0, 8) + "/" + originalStr.substring(0, 10) + "/" + originalStr);
        return newStr.toString();
    }

    public static void call(Context context, String phoneNumber) {
        if (ActivityCompat.checkSelfPermission(context, "android.permission.CALL_PHONE") == 0) {
            context.startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber)));
        }
    }

    public static void callDial(Context context, String phoneNumber) {
        context.startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + phoneNumber)));
    }

    public static void sendSms(Context context, String phoneNumber, String content) {
        StringBuilder append = new StringBuilder().append("smsto:");
        if (TextUtils.isEmpty(phoneNumber)) {
            phoneNumber = "";
        }
        Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse(append.append(phoneNumber).toString()));
        if (TextUtils.isEmpty(content)) {
            content = "";
        }
        intent.putExtra("sms_body", content);
        context.startActivity(intent);
    }

    public static void wakeUpAndUnlock(Context context) {
        ((KeyguardManager) context.getSystemService("keyguard")).newKeyguardLock("unLock").disableKeyguard();
        PowerManager.WakeLock wl = ((PowerManager) context.getSystemService("power")).newWakeLock(268435462, "bright");
        wl.acquire();
        wl.release();
    }

    public static boolean isApplicationBackground(Context context) {
        List<ActivityManager.RunningTaskInfo> tasks = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1);
        if (tasks.isEmpty() || tasks.get(0).topActivity.getPackageName().equals(context.getPackageName())) {
            return false;
        }
        return true;
    }

    public static boolean isSleeping(Context context) {
        return ((KeyguardManager) context.getSystemService("keyguard")).inKeyguardRestrictedInputMode();
    }

    public static boolean isOnline(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            return false;
        }
        return true;
    }

    public static boolean isWifiConnected(Context context) {
        if (((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(1).isConnected()) {
            return true;
        }
        return false;
    }

    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("application/vnd.android.package-archive");
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    public static boolean isPhone(Context context) {
        if (((TelephonyManager) context.getSystemService("phone")).getPhoneType() == 0) {
            return false;
        }
        return true;
    }

    public static int getDeviceWidth(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getWidth();
    }

    public static int getDeviceHeight(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getHeight();
    }

    @TargetApi(3)
    public static String getDeviceIMEI(Context context) {
        if (isPhone(context)) {
            return ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
        }
        return Settings.Secure.getString(context.getContentResolver(), "android_id");
    }

    public static String getMacAddress(Context context) {
        String macAddress = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
        if (macAddress == null) {
            return "";
        }
        return macAddress.replace(":", "");
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "0";
        }
    }

    public static int getAppVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
