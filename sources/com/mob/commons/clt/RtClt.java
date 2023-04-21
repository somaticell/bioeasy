package com.mob.commons.clt;

import android.content.Context;
import android.os.Build;
import com.mob.commons.LockAction;
import com.mob.commons.a;
import com.mob.commons.b;
import com.mob.commons.c;
import com.mob.commons.d;
import com.mob.tools.MobLog;
import com.mob.tools.utils.FileLocker;
import com.mob.tools.utils.ReflectHelper;
import com.mob.tools.utils.ResHelper;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.achartengine.chart.TimeChart;

public class RtClt {
    private static final String a = (Build.VERSION.SDK_INT >= 16 ? "^u\\d+_a\\d+" : "^app_\\d+");
    private static RtClt b;
    /* access modifiers changed from: private */
    public Context c;

    public static synchronized void startCollector(Context context, String str) {
        synchronized (RtClt.class) {
            startCollector(context);
        }
    }

    public static synchronized void startCollector(Context context) {
        synchronized (RtClt.class) {
            if (b == null) {
                b = new RtClt(context);
                b.a();
            }
        }
    }

    private RtClt(Context context) {
        this.c = context.getApplicationContext();
    }

    private void a() {
        AnonymousClass1 r0 = new Thread() {
            public void run() {
                d.a(new File(ResHelper.getCacheRoot(RtClt.this.c), "comm/locks/.rc_lock"), new LockAction() {
                    public boolean run(FileLocker fileLocker) {
                        if (c.a(RtClt.this.c, "comm/tags/.rcTag")) {
                            return false;
                        }
                        RtClt.this.b();
                        return true;
                    }
                });
            }
        };
        r0.setPriority(1);
        r0.start();
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b() {
        /*
            r8 = this;
            r2 = 0
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x0088 }
            android.content.Context r0 = r8.c     // Catch:{ Throwable -> 0x0088 }
            java.lang.String r0 = com.mob.tools.utils.ResHelper.getCacheRoot(r0)     // Catch:{ Throwable -> 0x0088 }
            java.lang.String r1 = "comm/dbs/.plst"
            r3.<init>(r0, r1)     // Catch:{ Throwable -> 0x0088 }
            java.io.File r0 = r3.getParentFile()     // Catch:{ Throwable -> 0x0088 }
            boolean r0 = r0.exists()     // Catch:{ Throwable -> 0x0088 }
            if (r0 != 0) goto L_0x001f
            java.io.File r0 = r3.getParentFile()     // Catch:{ Throwable -> 0x0088 }
            r0.mkdirs()     // Catch:{ Throwable -> 0x0088 }
        L_0x001f:
            java.lang.String r4 = r3.getAbsolutePath()     // Catch:{ Throwable -> 0x0088 }
            long r6 = r8.c()     // Catch:{ Throwable -> 0x0088 }
            android.content.Context r0 = r8.c     // Catch:{ Throwable -> 0x0088 }
            java.lang.String r1 = "comm/tags/.rcTag"
            com.mob.commons.c.b(r0, r1)     // Catch:{ Throwable -> 0x0088 }
            java.lang.Runtime r0 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x007e }
            java.lang.String r1 = "sh"
            java.lang.Process r1 = r0.exec(r1)     // Catch:{ Throwable -> 0x007e }
            java.io.OutputStream r2 = r1.getOutputStream()     // Catch:{ Throwable -> 0x008a }
        L_0x003c:
            android.content.Context r0 = r8.c     // Catch:{ Throwable -> 0x0088 }
            java.lang.String r5 = "comm/tags/.rcTag"
            com.mob.commons.c.c(r0, r5)     // Catch:{ Throwable -> 0x0088 }
            java.util.HashMap r0 = new java.util.HashMap     // Catch:{ Throwable -> 0x0088 }
            r0.<init>()     // Catch:{ Throwable -> 0x0088 }
            java.lang.String r5 = "nextUploadTime"
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ Throwable -> 0x0088 }
            r0.put(r5, r6)     // Catch:{ Throwable -> 0x0088 }
            java.lang.String r5 = "p"
            r0.put(r5, r1)     // Catch:{ Throwable -> 0x0088 }
            java.lang.String r1 = "os"
            r0.put(r1, r2)     // Catch:{ Throwable -> 0x0088 }
            java.lang.String r1 = "firstLog"
            r2 = 1
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)     // Catch:{ Throwable -> 0x0088 }
            r0.put(r1, r2)     // Catch:{ Throwable -> 0x0088 }
            com.mob.tools.MobLooper r1 = new com.mob.tools.MobLooper     // Catch:{ Throwable -> 0x0088 }
            android.content.Context r2 = r8.c     // Catch:{ Throwable -> 0x0088 }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0088 }
            com.mob.commons.clt.RtClt$2 r2 = new com.mob.commons.clt.RtClt$2     // Catch:{ Throwable -> 0x0088 }
            r2.<init>(r3, r4, r0)     // Catch:{ Throwable -> 0x0088 }
            android.content.Context r0 = r8.c     // Catch:{ Throwable -> 0x0088 }
            int r0 = com.mob.commons.a.c(r0)     // Catch:{ Throwable -> 0x0088 }
            int r0 = r0 * 1000
            long r4 = (long) r0     // Catch:{ Throwable -> 0x0088 }
            r1.start(r2, r4)     // Catch:{ Throwable -> 0x0088 }
        L_0x007d:
            return
        L_0x007e:
            r0 = move-exception
            r1 = r2
        L_0x0080:
            com.mob.tools.log.NLog r5 = com.mob.tools.MobLog.getInstance()     // Catch:{ Throwable -> 0x0088 }
            r5.w(r0)     // Catch:{ Throwable -> 0x0088 }
            goto L_0x003c
        L_0x0088:
            r0 = move-exception
            goto L_0x007d
        L_0x008a:
            r0 = move-exception
            goto L_0x0080
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.commons.clt.RtClt.b():void");
    }

    private long c() {
        long a2 = a.a(this.c);
        try {
            File file = new File(ResHelper.getCacheRoot(this.c), "comm/dbs/.nulplt");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
                long readLong = dataInputStream.readLong();
                dataInputStream.close();
                return readLong;
            }
            file.createNewFile();
            d();
            return a2 + TimeChart.DAY;
        } catch (Throwable th) {
            MobLog.getInstance().d(th);
            return a2 + TimeChart.DAY;
        }
    }

    /* access modifiers changed from: private */
    public long d() {
        long a2 = a.a(this.c) + TimeChart.DAY;
        try {
            File file = new File(ResHelper.getCacheRoot(this.c), "comm/dbs/.nulplt");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
            dataOutputStream.writeLong(a2);
            dataOutputStream.flush();
            dataOutputStream.close();
            return a2;
        } catch (Throwable th) {
            MobLog.getInstance().d(th);
            return 0;
        }
    }

    /* access modifiers changed from: private */
    public boolean a(String str) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        a(str, (ArrayList<ArrayList<HashMap<String, String>>>) arrayList, (ArrayList<long[]>) arrayList2);
        a(a((HashMap<String, String>[][]) a(a((ArrayList<ArrayList<HashMap<String, String>>>) arrayList), (ArrayList<ArrayList<HashMap<String, String>>>) arrayList), (ArrayList<long[]>) arrayList2), (ArrayList<long[]>) arrayList2);
        return b(str);
    }

    private void a(String str, ArrayList<ArrayList<HashMap<String, String>>> arrayList, ArrayList<long[]> arrayList2) {
        try {
            HashMap<String, String[]> e = e();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(str), "utf-8"));
            String readLine = bufferedReader.readLine();
            for (int i = 0; i < 7; i++) {
                readLine = bufferedReader.readLine();
            }
            ArrayList arrayList3 = new ArrayList();
            while (readLine != null) {
                if ("======================".equals(readLine)) {
                    try {
                        String[] split = bufferedReader.readLine().split("_");
                        long[] jArr = {Long.valueOf(split[0]).longValue(), Long.valueOf(split[1]).longValue()};
                        arrayList.add(arrayList3);
                        arrayList2.add(jArr);
                    } catch (Throwable th) {
                    }
                    arrayList3 = new ArrayList();
                    for (int i2 = 0; i2 < 7; i2++) {
                        bufferedReader.readLine();
                    }
                } else if (readLine.length() > 0) {
                    a(readLine, e, (ArrayList<HashMap<String, String>>) arrayList3);
                }
                readLine = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Throwable th2) {
            MobLog.getInstance().d(th2);
        }
    }

    private void a(String str, HashMap<String, String[]> hashMap, ArrayList<HashMap<String, String>> arrayList) {
        String[] strArr;
        String[] split = str.replaceAll(" +", " ").split(" ");
        if (split != null && split.length >= 10) {
            String str2 = split[split.length - 1];
            if (split[split.length - 2].matches(a) && !"top".equals(str2) && (strArr = hashMap.get(str2)) != null) {
                HashMap hashMap2 = new HashMap();
                hashMap2.put("pkg", str2);
                hashMap2.put("name", strArr[0]);
                hashMap2.put("version", strArr[1]);
                hashMap2.put("pcy", split[split.length - 3]);
                arrayList.add(hashMap2);
            }
        }
    }

    private HashMap<String, String[]> e() {
        ArrayList arrayList;
        try {
            arrayList = (ArrayList) ReflectHelper.invokeInstanceMethod(ReflectHelper.invokeStaticMethod("DeviceHelper", "getInstance", this.c), "getInstalledApp", false);
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
            arrayList = new ArrayList();
        }
        HashMap<String, String[]> hashMap = new HashMap<>();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            HashMap hashMap2 = (HashMap) it.next();
            hashMap.put((String) hashMap2.get("pkg"), new String[]{(String) hashMap2.get("name"), (String) hashMap2.get("version")});
        }
        return hashMap;
    }

    private HashMap<String, Integer> a(ArrayList<ArrayList<HashMap<String, String>>> arrayList) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        Iterator<ArrayList<HashMap<String, String>>> it = arrayList.iterator();
        int i = 0;
        while (it.hasNext()) {
            Iterator it2 = it.next().iterator();
            int i2 = i;
            while (it2.hasNext()) {
                HashMap hashMap2 = (HashMap) it2.next();
                String str = ((String) hashMap2.get("pkg")) + ":" + ((String) hashMap2.get("version"));
                if (!hashMap.containsKey(str)) {
                    hashMap.put(str, Integer.valueOf(i2));
                    i2++;
                }
            }
            i = i2;
        }
        return hashMap;
    }

    private HashMap<String, String>[][] a(HashMap<String, Integer> hashMap, ArrayList<ArrayList<HashMap<String, String>>> arrayList) {
        HashMap<String, String>[][] hashMapArr = (HashMap[][]) Array.newInstance(HashMap.class, new int[]{hashMap.size(), arrayList.size()});
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ArrayList arrayList2 = arrayList.get(i);
            int size2 = arrayList2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                HashMap<String, String> hashMap2 = (HashMap) arrayList2.get(i2);
                hashMapArr[hashMap.get(hashMap2.get("pkg") + ":" + hashMap2.get("version")).intValue()][i] = hashMap2;
            }
        }
        return hashMapArr;
    }

    private ArrayList<HashMap<String, Object>> a(HashMap<String, String>[][] hashMapArr, ArrayList<long[]> arrayList) {
        ArrayList<HashMap<String, Object>> arrayList2 = new ArrayList<>(hashMapArr.length);
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= hashMapArr.length) {
                return arrayList2;
            }
            HashMap hashMap = new HashMap();
            hashMap.put("runtimes", 0L);
            hashMap.put("fg", 0);
            hashMap.put("bg", 0);
            hashMap.put("empty", 0);
            arrayList2.add(hashMap);
            HashMap<String, String>[] hashMapArr2 = hashMapArr[i2];
            int length = hashMapArr2.length - 1;
            while (length >= 0) {
                if (hashMapArr2[length] != null) {
                    hashMap.put("runtimes", Long.valueOf((length == 0 ? 0 : arrayList.get(length)[1]) + ((Long) ResHelper.forceCast(hashMap.get("runtimes"), 0L)).longValue()));
                    if ("fg".equals(hashMapArr2[length].get("pcy"))) {
                        hashMap.put("fg", Integer.valueOf(((Integer) ResHelper.forceCast(hashMap.get("fg"), 0)).intValue() + 1));
                    } else if ("bg".equals(hashMapArr2[length].get("pcy"))) {
                        hashMap.put("bg", Integer.valueOf(((Integer) ResHelper.forceCast(hashMap.get("bg"), 0)).intValue() + 1));
                    } else {
                        hashMap.put("empty", Integer.valueOf(((Integer) ResHelper.forceCast(hashMap.get("empty"), 0)).intValue() + 1));
                    }
                    hashMap.put("pkg", hashMapArr2[length].get("pkg"));
                    hashMap.put("name", hashMapArr2[length].get("name"));
                    hashMap.put("version", hashMapArr2[length].get("version"));
                }
                length--;
            }
            i = i2 + 1;
        }
    }

    private void a(ArrayList<HashMap<String, Object>> arrayList, ArrayList<long[]> arrayList2) {
        HashMap hashMap = new HashMap();
        hashMap.put("type", "APP_RUNTIMES");
        hashMap.put("list", arrayList);
        hashMap.put("datatime", Long.valueOf(a.a(this.c)));
        hashMap.put("recordat", Long.valueOf(arrayList2.get(0)[0]));
        long j = 0;
        for (int i = 1; i < arrayList2.size(); i++) {
            j += arrayList2.get(i)[1];
        }
        hashMap.put("sdk_runtime_len", Long.valueOf(j));
        hashMap.put("top_count", Integer.valueOf(arrayList2.size()));
        b.a(this.c).a(a.a(this.c), (HashMap<String, Object>) hashMap);
    }

    private boolean b(String str) {
        try {
            File file = new File(str);
            file.delete();
            file.createNewFile();
            return true;
        } catch (Throwable th) {
            MobLog.getInstance().d(th);
            return false;
        }
    }
}
