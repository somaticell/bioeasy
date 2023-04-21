package com.loc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import com.alipay.android.phone.mrpc.core.Headers;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TreeMap;

public final class cl {
    /* access modifiers changed from: private */
    public static int G = 10000;
    private static String[] H = {"android.permission.READ_PHONE_STATE", "android.permission.ACCESS_WIFI_STATE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.CHANGE_WIFI_STATE", "android.permission.ACCESS_NETWORK_STATE"};
    static String a = "";
    protected static boolean b = true;
    protected static boolean c = false;
    private static cl x = null;
    /* access modifiers changed from: private */
    public CellLocation A = null;
    private cp B = null;
    /* access modifiers changed from: private */
    public List C = new ArrayList();
    /* access modifiers changed from: private */
    public Timer D = null;
    private Thread E = null;
    /* access modifiers changed from: private */
    public Looper F = null;
    private Context d = null;
    /* access modifiers changed from: private */
    public TelephonyManager e = null;
    private LocationManager f = null;
    /* access modifiers changed from: private */
    public WifiManager g = null;
    private SensorManager h = null;
    private String i = "";
    private String j = "";
    private String k = "";
    /* access modifiers changed from: private */
    public boolean l = false;
    /* access modifiers changed from: private */
    public int m = 0;
    /* access modifiers changed from: private */
    public boolean n = false;
    /* access modifiers changed from: private */
    public long o = -1;
    /* access modifiers changed from: private */
    public String p = "";
    private String q = "";
    /* access modifiers changed from: private */
    public int r = 0;
    /* access modifiers changed from: private */
    public int s = 0;
    private int t = 0;
    private String u = "";
    /* access modifiers changed from: private */
    public long v = 0;
    /* access modifiers changed from: private */
    public long w = 0;
    /* access modifiers changed from: private */
    public cn y = null;
    /* access modifiers changed from: private */
    public co z = null;

    private cl(Context context) {
        if (context != null) {
            this.d = context;
            this.i = Build.MODEL;
            this.e = (TelephonyManager) context.getSystemService("phone");
            this.f = (LocationManager) context.getSystemService(Headers.LOCATION);
            this.g = (WifiManager) context.getSystemService("wifi");
            this.h = (SensorManager) context.getSystemService("sensor");
            if (this.e != null && this.g != null) {
                try {
                    this.j = this.e.getDeviceId();
                } catch (Exception e2) {
                }
                this.k = this.e.getSubscriberId();
                if (this.g.getConnectionInfo() != null) {
                    this.q = this.g.getConnectionInfo().getMacAddress();
                    if (this.q != null && this.q.length() > 0) {
                        this.q = this.q.replace(":", "");
                    }
                }
                String[] b2 = b(this.e);
                this.r = Integer.parseInt(b2[0]);
                this.s = Integer.parseInt(b2[1]);
                this.t = this.e.getNetworkType();
                this.u = context.getPackageName();
                this.l = this.e.getPhoneType() == 2;
            }
        }
    }

    private CellLocation A() {
        CellLocation cellLocation;
        if (this.e == null) {
            return null;
        }
        try {
            cellLocation = b((List) ci.a(this.e, "getAllCellInfo", new Object[0]));
        } catch (NoSuchMethodException e2) {
            cellLocation = null;
        } catch (Exception e3) {
            cellLocation = null;
        }
        return cellLocation;
    }

    private static int a(CellLocation cellLocation, Context context) {
        if (Settings.System.getInt(context.getContentResolver(), "airplane_mode_on", 0) == 1 || cellLocation == null) {
            return 9;
        }
        if (cellLocation instanceof GsmCellLocation) {
            return 1;
        }
        try {
            Class.forName("android.telephony.cdma.CdmaCellLocation");
            return 2;
        } catch (Exception e2) {
            return 9;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0045 A[EDGE_INSN: B:19:0x0045->B:18:0x0045 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0022  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static com.loc.cl a(android.content.Context r3) {
        /*
            com.loc.cl r0 = x
            if (r0 != 0) goto L_0x0042
            boolean r0 = c((android.content.Context) r3)
            if (r0 == 0) goto L_0x0042
            java.lang.String r0 = "location"
            java.lang.Object r0 = r3.getSystemService(r0)
            android.location.LocationManager r0 = (android.location.LocationManager) r0
            if (r0 == 0) goto L_0x0045
            java.util.List r0 = r0.getAllProviders()
            java.util.Iterator r1 = r0.iterator()
        L_0x001c:
            boolean r0 = r1.hasNext()
            if (r0 == 0) goto L_0x0045
            java.lang.Object r0 = r1.next()
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r2 = "passive"
            boolean r2 = r0.equals(r2)
            if (r2 != 0) goto L_0x0038
            java.lang.String r2 = "gps"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x001c
        L_0x0038:
            r0 = 1
        L_0x0039:
            if (r0 == 0) goto L_0x0042
            com.loc.cl r0 = new com.loc.cl
            r0.<init>(r3)
            x = r0
        L_0x0042:
            com.loc.cl r0 = x
            return r0
        L_0x0045:
            r0 = 0
            goto L_0x0039
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cl.a(android.content.Context):com.loc.cl");
    }

    private void a(BroadcastReceiver broadcastReceiver) {
        if (broadcastReceiver != null && this.d != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
            this.d.registerReceiver(broadcastReceiver, intentFilter);
        }
    }

    /* access modifiers changed from: private */
    public static void a(WifiManager wifiManager) {
        if (wifiManager != null) {
            try {
                ci.a(wifiManager, "startScanActive", new Object[0]);
            } catch (Exception e2) {
                wifiManager.startScan();
            }
        }
    }

    static /* synthetic */ void a(cl clVar, GpsStatus.NmeaListener nmeaListener) {
        if (clVar.f != null && nmeaListener != null) {
            clVar.f.addNmeaListener(nmeaListener);
        }
    }

    static /* synthetic */ void a(cl clVar, PhoneStateListener phoneStateListener) {
        if (clVar.e != null) {
            clVar.e.listen(phoneStateListener, 273);
        }
    }

    private static void a(List list) {
        if (list != null && list.size() > 0) {
            HashMap hashMap = new HashMap();
            int i2 = 0;
            while (true) {
                int i3 = i2;
                if (i3 >= list.size()) {
                    break;
                }
                ScanResult scanResult = (ScanResult) list.get(i3);
                if (scanResult.SSID == null) {
                    scanResult.SSID = "null";
                }
                hashMap.put(Integer.valueOf(scanResult.level), scanResult);
                i2 = i3 + 1;
            }
            TreeMap treeMap = new TreeMap(Collections.reverseOrder());
            treeMap.putAll(hashMap);
            list.clear();
            for (Object obj : treeMap.keySet()) {
                list.add(treeMap.get(obj));
            }
            hashMap.clear();
            treeMap.clear();
        }
    }

    private boolean a(CellLocation cellLocation) {
        if (cellLocation == null) {
            return false;
        }
        switch (a(cellLocation, this.d)) {
            case 1:
                GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                if (gsmCellLocation.getLac() == -1 || gsmCellLocation.getLac() == 0 || gsmCellLocation.getLac() > 65535 || gsmCellLocation.getCid() == -1 || gsmCellLocation.getCid() == 0 || gsmCellLocation.getCid() == 65535 || gsmCellLocation.getCid() >= 268435455) {
                    return false;
                }
            case 2:
                try {
                    if (ci.b(cellLocation, "getSystemId", new Object[0]) <= 0 || ci.b(cellLocation, "getNetworkId", new Object[0]) < 0 || ci.b(cellLocation, "getBaseStationId", new Object[0]) < 0) {
                        return false;
                    }
                } catch (Exception e2) {
                    break;
                }
        }
        return true;
    }

    private static boolean a(Object obj) {
        try {
            Method declaredMethod = WifiManager.class.getDeclaredMethod("isScanAlwaysAvailable", (Class[]) null);
            if (declaredMethod != null) {
                return ((Boolean) declaredMethod.invoke(obj, (Object[]) null)).booleanValue();
            }
        } catch (Exception e2) {
        }
        return false;
    }

    private static int b(Object obj) {
        try {
            Method declaredMethod = Sensor.class.getDeclaredMethod("getMinDelay", (Class[]) null);
            if (declaredMethod != null) {
                return ((Integer) declaredMethod.invoke(obj, (Object[]) null)).intValue();
            }
        } catch (Exception e2) {
        }
        return 0;
    }

    /* JADX WARNING: type inference failed for: r6v6 */
    /* JADX WARNING: type inference failed for: r6v7 */
    /* JADX WARNING: type inference failed for: r6v8 */
    /* JADX WARNING: type inference failed for: r6v9 */
    /* JADX WARNING: type inference failed for: r6v10 */
    /* JADX WARNING: type inference failed for: r6v11 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.telephony.CellLocation b(java.util.List r12) {
        /*
            if (r12 == 0) goto L_0x0008
            boolean r0 = r12.isEmpty()
            if (r0 == 0) goto L_0x000a
        L_0x0008:
            r0 = 0
        L_0x0009:
            return r0
        L_0x000a:
            java.lang.ClassLoader r9 = java.lang.ClassLoader.getSystemClassLoader()
            r7 = 0
            r2 = 0
            r1 = 0
            r0 = 0
            r8 = r0
            r0 = r1
            r1 = r2
        L_0x0015:
            int r2 = r12.size()
            if (r8 >= r2) goto L_0x0124
            java.lang.Object r2 = r12.get(r8)
            if (r2 == 0) goto L_0x0121
            java.lang.String r3 = "android.telephony.CellInfoGsm"
            java.lang.Class r3 = r9.loadClass(r3)     // Catch:{ Exception -> 0x010e }
            java.lang.String r4 = "android.telephony.CellInfoWcdma"
            java.lang.Class r4 = r9.loadClass(r4)     // Catch:{ Exception -> 0x010e }
            java.lang.String r5 = "android.telephony.CellInfoLte"
            java.lang.Class r5 = r9.loadClass(r5)     // Catch:{ Exception -> 0x010e }
            java.lang.String r6 = "android.telephony.CellInfoCdma"
            java.lang.Class r10 = r9.loadClass(r6)     // Catch:{ Exception -> 0x010e }
            boolean r6 = r3.isInstance(r2)     // Catch:{ Exception -> 0x010e }
            if (r6 == 0) goto L_0x005c
            r6 = 1
        L_0x0040:
            if (r6 <= 0) goto L_0x010a
            r0 = 0
            r11 = 1
            if (r6 != r11) goto L_0x0076
            java.lang.Object r0 = r3.cast(r2)     // Catch:{ Exception -> 0x0112 }
        L_0x004a:
            java.lang.String r2 = "getCellIdentity"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0112 }
            java.lang.Object r2 = com.loc.ci.a(r0, r2, r3)     // Catch:{ Exception -> 0x0112 }
            if (r2 != 0) goto L_0x008e
            r0 = r6
            r2 = r7
        L_0x0057:
            int r3 = r8 + 1
            r8 = r3
            r7 = r2
            goto L_0x0015
        L_0x005c:
            boolean r6 = r4.isInstance(r2)     // Catch:{ Exception -> 0x010e }
            if (r6 == 0) goto L_0x0064
            r6 = 2
            goto L_0x0040
        L_0x0064:
            boolean r6 = r5.isInstance(r2)     // Catch:{ Exception -> 0x010e }
            if (r6 == 0) goto L_0x006c
            r6 = 3
            goto L_0x0040
        L_0x006c:
            boolean r0 = r10.isInstance(r2)     // Catch:{ Exception -> 0x010e }
            if (r0 == 0) goto L_0x0074
            r6 = 4
            goto L_0x0040
        L_0x0074:
            r6 = 0
            goto L_0x0040
        L_0x0076:
            r3 = 2
            if (r6 != r3) goto L_0x007e
            java.lang.Object r0 = r4.cast(r2)     // Catch:{ Exception -> 0x0112 }
            goto L_0x004a
        L_0x007e:
            r3 = 3
            if (r6 != r3) goto L_0x0086
            java.lang.Object r0 = r5.cast(r2)     // Catch:{ Exception -> 0x0112 }
            goto L_0x004a
        L_0x0086:
            r3 = 4
            if (r6 != r3) goto L_0x004a
            java.lang.Object r0 = r10.cast(r2)     // Catch:{ Exception -> 0x0112 }
            goto L_0x004a
        L_0x008e:
            r0 = 4
            if (r6 != r0) goto L_0x00cd
            android.telephony.cdma.CdmaCellLocation r0 = new android.telephony.cdma.CdmaCellLocation     // Catch:{ Exception -> 0x0112 }
            r0.<init>()     // Catch:{ Exception -> 0x0112 }
            java.lang.String r1 = "getSystemId"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0117 }
            int r4 = com.loc.ci.b(r2, r1, r3)     // Catch:{ Exception -> 0x0117 }
            java.lang.String r1 = "getNetworkId"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0117 }
            int r5 = com.loc.ci.b(r2, r1, r3)     // Catch:{ Exception -> 0x0117 }
            java.lang.String r1 = "getBasestationId"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0117 }
            int r1 = com.loc.ci.b(r2, r1, r3)     // Catch:{ Exception -> 0x0117 }
            java.lang.String r3 = "getLongitude"
            r10 = 0
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ Exception -> 0x0117 }
            int r3 = com.loc.ci.b(r2, r3, r10)     // Catch:{ Exception -> 0x0117 }
            java.lang.String r10 = "getLatitude"
            r11 = 0
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x0117 }
            int r2 = com.loc.ci.b(r2, r10, r11)     // Catch:{ Exception -> 0x0117 }
            r0.setCellLocationData(r1, r2, r3, r4, r5)     // Catch:{ Exception -> 0x0117 }
            r1 = r7
        L_0x00c7:
            r2 = 4
            if (r6 == r2) goto L_0x0009
            r0 = r1
            goto L_0x0009
        L_0x00cd:
            r0 = 3
            if (r6 != r0) goto L_0x00ed
            java.lang.String r0 = "getTac"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0112 }
            int r0 = com.loc.ci.b(r2, r0, r3)     // Catch:{ Exception -> 0x0112 }
            java.lang.String r3 = "getCi"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x0112 }
            int r3 = com.loc.ci.b(r2, r3, r4)     // Catch:{ Exception -> 0x0112 }
            android.telephony.gsm.GsmCellLocation r2 = new android.telephony.gsm.GsmCellLocation     // Catch:{ Exception -> 0x0112 }
            r2.<init>()     // Catch:{ Exception -> 0x0112 }
            r2.setLacAndCid(r0, r3)     // Catch:{ Exception -> 0x011d }
            r0 = r1
            r1 = r2
            goto L_0x00c7
        L_0x00ed:
            java.lang.String r0 = "getLac"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0112 }
            int r0 = com.loc.ci.b(r2, r0, r3)     // Catch:{ Exception -> 0x0112 }
            java.lang.String r3 = "getCid"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x0112 }
            int r3 = com.loc.ci.b(r2, r3, r4)     // Catch:{ Exception -> 0x0112 }
            android.telephony.gsm.GsmCellLocation r2 = new android.telephony.gsm.GsmCellLocation     // Catch:{ Exception -> 0x0112 }
            r2.<init>()     // Catch:{ Exception -> 0x0112 }
            r2.setLacAndCid(r0, r3)     // Catch:{ Exception -> 0x011d }
            r0 = r1
            r1 = r2
            goto L_0x00c7
        L_0x010a:
            r0 = r6
            r2 = r7
            goto L_0x0057
        L_0x010e:
            r2 = move-exception
            r2 = r7
            goto L_0x0057
        L_0x0112:
            r0 = move-exception
            r0 = r6
            r2 = r7
            goto L_0x0057
        L_0x0117:
            r1 = move-exception
            r1 = r0
            r2 = r7
            r0 = r6
            goto L_0x0057
        L_0x011d:
            r0 = move-exception
            r0 = r6
            goto L_0x0057
        L_0x0121:
            r2 = r7
            goto L_0x0057
        L_0x0124:
            r6 = r0
            r0 = r1
            r1 = r7
            goto L_0x00c7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cl.b(java.util.List):android.telephony.CellLocation");
    }

    private void b(BroadcastReceiver broadcastReceiver) {
        if (broadcastReceiver != null && this.d != null) {
            try {
                this.d.unregisterReceiver(broadcastReceiver);
            } catch (Exception e2) {
            }
        }
    }

    protected static boolean b(Context context) {
        boolean z2;
        boolean z3;
        if (context == null) {
            return true;
        }
        if (!Settings.Secure.getString(context.getContentResolver(), "mock_location").equals("0")) {
            PackageManager packageManager = context.getPackageManager();
            List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(128);
            String packageName = context.getPackageName();
            z2 = false;
            for (ApplicationInfo next : installedApplications) {
                if (z2) {
                    break;
                }
                try {
                    String[] strArr = packageManager.getPackageInfo(next.packageName, 4096).requestedPermissions;
                    if (strArr != null) {
                        int length = strArr.length;
                        int i2 = 0;
                        while (true) {
                            if (i2 >= length) {
                                break;
                            } else if (!strArr[i2].equals("android.permission.ACCESS_MOCK_LOCATION")) {
                                i2++;
                            } else if (!next.packageName.equals(packageName)) {
                                z3 = true;
                            }
                        }
                    }
                } catch (Exception e2) {
                    z3 = z2;
                }
            }
        } else {
            z2 = false;
        }
        return z2;
        z2 = z3;
    }

    /* access modifiers changed from: private */
    public static String[] b(TelephonyManager telephonyManager) {
        int i2 = 0;
        String str = null;
        if (telephonyManager != null) {
            str = telephonyManager.getNetworkOperator();
        }
        String[] strArr = {"0", "0"};
        if (TextUtils.isDigitsOnly(str) && str.length() > 4) {
            strArr[0] = str.substring(0, 3);
            char[] charArray = str.substring(3).toCharArray();
            while (i2 < charArray.length && Character.isDigit(charArray[i2])) {
                i2++;
            }
            strArr[1] = str.substring(3, i2 + 3);
        }
        return strArr;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0035 A[LOOP:0: B:4:0x0013->B:18:0x0035, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x002f A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean c(android.content.Context r7) {
        /*
            r3 = 1
            r1 = 0
            android.content.pm.PackageManager r0 = r7.getPackageManager()
            java.lang.String r2 = r7.getPackageName()     // Catch:{ NameNotFoundException -> 0x003b }
            r4 = 4096(0x1000, float:5.74E-42)
            android.content.pm.PackageInfo r0 = r0.getPackageInfo(r2, r4)     // Catch:{ NameNotFoundException -> 0x003b }
            java.lang.String[] r4 = r0.requestedPermissions
            r2 = r1
        L_0x0013:
            java.lang.String[] r0 = H
            int r0 = r0.length
            if (r2 >= r0) goto L_0x0039
            java.lang.String[] r0 = H
            r5 = r0[r2]
            if (r4 == 0) goto L_0x0033
            if (r5 == 0) goto L_0x0033
            r0 = r1
        L_0x0021:
            int r6 = r4.length
            if (r0 >= r6) goto L_0x0033
            r6 = r4[r0]
            boolean r6 = r6.equals(r5)
            if (r6 == 0) goto L_0x0030
            r0 = r3
        L_0x002d:
            if (r0 != 0) goto L_0x0035
        L_0x002f:
            return r1
        L_0x0030:
            int r0 = r0 + 1
            goto L_0x0021
        L_0x0033:
            r0 = r1
            goto L_0x002d
        L_0x0035:
            int r0 = r2 + 1
            r2 = r0
            goto L_0x0013
        L_0x0039:
            r1 = r3
            goto L_0x002f
        L_0x003b:
            r0 = move-exception
            goto L_0x002f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cl.c(android.content.Context):boolean");
    }

    private void z() {
        if (this.g != null) {
            try {
                if (b) {
                    a(this.g);
                }
            } catch (Exception e2) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public final List a(float f2) {
        CellLocation cellLocation;
        ArrayList arrayList = new ArrayList();
        long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(f2) <= 1.0f) {
            f2 = 1.0f;
        }
        if (c() && (cellLocation = (CellLocation) j().get(1)) != null && (cellLocation instanceof GsmCellLocation)) {
            arrayList.add(Integer.valueOf(((GsmCellLocation) cellLocation).getLac()));
            arrayList.add(Integer.valueOf(((GsmCellLocation) cellLocation).getCid()));
            if (((double) (currentTimeMillis - ((Long) j().get(0)).longValue())) <= 50000.0d / ((double) f2)) {
                arrayList.add(1);
            } else {
                arrayList.add(0);
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0022, code lost:
        if ((java.lang.System.currentTimeMillis() - r8.v < 3500) != false) goto L_0x0024;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List a(boolean r9) {
        /*
            r8 = this;
            r1 = 0
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            boolean r0 = r8.d()
            if (r0 == 0) goto L_0x004b
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            monitor-enter(r8)
            if (r9 != 0) goto L_0x0024
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0048 }
            long r6 = r8.v     // Catch:{ all -> 0x0048 }
            long r4 = r4 - r6
            r6 = 3500(0xdac, double:1.729E-320)
            int r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r2 >= 0) goto L_0x0041
            r2 = 1
        L_0x0022:
            if (r2 == 0) goto L_0x0046
        L_0x0024:
            long r4 = r8.v     // Catch:{ all -> 0x0048 }
            java.lang.Long r2 = java.lang.Long.valueOf(r4)     // Catch:{ all -> 0x0048 }
            r0.add(r2)     // Catch:{ all -> 0x0048 }
        L_0x002d:
            java.util.List r2 = r8.C     // Catch:{ all -> 0x0048 }
            int r2 = r2.size()     // Catch:{ all -> 0x0048 }
            if (r1 >= r2) goto L_0x0043
            java.util.List r2 = r8.C     // Catch:{ all -> 0x0048 }
            java.lang.Object r2 = r2.get(r1)     // Catch:{ all -> 0x0048 }
            r3.add(r2)     // Catch:{ all -> 0x0048 }
            int r1 = r1 + 1
            goto L_0x002d
        L_0x0041:
            r2 = r1
            goto L_0x0022
        L_0x0043:
            r0.add(r3)     // Catch:{ all -> 0x0048 }
        L_0x0046:
            monitor-exit(r8)     // Catch:{ all -> 0x0048 }
        L_0x0047:
            return r0
        L_0x0048:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
        L_0x004b:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            goto L_0x0047
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cl.a(boolean):java.util.List");
    }

    /* access modifiers changed from: protected */
    public final void a() {
        b();
        if (this.F != null) {
            this.F.quit();
            this.F = null;
        }
        if (this.E != null) {
            this.E.interrupt();
            this.E = null;
        }
        this.E = new cm(this, "");
        this.E.start();
    }

    /* access modifiers changed from: protected */
    public final void a(int i2) {
        if (i2 != G) {
            synchronized (this) {
                this.C.clear();
            }
            if (this.B != null) {
                b((BroadcastReceiver) this.B);
                this.B = null;
            }
            if (this.D != null) {
                this.D.cancel();
                this.D = null;
            }
            if (i2 >= 5000) {
                G = i2;
                this.D = new Timer();
                this.B = new cp(this, (byte) 0);
                a((BroadcastReceiver) this.B);
                z();
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x000c, code lost:
        r1 = r2.h.getSensorList(-1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String b(int r3) {
        /*
            r2 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            android.hardware.SensorManager r0 = r2.h
            if (r0 != 0) goto L_0x000c
            java.lang.String r0 = "null"
        L_0x000b:
            return r0
        L_0x000c:
            android.hardware.SensorManager r0 = r2.h
            r1 = -1
            java.util.List r1 = r0.getSensorList(r1)
            if (r1 == 0) goto L_0x0037
            java.lang.Object r0 = r1.get(r3)
            if (r0 == 0) goto L_0x0037
            java.lang.Object r0 = r1.get(r3)
            android.hardware.Sensor r0 = (android.hardware.Sensor) r0
            java.lang.String r0 = r0.getName()
            if (r0 == 0) goto L_0x0037
            java.lang.Object r0 = r1.get(r3)
            android.hardware.Sensor r0 = (android.hardware.Sensor) r0
            java.lang.String r0 = r0.getName()
            int r0 = r0.length()
            if (r0 > 0) goto L_0x003a
        L_0x0037:
            java.lang.String r0 = "null"
            goto L_0x000b
        L_0x003a:
            java.lang.Object r0 = r1.get(r3)
            android.hardware.Sensor r0 = (android.hardware.Sensor) r0
            java.lang.String r0 = r0.getName()
            goto L_0x000b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cl.b(int):java.lang.String");
    }

    /* access modifiers changed from: protected */
    public final List b(float f2) {
        CellLocation cellLocation;
        ArrayList arrayList = new ArrayList();
        long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(f2) <= 1.0f) {
            f2 = 1.0f;
        }
        if (c() && (cellLocation = (CellLocation) j().get(1)) != null && (cellLocation instanceof CdmaCellLocation)) {
            CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
            arrayList.add(Integer.valueOf(cdmaCellLocation.getSystemId()));
            arrayList.add(Integer.valueOf(cdmaCellLocation.getNetworkId()));
            arrayList.add(Integer.valueOf(cdmaCellLocation.getBaseStationId()));
            arrayList.add(Integer.valueOf(cdmaCellLocation.getBaseStationLongitude()));
            arrayList.add(Integer.valueOf(cdmaCellLocation.getBaseStationLatitude()));
            if (((double) (currentTimeMillis - ((Long) j().get(0)).longValue())) <= 50000.0d / ((double) f2)) {
                arrayList.add(1);
            } else {
                arrayList.add(0);
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public final void b() {
        if (this.y != null) {
            cn cnVar = this.y;
            if (this.e != null) {
                this.e.listen(cnVar, 0);
            }
            this.y = null;
        }
        if (this.z != null) {
            co coVar = this.z;
            if (!(this.f == null || coVar == null)) {
                this.f.removeNmeaListener(coVar);
            }
            this.z = null;
        }
        if (this.D != null) {
            this.D.cancel();
            this.D = null;
        }
        if (this.F != null) {
            this.F.quit();
            this.F = null;
        }
        if (this.E != null) {
            this.E.interrupt();
            this.E = null;
        }
    }

    /* access modifiers changed from: protected */
    public final double c(int i2) {
        List<Sensor> sensorList;
        new ArrayList();
        if (this.h == null || (sensorList = this.h.getSensorList(-1)) == null || sensorList.get(i2) == null) {
            return 0.0d;
        }
        return (double) sensorList.get(i2).getMaximumRange();
    }

    /* access modifiers changed from: protected */
    public final boolean c() {
        CellLocation cellLocation = null;
        if (this.e != null && this.e.getSimState() == 5 && this.n) {
            return true;
        }
        if (this.e != null) {
            try {
                cellLocation = this.e.getCellLocation();
            } catch (Exception e2) {
            }
            if (cellLocation != null) {
                this.w = System.currentTimeMillis();
                this.A = cellLocation;
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public final int d(int i2) {
        List<Sensor> sensorList;
        new ArrayList();
        if (this.h == null || (sensorList = this.h.getSensorList(-1)) == null || sensorList.get(i2) == null) {
            return 0;
        }
        return b((Object) sensorList.get(i2));
    }

    /* access modifiers changed from: protected */
    public final boolean d() {
        return this.g != null && (this.g.isWifiEnabled() || a((Object) this.g));
    }

    /* access modifiers changed from: protected */
    public final int e(int i2) {
        List<Sensor> sensorList;
        new ArrayList();
        if (this.h == null || (sensorList = this.h.getSensorList(-1)) == null || sensorList.get(i2) == null) {
            return 0;
        }
        return (int) (((double) sensorList.get(i2).getPower()) * 100.0d);
    }

    /* access modifiers changed from: protected */
    public final boolean e() {
        try {
            return this.f != null && this.f.isProviderEnabled("gps");
        } catch (Exception e2) {
        }
    }

    /* access modifiers changed from: protected */
    public final double f(int i2) {
        List<Sensor> sensorList;
        new ArrayList();
        if (this.h == null || (sensorList = this.h.getSensorList(-1)) == null || sensorList.get(i2) == null) {
            return 0.0d;
        }
        return (double) sensorList.get(i2).getResolution();
    }

    /* access modifiers changed from: protected */
    public final String f() {
        if (this.i == null) {
            this.i = Build.MODEL;
        }
        return this.i != null ? this.i : "";
    }

    /* access modifiers changed from: protected */
    public final byte g(int i2) {
        new ArrayList();
        if (this.h == null) {
            return Byte.MAX_VALUE;
        }
        List<Sensor> sensorList = this.h.getSensorList(-1);
        if (sensorList == null || sensorList.get(i2) == null || sensorList.get(i2).getType() > 127) {
            return Byte.MAX_VALUE;
        }
        return (byte) sensorList.get(i2).getType();
    }

    /* access modifiers changed from: protected */
    public final String g() {
        if (this.j == null && this.d != null) {
            this.e = (TelephonyManager) this.d.getSystemService("phone");
            if (this.e != null) {
                try {
                    this.j = this.e.getDeviceId();
                } catch (Exception e2) {
                }
            }
        }
        return this.j != null ? this.j : "";
    }

    /* access modifiers changed from: protected */
    public final String h() {
        if (this.k == null && this.d != null) {
            this.e = (TelephonyManager) this.d.getSystemService("phone");
            if (this.e != null) {
                this.k = this.e.getSubscriberId();
            }
        }
        return this.k != null ? this.k : "";
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x000c, code lost:
        r1 = r2.h.getSensorList(-1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String h(int r3) {
        /*
            r2 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            android.hardware.SensorManager r0 = r2.h
            if (r0 != 0) goto L_0x000c
            java.lang.String r0 = "null"
        L_0x000b:
            return r0
        L_0x000c:
            android.hardware.SensorManager r0 = r2.h
            r1 = -1
            java.util.List r1 = r0.getSensorList(r1)
            if (r1 == 0) goto L_0x0037
            java.lang.Object r0 = r1.get(r3)
            if (r0 == 0) goto L_0x0037
            java.lang.Object r0 = r1.get(r3)
            android.hardware.Sensor r0 = (android.hardware.Sensor) r0
            java.lang.String r0 = r0.getVendor()
            if (r0 == 0) goto L_0x0037
            java.lang.Object r0 = r1.get(r3)
            android.hardware.Sensor r0 = (android.hardware.Sensor) r0
            java.lang.String r0 = r0.getVendor()
            int r0 = r0.length()
            if (r0 > 0) goto L_0x003a
        L_0x0037:
            java.lang.String r0 = "null"
            goto L_0x000b
        L_0x003a:
            java.lang.Object r0 = r1.get(r3)
            android.hardware.Sensor r0 = (android.hardware.Sensor) r0
            java.lang.String r0 = r0.getVendor()
            goto L_0x000b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cl.h(int):java.lang.String");
    }

    /* access modifiers changed from: protected */
    public final byte i(int i2) {
        new ArrayList();
        if (this.h == null) {
            return Byte.MAX_VALUE;
        }
        List<Sensor> sensorList = this.h.getSensorList(-1);
        if (sensorList == null || sensorList.get(i2) == null || sensorList.get(i2).getType() > 127) {
            return Byte.MAX_VALUE;
        }
        return (byte) sensorList.get(i2).getVersion();
    }

    /* access modifiers changed from: protected */
    public final boolean i() {
        return this.l;
    }

    /* access modifiers changed from: protected */
    public final List j() {
        CellLocation cellLocation;
        if (Settings.System.getInt(this.d.getContentResolver(), "airplane_mode_on", 0) == 1) {
            return new ArrayList();
        }
        if (!c()) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        if (!a(this.A)) {
            cellLocation = A();
            if (a(cellLocation)) {
                this.w = System.currentTimeMillis();
                arrayList.add(Long.valueOf(this.w));
                arrayList.add(cellLocation);
                return arrayList;
            }
        }
        cellLocation = this.A;
        arrayList.add(Long.valueOf(this.w));
        arrayList.add(cellLocation);
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public final byte k() {
        if (c()) {
            return (byte) this.m;
        }
        return Byte.MIN_VALUE;
    }

    /* access modifiers changed from: protected */
    public final List l() {
        ArrayList arrayList = new ArrayList();
        if (this.e == null) {
            return arrayList;
        }
        if (!c()) {
            return arrayList;
        }
        if (this.e.getSimState() == 1) {
            return arrayList;
        }
        int i2 = 0;
        for (NeighboringCellInfo neighboringCellInfo : this.e.getNeighboringCellInfo()) {
            if (i2 > 15) {
                break;
            } else if (!(neighboringCellInfo.getLac() == 0 || neighboringCellInfo.getLac() == 65535 || neighboringCellInfo.getCid() == 65535 || neighboringCellInfo.getCid() == 268435455)) {
                arrayList.add(neighboringCellInfo);
                i2++;
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public final List m() {
        long j2;
        String str;
        ArrayList arrayList = new ArrayList();
        if (e()) {
            j2 = this.o;
            str = this.p;
        } else {
            j2 = -1;
            str = "";
        }
        if (j2 <= 0) {
            j2 = System.currentTimeMillis() / 1000;
        }
        if (j2 > 2147483647L) {
            j2 /= 1000;
        }
        arrayList.add(Long.valueOf(j2));
        arrayList.add(str);
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public final long n() {
        long j2 = 0;
        long j3 = this.o;
        if (j3 > 0) {
            j2 = j3;
            int length = String.valueOf(j3).length();
            while (length != 13) {
                j2 = length > 13 ? j2 / 10 : j2 * 10;
                length = String.valueOf(j2).length();
            }
        }
        return j2;
    }

    /* access modifiers changed from: protected */
    public final String o() {
        if (this.q == null && this.d != null) {
            this.g = (WifiManager) this.d.getSystemService("wifi");
            if (!(this.g == null || this.g.getConnectionInfo() == null)) {
                this.q = this.g.getConnectionInfo().getMacAddress();
                if (this.q != null && this.q.length() > 0) {
                    this.q = this.q.replace(":", "");
                }
            }
        }
        return this.q != null ? this.q : "";
    }

    /* access modifiers changed from: protected */
    public final int p() {
        return this.r;
    }

    /* access modifiers changed from: protected */
    public final int q() {
        return this.s;
    }

    /* access modifiers changed from: protected */
    public final int r() {
        return this.t;
    }

    /* access modifiers changed from: protected */
    public final String s() {
        if (this.u == null && this.d != null) {
            this.u = this.d.getPackageName();
        }
        return this.u != null ? this.u : "";
    }

    /* access modifiers changed from: protected */
    public final List t() {
        ArrayList arrayList = new ArrayList();
        if (d()) {
            List a2 = a(true);
            List list = (List) a2.get(1);
            long longValue = ((Long) a2.get(0)).longValue();
            a(list);
            arrayList.add(Long.valueOf(longValue));
            if (list != null && list.size() > 0) {
                for (int i2 = 0; i2 < list.size(); i2++) {
                    ScanResult scanResult = (ScanResult) list.get(i2);
                    if (arrayList.size() - 1 >= 40) {
                        break;
                    }
                    if (scanResult != null) {
                        ArrayList arrayList2 = new ArrayList();
                        arrayList2.add(scanResult.BSSID.replace(":", ""));
                        arrayList2.add(Integer.valueOf(scanResult.level));
                        arrayList2.add(scanResult.SSID);
                        arrayList.add(arrayList2);
                    }
                }
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public final void u() {
        synchronized (this) {
            this.C.clear();
        }
        if (this.B != null) {
            b((BroadcastReceiver) this.B);
            this.B = null;
        }
        if (this.D != null) {
            this.D.cancel();
            this.D = null;
        }
        this.D = new Timer();
        this.B = new cp(this, (byte) 0);
        a((BroadcastReceiver) this.B);
        z();
    }

    /* access modifiers changed from: protected */
    public final void v() {
        synchronized (this) {
            this.C.clear();
        }
        if (this.B != null) {
            b((BroadcastReceiver) this.B);
            this.B = null;
        }
        if (this.D != null) {
            this.D.cancel();
            this.D = null;
        }
    }

    /* access modifiers changed from: protected */
    public final byte w() {
        List<Sensor> sensorList;
        new ArrayList();
        if (this.h == null || (sensorList = this.h.getSensorList(-1)) == null) {
            return 0;
        }
        return (byte) sensorList.size();
    }

    /* access modifiers changed from: protected */
    public final Context x() {
        return this.d;
    }
}
