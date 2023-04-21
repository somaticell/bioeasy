package com.baidu.mapapi.map;

import android.view.View;
import com.baidu.platform.comapi.map.ae;

class w implements View.OnClickListener {
    final /* synthetic */ WearMapView a;

    w(WearMapView wearMapView) {
        this.a = wearMapView;
    }

    public void onClick(View view) {
        ae E = this.a.e.a().E();
        E.a += 1.0f;
        this.a.e.a().a(E, 300);
    }
}
