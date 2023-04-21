package com.mob.commons;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import com.mob.tools.MobHandlerThread;
import com.mob.tools.MobLog;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.FileLocker;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.MobRSA;
import com.mob.tools.utils.ReflectHelper;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.SQLiteHelper;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.zip.GZIPOutputStream;

/* compiled from: DataHeap */
public class b implements Handler.Callback {
    private static b a;
    /* access modifiers changed from: private */
    public Context b;
    private Handler c;
    /* access modifiers changed from: private */
    public SQLiteHelper.SingleTableDB d;
    /* access modifiers changed from: private */
    public Hashon e = new Hashon();
    private Random f = new Random();

    public static synchronized b a(Context context) {
        b bVar;
        synchronized (b.class) {
            if (a == null) {
                a = new b(context);
            }
            bVar = a;
        }
        return bVar;
    }

    private b(Context context) {
        this.b = context.getApplicationContext();
        MobHandlerThread mobHandlerThread = new MobHandlerThread();
        mobHandlerThread.start();
        this.c = new Handler(mobHandlerThread.getLooper(), this);
        File file = new File(ResHelper.getCacheRoot(context), "comm/dbs/.dh");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        this.d = SQLiteHelper.getDatabase(file.getAbsolutePath(), "DataHeap_1");
        this.d.addField("time", "text", true);
        this.d.addField("data", "text", true);
        this.c.sendEmptyMessage(1);
    }

    public synchronized void a(long j, HashMap<String, Object> hashMap) {
        Message message = new Message();
        message.what = 2;
        message.obj = new Object[]{Long.valueOf(j), hashMap};
        this.c.sendMessage(message);
    }

    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 1:
                a();
                this.c.sendEmptyMessageDelayed(1, 10000);
                break;
            case 2:
                Object[] objArr = (Object[]) message.obj;
                long longValue = ((Long) ResHelper.forceCast(objArr[0], -1L)).longValue();
                if (longValue > 0) {
                    b(longValue, (HashMap<String, Object>) (HashMap) objArr[1]);
                    break;
                }
                break;
        }
        return false;
    }

    private void b(final long j, final HashMap<String, Object> hashMap) {
        d.a(new File(ResHelper.getCacheRoot(this.b), "comm/locks/.dhlock"), new LockAction() {
            public boolean run(FileLocker fileLocker) {
                try {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("time", String.valueOf(j));
                    contentValues.put("data", Base64.encodeToString(Data.AES128Encode(Data.rawMD5(String.valueOf(ReflectHelper.invokeInstanceMethod(ReflectHelper.invokeStaticMethod("DeviceHelper", "getInstance", b.this.b), "getManufacturer", new Object[0]))), b.this.e.fromHashMap(hashMap).getBytes("utf-8")), 2));
                    SQLiteHelper.insert(b.this.d, contentValues);
                } catch (Throwable th) {
                    MobLog.getInstance().w(th);
                }
                return false;
            }
        });
    }

    private void a() {
        String str;
        try {
            str = (String) ReflectHelper.invokeInstanceMethod(ReflectHelper.invokeStaticMethod("DeviceHelper", "getInstance", this.b), "getNetworkType", new Object[0]);
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
            str = null;
        }
        if (str != null && !"none".equals(str)) {
            d.a(new File(ResHelper.getCacheRoot(this.b), "comm/locks/.dhlock"), new LockAction() {
                public boolean run(FileLocker fileLocker) {
                    ArrayList d = b.this.b();
                    if (d.size() <= 0 || !b.this.a((ArrayList<String[]>) d)) {
                        return false;
                    }
                    b.this.b((ArrayList<String[]>) d);
                    return false;
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public ArrayList<String[]> b() {
        ArrayList<String[]> arrayList = new ArrayList<>();
        try {
            Cursor query = SQLiteHelper.query(this.d, new String[]{"time", "data"}, (String) null, (String[]) null, (String) null);
            if (query != null) {
                if (query.moveToFirst()) {
                    long a2 = a.a(this.b);
                    do {
                        String[] strArr = {query.getString(0), query.getString(1)};
                        long j = -1;
                        try {
                            j = Long.parseLong(strArr[0]);
                        } catch (Throwable th) {
                        }
                        if (j <= a2) {
                            arrayList.add(strArr);
                        }
                    } while (query.moveToNext());
                }
                query.close();
            }
        } catch (Throwable th2) {
            MobLog.getInstance().w(th2);
        }
        return arrayList;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(java.util.ArrayList<java.lang.String[]> r12) {
        /*
            r11 = this;
            r10 = 0
            r6 = 0
            com.mob.tools.network.NetworkHelper r0 = new com.mob.tools.network.NetworkHelper     // Catch:{ Throwable -> 0x00e7 }
            r0.<init>()     // Catch:{ Throwable -> 0x00e7 }
            java.util.ArrayList r3 = com.mob.commons.MobProductCollector.getProducts()     // Catch:{ Throwable -> 0x00e7 }
            boolean r1 = r3.isEmpty()     // Catch:{ Throwable -> 0x00e7 }
            if (r1 == 0) goto L_0x0013
            r0 = r6
        L_0x0012:
            return r0
        L_0x0013:
            java.util.HashMap r4 = new java.util.HashMap     // Catch:{ Throwable -> 0x00e7 }
            r4.<init>()     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r1 = "DeviceHelper"
            java.lang.String r2 = "getInstance"
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x00e7 }
            r7 = 0
            android.content.Context r8 = r11.b     // Catch:{ Throwable -> 0x00e7 }
            r5[r7] = r8     // Catch:{ Throwable -> 0x00e7 }
            java.lang.Object r1 = com.mob.tools.utils.ReflectHelper.invokeStaticMethod(r1, r2, r5)     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r2 = "plat"
            java.lang.String r5 = "getPlatformCode"
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x00e7 }
            java.lang.Object r5 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r1, r5, r7)     // Catch:{ Throwable -> 0x00e7 }
            r4.put(r2, r5)     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r2 = "device"
            java.lang.String r5 = "getDeviceKey"
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x00e7 }
            java.lang.Object r5 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r1, r5, r7)     // Catch:{ Throwable -> 0x00e7 }
            r4.put(r2, r5)     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r2 = "mac"
            java.lang.String r5 = "getMacAddress"
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x00e7 }
            java.lang.Object r5 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r1, r5, r7)     // Catch:{ Throwable -> 0x00e7 }
            r4.put(r2, r5)     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r2 = "model"
            java.lang.String r5 = "getModel"
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x00e7 }
            java.lang.Object r5 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r1, r5, r7)     // Catch:{ Throwable -> 0x00e7 }
            r4.put(r2, r5)     // Catch:{ Throwable -> 0x00e7 }
            r2 = 0
            java.lang.String r5 = "duid"
            android.content.Context r7 = r11.b     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r2 = com.mob.commons.authorize.DeviceAuthorizer.authorize((android.content.Context) r7, (com.mob.commons.MobProduct) r2)     // Catch:{ Throwable -> 0x00e7 }
            r4.put(r5, r2)     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r2 = "imei"
            java.lang.String r5 = "getIMEI"
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x00e7 }
            java.lang.Object r5 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r1, r5, r7)     // Catch:{ Throwable -> 0x00e7 }
            r4.put(r2, r5)     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r2 = "serialno"
            java.lang.String r5 = "getSerialno"
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x00e7 }
            java.lang.Object r5 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r1, r5, r7)     // Catch:{ Throwable -> 0x00e7 }
            r4.put(r2, r5)     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r2 = "networktype"
            java.lang.String r5 = "getDetailNetworkTypeForStatic"
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x00e7 }
            java.lang.Object r5 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r1, r5, r7)     // Catch:{ Throwable -> 0x00e7 }
            r4.put(r2, r5)     // Catch:{ Throwable -> 0x00e7 }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ Throwable -> 0x00e7 }
            r2.<init>()     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r5 = "getManufacturer"
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x00e7 }
            java.lang.Object r1 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r1, r5, r7)     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ Throwable -> 0x00e7 }
            byte[] r5 = com.mob.tools.utils.Data.rawMD5((java.lang.String) r1)     // Catch:{ Throwable -> 0x00e7 }
            java.util.Iterator r7 = r12.iterator()     // Catch:{ Throwable -> 0x00e7 }
        L_0x00b0:
            boolean r1 = r7.hasNext()     // Catch:{ Throwable -> 0x00e7 }
            if (r1 == 0) goto L_0x00f7
            java.lang.Object r1 = r7.next()     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String[] r1 = (java.lang.String[]) r1     // Catch:{ Throwable -> 0x00e7 }
            r8 = 1
            r1 = r1[r8]     // Catch:{ Throwable -> 0x00de }
            r8 = 2
            byte[] r1 = android.util.Base64.decode(r1, r8)     // Catch:{ Throwable -> 0x00de }
            byte[] r1 = com.mob.tools.utils.Data.AES128Decode((byte[]) r5, (byte[]) r1)     // Catch:{ Throwable -> 0x00de }
            java.lang.String r8 = new java.lang.String     // Catch:{ Throwable -> 0x00de }
            java.lang.String r9 = "utf-8"
            r8.<init>(r1, r9)     // Catch:{ Throwable -> 0x00de }
            java.lang.String r1 = r8.trim()     // Catch:{ Throwable -> 0x00de }
            com.mob.tools.utils.Hashon r8 = r11.e     // Catch:{ Throwable -> 0x00de }
            java.util.HashMap r1 = r8.fromJson((java.lang.String) r1)     // Catch:{ Throwable -> 0x00de }
            r2.add(r1)     // Catch:{ Throwable -> 0x00de }
            goto L_0x00b0
        L_0x00de:
            r1 = move-exception
            com.mob.tools.log.NLog r8 = com.mob.tools.MobLog.getInstance()     // Catch:{ Throwable -> 0x00e7 }
            r8.w(r1)     // Catch:{ Throwable -> 0x00e7 }
            goto L_0x00b0
        L_0x00e7:
            r0 = move-exception
            android.content.Context r1 = r11.b
            com.mob.commons.e.e(r1, r10)
            com.mob.tools.log.NLog r1 = com.mob.tools.MobLog.getInstance()
            r1.w(r0)
            r0 = r6
            goto L_0x0012
        L_0x00f7:
            java.lang.String r1 = "datas"
            r4.put(r1, r2)     // Catch:{ Throwable -> 0x00e7 }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ Throwable -> 0x00e7 }
            r2.<init>()     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r1 = com.mob.MobSDK.getAppkey()     // Catch:{ Throwable -> 0x00e7 }
            if (r1 != 0) goto L_0x0112
            r1 = 0
            java.lang.Object r1 = r3.get(r1)     // Catch:{ Throwable -> 0x00e7 }
            com.mob.commons.MobProduct r1 = (com.mob.commons.MobProduct) r1     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r1 = r1.getProductAppkey()     // Catch:{ Throwable -> 0x00e7 }
        L_0x0112:
            com.mob.tools.network.KVPair r5 = new com.mob.tools.network.KVPair     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r7 = "appkey"
            r5.<init>(r7, r1)     // Catch:{ Throwable -> 0x00e7 }
            r2.add(r5)     // Catch:{ Throwable -> 0x00e7 }
            com.mob.tools.network.KVPair r1 = new com.mob.tools.network.KVPair     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r5 = "m"
            com.mob.tools.utils.Hashon r7 = r11.e     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r4 = r7.fromHashMap(r4)     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r4 = r11.a((java.lang.String) r4)     // Catch:{ Throwable -> 0x00e7 }
            r1.<init>(r5, r4)     // Catch:{ Throwable -> 0x00e7 }
            r2.add(r1)     // Catch:{ Throwable -> 0x00e7 }
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ Throwable -> 0x00e7 }
            r4.<init>()     // Catch:{ Throwable -> 0x00e7 }
            com.mob.tools.network.KVPair r1 = new com.mob.tools.network.KVPair     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r5 = "User-Identity"
            android.content.Context r7 = r11.b     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r3 = com.mob.commons.MobProductCollector.getUserIdentity(r7, r3)     // Catch:{ Throwable -> 0x00e7 }
            r1.<init>(r5, r3)     // Catch:{ Throwable -> 0x00e7 }
            r4.add(r1)     // Catch:{ Throwable -> 0x00e7 }
            com.mob.tools.network.NetworkHelper$NetworkTimeOut r5 = new com.mob.tools.network.NetworkHelper$NetworkTimeOut     // Catch:{ Throwable -> 0x00e7 }
            r5.<init>()     // Catch:{ Throwable -> 0x00e7 }
            r1 = 30000(0x7530, float:4.2039E-41)
            r5.readTimout = r1     // Catch:{ Throwable -> 0x00e7 }
            r1 = 30000(0x7530, float:4.2039E-41)
            r5.connectionTimeout = r1     // Catch:{ Throwable -> 0x00e7 }
            android.content.Context r1 = r11.b     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r1 = b((android.content.Context) r1)     // Catch:{ Throwable -> 0x00e7 }
            r3 = 0
            java.lang.String r0 = r0.httpPost(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x00e7 }
            com.mob.tools.utils.Hashon r1 = r11.e     // Catch:{ Throwable -> 0x00e7 }
            java.util.HashMap r0 = r1.fromJson((java.lang.String) r0)     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r1 = "200"
            java.lang.String r2 = "status"
            java.lang.Object r0 = r0.get(r2)     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ Throwable -> 0x00e7 }
            boolean r0 = r1.equals(r0)     // Catch:{ Throwable -> 0x00e7 }
            if (r0 != 0) goto L_0x0012
            android.content.Context r1 = r11.b     // Catch:{ Throwable -> 0x00e7 }
            r2 = 0
            com.mob.commons.e.e(r1, r2)     // Catch:{ Throwable -> 0x00e7 }
            goto L_0x0012
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.commons.b.a(java.util.ArrayList):boolean");
    }

    private String a(String str) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeLong(this.f.nextLong());
        dataOutputStream.writeLong(this.f.nextLong());
        dataOutputStream.flush();
        dataOutputStream.close();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream2);
        gZIPOutputStream.write(str.getBytes("utf-8"));
        gZIPOutputStream.flush();
        gZIPOutputStream.close();
        byte[] AES128Encode = Data.AES128Encode(byteArray, byteArrayOutputStream2.toByteArray());
        byte[] encode = new MobRSA(1024).encode(byteArray, new BigInteger("ceeef5035212dfe7c6a0acdc0ef35ce5b118aab916477037d7381f85c6b6176fcf57b1d1c3296af0bb1c483fe5e1eb0ce9eb2953b44e494ca60777a1b033cc07", 16), new BigInteger("191737288d17e660c4b61440d5d14228a0bf9854499f9d68d8274db55d6d954489371ecf314f26bec236e58fac7fffa9b27bcf923e1229c4080d49f7758739e5bd6014383ed2a75ce1be9b0ab22f283c5c5e11216c5658ba444212b6270d629f2d615b8dfdec8545fb7d4f935b0cc10b6948ab4fc1cb1dd496a8f94b51e888dd", 16));
        ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream2 = new DataOutputStream(byteArrayOutputStream3);
        dataOutputStream2.writeInt(encode.length);
        dataOutputStream2.write(encode);
        dataOutputStream2.writeInt(AES128Encode.length);
        dataOutputStream2.write(AES128Encode);
        dataOutputStream2.flush();
        dataOutputStream2.close();
        return Base64.encodeToString(byteArrayOutputStream3.toByteArray(), 2);
    }

    /* access modifiers changed from: private */
    public void b(ArrayList<String[]> arrayList) {
        try {
            StringBuilder sb = new StringBuilder();
            Iterator<String[]> it = arrayList.iterator();
            while (it.hasNext()) {
                String[] next = it.next();
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append('\'').append(next[0]).append('\'');
            }
            SQLiteHelper.delete(this.d, "time in (" + sb.toString() + ")", (String[]) null);
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
        }
    }

    private static String b(Context context) {
        String str = null;
        try {
            str = e.f(context);
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
        }
        return TextUtils.isEmpty(str) ? "http://c.data.mob.com/v2/cdata" : str;
    }
}
