package com.baidu.platform.comjni.map.radar;

import android.util.Log;
import com.baidu.mapapi.http.HttpClient;

class b extends HttpClient.ProtoResultCallback {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    public void onFailed(HttpClient.HttpStateError httpStateError) {
        Log.d("newsearch", "---network error: " + httpStateError);
    }

    public void onSuccess(String str) {
        String unused = this.a.g = str;
        if (!this.a.a()) {
            this.a.f.post(new c(this, str));
        } else if (this.a.a != null) {
            this.a.a.c(str);
        }
    }
}
