package com.baidu.mapapi.map;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.ae;
import com.baidu.platform.comapi.map.l;
import javax.microedition.khronos.opengles.GL10;

class i implements l {
    final /* synthetic */ MapView a;

    i(MapView mapView) {
        this.a = mapView;
    }

    public void a() {
        String format;
        if (this.a.d != null && this.a.d.a() != null) {
            float f = this.a.d.a().E().a;
            if (this.a.t != f) {
                int intValue = ((Integer) MapView.p.get((int) f)).intValue();
                int i = (int) (((double) intValue) / this.a.d.a().E().m);
                this.a.n.setPadding(i / 2, 0, i / 2, 0);
                if (intValue >= 1000) {
                    format = String.format(" %d公里 ", new Object[]{Integer.valueOf(intValue / 1000)});
                } else {
                    format = String.format(" %d米 ", new Object[]{Integer.valueOf(intValue)});
                }
                this.a.l.setText(format);
                this.a.m.setText(format);
                float unused = this.a.t = f;
            }
            this.a.b();
            this.a.requestLayout();
        }
    }

    public void a(Bitmap bitmap) {
    }

    public void a(MotionEvent motionEvent) {
    }

    public void a(GeoPoint geoPoint) {
    }

    public void a(ae aeVar) {
    }

    public void a(String str) {
    }

    public void a(GL10 gl10, ae aeVar) {
    }

    public void a(boolean z) {
    }

    public void b() {
    }

    public void b(GeoPoint geoPoint) {
    }

    public void b(ae aeVar) {
    }

    public boolean b(String str) {
        return false;
    }

    public void c() {
    }

    public void c(GeoPoint geoPoint) {
    }

    public void c(ae aeVar) {
    }

    public void d() {
    }

    public void d(GeoPoint geoPoint) {
    }

    public void e() {
    }

    public void e(GeoPoint geoPoint) {
    }

    public void f() {
    }
}
