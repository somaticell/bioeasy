package com.baidu.lbsapi.auth;

import com.baidu.lbsapi.auth.c;

class j implements c.a<String> {
    final /* synthetic */ String a;
    final /* synthetic */ LBSAuthManager b;

    j(LBSAuthManager lBSAuthManager, String str) {
        this.b = lBSAuthManager;
        this.a = str;
    }

    public void a(String str) {
        this.b.a(str, this.a);
    }
}
