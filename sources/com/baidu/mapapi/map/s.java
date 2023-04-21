package com.baidu.mapapi.map;

import android.view.View;
import com.baidu.platform.comapi.map.ae;

class s implements View.OnClickListener {
    final /* synthetic */ TextureMapView a;

    s(TextureMapView textureMapView) {
        this.a = textureMapView;
    }

    public void onClick(View view) {
        ae E = this.a.b.b().E();
        E.a += 1.0f;
        BaiduMap.mapStatusReason |= 16;
        this.a.b.b().a(E, 300);
    }
}
