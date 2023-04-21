package com.alipay.sdk.authjs;

import android.widget.Toast;
import com.alipay.sdk.authjs.a;
import com.facebook.common.util.UriUtil;
import java.util.Timer;
import org.json.JSONException;
import org.json.JSONObject;

final class d implements Runnable {
    final /* synthetic */ a a;
    final /* synthetic */ c b;

    d(c cVar, a aVar) {
        this.b = cVar;
        this.a = aVar;
    }

    public final void run() {
        c cVar = this.b;
        a aVar = this.a;
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
        int i2 = a.C0001a.a;
        if (i2 != a.C0001a.a) {
            try {
                this.b.a(this.a.i, i2);
            } catch (JSONException e) {
            }
        }
    }
}
