package cn.sharesdk.framework.b;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import cn.sharesdk.framework.b.a.e;
import cn.sharesdk.framework.b.b.b;
import cn.sharesdk.framework.b.b.f;
import cn.sharesdk.framework.utils.d;
import com.alipay.sdk.app.statistic.c;
import com.facebook.common.util.UriUtil;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.ResHelper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;
import org.achartengine.chart.TimeChart;

/* compiled from: EventManager */
public class a {
    private static a a;
    private c b;
    private DeviceHelper c;
    private e d;
    private boolean e;

    public static a a(Context context, String str) {
        if (a == null) {
            a = new a(context, str);
        }
        return a;
    }

    private a(Context context, String str) {
        this.b = new c(context, str);
        this.d = e.a(context);
        this.c = DeviceHelper.getInstance(context);
    }

    public void a() {
        try {
            String networkType = this.c.getNetworkType();
            if (!"none".equals(networkType) && !TextUtils.isEmpty(networkType)) {
                long longValue = this.d.h().longValue();
                long currentTimeMillis = System.currentTimeMillis();
                Calendar instance = Calendar.getInstance();
                instance.setTimeInMillis(longValue);
                int i = instance.get(5);
                instance.setTimeInMillis(currentTimeMillis);
                int i2 = instance.get(5);
                if (currentTimeMillis - longValue >= TimeChart.DAY || i != i2) {
                    HashMap<String, Object> a2 = this.b.a();
                    this.d.a(a2.containsKey(UriUtil.LOCAL_RESOURCE_SCHEME) ? "true".equals(String.valueOf(a2.get(UriUtil.LOCAL_RESOURCE_SCHEME))) : true);
                    if (a2 != null && a2.size() > 0) {
                        this.d.b(System.currentTimeMillis());
                    }
                }
            }
        } catch (Throwable th) {
            d.a().d(th);
        }
    }

    public void b() {
        HashMap hashMap;
        HashMap hashMap2;
        try {
            String networkType = this.c.getNetworkType();
            if (!"none".equals(networkType) && !TextUtils.isEmpty(networkType) && this.d.g()) {
                this.d.a(System.currentTimeMillis());
                HashMap<String, Object> c2 = this.b.c();
                if (!c2.containsKey("status") || ResHelper.parseInt(String.valueOf(c2.get("status"))) != -200) {
                    if (c2.containsKey("timestamp")) {
                        this.d.a("service_time", Long.valueOf(System.currentTimeMillis() - ResHelper.parseLong(String.valueOf(c2.get("timestamp")))));
                    }
                    if (c2.containsKey("switchs") && (hashMap2 = (HashMap) c2.get("switchs")) != null) {
                        String valueOf = String.valueOf(hashMap2.get(com.alipay.sdk.packet.d.n));
                        String valueOf2 = String.valueOf(hashMap2.get("share"));
                        String valueOf3 = String.valueOf(hashMap2.get(c.d));
                        String valueOf4 = String.valueOf(hashMap2.get("backflow"));
                        this.d.b(valueOf);
                        this.d.d(valueOf2);
                        this.d.c(valueOf3);
                        this.d.a(valueOf4);
                    }
                    if (c2.containsKey("serpaths") && (hashMap = (HashMap) c2.get("serpaths")) != null) {
                        String valueOf5 = String.valueOf(hashMap.get("defhost"));
                        String valueOf6 = String.valueOf(hashMap.get("defport"));
                        if (!TextUtils.isEmpty(valueOf5) && !TextUtils.isEmpty(valueOf6)) {
                            this.b.a("http://" + valueOf5 + ":" + valueOf6);
                        }
                        HashMap hashMap3 = new HashMap();
                        if (hashMap.containsKey("assigns")) {
                            HashMap hashMap4 = (HashMap) hashMap.get("assigns");
                            if (hashMap4 == null || hashMap4.size() == 0) {
                                this.b.a((HashMap<String, String>) null);
                                return;
                            }
                            for (String str : hashMap4.keySet()) {
                                HashMap hashMap5 = (HashMap) hashMap4.get(str);
                                String valueOf7 = String.valueOf(hashMap5.get(com.alipay.sdk.cons.c.f));
                                String valueOf8 = String.valueOf(hashMap5.get("port"));
                                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(valueOf7) && !TextUtils.isEmpty(valueOf8)) {
                                    hashMap3.put(str, "http://" + valueOf7 + ":" + valueOf8);
                                }
                            }
                            this.b.a((HashMap<String, String>) hashMap3);
                            return;
                        }
                        return;
                    }
                    return;
                }
                d.a().d((String) c2.get("error"), new Object[0]);
            }
        } catch (Throwable th) {
            d.a().d(th);
        }
    }

    public void a(boolean z) {
        this.e = z;
    }

    public void a(cn.sharesdk.framework.b.b.c cVar) {
        try {
            if (this.d.g()) {
                if (cVar instanceof b) {
                    a((b) cVar);
                } else if (cVar instanceof f) {
                    a((f) cVar);
                }
                if (!this.d.b()) {
                    cVar.m = null;
                }
                long a2 = this.d.a();
                if (a2 == 0) {
                    a2 = this.b.b();
                }
                cVar.e = System.currentTimeMillis() - a2;
                this.b.a(cVar);
            }
        } catch (Throwable th) {
            d.a().d(th);
        }
    }

    private void a(b bVar) throws Throwable {
        boolean c2 = this.d.c();
        String str = bVar.c;
        if (!c2 || TextUtils.isEmpty(str)) {
            bVar.d = null;
            bVar.c = null;
            return;
        }
        bVar.c = Data.Base64AES(str, bVar.f.substring(0, 16));
    }

    private void a(f fVar) throws Throwable {
        int e2 = this.d.e();
        boolean c2 = this.d.c();
        f.a aVar = fVar.d;
        if (e2 == 1 || (e2 == 0 && this.e)) {
            int size = (aVar == null || aVar.e == null) ? 0 : aVar.e.size();
            for (int i = 0; i < size; i++) {
                String a2 = a(aVar.e.get(i), b.FINISH_SHARE);
                if (!TextUtils.isEmpty(a2)) {
                    aVar.d.add(a2);
                }
            }
            int size2 = (aVar == null || aVar.f == null) ? 0 : aVar.f.size();
            for (int i2 = 0; i2 < size2; i2++) {
                String a3 = a(aVar.f.get(i2), b.FINISH_SHARE);
                if (!TextUtils.isEmpty(a3)) {
                    aVar.d.add(a3);
                }
            }
        } else {
            fVar.d = null;
        }
        if (!c2) {
            fVar.n = null;
        }
    }

    private String a(String str, b bVar) throws Throwable {
        int ceil;
        if (TextUtils.isEmpty(str) || !new File(str).exists()) {
            return null;
        }
        String networkType = this.c.getNetworkType();
        if ("none".equals(networkType) || TextUtils.isEmpty(networkType)) {
            return null;
        }
        Bitmap.CompressFormat bmpFormat = BitmapHelper.getBmpFormat(str);
        float f = 200.0f;
        if (bVar == b.BEFORE_SHARE) {
            f = 600.0f;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        options.inJustDecodeBounds = false;
        int i = options.outWidth;
        int i2 = options.outHeight;
        if (i >= i2 && ((float) i2) > f) {
            ceil = (int) Math.ceil((double) (((float) options.outHeight) / f));
        } else if (i >= i2 || ((float) i) <= f) {
            return c(str);
        } else {
            ceil = (int) Math.ceil((double) (((float) options.outWidth) / f));
        }
        if (ceil <= 0) {
            ceil = 1;
        }
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inSampleSize = ceil;
        options2.inPurgeable = true;
        options2.inInputShareable = true;
        Bitmap decodeFile = BitmapFactory.decodeFile(str, options2);
        decodeFile.getHeight();
        decodeFile.getWidth();
        File createTempFile = File.createTempFile("bm_tmp2", "." + bmpFormat.name().toLowerCase());
        FileOutputStream fileOutputStream = new FileOutputStream(createTempFile);
        decodeFile.compress(bmpFormat, 80, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        return c(createTempFile.getAbsolutePath());
    }

    private String c(String str) throws Throwable {
        HashMap<String, Object> b2 = this.b.b(str);
        if (b2 == null || b2.size() <= 0 || !b2.containsKey("status") || ResHelper.parseInt(String.valueOf(b2.get("status"))) != 200 || !b2.containsKey("url")) {
            return null;
        }
        return (String) b2.get("url");
    }

    private String a(Bitmap bitmap, b bVar) throws Throwable {
        File createTempFile = File.createTempFile("bm_tmp", ".png");
        FileOutputStream fileOutputStream = new FileOutputStream(createTempFile);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        return a(createTempFile.getAbsolutePath(), bVar);
    }

    public void c() {
        boolean a2;
        try {
            String networkType = this.c.getNetworkType();
            if (!"none".equals(networkType) && !TextUtils.isEmpty(networkType) && this.d.g()) {
                ArrayList<cn.sharesdk.framework.b.a.c> e2 = this.b.e();
                for (int i = 0; i < e2.size(); i++) {
                    cn.sharesdk.framework.b.a.c cVar = e2.get(i);
                    if (cVar.b.size() == 1) {
                        a2 = a(cVar.a, false);
                    } else {
                        a2 = a(d(cVar.a), true);
                    }
                    if (a2) {
                        this.b.a(cVar.b);
                    }
                }
            }
        } catch (Throwable th) {
            d.a().d(th);
        }
    }

    private String d(String str) throws Throwable {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        byte[] bArr = new byte[1024];
        while (true) {
            int read = byteArrayInputStream.read(bArr, 0, 1024);
            if (read != -1) {
                gZIPOutputStream.write(bArr, 0, read);
            } else {
                gZIPOutputStream.flush();
                gZIPOutputStream.close();
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.flush();
                byteArrayOutputStream.close();
                byteArrayInputStream.close();
                return Base64.encodeToString(byteArray, 2);
            }
        }
    }

    private boolean a(String str, boolean z) throws Throwable {
        return this.b.a(str, z);
    }

    public String a(String str, int i, boolean z, String str2) {
        String a2;
        try {
            if (!this.d.g() || !this.d.d()) {
                return str;
            }
            String networkType = this.c.getNetworkType();
            if ("none".equals(networkType) || TextUtils.isEmpty(networkType)) {
                return str;
            }
            if (z && (a2 = a(str, "<a[^>]*?href[\\s]*=[\\s]*[\"']?([^'\">]+?)['\"]?>", i, str2)) != null && !a2.equals(str)) {
                return a2;
            }
            String a3 = a(str, "(http://|https://){1}[\\w\\.\\-/:\\?&%=,;\\[\\]\\{\\}`~!@#\\$\\^\\*\\(\\)_\\+\\\\\\|]+", i, str2);
            if (a3 == null || a3.equals(str)) {
                return str;
            }
            return a3;
        } catch (Throwable th) {
            d.a().d(th);
            return str;
        }
    }

    private String a(String str, String str2, int i, String str3) throws Throwable {
        HashMap<String, Object> a2;
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        ArrayList arrayList = new ArrayList();
        Pattern compile = Pattern.compile(str2);
        Matcher matcher = compile.matcher(str);
        while (matcher.find()) {
            String group = matcher.group();
            if (group != null && group.length() > 0) {
                arrayList.add(group);
            }
        }
        if (arrayList.size() == 0 || (a2 = this.b.a(str, arrayList, i, str3)) == null || a2.size() <= 0 || !a2.containsKey("data")) {
            return str;
        }
        HashMap hashMap = new HashMap();
        Iterator it = ((ArrayList) a2.get("data")).iterator();
        while (it.hasNext()) {
            HashMap hashMap2 = (HashMap) it.next();
            hashMap.put(String.valueOf(hashMap2.get("source")), String.valueOf(hashMap2.get("surl")));
        }
        Matcher matcher2 = compile.matcher(str);
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        while (matcher2.find()) {
            sb.append(str.substring(i2, matcher2.start()));
            sb.append((String) hashMap.get(matcher2.group()));
            i2 = matcher2.end();
        }
        sb.append(str.substring(i2, str.length()));
        String sb2 = sb.toString();
        d.a().i("> SERVER_SHORT_LINK_URL content after replace link ===  %s", sb2);
        return sb2;
    }

    public String a(String str) {
        if (!this.d.g()) {
            return null;
        }
        try {
            return a(str, b.BEFORE_SHARE);
        } catch (Throwable th) {
            d.a().d(th);
            return null;
        }
    }

    public String a(Bitmap bitmap) {
        if (!this.d.g()) {
            return null;
        }
        try {
            return a(bitmap, b.BEFORE_SHARE);
        } catch (Throwable th) {
            d.a().d(th);
            return null;
        }
    }

    public HashMap<String, Object> d() {
        try {
            return this.b.f();
        } catch (Throwable th) {
            d.a().d(th);
            return new HashMap<>();
        }
    }

    public HashMap<String, Object> e() {
        if (!this.d.g() && this.d.i()) {
            return new HashMap<>();
        }
        try {
            HashMap<String, Object> d2 = this.b.d();
            this.d.b(true);
            return d2;
        } catch (Throwable th) {
            this.d.b(false);
            d.a().d(th);
            return new HashMap<>();
        }
    }

    public void a(HashMap<String, Object> hashMap) {
        try {
            this.b.b(hashMap);
        } catch (Throwable th) {
            d.a().d(th);
        }
    }

    public HashMap<String, Object> b(String str) {
        try {
            return this.b.c(str);
        } catch (Throwable th) {
            d.a().d(th);
            return new HashMap<>();
        }
    }
}
