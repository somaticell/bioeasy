package com.loc;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.alipay.sdk.util.h;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* compiled from: DeviceInfo */
public class q {
    /* access modifiers changed from: private */
    public static String a = "";
    /* access modifiers changed from: private */
    public static boolean b = false;
    private static String c = "";
    private static String d = "";
    private static String e = "";
    private static String f = "";

    public static void a() {
        try {
            if (Build.VERSION.SDK_INT > 14) {
                TrafficStats.class.getDeclaredMethod("setThreadStatsTag", new Class[]{Integer.TYPE}).invoke((Object) null, new Object[]{40964});
            }
        } catch (NoSuchMethodException e2) {
            y.a(e2, "DeviceInfo", "setTraficTag");
        } catch (IllegalAccessException e3) {
            y.a(e3, "DeviceInfo", "setTraficTag");
        } catch (IllegalArgumentException e4) {
            y.a(e4, "DeviceInfo", "setTraficTag");
        } catch (InvocationTargetException e5) {
            y.a(e5, "DeviceInfo", "setTraficTag");
        } catch (Throwable th) {
            y.a(th, "DeviceInfo", "setTraficTag");
        }
    }

    /* compiled from: DeviceInfo */
    static class a extends DefaultHandler {
        a() {
        }

        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            if (str2.equals("string") && "UTDID".equals(attributes.getValue("name"))) {
                boolean unused = q.b = true;
            }
        }

        public void characters(char[] cArr, int i, int i2) throws SAXException {
            if (q.b) {
                String unused = q.a = new String(cArr, i, i2);
            }
        }

        public void endElement(String str, String str2, String str3) throws SAXException {
            boolean unused = q.b = false;
        }
    }

    public static String a(Context context) {
        try {
            if (a != null && !"".equals(a)) {
                return a;
            }
            if (context.checkCallingOrSelfPermission("android.permission.WRITE_SETTINGS") == 0) {
                a = Settings.System.getString(context.getContentResolver(), "mqBRboGZkQPcAkyk");
            }
            if (a != null && !"".equals(a)) {
                return a;
            }
            try {
                if ("mounted".equals(Environment.getExternalStorageState())) {
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/.UTSystemConfig/Global/Alvin2.xml");
                    if (file.exists()) {
                        SAXParserFactory.newInstance().newSAXParser().parse(file, new a());
                    }
                }
            } catch (FileNotFoundException e2) {
                y.a(e2, "DeviceInfo", "getUTDID");
            } catch (ParserConfigurationException e3) {
                y.a(e3, "DeviceInfo", "getUTDID");
            } catch (SAXException e4) {
                y.a(e4, "DeviceInfo", "getUTDID");
            } catch (IOException e5) {
                y.a(e5, "DeviceInfo", "getUTDID");
            } catch (Throwable th) {
                y.a(th, "DeviceInfo", "getUTDID");
            }
            return a;
        } catch (Throwable th2) {
            y.a(th2, "DeviceInfo", "getUTDID");
        }
    }

    static String b(Context context) {
        if (context != null) {
            try {
                if (context.checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE") == 0) {
                    WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                    if (wifiManager.isWifiEnabled()) {
                        return wifiManager.getConnectionInfo().getBSSID();
                    }
                    return null;
                }
            } catch (Throwable th) {
                y.a(th, "DeviceInfo", "getWifiMacs");
            }
        }
        return null;
    }

    static String c(Context context) {
        StringBuilder sb = new StringBuilder();
        if (context != null) {
            try {
                if (context.checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE") == 0) {
                    WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                    if (wifiManager.isWifiEnabled()) {
                        List<ScanResult> scanResults = wifiManager.getScanResults();
                        if (scanResults == null || scanResults.size() == 0) {
                            return sb.toString();
                        }
                        List<ScanResult> a2 = a(scanResults);
                        boolean z = true;
                        int i = 0;
                        while (i < a2.size() && i < 7) {
                            ScanResult scanResult = a2.get(i);
                            if (z) {
                                z = false;
                            } else {
                                sb.append(h.b);
                            }
                            sb.append(scanResult.BSSID);
                            i++;
                        }
                    }
                    return sb.toString();
                }
            } catch (Throwable th) {
                y.a(th, "DeviceInfo", "getWifiMacs");
            }
        }
        return sb.toString();
    }

    static String d(Context context) {
        try {
            if (c != null && !"".equals(c)) {
                return c;
            }
            if (context.checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE") != 0) {
                return c;
            }
            c = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            return c;
        } catch (Throwable th) {
            y.a(th, "DeviceInfo", "getDeviceMac");
        }
    }

    static String[] e(Context context) {
        try {
            if (context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") == 0 && context.checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0) {
                CellLocation cellLocation = ((TelephonyManager) context.getSystemService("phone")).getCellLocation();
                if (cellLocation instanceof GsmCellLocation) {
                    GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                    return new String[]{gsmCellLocation.getLac() + "||" + gsmCellLocation.getCid(), "gsm"};
                }
                if (cellLocation instanceof CdmaCellLocation) {
                    CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
                    int systemId = cdmaCellLocation.getSystemId();
                    int networkId = cdmaCellLocation.getNetworkId();
                    int baseStationId = cdmaCellLocation.getBaseStationId();
                    if (systemId < 0 || networkId < 0 || baseStationId < 0) {
                    }
                    return new String[]{systemId + "||" + networkId + "||" + baseStationId, "cdma"};
                }
                return new String[]{"", ""};
            }
            return new String[]{"", ""};
        } catch (Throwable th) {
            y.a(th, "DeviceInfo", "cellInfo");
        }
    }

    static String f(Context context) {
        String str;
        try {
            if (context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
                return "";
            }
            String networkOperator = ((TelephonyManager) context.getSystemService("phone")).getNetworkOperator();
            if (TextUtils.isEmpty(networkOperator) && networkOperator.length() < 3) {
                return "";
            }
            str = networkOperator.substring(3);
            return str;
        } catch (Throwable th) {
            th.printStackTrace();
            y.a(th, "DeviceInfo", "getMNC");
            str = "";
        }
    }

    public static int g(Context context) {
        try {
            return r(context);
        } catch (Throwable th) {
            y.a(th, "DeviceInfo", "getNetWorkType");
            return -1;
        }
    }

    public static int h(Context context) {
        try {
            return q(context);
        } catch (Throwable th) {
            y.a(th, "DeviceInfo", "getActiveNetWorkType");
            return -1;
        }
    }

    public static NetworkInfo i(Context context) {
        if (context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") != 0) {
            return null;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return null;
        }
        return connectivityManager.getActiveNetworkInfo();
    }

    static String j(Context context) {
        try {
            NetworkInfo i = i(context);
            if (i == null) {
                return null;
            }
            return i.getExtraInfo();
        } catch (Throwable th) {
            y.a(th, "DeviceInfo", "getNetworkExtraInfo");
            return null;
        }
    }

    static String k(Context context) {
        try {
            if (d != null && !"".equals(d)) {
                return d;
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            int i = displayMetrics.widthPixels;
            int i2 = displayMetrics.heightPixels;
            d = i2 > i ? i + "*" + i2 : i2 + "*" + i;
            return d;
        } catch (Throwable th) {
            y.a(th, "DeviceInfo", "getReslution");
        }
    }

    public static String l(Context context) {
        try {
            if (e != null && !"".equals(e)) {
                return e;
            }
            if (context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
                return e;
            }
            e = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            if (e == null) {
                e = "";
            }
            return e;
        } catch (Throwable th) {
            y.a(th, "DeviceInfo", "getDeviceID");
        }
    }

    public static String m(Context context) {
        try {
            return o(context);
        } catch (Throwable th) {
            y.a(th, "DeviceInfo", "getSubscriberId");
            return "";
        }
    }

    static String n(Context context) {
        try {
            return p(context);
        } catch (Throwable th) {
            y.a(th, "DeviceInfo", "getNetworkOperatorName");
            return "";
        }
    }

    private static String o(Context context) {
        if (f != null && !"".equals(f)) {
            return f;
        }
        if (context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
            return f;
        }
        f = ((TelephonyManager) context.getSystemService("phone")).getSubscriberId();
        if (f == null) {
            f = "";
        }
        return f;
    }

    private static String p(Context context) {
        if (context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
            return null;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        String simOperatorName = telephonyManager.getSimOperatorName();
        return TextUtils.isEmpty(simOperatorName) ? telephonyManager.getNetworkOperatorName() : simOperatorName;
    }

    private static int q(Context context) {
        if (context == null || context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") != 0) {
            return -1;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return -1;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return -1;
        }
        return activeNetworkInfo.getType();
    }

    private static int r(Context context) {
        if (context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
            return -1;
        }
        return ((TelephonyManager) context.getSystemService("phone")).getNetworkType();
    }

    private static List<ScanResult> a(List<ScanResult> list) {
        int size = list.size();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= size - 1) {
                return list;
            }
            int i3 = 1;
            while (true) {
                int i4 = i3;
                if (i4 >= size - i2) {
                    break;
                }
                if (list.get(i4 - 1).level > list.get(i4).level) {
                    list.set(i4 - 1, list.get(i4));
                    list.set(i4, list.get(i4 - 1));
                }
                i3 = i4 + 1;
            }
            i = i2 + 1;
        }
    }
}
