package cn.sharesdk.framework.utils;

import android.util.Base64;
import com.mob.tools.network.KVPair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: Oauth1Signer */
public class a {
    private b a = new b();
    private b b = new b("-._~", false);

    /* renamed from: cn.sharesdk.framework.utils.a$a  reason: collision with other inner class name */
    /* compiled from: Oauth1Signer */
    public enum C0000a {
        HMAC_SHA1,
        PLAINTEXT
    }

    /* compiled from: Oauth1Signer */
    public static class b {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
    }

    public void a(String str, String str2, String str3) {
        this.a.a = str;
        this.a.b = str2;
        this.a.e = str3;
    }

    public b a() {
        return this.a;
    }

    public ArrayList<KVPair<String>> a(String str, ArrayList<KVPair<String>> arrayList) throws Throwable {
        return a(str, arrayList, C0000a.HMAC_SHA1);
    }

    public ArrayList<KVPair<String>> a(String str, ArrayList<KVPair<String>> arrayList, C0000a aVar) throws Throwable {
        return a(str, "POST", arrayList, aVar);
    }

    public ArrayList<KVPair<String>> b(String str, ArrayList<KVPair<String>> arrayList) throws Throwable {
        return b(str, arrayList, C0000a.HMAC_SHA1);
    }

    public ArrayList<KVPair<String>> b(String str, ArrayList<KVPair<String>> arrayList, C0000a aVar) throws Throwable {
        return a(str, "GET", arrayList, aVar);
    }

    public ArrayList<KVPair<String>> c(String str, ArrayList<KVPair<String>> arrayList, C0000a aVar) throws Throwable {
        return a(str, "PUT", arrayList, aVar);
    }

    public void a(String str, String str2) {
        this.a.c = str;
        this.a.d = str2;
    }

    private ArrayList<KVPair<String>> a(String str, String str2, ArrayList<KVPair<String>> arrayList, C0000a aVar) throws Throwable {
        String str3;
        String str4 = null;
        long currentTimeMillis = System.currentTimeMillis();
        switch (aVar) {
            case HMAC_SHA1:
                str4 = "HMAC-SHA1";
                SecretKeySpec secretKeySpec = new SecretKeySpec((a(this.a.b) + '&' + a(this.a.d)).getBytes("utf-8"), "HMAC-SHA1");
                Mac instance = Mac.getInstance("HMAC-SHA1");
                instance.init(secretKeySpec);
                str3 = new String(Base64.encode(instance.doFinal((str2 + '&' + a(str) + '&' + a(b(a(currentTimeMillis, arrayList, str4)))).getBytes("utf-8")), 0)).trim();
                break;
            case PLAINTEXT:
                str4 = "PLAINTEXT";
                str3 = a(this.a.b) + '&' + a(this.a.d);
                break;
            default:
                str3 = null;
                break;
        }
        ArrayList<KVPair<String>> a2 = a(currentTimeMillis, str4);
        a2.add(new KVPair("oauth_signature", str3));
        return a2;
    }

    public String a(String str) {
        if (str == null) {
            return "";
        }
        return this.b.escape(str);
    }

    private ArrayList<KVPair<String>> a(long j, ArrayList<KVPair<String>> arrayList, String str) {
        HashMap hashMap = new HashMap();
        if (arrayList != null) {
            Iterator<KVPair<String>> it = arrayList.iterator();
            while (it.hasNext()) {
                KVPair next = it.next();
                hashMap.put(a(next.name), a((String) next.value));
            }
        }
        ArrayList<KVPair<String>> a2 = a(j, str);
        if (a2 != null) {
            Iterator<KVPair<String>> it2 = a2.iterator();
            while (it2.hasNext()) {
                KVPair next2 = it2.next();
                hashMap.put(a(next2.name), a((String) next2.value));
            }
        }
        String[] strArr = new String[hashMap.size()];
        int i = 0;
        for (Map.Entry key : hashMap.entrySet()) {
            strArr[i] = (String) key.getKey();
            i++;
        }
        Arrays.sort(strArr);
        ArrayList<KVPair<String>> arrayList2 = new ArrayList<>();
        for (String str2 : strArr) {
            arrayList2.add(new KVPair(str2, hashMap.get(str2)));
        }
        return arrayList2;
    }

    private String b(ArrayList<KVPair<String>> arrayList) {
        if (arrayList == null || arrayList.size() <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        Iterator<KVPair<String>> it = arrayList.iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return sb.toString();
            }
            KVPair next = it.next();
            if (i2 > 0) {
                sb.append('&');
            }
            sb.append(next.name).append('=').append((String) next.value);
            i = i2 + 1;
        }
    }

    private ArrayList<KVPair<String>> a(long j, String str) {
        ArrayList<KVPair<String>> arrayList = new ArrayList<>();
        arrayList.add(new KVPair("oauth_consumer_key", this.a.a));
        arrayList.add(new KVPair("oauth_signature_method", str));
        arrayList.add(new KVPair("oauth_timestamp", String.valueOf(j / 1000)));
        arrayList.add(new KVPair("oauth_nonce", String.valueOf(j)));
        arrayList.add(new KVPair("oauth_version", "1.0"));
        String str2 = this.a.c;
        if (str2 != null && str2.length() > 0) {
            arrayList.add(new KVPair("oauth_token", str2));
        }
        return arrayList;
    }

    public ArrayList<KVPair<String>> a(ArrayList<KVPair<String>> arrayList) {
        StringBuilder sb = new StringBuilder("OAuth ");
        int i = 0;
        Iterator<KVPair<String>> it = arrayList.iterator();
        while (true) {
            int i2 = i;
            if (it.hasNext()) {
                KVPair next = it.next();
                if (i2 > 0) {
                    sb.append(',');
                }
                sb.append(next.name).append("=\"").append(a((String) next.value)).append("\"");
                i = i2 + 1;
            } else {
                ArrayList<KVPair<String>> arrayList2 = new ArrayList<>();
                arrayList2.add(new KVPair("Authorization", sb.toString()));
                arrayList2.add(new KVPair("Content-Type", "application/x-www-form-urlencoded"));
                return arrayList2;
            }
        }
    }
}
