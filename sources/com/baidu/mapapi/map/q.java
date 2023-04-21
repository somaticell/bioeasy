package com.baidu.mapapi.map;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.ae;
import com.baidu.platform.comapi.map.l;
import javax.microedition.khronos.opengles.GL10;

class q implements l {
    final /* synthetic */ TextureMapView a;

    q(TextureMapView textureMapView) {
        this.a = textureMapView;
    }

    public void a() {
        String format;
        if (this.a.b != null && this.a.b.b() != null) {
            float f = this.a.b.b().E().a;
            if (this.a.q != f) {
                int intValue = ((Integer) TextureMapView.p.get((int) f)).intValue();
                int i = (int) (((double) intValue) / this.a.b.b().E().m);
                this.a.n.setPadding(i / 2, 0, i / 2, 0);
                if (intValue >= 1000) {
                    format = String.format(" %d公里 ", new Object[]{Integer.valueOf(intValue / 1000)});
                } else {
                    format = String.format(" %d米 ", new Object[]{Integer.valueOf(intValue)});
                }
                this.a.l.setText(format);
                this.a.m.setText(format);
                float unused = this.a.q = f;
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
