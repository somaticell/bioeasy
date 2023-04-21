package com.baidu.mapapi.map;

import android.view.View;
import com.baidu.platform.comapi.map.ae;

class k implements View.OnClickListener {
    final /* synthetic */ MapView a;

    k(MapView mapView) {
        this.a = mapView;
    }

    public void onClick(View view) {
        ae E = this.a.d.a().E();
        E.a += 1.0f;
        BaiduMap.mapStatusReason |= 16;
        this.a.d.a().a(E, 300);
    }
}
