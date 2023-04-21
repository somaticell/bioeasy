package com.loc;

import java.io.IOException;

/* compiled from: OfflineFileCache */
public class bn extends bo<String, bi> {
    public bn() {
        super(1048576);
    }

    /* access modifiers changed from: protected */
    public int a(String str, bi biVar) {
        if (biVar == null) {
            return 0;
        }
        try {
            return (int) biVar.g();
        } catch (IOException e) {
            return 0;
        }
    }

    /* access modifiers changed from: protected */
    public void a(boolean z, String str, bi biVar, bi biVar2) {
        if (biVar != null) {
            try {
                biVar.b();
            } catch (IOException e) {
            }
        }
        super.a(z, str, biVar, biVar2);
    }
}
