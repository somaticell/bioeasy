package com.baidu.android.bbalbs.common.util;

import com.baidu.android.bbalbs.common.util.b;
import java.util.Comparator;

class c implements Comparator<b.a> {
    final /* synthetic */ b a;

    c(b bVar) {
        this.a = bVar;
    }

    /* renamed from: a */
    public int compare(b.a aVar, b.a aVar2) {
        int i = aVar2.b - aVar.b;
        if (i != 0) {
            return i;
        }
        if (aVar.d && aVar2.d) {
            return 0;
        }
        if (aVar.d) {
            return -1;
        }
        if (aVar2.d) {
            return 1;
        }
        return i;
    }
}
