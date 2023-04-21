package com.baidu.platform.comjni.map.radar;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.baidu.mapapi.http.AsyncHttpClient;
import com.baidu.mapapi.http.HttpClient;
import com.baidu.platform.comapi.radar.b;
import com.baidu.platform.comjni.util.AppMD5;
import java.util.LinkedList;
import java.util.Queue;

public class a implements IRadarCenter {
    b a;
    private boolean b = true;
    private boolean c = true;
    private AsyncHttpClient d = new AsyncHttpClient();
    private AsyncHttpClient e = new AsyncHttpClient();
    /* access modifiers changed from: private */
    public Handler f = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public String g;
    private final int h = 5;
    private Queue<String> i = new LinkedList();

    /* access modifiers changed from: private */
    public boolean a() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    private String c(String str) {
        String authToken;
        if (str == null || (authToken = HttpClient.getAuthToken()) == null) {
            return null;
        }
        if (this.b) {
            str = str + "&token=" + AppMD5.encodeUrlParamsValue(authToken);
        }
        String str2 = str + HttpClient.getPhoneInfo();
        if (!this.c) {
            return str2;
        }
        return str2 + "&sign=" + AppMD5.getSignMD5String(Uri.parse(str2).buildUpon().build().getEncodedQuery());
    }

    private void d(String str) {
        if (this.i == null) {
            this.i = new LinkedList();
        }
        if (this.i.size() >= 5) {
            this.i.poll();
        }
        this.i.offer(str);
    }

    public void a(b bVar) {
        this.a = bVar;
    }

    public boolean a(String str) {
        String c2 = c(str);
        if (c2 == null) {
            return false;
        }
        d(c2);
        if (this.i == null || this.i.size() <= 0) {
            return false;
        }
        this.e.get(this.i.poll(), new b(this));
        return true;
    }

    public boolean b(String str) {
        String c2 = c(str);
        if (c2 == null) {
            return false;
        }
        Log.d("newsearch", " send Request str: " + c2);
        this.d.get(c2, new d(this));
        return true;
    }
}
