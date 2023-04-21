package com.baidu.mapapi.map;

import android.content.Context;
import android.os.Bundle;
import com.baidu.platform.comapi.map.al;

class d implements al {
    final /* synthetic */ BaiduMap a;

    d(BaiduMap baiduMap) {
        this.a = baiduMap;
    }

    public Bundle a(int i, int i2, int i3, Context context) {
        Tile a2;
        this.a.F.lock();
        try {
            if (this.a.C != null && (a2 = this.a.C.a(i, i2, i3)) != null) {
                return a2.toBundle();
            }
            this.a.F.unlock();
            return null;
        } finally {
            this.a.F.unlock();
        }
    }
}
