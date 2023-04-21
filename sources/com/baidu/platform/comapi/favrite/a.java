package com.baidu.platform.comapi.favrite;

import android.os.Bundle;
import android.text.TextUtils;
import com.baidu.mapapi.UIMsg;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.mapapi.model.inner.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a {
    private static a b = null;
    private com.baidu.platform.comjni.map.favorite.a a = null;
    private boolean c = false;
    private boolean d = false;
    private Vector<String> e = null;
    private Vector<String> f = null;
    private boolean g = false;
    private c h = new c();
    private b i = new b();

    /* renamed from: com.baidu.platform.comapi.favrite.a$a  reason: collision with other inner class name */
    class C0016a implements Comparator<String> {
        C0016a() {
        }

        /* renamed from: a */
        public int compare(String str, String str2) {
            return str2.compareTo(str);
        }
    }

    private class b {
        private long b;
        private long c;

        private b() {
        }

        /* access modifiers changed from: private */
        public void a() {
            this.b = System.currentTimeMillis();
        }

        /* access modifiers changed from: private */
        public void b() {
            this.c = System.currentTimeMillis();
        }

        /* access modifiers changed from: private */
        public boolean c() {
            return this.c - this.b > 1000;
        }
    }

    private class c {
        private String b;
        private long c;
        private long d;

        private c() {
            this.c = 5000;
            this.d = 0;
        }

        /* access modifiers changed from: private */
        public String a() {
            return this.b;
        }

        /* access modifiers changed from: private */
        public void a(String str) {
            this.b = str;
            this.d = System.currentTimeMillis();
        }

        /* access modifiers changed from: private */
        public boolean b() {
            return TextUtils.isEmpty(this.b);
        }

        /* access modifiers changed from: private */
        public boolean c() {
            return true;
        }
    }

    private a() {
    }

    public static a a() {
        if (b == null) {
            synchronized (a.class) {
                if (b == null) {
                    b = new a();
                    b.h();
                }
            }
        }
        return b;
    }

    public static boolean g() {
        return (b == null || b.a == null || !b.a.d()) ? false : true;
    }

    private boolean h() {
        if (this.a != null) {
            return true;
        }
        this.a = new com.baidu.platform.comjni.map.favorite.a();
        if (this.a.a() == 0) {
            this.a = null;
            return false;
        }
        j();
        i();
        return true;
    }

    private boolean i() {
        if (this.a == null) {
            return false;
        }
        String str = SysOSUtil.getModuleFileName() + "/";
        this.a.a(1);
        return this.a.a(str, "fav_poi", "fifo", 10, UIMsg.d_ResultType.VERSION_CHECK, -1);
    }

    private void j() {
        this.c = false;
        this.d = false;
    }

    /* JADX INFO: finally extract failed */
    public synchronized int a(String str, FavSyncPoi favSyncPoi) {
        int i2;
        if (this.a == null) {
            i2 = 0;
        } else {
            if (str != null) {
                if (!str.equals("") && favSyncPoi != null) {
                    j();
                    ArrayList<String> e2 = e();
                    if ((e2 != null ? e2.size() : 0) + 1 > 500) {
                        i2 = -2;
                    } else {
                        if (e2 != null && e2.size() > 0) {
                            Iterator<String> it = e2.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                FavSyncPoi b2 = b(it.next());
                                if (b2 != null && str.equals(b2.b)) {
                                    i2 = -1;
                                    break;
                                }
                            }
                        }
                        try {
                            JSONObject jSONObject = new JSONObject();
                            favSyncPoi.b = str;
                            String valueOf = String.valueOf(System.currentTimeMillis());
                            String str2 = valueOf + "_" + favSyncPoi.hashCode();
                            favSyncPoi.h = valueOf;
                            favSyncPoi.a = str2;
                            jSONObject.put("bdetail", favSyncPoi.i);
                            jSONObject.put("uspoiname", favSyncPoi.b);
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject2.put("x", favSyncPoi.c.getmPtx());
                            jSONObject2.put("y", favSyncPoi.c.getmPty());
                            jSONObject.put("pt", jSONObject2);
                            jSONObject.put("ncityid", favSyncPoi.e);
                            jSONObject.put("npoitype", favSyncPoi.g);
                            jSONObject.put("uspoiuid", favSyncPoi.f);
                            jSONObject.put("addr", favSyncPoi.d);
                            jSONObject.put("addtimesec", favSyncPoi.h);
                            JSONObject jSONObject3 = new JSONObject();
                            jSONObject3.put("Fav_Sync", jSONObject);
                            jSONObject3.put("Fav_Content", favSyncPoi.j);
                            if (this.a.a(str2, jSONObject3.toString())) {
                                j();
                                i2 = 1;
                                g();
                            } else {
                                g();
                                i2 = 0;
                            }
                        } catch (JSONException e3) {
                            g();
                            i2 = 0;
                        } catch (Throwable th) {
                            g();
                            throw th;
                        }
                    }
                }
            }
            i2 = -1;
        }
        return i2;
    }

    public synchronized boolean a(String str) {
        boolean z = false;
        synchronized (this) {
            if (!(this.a == null || str == null)) {
                if (!str.equals("") && c(str)) {
                    j();
                    z = this.a.a(str);
                }
            }
        }
        return z;
    }

    public FavSyncPoi b(String str) {
        if (this.a == null || str == null || str.equals("")) {
            return null;
        }
        try {
            if (!c(str)) {
                return null;
            }
            FavSyncPoi favSyncPoi = new FavSyncPoi();
            String b2 = this.a.b(str);
            if (b2 == null || b2.equals("")) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(b2);
            JSONObject optJSONObject = jSONObject.optJSONObject("Fav_Sync");
            String optString = jSONObject.optString("Fav_Content");
            favSyncPoi.b = optJSONObject.optString("uspoiname");
            JSONObject optJSONObject2 = optJSONObject.optJSONObject("pt");
            favSyncPoi.c = new Point(optJSONObject2.optInt("x"), optJSONObject2.optInt("y"));
            favSyncPoi.e = optJSONObject.optString("ncityid");
            favSyncPoi.f = optJSONObject.optString("uspoiuid");
            favSyncPoi.g = optJSONObject.optInt("npoitype");
            favSyncPoi.d = optJSONObject.optString("addr");
            favSyncPoi.h = optJSONObject.optString("addtimesec");
            favSyncPoi.i = optJSONObject.optBoolean("bdetail");
            favSyncPoi.j = optString;
            favSyncPoi.a = str;
            return favSyncPoi;
        } catch (NullPointerException e2) {
            e2.printStackTrace();
            return null;
        } catch (JSONException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public void b() {
        if (b != null) {
            if (b.a != null) {
                b.a.b();
                b.a = null;
            }
            b = null;
        }
    }

    public synchronized boolean b(String str, FavSyncPoi favSyncPoi) {
        boolean z = false;
        synchronized (this) {
            if (!(this.a == null || str == null || str.equals("") || favSyncPoi == null)) {
                if (c(str)) {
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("uspoiname", favSyncPoi.b);
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("x", favSyncPoi.c.getmPtx());
                        jSONObject2.put("y", favSyncPoi.c.getmPty());
                        jSONObject.put("pt", jSONObject2);
                        jSONObject.put("ncityid", favSyncPoi.e);
                        jSONObject.put("npoitype", favSyncPoi.g);
                        jSONObject.put("uspoiuid", favSyncPoi.f);
                        jSONObject.put("addr", favSyncPoi.d);
                        favSyncPoi.h = String.valueOf(System.currentTimeMillis());
                        jSONObject.put("addtimesec", favSyncPoi.h);
                        jSONObject.put("bdetail", false);
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put("Fav_Sync", jSONObject);
                        jSONObject3.put("Fav_Content", favSyncPoi.j);
                        j();
                        if (this.a != null && this.a.b(str, jSONObject3.toString())) {
                            z = true;
                        }
                    } catch (JSONException e2) {
                    }
                }
            }
        }
        return z;
    }

    public synchronized boolean c() {
        boolean c2;
        if (this.a == null) {
            c2 = false;
        } else {
            j();
            c2 = this.a.c();
            g();
        }
        return c2;
    }

    public boolean c(String str) {
        return this.a != null && str != null && !str.equals("") && this.a.c(str);
    }

    public ArrayList<String> d() {
        String b2;
        if (this.a == null) {
            return null;
        }
        if (this.d && this.f != null) {
            return new ArrayList<>(this.f);
        }
        try {
            Bundle bundle = new Bundle();
            this.a.a(bundle);
            String[] stringArray = bundle.getStringArray("rstString");
            if (stringArray != null) {
                if (this.f == null) {
                    this.f = new Vector<>();
                } else {
                    this.f.clear();
                }
                for (int i2 = 0; i2 < stringArray.length; i2++) {
                    if (!stringArray[i2].equals("data_version") && (b2 = this.a.b(stringArray[i2])) != null && !b2.equals("")) {
                        this.f.add(stringArray[i2]);
                    }
                }
                if (this.f.size() > 0) {
                    try {
                        Collections.sort(this.f, new C0016a());
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    this.d = true;
                }
            } else if (this.f != null) {
                this.f.clear();
                this.f = null;
            }
            return (this.f == null || this.f.isEmpty()) ? null : new ArrayList<>(this.f);
        } catch (Exception e3) {
            return null;
        }
    }

    public ArrayList<String> e() {
        if (this.a == null) {
            return null;
        }
        if (this.c && this.e != null) {
            return new ArrayList<>(this.e);
        }
        try {
            Bundle bundle = new Bundle();
            this.a.a(bundle);
            String[] stringArray = bundle.getStringArray("rstString");
            if (stringArray != null) {
                if (this.e == null) {
                    this.e = new Vector<>();
                } else {
                    this.e.clear();
                }
                for (String str : stringArray) {
                    if (!str.equals("data_version")) {
                        this.e.add(str);
                    }
                }
                if (this.e.size() > 0) {
                    try {
                        Collections.sort(this.e, new C0016a());
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    this.c = true;
                }
            } else if (this.e != null) {
                this.e.clear();
                this.e = null;
            }
            if (this.e == null || this.e.size() == 0) {
                return null;
            }
            return new ArrayList<>(this.e);
        } catch (Exception e3) {
            return null;
        }
    }

    public String f() {
        int i2;
        String b2;
        if (this.i.c() && !this.h.c() && !this.h.b()) {
            return this.h.a();
        }
        this.i.a();
        if (this.a == null) {
            return null;
        }
        ArrayList<String> d2 = d();
        JSONObject jSONObject = new JSONObject();
        if (d2 != null) {
            try {
                JSONArray jSONArray = new JSONArray();
                int i3 = 0;
                Iterator<String> it = d2.iterator();
                while (it.hasNext()) {
                    String next = it.next();
                    if (next == null || next.equals("data_version") || (b2 = this.a.b(next)) == null || b2.equals("")) {
                        i2 = i3;
                    } else {
                        JSONObject optJSONObject = new JSONObject(b2).optJSONObject("Fav_Sync");
                        optJSONObject.put("key", next);
                        jSONArray.put(i3, optJSONObject);
                        i2 = i3 + 1;
                    }
                    i3 = i2;
                }
                if (i3 > 0) {
                    jSONObject.put("favcontents", jSONArray);
                    jSONObject.put("favpoinum", i3);
                }
            } catch (JSONException e2) {
                return null;
            }
        }
        this.i.b();
        this.h.a(jSONObject.toString());
        return this.h.a();
    }
}
