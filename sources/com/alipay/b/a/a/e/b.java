package com.alipay.b.a.a.e;

import com.alipay.b.a.a.c.b.a;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import org.json.JSONObject;

public final class b {
    private File a = null;
    private a b = null;

    public b(String str, a aVar) {
        this.a = new File(str);
        this.b = aVar;
    }

    private static String a(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", "id");
            jSONObject.put("error", str);
            return jSONObject.toString();
        } catch (Exception e) {
            return "";
        }
    }

    /* access modifiers changed from: private */
    public synchronized void b() {
        String str;
        int i;
        synchronized (this) {
            if (this.a != null) {
                if (this.a.exists() && this.a.isDirectory() && this.a.list().length != 0) {
                    ArrayList arrayList = new ArrayList();
                    for (String add : this.a.list()) {
                        arrayList.add(add);
                    }
                    Collections.sort(arrayList);
                    String str2 = (String) arrayList.get(arrayList.size() - 1);
                    int size = arrayList.size();
                    if (!str2.equals(new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()) + ".log")) {
                        int i2 = size;
                        str = str2;
                        i = i2;
                    } else if (arrayList.size() >= 2) {
                        int i3 = size - 1;
                        str = (String) arrayList.get(arrayList.size() - 2);
                        i = i3;
                    }
                    int i4 = !this.b.a(a(com.alipay.b.a.a.a.b.a(this.a.getAbsolutePath(), str))) ? i - 1 : i;
                    for (int i5 = 0; i5 < i4; i5++) {
                        new File(this.a, (String) arrayList.get(i5)).delete();
                    }
                }
            }
        }
    }

    public final void a() {
        new Thread(new c(this)).start();
    }
}
