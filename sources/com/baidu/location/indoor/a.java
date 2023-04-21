package com.baidu.location.indoor;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import cn.com.bioeasy.app.utils.ListUtils;
import com.baidu.android.bbalbs.common.a.b;
import com.baidu.android.bbalbs.common.a.c;
import com.baidu.location.f.e;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import org.achartengine.chart.TimeChart;

public class a extends e {
    private static HashMap<String, Long> a = new HashMap<>();
    private final String b = "http://loc.map.baidu.com/indoorlocbuildinginfo.php";
    private final SimpleDateFormat c = new SimpleDateFormat("yyyyMM");
    private Context d;
    private boolean e;
    /* access modifiers changed from: private */
    public String f;
    private HashSet<String> p;
    private C0009a q;
    /* access modifiers changed from: private */
    public String r = null;
    private Handler s;
    private Runnable t;

    /* renamed from: com.baidu.location.indoor.a$a  reason: collision with other inner class name */
    public interface C0009a {
        void a(boolean z);
    }

    public a(Context context) {
        this.d = context;
        this.p = new HashSet<>();
        this.e = false;
        this.k = new HashMap();
        this.s = new Handler();
        this.t = new b(this);
    }

    private String a(Date date) {
        File file = new File(this.d.getCacheDir(), c.a((this.f + this.c.format(date)).getBytes(), false));
        if (!file.isFile()) {
            return null;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String str = "";
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                str = str + readLine + "\n";
            }
            bufferedReader.close();
            if (!str.equals("")) {
                return new String(b.a(str.getBytes()));
            }
            return null;
        } catch (Exception e2) {
            return null;
        }
    }

    private void d(String str) {
        for (String lowerCase : str.split(ListUtils.DEFAULT_JOIN_SEPARATOR)) {
            this.p.add(lowerCase.toLowerCase());
        }
    }

    private void e(String str) {
        try {
            FileWriter fileWriter = new FileWriter(new File(this.d.getCacheDir(), c.a((this.f + this.c.format(new Date())).getBytes(), false)));
            fileWriter.write(b.a(str.getBytes(), "UTF-8"));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e2) {
        }
    }

    private Date f() {
        Calendar instance = Calendar.getInstance();
        instance.add(2, -1);
        return instance.getTime();
    }

    private void f(String str) {
        try {
            FileWriter fileWriter = new FileWriter(new File(this.d.getCacheDir(), "buildings"), true);
            fileWriter.write(str + "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private void g() {
        try {
            File file = new File(this.d.getCacheDir(), c.a((this.f + this.c.format(f())).getBytes(), false));
            if (file.isFile()) {
                file.delete();
            }
        } catch (Exception e2) {
        }
    }

    public void a() {
        this.h = "http://loc.map.baidu.com/indoorlocbuildinginfo.php";
        this.k.clear();
        this.k.put("bid", "none");
        this.k.put("bldg", this.f);
        this.k.put("mb", Build.MODEL);
        this.k.put("msdk", "2.0");
        this.k.put("cuid", com.baidu.location.f.b.a().b);
        this.k.put("anchors", "v1");
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0068  */
    /* JADX WARNING: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(boolean r7) {
        /*
            r6 = this;
            r4 = 0
            r1 = 1
            r2 = 0
            if (r7 == 0) goto L_0x0091
            java.lang.String r0 = r6.j
            if (r0 == 0) goto L_0x0091
            java.lang.String r0 = r6.j     // Catch:{ Exception -> 0x008b }
            java.lang.String r3 = new java.lang.String     // Catch:{ Exception -> 0x008b }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x008b }
            byte[] r0 = r0.getBytes()     // Catch:{ Exception -> 0x008b }
            byte[] r0 = com.baidu.android.bbalbs.common.a.b.a(r0)     // Catch:{ Exception -> 0x008b }
            r3.<init>(r0)     // Catch:{ Exception -> 0x008b }
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x008b }
            r0.<init>(r3)     // Catch:{ Exception -> 0x008b }
            java.lang.String r3 = "anchorinfo"
            boolean r3 = r0.has(r3)     // Catch:{ Exception -> 0x008b }
            if (r3 == 0) goto L_0x0091
            java.lang.String r3 = "anchorinfo"
            java.lang.String r0 = r0.optString(r3)     // Catch:{ Exception -> 0x008b }
            if (r0 == 0) goto L_0x0091
            java.lang.String r3 = ""
            boolean r3 = r0.equals(r3)     // Catch:{ Exception -> 0x008b }
            if (r3 != 0) goto L_0x0091
            java.util.HashSet<java.lang.String> r3 = r6.p     // Catch:{ Exception -> 0x008b }
            r3.clear()     // Catch:{ Exception -> 0x008b }
            r6.d(r0)     // Catch:{ Exception -> 0x008b }
            r6.e(r0)     // Catch:{ Exception -> 0x008b }
            r6.g()     // Catch:{ Exception -> 0x008e }
            r0 = r1
        L_0x0048:
            if (r0 != 0) goto L_0x0068
            java.lang.String r3 = r6.r
            if (r3 != 0) goto L_0x0068
            java.lang.String r1 = r6.f
            r6.r = r1
            android.os.Handler r1 = r6.s
            java.lang.Runnable r3 = r6.t
            r4 = 60000(0xea60, double:2.9644E-319)
            r1.postDelayed(r3, r4)
        L_0x005c:
            r6.e = r2
            com.baidu.location.indoor.a$a r1 = r6.q
            if (r1 == 0) goto L_0x0067
            com.baidu.location.indoor.a$a r1 = r6.q
            r1.a(r0)
        L_0x0067:
            return
        L_0x0068:
            if (r0 == 0) goto L_0x006d
            r6.r = r4
            goto L_0x005c
        L_0x006d:
            java.lang.String r3 = r6.r
            r6.f(r3)
            r6.r = r4
            java.util.Date r3 = r6.f()
            java.lang.String r3 = r6.a((java.util.Date) r3)
            if (r3 == 0) goto L_0x005c
            r6.d(r3)
            com.baidu.location.indoor.a$a r3 = r6.q
            if (r3 == 0) goto L_0x005c
            com.baidu.location.indoor.a$a r3 = r6.q
            r3.a(r1)
            goto L_0x005c
        L_0x008b:
            r0 = move-exception
            r0 = r2
            goto L_0x0048
        L_0x008e:
            r0 = move-exception
            r0 = r1
            goto L_0x0048
        L_0x0091:
            r0 = r2
            goto L_0x0048
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.indoor.a.a(boolean):void");
    }

    public boolean a(String str) {
        return this.f != null && this.f.equalsIgnoreCase(str) && !this.p.isEmpty();
    }

    public boolean a(String str, C0009a aVar) {
        if (!this.e) {
            this.q = aVar;
            this.e = true;
            this.f = str;
            try {
                String a2 = a(new Date());
                if (a2 == null) {
                    long currentTimeMillis = System.currentTimeMillis();
                    if (a.get(str) == null || currentTimeMillis - a.get(str).longValue() > TimeChart.DAY) {
                        a.put(str, Long.valueOf(currentTimeMillis));
                        e();
                    }
                } else {
                    d(a2);
                    if (this.q != null) {
                        this.q.a(true);
                    }
                    this.e = false;
                }
            } catch (Exception e2) {
                this.e = false;
            }
        }
        return false;
    }

    public boolean b() {
        return this.p != null && !this.p.isEmpty();
    }

    public boolean b(String str) {
        return this.f != null && this.p != null && !this.p.isEmpty() && this.p.contains(str);
    }

    public void c() {
        this.f = null;
        this.p.clear();
    }
}
