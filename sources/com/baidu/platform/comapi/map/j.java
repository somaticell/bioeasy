package com.baidu.platform.comapi.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.baidu.mapapi.common.EnvironmentUtilities;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.MapRenderer;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

@SuppressLint({"NewApi"})
public class j extends GLSurfaceView implements GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener, MapRenderer.a {
    private static final String a = j.class.getSimpleName();
    private Handler b;
    private MapRenderer c;
    /* access modifiers changed from: private */
    public int d;
    /* access modifiers changed from: private */
    public int e;
    private GestureDetector f;
    /* access modifiers changed from: private */
    public e g;

    static class a {
        float a;
        float b;
        float c;
        float d;
        boolean e;
        float f;
        float g;
        double h;

        a() {
        }

        public String toString() {
            return "MultiTouch{x1=" + this.a + ", x2=" + this.b + ", y1=" + this.c + ", y2=" + this.d + ", mTwoTouch=" + this.e + ", centerX=" + this.f + ", centerY=" + this.g + ", length=" + this.h + '}';
        }
    }

    public j(Context context, ac acVar, String str, int i) {
        super(context);
        if (context == null) {
            throw new RuntimeException("when you create an mapview, the context can not be null");
        }
        setEGLContextClientVersion(2);
        this.f = new GestureDetector(context, this);
        EnvironmentUtilities.initAppDirectory(context);
        if (this.g == null) {
            this.g = new e(context, str, i);
        }
        this.g.a(context.hashCode());
        g();
        this.g.a();
        this.g.a(acVar);
        h();
        this.g.a(this.b);
        this.g.f();
        setBackgroundColor(0);
    }

    private static boolean a(int i, int i2, int i3, int i4, int i5, int i6) {
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        EGLDisplay eglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        egl10.eglInitialize(eglGetDisplay, new int[2]);
        int[] iArr = new int[1];
        return egl10.eglChooseConfig(eglGetDisplay, new int[]{12324, i, 12323, i2, 12322, i3, 12321, i4, 12325, i5, 12326, i6, 12344}, new EGLConfig[100], 100, iArr) && iArr[0] > 0;
    }

    private void g() {
        try {
            if (a(8, 8, 8, 8, 24, 0)) {
                setEGLConfigChooser(8, 8, 8, 8, 24, 0);
            } else if (a(5, 6, 5, 0, 24, 0)) {
                setEGLConfigChooser(5, 6, 5, 0, 24, 0);
            } else {
                setEGLConfigChooser(true);
            }
        } catch (IllegalArgumentException e2) {
            setEGLConfigChooser(true);
        }
        this.c = new MapRenderer(this, this);
        this.c.a(this.g.h);
        setRenderer(this.c);
        setRenderMode(1);
    }

    private void h() {
        this.b = new k(this);
    }

    public e a() {
        return this.g;
    }

    public void a(float f2, float f3) {
        if (this.g != null && this.g.g != null) {
            this.g.b(f2, f3);
        }
    }

    public void a(int i) {
        if (this.g != null) {
            Message message = new Message();
            message.what = 50;
            message.obj = Long.valueOf(this.g.h);
            boolean q = this.g.q();
            if (i == 3) {
                message.arg1 = 0;
            } else if (q) {
                message.arg1 = 1;
            }
            this.b.sendMessage(message);
        }
    }

    public void a(String str, Rect rect) {
        if (this.g != null && this.g.g != null) {
            if (rect != null) {
                int i = rect.left;
                int i2 = this.e < rect.bottom ? 0 : this.e - rect.bottom;
                int width = rect.width();
                int height = rect.height();
                if (i >= 0 && i2 >= 0 && width > 0 && height > 0) {
                    if (width > this.d) {
                        width = Math.abs(rect.width()) - (rect.right - this.d);
                    }
                    if (height > this.e) {
                        height = Math.abs(rect.height()) - (rect.bottom - this.e);
                    }
                    if (i > SysOSUtil.getScreenSizeX() || i2 > SysOSUtil.getScreenSizeY()) {
                        this.g.g.a(str, (Bundle) null);
                        requestRender();
                        return;
                    }
                    this.d = width;
                    this.e = height;
                    Bundle bundle = new Bundle();
                    bundle.putInt("x", i);
                    bundle.putInt("y", i2);
                    bundle.putInt("width", width);
                    bundle.putInt("height", height);
                    this.g.g.a(str, bundle);
                    requestRender();
                    return;
                }
                return;
            }
            this.g.g.a(str, (Bundle) null);
            requestRender();
        }
    }

    public boolean a(float f2, float f3, float f4, float f5) {
        if (this.g == null || this.g.g == null) {
            return false;
        }
        return this.g.a(f2, f3, f4, f5);
    }

    public void b() {
        if (this.g != null) {
            this.g.u();
        }
    }

    public void b(int i) {
        if (this.g != null) {
            for (l f2 : this.g.f) {
                f2.f();
            }
            this.g.b(this.b);
            this.g.b(i);
            this.g = null;
        }
    }

    public boolean b(float f2, float f3) {
        if (this.g == null || this.g.g == null) {
            return false;
        }
        return this.g.d(f2, f3);
    }

    public void c() {
        if (this.g != null) {
            this.g.v();
        }
    }

    public boolean c(float f2, float f3) {
        if (this.g == null || this.g.g == null) {
            return false;
        }
        return this.g.c(f2, f3);
    }

    public void d() {
        getHolder().setFormat(-3);
        this.g.g.s();
    }

    public boolean d(float f2, float f3) {
        if (this.g == null || this.g.g == null) {
            return false;
        }
        return this.g.c((int) f2, (int) f3);
    }

    public void e() {
        getHolder().setFormat(-1);
        this.g.g.t();
    }

    public void f() {
    }

    public boolean onDoubleTap(MotionEvent motionEvent) {
        if (this.g == null || this.g.g == null || !this.g.i) {
            return true;
        }
        GeoPoint b2 = this.g.b((int) motionEvent.getX(), (int) motionEvent.getY());
        if (b2 == null) {
            return false;
        }
        for (l b3 : this.g.f) {
            b3.b(b2);
        }
        if (!this.g.e) {
            return false;
        }
        ae E = this.g.E();
        E.a += 1.0f;
        E.d = b2.getLongitudeE6();
        E.e = b2.getLatitudeE6();
        BaiduMap.mapStatusReason |= 1;
        this.g.a(E, 300);
        e eVar = this.g;
        e.k = System.currentTimeMillis();
        return true;
    }

    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
        if (this.g == null || this.g.g == null || !this.g.i) {
            return true;
        }
        if (!this.g.d) {
            return false;
        }
        float sqrt = (float) Math.sqrt((double) ((f2 * f2) + (f3 * f3)));
        if (sqrt <= 500.0f) {
            return false;
        }
        BaiduMap.mapStatusReason |= 1;
        this.g.A();
        this.g.a(34, (int) (sqrt * 0.6f), (((int) motionEvent2.getY()) << 16) | ((int) motionEvent2.getX()));
        this.g.M();
        return true;
    }

    public void onLongPress(MotionEvent motionEvent) {
        if (this.g != null && this.g.g != null && this.g.i) {
            String a2 = this.g.g.a(-1, (int) motionEvent.getX(), (int) motionEvent.getY(), this.g.j);
            if (a2 == null || a2.equals("")) {
                for (l c2 : this.g.f) {
                    c2.c(this.g.b((int) motionEvent.getX(), (int) motionEvent.getY()));
                }
                return;
            }
            for (l next : this.g.f) {
                if (next.b(a2)) {
                    this.g.n = true;
                } else {
                    next.c(this.g.b((int) motionEvent.getX(), (int) motionEvent.getY()));
                }
            }
        }
    }

    public void onPause() {
        super.onPause();
        if (this.g != null && this.g.g != null) {
            this.g.g.c();
        }
    }

    public void onResume() {
        super.onResume();
        if (this.g != null && this.g.g != null) {
            for (l d2 : this.g.f) {
                d2.d();
            }
            this.g.g.g();
            this.g.g.d();
            this.g.g.n();
            setRenderMode(1);
        }
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
        return false;
    }

    public void onShowPress(MotionEvent motionEvent) {
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x005c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onSingleTapConfirmed(android.view.MotionEvent r8) {
        /*
            r7 = this;
            r5 = 1
            com.baidu.platform.comapi.map.e r0 = r7.g
            if (r0 == 0) goto L_0x0011
            com.baidu.platform.comapi.map.e r0 = r7.g
            com.baidu.platform.comjni.map.basemap.a r0 = r0.g
            if (r0 == 0) goto L_0x0011
            com.baidu.platform.comapi.map.e r0 = r7.g
            boolean r0 = r0.i
            if (r0 != 0) goto L_0x0012
        L_0x0011:
            return r5
        L_0x0012:
            com.baidu.platform.comapi.map.e r0 = r7.g
            com.baidu.platform.comjni.map.basemap.a r0 = r0.g
            r1 = -1
            float r2 = r8.getX()
            int r2 = (int) r2
            float r3 = r8.getY()
            int r3 = (int) r3
            com.baidu.platform.comapi.map.e r4 = r7.g
            int r4 = r4.j
            java.lang.String r2 = r0.a((int) r1, (int) r2, (int) r3, (int) r4)
            r1 = 0
            if (r2 == 0) goto L_0x0075
            java.lang.String r0 = ""
            boolean r0 = r2.equals(r0)
            if (r0 != 0) goto L_0x0075
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x006c }
            r0.<init>(r2)     // Catch:{ JSONException -> 0x006c }
            java.lang.String r1 = "px"
            float r2 = r8.getX()     // Catch:{ JSONException -> 0x009d }
            int r2 = (int) r2     // Catch:{ JSONException -> 0x009d }
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x009d }
            java.lang.String r1 = "py"
            float r2 = r8.getY()     // Catch:{ JSONException -> 0x009d }
            int r2 = (int) r2     // Catch:{ JSONException -> 0x009d }
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x009d }
            r1 = r0
        L_0x004e:
            com.baidu.platform.comapi.map.e r0 = r7.g
            java.util.List<com.baidu.platform.comapi.map.l> r0 = r0.f
            java.util.Iterator r2 = r0.iterator()
        L_0x0056:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L_0x0011
            java.lang.Object r0 = r2.next()
            com.baidu.platform.comapi.map.l r0 = (com.baidu.platform.comapi.map.l) r0
            if (r1 == 0) goto L_0x0056
            java.lang.String r3 = r1.toString()
            r0.a((java.lang.String) r3)
            goto L_0x0056
        L_0x006c:
            r0 = move-exception
            r6 = r0
            r0 = r1
            r1 = r6
        L_0x0070:
            r1.printStackTrace()
            r1 = r0
            goto L_0x004e
        L_0x0075:
            com.baidu.platform.comapi.map.e r0 = r7.g
            java.util.List<com.baidu.platform.comapi.map.l> r0 = r0.f
            java.util.Iterator r1 = r0.iterator()
        L_0x007d:
            boolean r0 = r1.hasNext()
            if (r0 == 0) goto L_0x0011
            java.lang.Object r0 = r1.next()
            com.baidu.platform.comapi.map.l r0 = (com.baidu.platform.comapi.map.l) r0
            com.baidu.platform.comapi.map.e r2 = r7.g
            float r3 = r8.getX()
            int r3 = (int) r3
            float r4 = r8.getY()
            int r4 = (int) r4
            com.baidu.mapapi.model.inner.GeoPoint r2 = r2.b((int) r3, (int) r4)
            r0.a((com.baidu.mapapi.model.inner.GeoPoint) r2)
            goto L_0x007d
        L_0x009d:
            r1 = move-exception
            goto L_0x0070
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.comapi.map.j.onSingleTapConfirmed(android.view.MotionEvent):boolean");
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.g == null || this.g.g == null) {
            return true;
        }
        super.onTouchEvent(motionEvent);
        for (l a2 : this.g.f) {
            a2.a(motionEvent);
        }
        if (this.f.onTouchEvent(motionEvent)) {
            return true;
        }
        return this.g.a(motionEvent);
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        super.surfaceChanged(surfaceHolder, i, i2, i3);
        if (this.g != null && this.g.g != null) {
            this.c.a = i2;
            this.c.b = i3;
            this.d = i2;
            this.e = i3;
            this.c.c = 0;
            ae E = this.g.E();
            if (E.f == 0 || E.f == -1 || E.f == (E.j.left - E.j.right) / 2) {
                E.f = -1;
            }
            if (E.g == 0 || E.g == -1 || E.g == (E.j.bottom - E.j.top) / 2) {
                E.g = -1;
            }
            E.j.left = 0;
            E.j.top = 0;
            E.j.bottom = i3;
            E.j.right = i2;
            this.g.a(E);
            this.g.a(this.d, this.e);
        }
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        super.surfaceCreated(surfaceHolder);
        if (surfaceHolder != null && !surfaceHolder.getSurface().isValid()) {
            surfaceDestroyed(surfaceHolder);
        }
    }
}
