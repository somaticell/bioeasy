package com.loc;

import android.content.Context;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* compiled from: CgiManager */
public class bf {
    private Context a;
    /* access modifiers changed from: private */
    public int b = 9;
    private ArrayList<be> c = new ArrayList<>();
    private int d = -113;
    private TelephonyManager e;
    private Object f;
    private long g = 0;
    private JSONObject h;
    private PhoneStateListener i;
    /* access modifiers changed from: private */
    public CellLocation j;

    public bf(Context context, JSONObject jSONObject) {
        if (context == null) {
        }
        p();
        this.e = (TelephonyManager) bw.a(context, "phone");
        this.h = jSONObject;
        this.a = context;
        try {
            this.b = bw.a(this.e.getCellLocation(), context);
        } catch (Exception e2) {
            this.b = 9;
        }
        q();
    }

    public ArrayList<be> a() {
        return this.c;
    }

    public be b() {
        ArrayList<be> arrayList = this.c;
        if (arrayList.size() >= 1) {
            return arrayList.get(0);
        }
        return null;
    }

    public int c() {
        return this.b;
    }

    public CellLocation d() {
        CellLocation cellLocation = null;
        if (this.e != null) {
            try {
                cellLocation = this.e.getCellLocation();
                if (a(cellLocation)) {
                    this.j = cellLocation;
                }
            } catch (Exception e2) {
            }
        }
        return cellLocation;
    }

    public TelephonyManager e() {
        return this.e;
    }

    public void f() {
        l();
    }

    public void g() {
        r();
    }

    public void h() {
        p();
    }

    public void i() {
        if (!(this.e == null || this.i == null)) {
            try {
                this.e.listen(this.i, 0);
            } catch (Exception e2) {
            }
        }
        this.c.clear();
        this.d = -113;
        this.e = null;
        this.f = null;
    }

    public void j() {
        switch (this.b) {
            case 1:
                if (this.c.isEmpty()) {
                    this.b = 9;
                    return;
                }
                return;
            case 2:
                if (this.c.isEmpty()) {
                    this.b = 9;
                    return;
                }
                return;
            default:
                return;
        }
    }

    public boolean a(boolean z) {
        if (z || this.g == 0 || bw.b() - this.g < 30000) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0047, code lost:
        if (r6.getCid() < 268435455) goto L_0x0012;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0069, code lost:
        if (com.loc.bv.b(r6, "getBaseStationId", new java.lang.Object[0]) >= 0) goto L_0x0012;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(android.telephony.CellLocation r6) {
        /*
            r5 = this;
            r4 = 65535(0xffff, float:9.1834E-41)
            r3 = -1
            r0 = 0
            if (r6 != 0) goto L_0x0008
        L_0x0007:
            return r0
        L_0x0008:
            r1 = 1
            android.content.Context r2 = r5.a
            int r2 = com.loc.bw.a((android.telephony.CellLocation) r6, (android.content.Context) r2)
            switch(r2) {
                case 1: goto L_0x001a;
                case 2: goto L_0x004a;
                default: goto L_0x0012;
            }
        L_0x0012:
            r0 = r1
        L_0x0013:
            if (r0 != 0) goto L_0x0007
            r1 = 9
            r5.b = r1
            goto L_0x0007
        L_0x001a:
            android.telephony.gsm.GsmCellLocation r6 = (android.telephony.gsm.GsmCellLocation) r6
            int r2 = r6.getLac()
            if (r2 == r3) goto L_0x0013
            int r2 = r6.getLac()
            if (r2 == 0) goto L_0x0013
            int r2 = r6.getLac()
            if (r2 > r4) goto L_0x0013
            int r2 = r6.getCid()
            if (r2 == r3) goto L_0x0013
            int r2 = r6.getCid()
            if (r2 == 0) goto L_0x0013
            int r2 = r6.getCid()
            if (r2 == r4) goto L_0x0013
            int r2 = r6.getCid()
            r3 = 268435455(0xfffffff, float:2.5243547E-29)
            if (r2 < r3) goto L_0x0012
            goto L_0x0013
        L_0x004a:
            java.lang.String r2 = "getSystemId"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x006c }
            int r2 = com.loc.bv.b(r6, r2, r3)     // Catch:{ Exception -> 0x006c }
            if (r2 <= 0) goto L_0x0013
            java.lang.String r2 = "getNetworkId"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x006c }
            int r2 = com.loc.bv.b(r6, r2, r3)     // Catch:{ Exception -> 0x006c }
            if (r2 < 0) goto L_0x0013
            java.lang.String r2 = "getBaseStationId"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x006c }
            int r2 = com.loc.bv.b(r6, r2, r3)     // Catch:{ Exception -> 0x006c }
            if (r2 >= 0) goto L_0x0012
            goto L_0x0013
        L_0x006c:
            r0 = move-exception
            r0 = r1
            goto L_0x0013
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bf.a(android.telephony.CellLocation):boolean");
    }

    public boolean a(NeighboringCellInfo neighboringCellInfo) {
        if (neighboringCellInfo == null || neighboringCellInfo.getLac() == -1 || neighboringCellInfo.getLac() == 0 || neighboringCellInfo.getLac() > 65535 || neighboringCellInfo.getCid() == -1 || neighboringCellInfo.getCid() == 0 || neighboringCellInfo.getCid() == 65535 || neighboringCellInfo.getCid() >= 268435455) {
            return false;
        }
        return true;
    }

    public void a(JSONObject jSONObject) {
        this.h = jSONObject;
    }

    private synchronized void l() {
        if (!bw.a(this.a) && this.e != null) {
            CellLocation m = m();
            if (!a(m)) {
                m = n();
            }
            if (a(m)) {
                this.j = m;
            }
        }
        if (a(this.j)) {
            switch (bw.a(this.j, this.a)) {
                case 1:
                    b(this.j);
                    break;
                case 2:
                    c(this.j);
                    break;
            }
        }
    }

    private void b(CellLocation cellLocation) {
        be b2;
        if (cellLocation != null && this.e != null) {
            this.c.clear();
            if (a(cellLocation)) {
                this.b = 1;
                this.c.add(d(cellLocation));
                List<NeighboringCellInfo> neighboringCellInfo = this.e.getNeighboringCellInfo();
                if (neighboringCellInfo != null && !neighboringCellInfo.isEmpty()) {
                    for (NeighboringCellInfo neighboringCellInfo2 : neighboringCellInfo) {
                        if (a(neighboringCellInfo2) && (b2 = b(neighboringCellInfo2)) != null && !this.c.contains(b2)) {
                            this.c.add(b2);
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:49:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c(android.telephony.CellLocation r7) {
        /*
            r6 = this;
            r5 = 2147483647(0x7fffffff, float:NaN)
            r1 = 1
            r2 = 0
            if (r7 != 0) goto L_0x0008
        L_0x0007:
            return
        L_0x0008:
            java.util.ArrayList<com.loc.be> r0 = r6.c
            r0.clear()
            int r0 = com.loc.bw.c()
            r3 = 5
            if (r0 < r3) goto L_0x0007
            java.lang.Object r0 = r6.f     // Catch:{ Exception -> 0x00b9 }
            if (r0 == 0) goto L_0x0040
            java.lang.Class r0 = r7.getClass()     // Catch:{ Exception -> 0x00bc }
            java.lang.String r3 = "mGsmCellLoc"
            java.lang.reflect.Field r0 = r0.getDeclaredField(r3)     // Catch:{ Exception -> 0x00bc }
            boolean r3 = r0.isAccessible()     // Catch:{ Exception -> 0x00bc }
            if (r3 != 0) goto L_0x002c
            r3 = 1
            r0.setAccessible(r3)     // Catch:{ Exception -> 0x00bc }
        L_0x002c:
            java.lang.Object r0 = r0.get(r7)     // Catch:{ Exception -> 0x00bc }
            android.telephony.gsm.GsmCellLocation r0 = (android.telephony.gsm.GsmCellLocation) r0     // Catch:{ Exception -> 0x00bc }
            if (r0 == 0) goto L_0x00e8
            boolean r3 = r6.a((android.telephony.CellLocation) r0)     // Catch:{ Exception -> 0x00bc }
            if (r3 == 0) goto L_0x00e8
            r6.b((android.telephony.CellLocation) r0)     // Catch:{ Exception -> 0x00bc }
            r0 = r1
        L_0x003e:
            if (r0 != 0) goto L_0x0007
        L_0x0040:
            boolean r0 = r6.a((android.telephony.CellLocation) r7)     // Catch:{ Exception -> 0x00b9 }
            if (r0 == 0) goto L_0x0007
            r0 = 2
            r6.b = r0     // Catch:{ Exception -> 0x00b9 }
            android.telephony.TelephonyManager r0 = r6.e     // Catch:{ Exception -> 0x00b9 }
            java.lang.String[] r0 = com.loc.bw.a((android.telephony.TelephonyManager) r0)     // Catch:{ Exception -> 0x00b9 }
            com.loc.be r3 = new com.loc.be     // Catch:{ Exception -> 0x00b9 }
            r4 = 2
            r3.<init>(r4)     // Catch:{ Exception -> 0x00b9 }
            r4 = 0
            r4 = r0[r4]     // Catch:{ Exception -> 0x00b9 }
            r3.a = r4     // Catch:{ Exception -> 0x00b9 }
            r4 = 1
            r0 = r0[r4]     // Catch:{ Exception -> 0x00b9 }
            r3.b = r0     // Catch:{ Exception -> 0x00b9 }
            java.lang.String r0 = "getSystemId"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x00b9 }
            int r0 = com.loc.bv.b(r7, r0, r4)     // Catch:{ Exception -> 0x00b9 }
            r3.g = r0     // Catch:{ Exception -> 0x00b9 }
            java.lang.String r0 = "getNetworkId"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x00b9 }
            int r0 = com.loc.bv.b(r7, r0, r4)     // Catch:{ Exception -> 0x00b9 }
            r3.h = r0     // Catch:{ Exception -> 0x00b9 }
            java.lang.String r0 = "getBaseStationId"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x00b9 }
            int r0 = com.loc.bv.b(r7, r0, r4)     // Catch:{ Exception -> 0x00b9 }
            r3.i = r0     // Catch:{ Exception -> 0x00b9 }
            int r0 = r6.d     // Catch:{ Exception -> 0x00b9 }
            r3.j = r0     // Catch:{ Exception -> 0x00b9 }
            java.lang.String r0 = "getBaseStationLatitude"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x00b9 }
            int r0 = com.loc.bv.b(r7, r0, r4)     // Catch:{ Exception -> 0x00b9 }
            r3.e = r0     // Catch:{ Exception -> 0x00b9 }
            java.lang.String r0 = "getBaseStationLongitude"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x00b9 }
            int r0 = com.loc.bv.b(r7, r0, r4)     // Catch:{ Exception -> 0x00b9 }
            r3.f = r0     // Catch:{ Exception -> 0x00b9 }
            int r0 = r3.e     // Catch:{ Exception -> 0x00b9 }
            if (r0 < 0) goto L_0x00a2
            int r0 = r3.f     // Catch:{ Exception -> 0x00b9 }
            if (r0 >= 0) goto L_0x00bf
        L_0x00a2:
            r0 = 0
            r3.e = r0     // Catch:{ Exception -> 0x00b9 }
            r0 = 0
            r3.f = r0     // Catch:{ Exception -> 0x00b9 }
        L_0x00a8:
            if (r2 != 0) goto L_0x00aa
        L_0x00aa:
            java.util.ArrayList<com.loc.be> r0 = r6.c     // Catch:{ Exception -> 0x00b9 }
            boolean r0 = r0.contains(r3)     // Catch:{ Exception -> 0x00b9 }
            if (r0 != 0) goto L_0x0007
            java.util.ArrayList<com.loc.be> r0 = r6.c     // Catch:{ Exception -> 0x00b9 }
            r0.add(r3)     // Catch:{ Exception -> 0x00b9 }
            goto L_0x0007
        L_0x00b9:
            r0 = move-exception
            goto L_0x0007
        L_0x00bc:
            r0 = move-exception
            r0 = r2
            goto L_0x003e
        L_0x00bf:
            int r0 = r3.e     // Catch:{ Exception -> 0x00b9 }
            if (r0 != r5) goto L_0x00ca
            r0 = 0
            r3.e = r0     // Catch:{ Exception -> 0x00b9 }
            r0 = 0
            r3.f = r0     // Catch:{ Exception -> 0x00b9 }
            goto L_0x00a8
        L_0x00ca:
            int r0 = r3.f     // Catch:{ Exception -> 0x00b9 }
            if (r0 != r5) goto L_0x00d5
            r0 = 0
            r3.e = r0     // Catch:{ Exception -> 0x00b9 }
            r0 = 0
            r3.f = r0     // Catch:{ Exception -> 0x00b9 }
            goto L_0x00a8
        L_0x00d5:
            int r0 = r3.e     // Catch:{ Exception -> 0x00b9 }
            int r4 = r3.f     // Catch:{ Exception -> 0x00b9 }
            if (r0 != r4) goto L_0x00e6
            int r0 = r3.e     // Catch:{ Exception -> 0x00b9 }
            if (r0 <= 0) goto L_0x00e6
            r0 = 0
            r3.e = r0     // Catch:{ Exception -> 0x00b9 }
            r0 = 0
            r3.f = r0     // Catch:{ Exception -> 0x00b9 }
            goto L_0x00a8
        L_0x00e6:
            r2 = r1
            goto L_0x00a8
        L_0x00e8:
            r0 = r2
            goto L_0x003e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bf.c(android.telephony.CellLocation):void");
    }

    private CellLocation m() {
        CellLocation cellLocation;
        CellLocation cellLocation2;
        CellLocation cellLocation3;
        TelephonyManager telephonyManager = this.e;
        if (telephonyManager == null) {
            return null;
        }
        try {
            cellLocation = telephonyManager.getCellLocation();
        } catch (Exception e2) {
            cellLocation = null;
        }
        if (a(cellLocation)) {
            return cellLocation;
        }
        try {
            cellLocation = a((List<?>) (List) bv.a(telephonyManager, "getAllCellInfo", new Object[0]));
        } catch (Exception | NoSuchMethodException e3) {
        }
        if (a(cellLocation)) {
            return cellLocation;
        }
        try {
            Object a2 = bv.a(telephonyManager, "getCellLocationExt", 1);
            if (a2 != null) {
                cellLocation3 = (CellLocation) a2;
            } else {
                cellLocation3 = cellLocation;
            }
            cellLocation = cellLocation3;
        } catch (Exception | NoSuchMethodException e4) {
        }
        if (a(cellLocation)) {
            return cellLocation;
        }
        try {
            Object a3 = bv.a(telephonyManager, "getCellLocationGemini", 1);
            if (a3 != null) {
                cellLocation2 = (CellLocation) a3;
            } else {
                cellLocation2 = cellLocation;
            }
            cellLocation = cellLocation2;
        } catch (Exception | NoSuchMethodException e5) {
        }
        if (a(cellLocation)) {
        }
        return cellLocation;
    }

    private CellLocation n() {
        CellLocation cellLocation;
        Object obj;
        List list;
        Object obj2 = this.f;
        if (obj2 == null) {
            return null;
        }
        try {
            Class<?> o = o();
            if (o.isInstance(obj2)) {
                Object cast = o.cast(obj2);
                try {
                    obj = bv.a(cast, "getCellLocation", new Object[0]);
                } catch (NoSuchMethodException e2) {
                    obj = null;
                } catch (Exception e3) {
                    obj = null;
                }
                if (obj == null) {
                    try {
                        obj = bv.a(cast, "getCellLocation", 1);
                    } catch (Exception | NoSuchMethodException e4) {
                    }
                }
                if (obj == null) {
                    try {
                        obj = bv.a(cast, "getCellLocationGemini", 1);
                    } catch (Exception | NoSuchMethodException e5) {
                    }
                }
                if (obj == null) {
                    try {
                        list = (List) bv.a(cast, "getAllCellInfo", new Object[0]);
                    } catch (NoSuchMethodException e6) {
                        list = null;
                    } catch (Exception e7) {
                        list = null;
                    }
                    obj = a((List<?>) list);
                    if (obj != null) {
                    }
                }
            } else {
                obj = null;
            }
            if (obj != null) {
                cellLocation = (CellLocation) obj;
            } else {
                cellLocation = null;
            }
        } catch (Exception e8) {
            cellLocation = null;
        }
        return cellLocation;
    }

    private Class<?> o() {
        String str;
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        switch (k()) {
            case 0:
                str = "android.telephony.TelephonyManager";
                break;
            case 1:
                str = "android.telephony.MSimTelephonyManager";
                break;
            case 2:
                str = "android.telephony.TelephonyManager2";
                break;
            default:
                str = null;
                break;
        }
        try {
            return systemClassLoader.loadClass(str);
        } catch (Exception e2) {
            return null;
        }
    }

    /* JADX WARNING: type inference failed for: r7v6 */
    /* JADX WARNING: type inference failed for: r7v7 */
    /* JADX WARNING: type inference failed for: r7v8 */
    /* JADX WARNING: type inference failed for: r7v9 */
    /* JADX WARNING: type inference failed for: r7v10 */
    /* JADX WARNING: type inference failed for: r7v11 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.telephony.CellLocation a(java.util.List<?> r14) {
        /*
            r13 = this;
            if (r14 == 0) goto L_0x0008
            boolean r0 = r14.isEmpty()
            if (r0 == 0) goto L_0x000a
        L_0x0008:
            r0 = 0
        L_0x0009:
            return r0
        L_0x000a:
            java.lang.ClassLoader r9 = java.lang.ClassLoader.getSystemClassLoader()
            r6 = 0
            r2 = 0
            r1 = 0
            r0 = 0
            r8 = r0
            r0 = r1
            r1 = r2
        L_0x0015:
            int r2 = r14.size()
            if (r8 >= r2) goto L_0x0124
            java.lang.Object r2 = r14.get(r8)
            if (r2 != 0) goto L_0x0025
        L_0x0021:
            int r2 = r8 + 1
            r8 = r2
            goto L_0x0015
        L_0x0025:
            java.lang.String r3 = "android.telephony.CellInfoGsm"
            java.lang.Class r3 = r9.loadClass(r3)     // Catch:{ Exception -> 0x010e }
            java.lang.String r4 = "android.telephony.CellInfoWcdma"
            java.lang.Class r4 = r9.loadClass(r4)     // Catch:{ Exception -> 0x010e }
            java.lang.String r5 = "android.telephony.CellInfoLte"
            java.lang.Class r5 = r9.loadClass(r5)     // Catch:{ Exception -> 0x010e }
            java.lang.String r7 = "android.telephony.CellInfoCdma"
            java.lang.Class r10 = r9.loadClass(r7)     // Catch:{ Exception -> 0x010e }
            boolean r7 = r3.isInstance(r2)     // Catch:{ Exception -> 0x010e }
            if (r7 == 0) goto L_0x005b
            r7 = 1
        L_0x0044:
            if (r7 <= 0) goto L_0x010b
            r0 = 0
            r11 = 1
            if (r7 != r11) goto L_0x0075
            java.lang.Object r0 = r3.cast(r2)     // Catch:{ Exception -> 0x0111 }
        L_0x004e:
            java.lang.String r2 = "getCellIdentity"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0111 }
            java.lang.Object r2 = com.loc.bv.a(r0, r2, r3)     // Catch:{ Exception -> 0x0111 }
            if (r2 != 0) goto L_0x008d
            r0 = r7
            goto L_0x0021
        L_0x005b:
            boolean r7 = r4.isInstance(r2)     // Catch:{ Exception -> 0x010e }
            if (r7 == 0) goto L_0x0063
            r7 = 2
            goto L_0x0044
        L_0x0063:
            boolean r7 = r5.isInstance(r2)     // Catch:{ Exception -> 0x010e }
            if (r7 == 0) goto L_0x006b
            r7 = 3
            goto L_0x0044
        L_0x006b:
            boolean r0 = r10.isInstance(r2)     // Catch:{ Exception -> 0x010e }
            if (r0 == 0) goto L_0x0073
            r7 = 4
            goto L_0x0044
        L_0x0073:
            r7 = 0
            goto L_0x0044
        L_0x0075:
            r3 = 2
            if (r7 != r3) goto L_0x007d
            java.lang.Object r0 = r4.cast(r2)     // Catch:{ Exception -> 0x0111 }
            goto L_0x004e
        L_0x007d:
            r3 = 3
            if (r7 != r3) goto L_0x0085
            java.lang.Object r0 = r5.cast(r2)     // Catch:{ Exception -> 0x0111 }
            goto L_0x004e
        L_0x0085:
            r3 = 4
            if (r7 != r3) goto L_0x004e
            java.lang.Object r0 = r10.cast(r2)     // Catch:{ Exception -> 0x0111 }
            goto L_0x004e
        L_0x008d:
            r0 = 4
            if (r7 != r0) goto L_0x00cc
            android.telephony.cdma.CdmaCellLocation r0 = new android.telephony.cdma.CdmaCellLocation     // Catch:{ Exception -> 0x0111 }
            r0.<init>()     // Catch:{ Exception -> 0x0111 }
            java.lang.String r1 = "getSystemId"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0115 }
            int r4 = com.loc.bv.b(r2, r1, r3)     // Catch:{ Exception -> 0x0115 }
            java.lang.String r1 = "getNetworkId"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0115 }
            int r5 = com.loc.bv.b(r2, r1, r3)     // Catch:{ Exception -> 0x0115 }
            java.lang.String r1 = "getBasestationId"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0115 }
            int r1 = com.loc.bv.b(r2, r1, r3)     // Catch:{ Exception -> 0x0115 }
            java.lang.String r3 = "getLongitude"
            r10 = 0
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ Exception -> 0x0115 }
            int r3 = com.loc.bv.b(r2, r3, r10)     // Catch:{ Exception -> 0x0115 }
            java.lang.String r10 = "getLatitude"
            r11 = 0
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x0115 }
            int r2 = com.loc.bv.b(r2, r10, r11)     // Catch:{ Exception -> 0x0115 }
            r0.setCellLocationData(r1, r2, r3, r4, r5)     // Catch:{ Exception -> 0x0115 }
            r1 = r6
        L_0x00c6:
            r2 = 4
            if (r7 == r2) goto L_0x0009
            r0 = r1
            goto L_0x0009
        L_0x00cc:
            r0 = 3
            if (r7 != r0) goto L_0x00ed
            java.lang.String r0 = "getTac"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0111 }
            int r3 = com.loc.bv.b(r2, r0, r3)     // Catch:{ Exception -> 0x0111 }
            java.lang.String r0 = "getCi"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x0111 }
            int r2 = com.loc.bv.b(r2, r0, r4)     // Catch:{ Exception -> 0x0111 }
            android.telephony.gsm.GsmCellLocation r0 = new android.telephony.gsm.GsmCellLocation     // Catch:{ Exception -> 0x0111 }
            r0.<init>()     // Catch:{ Exception -> 0x0111 }
            r0.setLacAndCid(r3, r2)     // Catch:{ Exception -> 0x011a }
            r12 = r1
            r1 = r0
            r0 = r12
            goto L_0x00c6
        L_0x00ed:
            java.lang.String r0 = "getLac"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0111 }
            int r3 = com.loc.bv.b(r2, r0, r3)     // Catch:{ Exception -> 0x0111 }
            java.lang.String r0 = "getCid"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x0111 }
            int r2 = com.loc.bv.b(r2, r0, r4)     // Catch:{ Exception -> 0x0111 }
            android.telephony.gsm.GsmCellLocation r0 = new android.telephony.gsm.GsmCellLocation     // Catch:{ Exception -> 0x0111 }
            r0.<init>()     // Catch:{ Exception -> 0x0111 }
            r0.setLacAndCid(r3, r2)     // Catch:{ Exception -> 0x011f }
            r12 = r1
            r1 = r0
            r0 = r12
            goto L_0x00c6
        L_0x010b:
            r0 = r7
            goto L_0x0021
        L_0x010e:
            r2 = move-exception
            goto L_0x0021
        L_0x0111:
            r0 = move-exception
            r0 = r7
            goto L_0x0021
        L_0x0115:
            r1 = move-exception
            r1 = r0
            r0 = r7
            goto L_0x0021
        L_0x011a:
            r2 = move-exception
            r6 = r0
            r0 = r7
            goto L_0x0021
        L_0x011f:
            r2 = move-exception
            r6 = r0
            r0 = r7
            goto L_0x0021
        L_0x0124:
            r7 = r0
            r0 = r1
            r1 = r6
            goto L_0x00c6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bf.a(java.util.List):android.telephony.CellLocation");
    }

    private be d(CellLocation cellLocation) {
        GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
        be beVar = new be(1);
        String[] a2 = bw.a(this.e);
        beVar.a = a2[0];
        beVar.b = a2[1];
        beVar.c = gsmCellLocation.getLac();
        beVar.d = gsmCellLocation.getCid();
        beVar.j = this.d;
        return beVar;
    }

    private be b(NeighboringCellInfo neighboringCellInfo) {
        if (bw.c() < 5) {
            return null;
        }
        try {
            be beVar = new be(1);
            String[] a2 = bw.a(this.e);
            beVar.a = a2[0];
            beVar.b = a2[1];
            beVar.c = bv.b(neighboringCellInfo, "getLac", new Object[0]);
            beVar.d = neighboringCellInfo.getCid();
            beVar.j = bw.a(neighboringCellInfo.getRssi());
            return beVar;
        } catch (Exception e2) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    public void p() {
        boolean z = true;
        JSONObject jSONObject = this.h;
        if (jSONObject != null) {
            try {
                if (jSONObject.getString("cellupdate").equals("0")) {
                    z = false;
                }
            } catch (Exception e2) {
            }
        }
        if (z) {
            try {
                CellLocation.requestLocationUpdate();
            } catch (Exception e3) {
            }
            this.g = bw.b();
        }
    }

    private void q() {
        this.i = new PhoneStateListener() {
            public void onCellLocationChanged(CellLocation cellLocation) {
                try {
                    if (bf.this.a(cellLocation)) {
                        CellLocation unused = bf.this.j = cellLocation;
                    }
                } catch (Throwable th) {
                }
            }

            public void onSignalStrengthChanged(int i) {
                int i2 = -113;
                try {
                    switch (bf.this.b) {
                        case 1:
                            i2 = bw.a(i);
                            break;
                        case 2:
                            i2 = bw.a(i);
                            break;
                    }
                    bf.this.a(i2);
                } catch (Throwable th) {
                }
            }

            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                if (signalStrength != null) {
                    int i = -113;
                    try {
                        switch (bf.this.b) {
                            case 1:
                                i = bw.a(signalStrength.getGsmSignalStrength());
                                break;
                            case 2:
                                i = signalStrength.getCdmaDbm();
                                break;
                        }
                        bf.this.a(i);
                    } catch (Throwable th) {
                    }
                }
            }

            public void onServiceStateChanged(ServiceState serviceState) {
                try {
                    switch (serviceState.getState()) {
                        case 0:
                            bf.this.p();
                            return;
                        case 1:
                            bf.this.r();
                            return;
                        default:
                            return;
                    }
                } catch (Throwable th) {
                }
            }
        };
        int i2 = 0;
        if (bw.c() < 7) {
            try {
                i2 = bv.b("android.telephony.PhoneStateListener", "LISTEN_SIGNAL_STRENGTH");
            } catch (Exception e2) {
            }
        } else {
            try {
                i2 = bv.b("android.telephony.PhoneStateListener", "LISTEN_SIGNAL_STRENGTHS");
            } catch (Exception e3) {
            }
        }
        if (i2 == 0) {
            this.e.listen(this.i, 16);
        } else {
            try {
                this.e.listen(this.i, i2 | 16);
            } catch (Exception e4) {
            }
        }
        try {
            switch (k()) {
                case 0:
                    this.f = bw.a(this.a, "phone2");
                    return;
                case 1:
                    this.f = bw.a(this.a, "phone_msim");
                    return;
                case 2:
                    this.f = bw.a(this.a, "phone2");
                    return;
                default:
                    return;
            }
        } catch (Throwable th) {
        }
    }

    /* access modifiers changed from: private */
    public void a(int i2) {
        if (i2 == -113) {
            this.d = -113;
            return;
        }
        this.d = i2;
        switch (this.b) {
            case 1:
            case 2:
                if (!this.c.isEmpty()) {
                    try {
                        this.c.get(0).j = this.d;
                        return;
                    } catch (Exception e2) {
                        return;
                    }
                } else {
                    return;
                }
            default:
                return;
        }
    }

    /* access modifiers changed from: private */
    public void r() {
        this.j = null;
        this.b = 9;
        this.c.clear();
    }

    public static int k() {
        int i2 = 0;
        try {
            Class.forName("android.telephony.MSimTelephonyManager");
            i2 = 1;
        } catch (Exception e2) {
        }
        if (i2 != 0) {
            return i2;
        }
        try {
            Class.forName("android.telephony.TelephonyManager2");
            return 2;
        } catch (Exception e3) {
            return i2;
        }
    }
}
