package com.baidu.platform.comapi.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.TextureView;
import com.baidu.mapapi.common.EnvironmentUtilities;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.m;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressLint({"NewApi"})
public class af extends TextureView implements GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener, TextureView.SurfaceTextureListener, m.a {
    public static int a;
    public static int b;
    private GestureDetector c;
    private Handler d;
    private boolean e = false;
    private SurfaceTexture f;
    /* access modifiers changed from: private */
    public m g = null;
    /* access modifiers changed from: private */
    public e h;

    public af(Context context, ac acVar, String str, int i) {
        super(context);
        a(context, acVar, str, i);
    }

    private void a(Context context, ac acVar, String str, int i) {
        setSurfaceTextureListener(this);
        if (context == null) {
            throw new RuntimeException("when you create an mapview, the context can not be null");
        }
        this.c = new GestureDetector(context, this);
        EnvironmentUtilities.initAppDirectory(context);
        if (this.h == null) {
            this.h = new e(context, str, i);
        }
        this.h.a(context.hashCode());
        this.h.a();
        this.h.a(acVar);
        e();
        this.h.a(this.d);
        this.h.f();
    }

    private void e() {
        this.d = new ag(this);
    }

    public int a() {
        if (this.h == null) {
            return 0;
        }
        return MapRenderer.nativeRender(this.h.h);
    }

    public void a(int i) {
        synchronized (this.h) {
            for (l f2 : this.h.f) {
                f2.f();
            }
            if (this.h != null) {
                this.h.b(this.d);
                this.h.b(i);
                this.h = null;
            }
            this.d.removeCallbacksAndMessages((Object) null);
            if (this.g != null) {
                this.g.c();
                this.g = null;
            }
            if (this.f != null) {
                this.f.release();
                this.f = null;
            }
        }
    }

    public void a(String str, Rect rect) {
        if (this.h != null && this.h.g != null) {
            if (rect != null) {
                int i = rect.left;
                int i2 = b < rect.bottom ? 0 : b - rect.bottom;
                int width = rect.width();
                int height = rect.height();
                if (i >= 0 && i2 >= 0 && width > 0 && height > 0) {
                    if (width > a) {
                        width = Math.abs(rect.width()) - (rect.right - a);
                    }
                    if (height > b) {
                        height = Math.abs(rect.height()) - (rect.bottom - b);
                    }
                    if (i > SysOSUtil.getScreenSizeX() || i2 > SysOSUtil.getScreenSizeY()) {
                        this.h.g.a(str, (Bundle) null);
                        if (this.g != null) {
                            this.g.a();
                            return;
                        }
                        return;
                    }
                    a = width;
                    b = height;
                    Bundle bundle = new Bundle();
                    bundle.putInt("x", i);
                    bundle.putInt("y", i2);
                    bundle.putInt("width", width);
                    bundle.putInt("height", height);
                    this.h.g.a(str, bundle);
                    if (this.g != null) {
                        this.g.a();
                        return;
                    }
                    return;
                }
                return;
            }
            this.h.g.a(str, (Bundle) null);
            if (this.g != null) {
                this.g.a();
            }
        }
    }

    public e b() {
        return this.h;
    }

    public void c() {
        if (this.h != null && this.h.g != null) {
            for (l d2 : this.h.f) {
                d2.d();
            }
            this.h.g.g();
            this.h.g.d();
            this.h.g.n();
            if (this.g != null) {
                this.g.a();
            }
            if (this.h.b()) {
                this.e = true;
            }
        }
    }

    public void d() {
        if (this.h != null && this.h.g != null) {
            this.e = false;
            this.h.g.c();
            synchronized (this.h) {
                this.h.g.c();
                if (this.g != null) {
                    this.g.b();
                }
            }
        }
    }

    public boolean onDoubleTap(MotionEvent motionEvent) {
        if (this.h == null || this.h.g == null || !this.h.i) {
            return true;
        }
        GeoPoint b2 = this.h.b((int) motionEvent.getX(), (int) motionEvent.getY());
        if (b2 == null) {
            return false;
        }
        for (l b3 : this.h.f) {
            b3.b(b2);
        }
        if (!this.h.e) {
            return false;
        }
        ae E = this.h.E();
        E.a += 1.0f;
        E.d = b2.getLongitudeE6();
        E.e = b2.getLatitudeE6();
        BaiduMap.mapStatusReason |= 1;
        this.h.a(E, 300);
        e eVar = this.h;
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
        if (this.h == null || this.h.g == null || !this.h.i) {
            return true;
        }
        if (!this.h.d) {
            return false;
        }
        float sqrt = (float) Math.sqrt((double) ((f2 * f2) + (f3 * f3)));
        if (sqrt <= 500.0f) {
            return false;
        }
        BaiduMap.mapStatusReason |= 1;
        this.h.A();
        this.h.a(34, (int) (sqrt * 0.6f), (((int) motionEvent2.getY()) << 16) | ((int) motionEvent2.getX()));
        this.h.M();
        return true;
    }

    public void onLongPress(MotionEvent motionEvent) {
        if (this.h != null && this.h.g != null && this.h.i) {
            String a2 = this.h.g.a(-1, (int) motionEvent.getX(), (int) motionEvent.getY(), this.h.j);
            if (a2 == null || a2.equals("")) {
                for (l c2 : this.h.f) {
                    c2.c(this.h.b((int) motionEvent.getX(), (int) motionEvent.getY()));
                }
                return;
            }
            for (l next : this.h.f) {
                if (next.b(a2)) {
                    this.h.n = true;
                } else {
                    next.c(this.h.b((int) motionEvent.getX(), (int) motionEvent.getY()));
                }
            }
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
            com.baidu.platform.comapi.map.e r0 = r7.h
            if (r0 == 0) goto L_0x0011
            com.baidu.platform.comapi.map.e r0 = r7.h
            com.baidu.platform.comjni.map.basemap.a r0 = r0.g
            if (r0 == 0) goto L_0x0011
            com.baidu.platform.comapi.map.e r0 = r7.h
            boolean r0 = r0.i
            if (r0 != 0) goto L_0x0012
        L_0x0011:
            return r5
        L_0x0012:
            com.baidu.platform.comapi.map.e r0 = r7.h
            com.baidu.platform.comjni.map.basemap.a r0 = r0.g
            r1 = -1
            float r2 = r8.getX()
            int r2 = (int) r2
            float r3 = r8.getY()
            int r3 = (int) r3
            com.baidu.platform.comapi.map.e r4 = r7.h
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
            com.baidu.platform.comapi.map.e r0 = r7.h
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
            com.baidu.platform.comapi.map.e r0 = r7.h
            java.util.List<com.baidu.platform.comapi.map.l> r0 = r0.f
            java.util.Iterator r1 = r0.iterator()
        L_0x007d:
            boolean r0 = r1.hasNext()
            if (r0 == 0) goto L_0x0011
            java.lang.Object r0 = r1.next()
            com.baidu.platform.comapi.map.l r0 = (com.baidu.platform.comapi.map.l) r0
            com.baidu.platform.comapi.map.e r2 = r7.h
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
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.comapi.map.af.onSingleTapConfirmed(android.view.MotionEvent):boolean");
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        if (this.h != null) {
            if (this.f == null) {
                this.f = surfaceTexture;
                this.g = new m(this.f, this, new AtomicBoolean(true), this);
                this.g.start();
                a = i;
                b = i2;
                ae E = this.h.E();
                if (E != null) {
                    if (E.f == 0 || E.f == -1 || E.f == (E.j.left - E.j.right) / 2) {
                        E.f = -1;
                    }
                    if (E.g == 0 || E.g == -1 || E.g == (E.j.bottom - E.j.top) / 2) {
                        E.g = -1;
                    }
                    E.j.left = 0;
                    E.j.top = 0;
                    E.j.bottom = i2;
                    E.j.right = i;
                    this.h.a(E);
                    this.h.a(a, b);
                    return;
                }
                return;
            }
            setSurfaceTexture(this.f);
        }
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        if (this.h != null) {
            a = i;
            b = i2;
            this.h.a(a, b);
            MapRenderer.nativeResize(this.h.h, i, i2);
        }
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        if (this.e && this.g != null) {
            this.g.a();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.h == null || this.h.g == null) {
            return true;
        }
        super.onTouchEvent(motionEvent);
        for (l a2 : this.h.f) {
            a2.a(motionEvent);
        }
        if (this.c.onTouchEvent(motionEvent)) {
            return true;
        }
        return this.h.a(motionEvent);
    }
}
