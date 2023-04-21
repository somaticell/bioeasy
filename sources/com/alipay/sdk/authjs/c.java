package com.alipay.sdk.authjs;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;
import com.alipay.sdk.authjs.a;
import com.facebook.common.util.UriUtil;
import java.util.Timer;
import org.json.JSONException;
import org.json.JSONObject;

public final class c {
    /* access modifiers changed from: package-private */
    public b a;
    Context b;

    private static /* synthetic */ int a(c cVar, a aVar) {
        if (aVar != null && "toast".equals(aVar.k)) {
            JSONObject jSONObject = aVar.m;
            String optString = jSONObject.optString(UriUtil.LOCAL_CONTENT_SCHEME);
            int optInt = jSONObject.optInt("duration");
            int i = 1;
            if (optInt < 2500) {
                i = 0;
            }
            Toast.makeText(cVar.b, optString, i).show();
            new Timer().schedule(new e(cVar, aVar), (long) i);
        }
        return a.C0001a.a;
    }

    public c(Context context, b bVar) {
        this.b = context;
        this.a = bVar;
    }

    private void a(String str) {
        String str2;
        JSONObject jSONObject;
        try {
            JSONObject jSONObject2 = new JSONObject(str);
            String string = jSONObject2.getString(a.e);
            try {
                if (!TextUtils.isEmpty(string)) {
                    JSONObject jSONObject3 = jSONObject2.getJSONObject(a.f);
                    if (jSONObject3 instanceof JSONObject) {
                        jSONObject = jSONObject3;
                    } else {
                        jSONObject = null;
                    }
                    String string2 = jSONObject2.getString(a.g);
                    String string3 = jSONObject2.getString(a.d);
                    a aVar = new a("call");
                    aVar.j = string3;
                    aVar.k = string2;
                    aVar.m = jSONObject;
                    aVar.i = string;
                    a(aVar);
                }
            } catch (Exception e) {
                str2 = string;
                if (!TextUtils.isEmpty(str2)) {
                    try {
                        a(str2, a.C0001a.d);
                    } catch (JSONException e2) {
                    }
                }
            }
        } catch (Exception e3) {
            str2 = null;
        }
    }

    public final void a(a aVar) throws JSONException {
        if (TextUtils.isEmpty(aVar.k)) {
            a(aVar.i, a.C0001a.c);
            return;
        }
        d dVar = new d(this, aVar);
        if (Looper.getMainLooper() == Looper.myLooper()) {
            dVar.run();
        } else {
            new Handler(Looper.getMainLooper()).post(dVar);
        }
    }

    public final void a(String str, int i) throws JSONException {
        if (!TextUtils.isEmpty(str)) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("error", i - 1);
            a aVar = new a(a.c);
            aVar.m = jSONObject;
            aVar.i = str;
            this.a.a(aVar);
        }
    }

    private static void a(Runnable runnable) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            runnable.run();
        } else {
            new Handler(Looper.getMainLooper()).post(runnable);
        }
    }

    private int b(a aVar) {
        if (aVar != null && "toast".equals(aVar.k)) {
            JSONObject jSONObject = aVar.m;
            String optString = jSONObject.optString(UriUtil.LOCAL_CONTENT_SCHEME);
            int optInt = jSONObject.optInt("duration");
            int i = 1;
            if (optInt < 2500) {
                i = 0;
            }
            Toast.makeText(this.b, optString, i).show();
            new Timer().schedule(new e(this, aVar), (long) i);
        }
        return a.C0001a.a;
    }

    private void c(a aVar) {
        JSONObject jSONObject = aVar.m;
        String optString = jSONObject.optString(UriUtil.LOCAL_CONTENT_SCHEME);
        int optInt = jSONObject.optInt("duration");
        int i = 1;
        if (optInt < 2500) {
            i = 0;
        }
        Toast.makeText(this.b, optString, i).show();
        new Timer().schedule(new e(this, aVar), (long) i);
    }
}
