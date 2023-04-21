package com.baidu.location.a;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import com.alipay.sdk.util.h;
import com.baidu.android.bbalbs.common.util.CommonParam;
import com.baidu.location.BDLocation;
import com.baidu.location.Jni;
import com.baidu.location.LocationClientOption;
import com.baidu.location.f.e;
import com.baidu.location.f.j;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class c {
    private static Method g = null;
    private static Method h = null;
    private static Method i = null;
    private static Method j = null;
    private static Method k = null;
    private static Class<?> l = null;
    String a = null;
    String b = null;
    b c = new b();
    private Context d = null;
    private TelephonyManager e = null;
    private com.baidu.location.d.a f = new com.baidu.location.d.a();
    /* access modifiers changed from: private */
    public WifiManager m = null;
    private C0005c n = null;
    private String o = null;
    /* access modifiers changed from: private */
    public LocationClientOption p;
    /* access modifiers changed from: private */
    public a q;
    private String r = null;
    /* access modifiers changed from: private */
    public String s = null;
    /* access modifiers changed from: private */
    public String t = null;

    public interface a {
        void onReceiveLocation(BDLocation bDLocation);
    }

    class b extends e {
        String a = null;

        b() {
            this.k = new HashMap();
        }

        public void a() {
            this.h = j.c();
            if (!(c.this.s == null || c.this.t == null)) {
                this.a += String.format(Locale.CHINA, "&ki=%s&sn=%s", new Object[]{c.this.s, c.this.t});
            }
            String encodeTp4 = Jni.encodeTp4(this.a);
            this.a = null;
            this.k.put("bloc", encodeTp4);
            this.k.put("trtm", String.format(Locale.CHINA, "%d", new Object[]{Long.valueOf(System.currentTimeMillis())}));
        }

        public void a(String str) {
            this.a = str;
            c(j.f);
        }

        public void a(boolean z) {
            BDLocation bDLocation;
            if (z && this.j != null) {
                try {
                    try {
                        bDLocation = new BDLocation(this.j);
                    } catch (Exception e) {
                        bDLocation = new BDLocation();
                        bDLocation.setLocType(63);
                    }
                    if (bDLocation != null) {
                        if (bDLocation.getLocType() == 161) {
                            bDLocation.setCoorType(c.this.p.coorType);
                            bDLocation.setLocationID(Jni.en1(c.this.a + h.b + c.this.b + h.b + bDLocation.getTime()));
                            c.this.q.onReceiveLocation(bDLocation);
                        }
                    }
                } catch (Exception e2) {
                }
            }
            if (this.k != null) {
                this.k.clear();
            }
        }
    }

    /* renamed from: com.baidu.location.a.c$c  reason: collision with other inner class name */
    protected class C0005c {
        public List<ScanResult> a = null;
        private long c = 0;

        public C0005c(List<ScanResult> list) {
            this.a = list;
            this.c = System.currentTimeMillis();
            c();
        }

        private String b() {
            WifiInfo connectionInfo = c.this.m.getConnectionInfo();
            if (connectionInfo == null) {
                return null;
            }
            try {
                String bssid = connectionInfo.getBSSID();
                String replace = bssid != null ? bssid.replace(":", "") : null;
                if (replace == null || replace.length() == 12) {
                    return new String(replace);
                }
                return null;
            } catch (Exception e) {
                return null;
            }
        }

        private void c() {
            boolean z;
            if (a() >= 1) {
                boolean z2 = true;
                for (int size = this.a.size() - 1; size >= 1 && z2; size--) {
                    int i = 0;
                    z2 = false;
                    while (i < size) {
                        if (this.a.get(i).level < this.a.get(i + 1).level) {
                            this.a.set(i + 1, this.a.get(i));
                            this.a.set(i, this.a.get(i + 1));
                            z = true;
                        } else {
                            z = z2;
                        }
                        i++;
                        z2 = z;
                    }
                }
            }
        }

        public int a() {
            if (this.a == null) {
                return 0;
            }
            return this.a.size();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:36:0x00e0, code lost:
            if (r10 > r12) goto L_0x00e2;
         */
        /* JADX WARNING: Removed duplicated region for block: B:13:0x0047  */
        /* JADX WARNING: Removed duplicated region for block: B:40:0x00e8  */
        /* JADX WARNING: Removed duplicated region for block: B:42:0x00f6 A[RETURN, SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:46:0x0107  */
        /* JADX WARNING: Removed duplicated region for block: B:68:0x0198 A[SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.String a(int r23) {
            /*
                r22 = this;
                int r2 = r22.a()
                r3 = 2
                if (r2 >= r3) goto L_0x0009
                r2 = 0
            L_0x0008:
                return r2
            L_0x0009:
                java.util.ArrayList r15 = new java.util.ArrayList
                r15.<init>()
                r4 = 0
                r12 = 0
                r2 = 0
                int r3 = android.os.Build.VERSION.SDK_INT
                r6 = 19
                if (r3 < r6) goto L_0x019b
                long r4 = android.os.SystemClock.elapsedRealtimeNanos()     // Catch:{ Error -> 0x005d }
                r6 = 1000(0x3e8, double:4.94E-321)
                long r4 = r4 / r6
            L_0x0020:
                r6 = 0
                int r3 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                if (r3 <= 0) goto L_0x019b
                r2 = 1
                r3 = r2
            L_0x0028:
                java.lang.StringBuffer r16 = new java.lang.StringBuffer
                r2 = 512(0x200, float:7.175E-43)
                r0 = r16
                r0.<init>(r2)
                r0 = r22
                java.util.List<android.net.wifi.ScanResult> r2 = r0.a
                int r17 = r2.size()
                r6 = 1
                r9 = 0
                java.lang.String r18 = r22.b()
                r8 = 0
                r7 = 0
                r2 = 0
                r14 = r2
            L_0x0043:
                r0 = r17
                if (r14 >= r0) goto L_0x0198
                r0 = r22
                java.util.List<android.net.wifi.ScanResult> r2 = r0.a
                java.lang.Object r2 = r2.get(r14)
                android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2
                int r2 = r2.level
                if (r2 != 0) goto L_0x0061
                r2 = r9
                r10 = r12
            L_0x0057:
                int r9 = r14 + 1
                r14 = r9
                r12 = r10
                r9 = r2
                goto L_0x0043
            L_0x005d:
                r3 = move-exception
                r4 = 0
                goto L_0x0020
            L_0x0061:
                int r8 = r8 + 1
                if (r6 == 0) goto L_0x00f9
                r2 = 0
                java.lang.String r6 = "&wf="
                r0 = r16
                r0.append(r6)
                r6 = r2
            L_0x006e:
                r0 = r22
                java.util.List<android.net.wifi.ScanResult> r2 = r0.a
                java.lang.Object r2 = r2.get(r14)
                android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2
                java.lang.String r2 = r2.BSSID
                java.lang.String r10 = ":"
                java.lang.String r11 = ""
                java.lang.String r2 = r2.replace(r10, r11)
                r0 = r16
                r0.append(r2)
                if (r18 == 0) goto L_0x0092
                r0 = r18
                boolean r2 = r2.equals(r0)
                if (r2 == 0) goto L_0x0092
                r7 = r8
            L_0x0092:
                r0 = r22
                java.util.List<android.net.wifi.ScanResult> r2 = r0.a
                java.lang.Object r2 = r2.get(r14)
                android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2
                int r2 = r2.level
                if (r2 >= 0) goto L_0x00a1
                int r2 = -r2
            L_0x00a1:
                java.util.Locale r10 = java.util.Locale.CHINA
                java.lang.String r11 = ";%d;"
                r19 = 1
                r0 = r19
                java.lang.Object[] r0 = new java.lang.Object[r0]
                r19 = r0
                r20 = 0
                java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
                r19[r20] = r2
                r0 = r19
                java.lang.String r2 = java.lang.String.format(r10, r11, r0)
                r0 = r16
                r0.append(r2)
                int r9 = r9 + 1
                if (r3 == 0) goto L_0x0195
                r0 = r22
                java.util.List<android.net.wifi.ScanResult> r2 = r0.a     // Catch:{ Throwable -> 0x0103 }
                java.lang.Object r2 = r2.get(r14)     // Catch:{ Throwable -> 0x0103 }
                android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2     // Catch:{ Throwable -> 0x0103 }
                long r10 = r2.timestamp     // Catch:{ Throwable -> 0x0103 }
                long r10 = r4 - r10
                r20 = 1000000(0xf4240, double:4.940656E-318)
                long r10 = r10 / r20
            L_0x00d7:
                java.lang.Long r2 = java.lang.Long.valueOf(r10)
                r15.add(r2)
                int r2 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
                if (r2 <= 0) goto L_0x0195
            L_0x00e2:
                r0 = r23
                if (r9 <= r0) goto L_0x0192
            L_0x00e6:
                if (r7 <= 0) goto L_0x00f4
                java.lang.String r2 = "&wf_n="
                r0 = r16
                r0.append(r2)
                r0 = r16
                r0.append(r7)
            L_0x00f4:
                if (r6 == 0) goto L_0x0107
                r2 = 0
                goto L_0x0008
            L_0x00f9:
                java.lang.String r2 = "|"
                r0 = r16
                r0.append(r2)
                goto L_0x006e
            L_0x0103:
                r2 = move-exception
                r10 = 0
                goto L_0x00d7
            L_0x0107:
                r2 = 10
                int r2 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
                if (r2 <= 0) goto L_0x018c
                int r2 = r15.size()
                if (r2 <= 0) goto L_0x018c
                r2 = 0
                java.lang.Object r2 = r15.get(r2)
                java.lang.Long r2 = (java.lang.Long) r2
                long r2 = r2.longValue()
                r4 = 0
                int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r2 <= 0) goto L_0x018c
                java.lang.StringBuffer r5 = new java.lang.StringBuffer
                r2 = 128(0x80, float:1.794E-43)
                r5.<init>(r2)
                java.lang.String r2 = "&wf_ut="
                r5.append(r2)
                r3 = 1
                r2 = 0
                java.lang.Object r2 = r15.get(r2)
                java.lang.Long r2 = (java.lang.Long) r2
                java.util.Iterator r6 = r15.iterator()
                r4 = r3
            L_0x013d:
                boolean r3 = r6.hasNext()
                if (r3 == 0) goto L_0x0183
                java.lang.Object r3 = r6.next()
                java.lang.Long r3 = (java.lang.Long) r3
                if (r4 == 0) goto L_0x015c
                r4 = 0
                long r8 = r3.longValue()
                r5.append(r8)
                r3 = r4
            L_0x0154:
                java.lang.String r4 = "|"
                r5.append(r4)
                r4 = r3
                goto L_0x013d
            L_0x015c:
                long r8 = r3.longValue()
                long r10 = r2.longValue()
                long r8 = r8 - r10
                r10 = 0
                int r3 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
                if (r3 == 0) goto L_0x0181
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r7 = ""
                java.lang.StringBuilder r3 = r3.append(r7)
                java.lang.StringBuilder r3 = r3.append(r8)
                java.lang.String r3 = r3.toString()
                r5.append(r3)
            L_0x0181:
                r3 = r4
                goto L_0x0154
            L_0x0183:
                java.lang.String r2 = r5.toString()
                r0 = r16
                r0.append(r2)
            L_0x018c:
                java.lang.String r2 = r16.toString()
                goto L_0x0008
            L_0x0192:
                r2 = r9
                goto L_0x0057
            L_0x0195:
                r10 = r12
                goto L_0x00e2
            L_0x0198:
                r10 = r12
                goto L_0x00e6
            L_0x019b:
                r3 = r2
                goto L_0x0028
            */
            throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.a.c.C0005c.a(int):java.lang.String");
        }
    }

    public c(Context context, LocationClientOption locationClientOption, a aVar) {
        String str;
        this.d = context.getApplicationContext();
        this.p = new LocationClientOption(locationClientOption);
        this.q = aVar;
        this.a = this.d.getPackageName();
        this.b = null;
        try {
            this.e = (TelephonyManager) this.d.getSystemService("phone");
            str = this.e.getDeviceId();
        } catch (Exception e2) {
            str = null;
        }
        try {
            this.b = CommonParam.a(this.d);
        } catch (Exception e3) {
            this.b = null;
        }
        if (this.b != null) {
            j.n = "" + this.b;
            this.o = "&prod=" + this.p.prodName + ":" + this.a + "|&cu=" + this.b + "&coor=" + locationClientOption.getCoorType();
        } else {
            this.o = "&prod=" + this.p.prodName + ":" + this.a + "|&im=" + str + "&coor=" + locationClientOption.getCoorType();
        }
        StringBuffer stringBuffer = new StringBuffer(256);
        stringBuffer.append("&fw=");
        stringBuffer.append("7.53");
        stringBuffer.append("&sdk=");
        stringBuffer.append("7.53");
        stringBuffer.append("&lt=1");
        stringBuffer.append("&mb=");
        stringBuffer.append(Build.MODEL);
        stringBuffer.append("&resid=");
        stringBuffer.append("12");
        if (locationClientOption.getAddrType() != null) {
        }
        if (locationClientOption.getAddrType() != null && locationClientOption.getAddrType().equals("all")) {
            this.o += "&addr=allj";
        }
        if (locationClientOption.isNeedAptag || locationClientOption.isNeedAptagd) {
            this.o += "&sema=";
            if (locationClientOption.isNeedAptag) {
                this.o += "aptag|";
            }
            if (locationClientOption.isNeedAptagd) {
                this.o += "aptagd|";
            }
            this.s = j.b(this.d);
            this.t = j.c(this.d);
        }
        stringBuffer.append("&first=1");
        stringBuffer.append("&os=A");
        stringBuffer.append(Build.VERSION.SDK);
        this.o += stringBuffer.toString();
        this.m = (WifiManager) this.d.getApplicationContext().getSystemService("wifi");
        String a2 = a();
        a2 = !TextUtils.isEmpty(a2) ? a2.replace(":", "") : a2;
        if (!TextUtils.isEmpty(a2) && !a2.equals("020000000000")) {
            this.o += "&mac=" + a2;
        }
        b();
    }

    private int a(int i2) {
        if (i2 == Integer.MAX_VALUE) {
            return -1;
        }
        return i2;
    }

    @SuppressLint({"NewApi"})
    private com.baidu.location.d.a a(CellInfo cellInfo) {
        boolean z = false;
        int i2 = -1;
        int intValue = Integer.valueOf(Build.VERSION.SDK_INT).intValue();
        if (intValue < 17) {
            return null;
        }
        com.baidu.location.d.a aVar = new com.baidu.location.d.a();
        if (cellInfo instanceof CellInfoGsm) {
            CellIdentityGsm cellIdentity = ((CellInfoGsm) cellInfo).getCellIdentity();
            aVar.c = a(cellIdentity.getMcc());
            aVar.d = a(cellIdentity.getMnc());
            aVar.a = a(cellIdentity.getLac());
            aVar.b = a(cellIdentity.getCid());
            aVar.i = 'g';
            aVar.h = ((CellInfoGsm) cellInfo).getCellSignalStrength().getAsuLevel();
            z = true;
        } else if (cellInfo instanceof CellInfoCdma) {
            CellIdentityCdma cellIdentity2 = ((CellInfoCdma) cellInfo).getCellIdentity();
            aVar.e = cellIdentity2.getLatitude();
            aVar.f = cellIdentity2.getLongitude();
            aVar.d = a(cellIdentity2.getSystemId());
            aVar.a = a(cellIdentity2.getNetworkId());
            aVar.b = a(cellIdentity2.getBasestationId());
            aVar.i = 'c';
            aVar.h = ((CellInfoCdma) cellInfo).getCellSignalStrength().getCdmaDbm();
            if (this.f == null || this.f.c <= 0) {
                try {
                    String networkOperator = this.e.getNetworkOperator();
                    if (networkOperator != null && networkOperator.length() > 0 && networkOperator.length() >= 3) {
                        int intValue2 = Integer.valueOf(networkOperator.substring(0, 3)).intValue();
                        if (intValue2 < 0) {
                            intValue2 = -1;
                        }
                        i2 = intValue2;
                    }
                } catch (Exception e2) {
                }
                if (i2 > 0) {
                    aVar.c = i2;
                }
            } else {
                aVar.c = this.f.c;
            }
            z = true;
        } else if (cellInfo instanceof CellInfoLte) {
            CellIdentityLte cellIdentity3 = ((CellInfoLte) cellInfo).getCellIdentity();
            aVar.c = a(cellIdentity3.getMcc());
            aVar.d = a(cellIdentity3.getMnc());
            aVar.a = a(cellIdentity3.getTac());
            aVar.b = a(cellIdentity3.getCi());
            aVar.i = 'g';
            aVar.h = ((CellInfoLte) cellInfo).getCellSignalStrength().getAsuLevel();
            z = true;
        }
        if (intValue >= 18 && !z) {
            try {
                if (cellInfo instanceof CellInfoWcdma) {
                    CellIdentityWcdma cellIdentity4 = ((CellInfoWcdma) cellInfo).getCellIdentity();
                    aVar.c = a(cellIdentity4.getMcc());
                    aVar.d = a(cellIdentity4.getMnc());
                    aVar.a = a(cellIdentity4.getLac());
                    aVar.b = a(cellIdentity4.getCid());
                    aVar.i = 'g';
                    aVar.h = ((CellInfoWcdma) cellInfo).getCellSignalStrength().getAsuLevel();
                }
            } catch (Exception e3) {
            }
        }
        try {
            aVar.g = System.currentTimeMillis() - ((SystemClock.elapsedRealtimeNanos() - cellInfo.getTimeStamp()) / 1000000);
        } catch (Error e4) {
            aVar.g = System.currentTimeMillis();
        }
        return aVar;
    }

    private void a(CellLocation cellLocation) {
        int i2 = 0;
        if (cellLocation != null && this.e != null) {
            com.baidu.location.d.a aVar = new com.baidu.location.d.a();
            String networkOperator = this.e.getNetworkOperator();
            if (networkOperator != null && networkOperator.length() > 0) {
                try {
                    if (networkOperator.length() >= 3) {
                        int intValue = Integer.valueOf(networkOperator.substring(0, 3)).intValue();
                        if (intValue < 0) {
                            intValue = this.f.c;
                        }
                        aVar.c = intValue;
                    }
                    String substring = networkOperator.substring(3);
                    if (substring != null) {
                        char[] charArray = substring.toCharArray();
                        while (i2 < charArray.length && Character.isDigit(charArray[i2])) {
                            i2++;
                        }
                    }
                    int intValue2 = Integer.valueOf(substring.substring(0, i2)).intValue();
                    if (intValue2 < 0) {
                        intValue2 = this.f.d;
                    }
                    aVar.d = intValue2;
                } catch (Exception e2) {
                }
            }
            if (cellLocation instanceof GsmCellLocation) {
                aVar.a = ((GsmCellLocation) cellLocation).getLac();
                aVar.b = ((GsmCellLocation) cellLocation).getCid();
                aVar.i = 'g';
            } else if (cellLocation instanceof CdmaCellLocation) {
                aVar.i = 'c';
                if (l == null) {
                    try {
                        l = Class.forName("android.telephony.cdma.CdmaCellLocation");
                        g = l.getMethod("getBaseStationId", new Class[0]);
                        h = l.getMethod("getNetworkId", new Class[0]);
                        i = l.getMethod("getSystemId", new Class[0]);
                        j = l.getMethod("getBaseStationLatitude", new Class[0]);
                        k = l.getMethod("getBaseStationLongitude", new Class[0]);
                    } catch (Exception e3) {
                        l = null;
                        return;
                    }
                }
                if (l != null && l.isInstance(cellLocation)) {
                    try {
                        int intValue3 = ((Integer) i.invoke(cellLocation, new Object[0])).intValue();
                        if (intValue3 < 0) {
                            intValue3 = this.f.d;
                        }
                        aVar.d = intValue3;
                        aVar.b = ((Integer) g.invoke(cellLocation, new Object[0])).intValue();
                        aVar.a = ((Integer) h.invoke(cellLocation, new Object[0])).intValue();
                        Object invoke = j.invoke(cellLocation, new Object[0]);
                        if (((Integer) invoke).intValue() < Integer.MAX_VALUE) {
                            aVar.e = ((Integer) invoke).intValue();
                        }
                        Object invoke2 = k.invoke(cellLocation, new Object[0]);
                        if (((Integer) invoke2).intValue() < Integer.MAX_VALUE) {
                            aVar.f = ((Integer) invoke2).intValue();
                        }
                    } catch (Exception e4) {
                        return;
                    }
                }
            }
            if (aVar.b()) {
                this.f = aVar;
            } else {
                this.f = null;
            }
        }
    }

    private String b(int i2) {
        String str;
        String str2;
        try {
            com.baidu.location.d.a d2 = d();
            if (d2 == null || !d2.b()) {
                a(this.e.getCellLocation());
            } else {
                this.f = d2;
            }
            str = (this.f == null || !this.f.b()) ? null : this.f.g();
            try {
                if (!TextUtils.isEmpty(str) && this.f.j != null) {
                    str = str + this.f.j;
                }
            } catch (Throwable th) {
            }
        } catch (Throwable th2) {
            str = null;
        }
        try {
            this.n = null;
            this.n = new C0005c(this.m.getScanResults());
            str2 = this.n.a(i2);
        } catch (Exception e2) {
            str2 = null;
        }
        if (str == null && str2 == null) {
            this.r = null;
            return null;
        }
        if (str2 != null) {
            str = str == null ? str2 : str + str2;
        }
        if (str == null) {
            return null;
        }
        this.r = str;
        if (this.o != null) {
            this.r += this.o;
        }
        return str + this.o;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0047, code lost:
        if (r1 == null) goto L_0x0049;
     */
    @android.annotation.SuppressLint({"NewApi"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.baidu.location.d.a d() {
        /*
            r6 = this;
            r2 = 0
            int r0 = android.os.Build.VERSION.SDK_INT
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            int r0 = r0.intValue()
            r1 = 17
            if (r0 >= r1) goto L_0x0011
            r1 = r2
        L_0x0010:
            return r1
        L_0x0011:
            android.telephony.TelephonyManager r0 = r6.e     // Catch:{ Throwable -> 0x0054 }
            java.util.List r0 = r0.getAllCellInfo()     // Catch:{ Throwable -> 0x0054 }
            if (r0 == 0) goto L_0x0059
            int r1 = r0.size()     // Catch:{ Throwable -> 0x0054 }
            if (r1 <= 0) goto L_0x0059
            java.util.Iterator r4 = r0.iterator()     // Catch:{ Throwable -> 0x0054 }
            r1 = r2
        L_0x0024:
            boolean r0 = r4.hasNext()     // Catch:{ Throwable -> 0x0054 }
            if (r0 == 0) goto L_0x0010
            java.lang.Object r0 = r4.next()     // Catch:{ Throwable -> 0x0054 }
            android.telephony.CellInfo r0 = (android.telephony.CellInfo) r0     // Catch:{ Throwable -> 0x0054 }
            boolean r3 = r0.isRegistered()     // Catch:{ Throwable -> 0x0054 }
            if (r3 == 0) goto L_0x0057
            r3 = 0
            if (r1 == 0) goto L_0x003a
            r3 = 1
        L_0x003a:
            com.baidu.location.d.a r0 = r6.a((android.telephony.CellInfo) r0)     // Catch:{ Throwable -> 0x0054 }
            if (r0 == 0) goto L_0x0024
            boolean r5 = r0.b()     // Catch:{ Throwable -> 0x0054 }
            if (r5 != 0) goto L_0x004b
            r0 = r2
        L_0x0047:
            if (r1 != 0) goto L_0x0057
        L_0x0049:
            r1 = r0
            goto L_0x0024
        L_0x004b:
            if (r3 == 0) goto L_0x0047
            java.lang.String r0 = r0.h()     // Catch:{ Throwable -> 0x0054 }
            r1.j = r0     // Catch:{ Throwable -> 0x0054 }
            goto L_0x0010
        L_0x0054:
            r0 = move-exception
            r1 = r2
            goto L_0x0010
        L_0x0057:
            r0 = r1
            goto L_0x0049
        L_0x0059:
            r1 = r2
            goto L_0x0010
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.a.c.d():com.baidu.location.d.a");
    }

    public String a() {
        try {
            WifiInfo connectionInfo = this.m.getConnectionInfo();
            if (connectionInfo != null) {
                return connectionInfo.getMacAddress();
            }
            return null;
        } catch (Error | Exception e2) {
            return null;
        }
    }

    public String b() {
        try {
            return b(15);
        } catch (Exception e2) {
            return null;
        }
    }

    public void c() {
        if (this.r != null && 0 == 0) {
            this.c.a(this.r);
        }
    }
}
