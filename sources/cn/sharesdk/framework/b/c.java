package cn.sharesdk.framework.b;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import cn.sharesdk.framework.b.a.e;
import cn.sharesdk.framework.utils.d;
import com.alipay.sdk.sys.a;
import com.baidu.mapapi.UIMsg;
import com.facebook.common.util.UriUtil;
import com.mob.tools.network.KVPair;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.ResHelper;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: Protocols */
public class c {
    private String a;
    private Context b;
    private e c = e.a(this.b);
    private DeviceHelper d = DeviceHelper.getInstance(this.b);
    private NetworkHelper e = new NetworkHelper();
    private Hashon f = new Hashon();
    private String g;
    private String h;
    private boolean i;
    private HashMap<String, String> j;

    public c(Context context, String str) {
        this.a = str;
        this.b = context.getApplicationContext();
        try {
            this.j = (HashMap) this.c.h("buffered_server_paths");
        } catch (Throwable th) {
            this.j = new HashMap<>();
        }
        g();
    }

    private void g() {
        this.g = (this.d.getPackageName() + "/" + this.d.getAppVersionName()) + " " + "ShareSDK/2.8.3" + " " + ("Android/" + this.d.getOSVersionInt());
        this.h = "http://api.share.mob.com:80";
        this.i = true;
    }

    public void a(String str) {
        this.h = str;
    }

    public void a(HashMap<String, String> hashMap) {
        this.j = hashMap;
        this.c.a("buffered_server_paths", (Object) this.j);
    }

    private String h() {
        return this.h + "/conn";
    }

    public HashMap<String, Object> a() throws Throwable {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair(a.f, this.a));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new KVPair("User-Agent", this.g));
        NetworkHelper.NetworkTimeOut networkTimeOut = new NetworkHelper.NetworkTimeOut();
        networkTimeOut.readTimout = UIMsg.m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT;
        networkTimeOut.connectionTimeout = UIMsg.m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT;
        String httpPost = this.e.httpPost(h(), arrayList, (KVPair<String>) null, arrayList2, networkTimeOut);
        d.a().i(" isConnectToServer response == %s", httpPost);
        return this.f.fromJson(httpPost);
    }

    private String i() {
        if (this.j == null || !this.j.containsKey("/date")) {
            return this.h + "/date";
        }
        return this.j.get("/date") + "/date";
    }

    public long b() throws Throwable {
        if (!this.c.g()) {
            return 0;
        }
        String str = "{}";
        try {
            str = this.e.httpGet(i(), (ArrayList<KVPair<String>>) null, (ArrayList<KVPair<String>>) null, (NetworkHelper.NetworkTimeOut) null);
        } catch (Throwable th) {
            d.a().d(th);
        }
        HashMap fromJson = this.f.fromJson(str);
        if (!fromJson.containsKey("timestamp")) {
            return this.c.a();
        }
        try {
            long currentTimeMillis = System.currentTimeMillis() - ResHelper.parseLong(String.valueOf(fromJson.get("timestamp")));
            this.c.a("service_time", Long.valueOf(currentTimeMillis));
            return currentTimeMillis;
        } catch (Throwable th2) {
            d.a().d(th2);
            return this.c.a();
        }
    }

    private String j() {
        return this.h + "/conf5";
    }

    public HashMap<String, Object> c() throws Throwable {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair(a.f, this.a));
        arrayList.add(new KVPair(com.alipay.sdk.packet.d.n, this.d.getDeviceKey()));
        arrayList.add(new KVPair("plat", String.valueOf(this.d.getPlatformCode())));
        arrayList.add(new KVPair("apppkg", this.d.getPackageName()));
        arrayList.add(new KVPair("appver", String.valueOf(this.d.getAppVersion())));
        arrayList.add(new KVPair("sdkver", String.valueOf(60072)));
        arrayList.add(new KVPair("networktype", this.d.getDetailNetworkTypeForStatic()));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new KVPair("User-Agent", this.g));
        NetworkHelper.NetworkTimeOut networkTimeOut = new NetworkHelper.NetworkTimeOut();
        networkTimeOut.readTimout = 10000;
        networkTimeOut.connectionTimeout = 10000;
        String httpPost = this.e.httpPost(j(), arrayList, (KVPair<String>) null, arrayList2, networkTimeOut);
        d.a().i(" get server config response == %s", httpPost);
        return this.f.fromJson(httpPost);
    }

    private String k() {
        return "http://up.sharesdk.cn/upload/image";
    }

    public HashMap<String, Object> b(String str) throws Throwable {
        KVPair kVPair = new KVPair(UriUtil.LOCAL_FILE_SCHEME, str);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("User-Agent", this.g));
        String httpPost = this.e.httpPost(k(), (ArrayList<KVPair<String>>) null, kVPair, arrayList, (NetworkHelper.NetworkTimeOut) null);
        d.a().i("upload file response == %s", httpPost);
        return this.f.fromJson(httpPost);
    }

    private String l() {
        if (this.j == null || !this.j.containsKey("/log4")) {
            return this.h + "/log4";
        }
        return this.j.get("/log4") + "/log4";
    }

    public boolean a(String str, boolean z) {
        try {
            if ("none".equals(this.d.getDetailNetworkTypeForStatic())) {
                throw new IllegalStateException("network is disconnected!");
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(new KVPair("m", str));
            arrayList.add(new KVPair("t", z ? com.alipay.sdk.cons.a.e : "0"));
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(new KVPair("User-Agent", this.g));
            NetworkHelper.NetworkTimeOut networkTimeOut = new NetworkHelper.NetworkTimeOut();
            networkTimeOut.readTimout = UIMsg.m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT;
            networkTimeOut.connectionTimeout = UIMsg.m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT;
            String httpPost = this.e.httpPost(l(), arrayList, (KVPair<String>) null, arrayList2, networkTimeOut);
            d.a().i("> Upload All Log  resp: %s", httpPost);
            if (TextUtils.isEmpty(httpPost) || ((Integer) this.f.fromJson(httpPost).get("status")).intValue() == 200) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            d.a().d(th);
            return false;
        }
    }

    private String m() {
        return "http://l.mob.com/url/ShareSdkMapping.do";
    }

    public HashMap<String, Object> a(String str, ArrayList<String> arrayList, int i2, String str2) throws Throwable {
        if (!this.i) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new KVPair("key", this.a));
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            arrayList2.add(new KVPair("urls", arrayList.get(i3).toString()));
        }
        arrayList2.add(new KVPair("deviceid", this.d.getDeviceKey()));
        arrayList2.add(new KVPair("snsplat", String.valueOf(i2)));
        String d2 = d(str2);
        if (TextUtils.isEmpty(d2)) {
            return null;
        }
        arrayList2.add(new KVPair("m", d2));
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(new KVPair("User-Agent", this.g));
        NetworkHelper.NetworkTimeOut networkTimeOut = new NetworkHelper.NetworkTimeOut();
        networkTimeOut.readTimout = 5000;
        networkTimeOut.connectionTimeout = 5000;
        String httpPost = this.e.httpPost(m(), arrayList2, (KVPair<String>) null, arrayList3, networkTimeOut);
        d.a().i("> SERVER_SHORT_LINK_URL  resp: %s", httpPost);
        if (TextUtils.isEmpty(httpPost)) {
            this.i = false;
            return null;
        }
        HashMap<String, Object> fromJson = this.f.fromJson(httpPost);
        if (((Integer) fromJson.get("status")).intValue() == 200) {
            return fromJson;
        }
        return null;
    }

    private String d(String str) throws Throwable {
        boolean b2 = this.c.b();
        boolean c2 = this.c.c();
        StringBuilder sb = new StringBuilder();
        sb.append(Data.urlEncode(this.d.getPackageName(), "utf-8")).append("|");
        sb.append(Data.urlEncode(this.d.getAppVersionName(), "utf-8")).append("|");
        sb.append(Data.urlEncode(String.valueOf(60072), "utf-8")).append("|");
        sb.append(Data.urlEncode(String.valueOf(this.d.getPlatformCode()), "utf-8")).append("|");
        sb.append(Data.urlEncode(this.d.getDetailNetworkTypeForStatic(), "utf-8")).append("|");
        if (b2) {
            sb.append(Data.urlEncode(String.valueOf(this.d.getOSVersionInt()), "utf-8")).append("|");
            sb.append(Data.urlEncode(this.d.getScreenSize(), "utf-8")).append("|");
            sb.append(Data.urlEncode(this.d.getManufacturer(), "utf-8")).append("|");
            sb.append(Data.urlEncode(this.d.getModel(), "utf-8")).append("|");
            sb.append(Data.urlEncode(this.d.getCarrier(), "utf-8")).append("|");
        } else {
            sb.append("|||||");
        }
        if (c2) {
            sb.append(str);
        } else {
            sb.append(str.split("\\|")[0]);
            sb.append("|||||");
        }
        String sb2 = sb.toString();
        d.a().i("shorLinkMsg ===>>>>", sb2);
        return Base64.encodeToString(Data.AES128Encode(Data.rawMD5(String.format("%s:%s", new Object[]{this.d.getDeviceKey(), this.a})), sb2), 2);
    }

    private String n() {
        if (this.j == null || !this.j.containsKey("/snsconf")) {
            return this.h + "/snsconf";
        }
        return this.j.get("/snsconf") + "/snsconf";
    }

    public HashMap<String, Object> d() throws Throwable {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair(a.f, this.a));
        arrayList.add(new KVPair(com.alipay.sdk.packet.d.n, this.d.getDeviceKey()));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new KVPair("User-Agent", this.g));
        NetworkHelper.NetworkTimeOut networkTimeOut = new NetworkHelper.NetworkTimeOut();
        networkTimeOut.readTimout = 10000;
        networkTimeOut.connectionTimeout = 10000;
        return this.f.fromJson(this.e.httpPost(n(), arrayList, (KVPair<String>) null, arrayList2, networkTimeOut));
    }

    public void a(cn.sharesdk.framework.b.b.c cVar) throws Throwable {
        cn.sharesdk.framework.b.a.d.a(this.b, cVar.toString(), cVar.e);
    }

    public ArrayList<cn.sharesdk.framework.b.a.c> e() throws Throwable {
        ArrayList<cn.sharesdk.framework.b.a.c> a2 = cn.sharesdk.framework.b.a.d.a(this.b);
        if (a2 == null) {
            return new ArrayList<>();
        }
        return a2;
    }

    public void a(ArrayList<String> arrayList) throws Throwable {
        cn.sharesdk.framework.b.a.d.a(this.b, arrayList);
    }

    public HashMap<String, Object> f() throws Throwable {
        return this.f.fromJson(this.c.e(this.a));
    }

    public void b(HashMap<String, Object> hashMap) throws Throwable {
        this.c.a(this.a, this.f.fromHashMap(hashMap));
    }

    public HashMap<String, Object> c(String str) throws Throwable {
        return this.f.fromJson(new String(Data.AES128Decode(Data.rawMD5(this.a + ":" + this.d.getDeviceKey()), Base64.decode(str, 2)), "UTF-8").trim());
    }
}
