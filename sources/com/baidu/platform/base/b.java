package com.baidu.platform.base;

import com.baidu.mapapi.http.HttpClient;

class b extends HttpClient.ProtoResultCallback {
    final /* synthetic */ d a;
    final /* synthetic */ Object b;
    final /* synthetic */ a c;

    b(a aVar, d dVar, Object obj) {
        this.c = aVar;
        this.a = dVar;
        this.b = obj;
    }

    public void onFailed(HttpClient.HttpStateError httpStateError) {
        this.c.a(httpStateError, this.a, this.b);
    }

    public void onSuccess(String str) {
        this.c.a(str);
        this.c.a(str, this.a, this.b, this.c.b, this);
    }
}
