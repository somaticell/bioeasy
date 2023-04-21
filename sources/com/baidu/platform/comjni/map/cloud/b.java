package com.baidu.platform.comjni.map.cloud;

import com.baidu.mapapi.http.HttpClient;

class b extends HttpClient.ProtoResultCallback {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    public void onFailed(HttpClient.HttpStateError httpStateError) {
        if (httpStateError == HttpClient.HttpStateError.NETWORK_ERROR) {
            this.a.a(-3);
        } else {
            this.a.a(1);
        }
    }

    public void onSuccess(String str) {
        String unused = this.a.h = str;
        if (this.a.a()) {
            this.a.f(str);
        } else {
            this.a.g.post(new c(this, str));
        }
    }
}
