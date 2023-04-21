package com.baidu.platform.comapi.map;

import android.annotation.SuppressLint;
import android.graphics.SurfaceTexture;
import android.opengl.GLUtils;
import java.lang.Thread;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

@SuppressLint({"NewApi"})
public class m extends Thread {
    private AtomicBoolean a;
    private SurfaceTexture b;
    private a c;
    private EGL10 d;
    private EGLDisplay e = EGL10.EGL_NO_DISPLAY;
    private EGLContext f = EGL10.EGL_NO_CONTEXT;
    private EGLSurface g = EGL10.EGL_NO_SURFACE;
    private GL10 h;
    private int i = 1;
    private boolean j = false;
    private final af k;

    public interface a {
        int a();
    }

    public m(SurfaceTexture surfaceTexture, a aVar, AtomicBoolean atomicBoolean, af afVar) {
        this.b = surfaceTexture;
        this.c = aVar;
        this.a = atomicBoolean;
        this.k = afVar;
    }

    private boolean a(int i2, int i3, int i4, int i5, int i6, int i7) {
        this.d = (EGL10) EGLContext.getEGL();
        this.e = this.d.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        if (this.e == EGL10.EGL_NO_DISPLAY) {
            throw new RuntimeException("eglGetdisplay failed : " + GLUtils.getEGLErrorString(this.d.eglGetError()));
        }
        if (!this.d.eglInitialize(this.e, new int[2])) {
            throw new RuntimeException("eglInitialize failed : " + GLUtils.getEGLErrorString(this.d.eglGetError()));
        }
        EGLConfig[] eGLConfigArr = new EGLConfig[100];
        int[] iArr = new int[1];
        if (!this.d.eglChooseConfig(this.e, new int[]{12352, 4, 12324, i2, 12323, i3, 12322, i4, 12321, i5, 12325, i6, 12326, i7, 12344}, eGLConfigArr, 100, iArr) || iArr[0] <= 0) {
            return false;
        }
        this.f = this.d.eglCreateContext(this.e, eGLConfigArr[0], EGL10.EGL_NO_CONTEXT, new int[]{12440, 2, 12344});
        this.g = this.d.eglCreateWindowSurface(this.e, eGLConfigArr[0], this.b, (int[]) null);
        if (this.g == EGL10.EGL_NO_SURFACE || this.f == EGL10.EGL_NO_CONTEXT) {
            if (this.d.eglGetError() == 12299) {
                throw new RuntimeException("eglCreateWindowSurface returned EGL_BAD_NATIVE_WINDOW. ");
            }
            GLUtils.getEGLErrorString(this.d.eglGetError());
        }
        if (!this.d.eglMakeCurrent(this.e, this.g, this.g, this.f)) {
            throw new RuntimeException("eglMakeCurrent failed : " + GLUtils.getEGLErrorString(this.d.eglGetError()));
        }
        this.h = (GL10) this.f.getGL();
        return true;
    }

    private static boolean b(int i2, int i3, int i4, int i5, int i6, int i7) {
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        EGLDisplay eglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        egl10.eglInitialize(eglGetDisplay, new int[2]);
        int[] iArr = new int[1];
        return egl10.eglChooseConfig(eglGetDisplay, new int[]{12324, i2, 12323, i3, 12322, i4, 12321, i5, 12325, i6, 12326, i7, 12344}, new EGLConfig[100], 100, iArr) && iArr[0] > 0;
    }

    private void d() {
        try {
            if (b(5, 6, 5, 0, 24, 0)) {
                a(8, 8, 8, 0, 24, 0);
            } else {
                a(8, 8, 8, 0, 24, 0);
            }
        } catch (IllegalArgumentException e2) {
            a(8, 8, 8, 0, 24, 0);
        }
        MapRenderer.nativeInit(this.k.b().h);
        MapRenderer.nativeResize(this.k.b().h, af.a, af.b);
    }

    private void e() {
        this.d.eglDestroyContext(this.e, this.f);
        this.d.eglDestroySurface(this.e, this.g);
        this.d.eglTerminate(this.e);
        this.f = EGL10.EGL_NO_CONTEXT;
        this.g = EGL10.EGL_NO_SURFACE;
    }

    public void a() {
        this.i = 1;
        this.j = false;
        synchronized (this) {
            if (getState() == Thread.State.WAITING) {
                notify();
            }
        }
    }

    public void b() {
        this.i = 0;
        synchronized (this) {
            this.j = true;
        }
    }

    public void c() {
        this.j = true;
        synchronized (this) {
            if (getState() == Thread.State.WAITING) {
                notify();
            }
        }
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:659)
        	at java.util.ArrayList.get(ArrayList.java:435)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:225)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    public void run() {
        /*
            r5 = this;
            r5.d()
        L_0x0003:
            com.baidu.platform.comapi.map.m$a r0 = r5.c
            if (r0 == 0) goto L_0x0018
            int r0 = r5.i
            r1 = 1
            if (r0 != r1) goto L_0x0074
            boolean r0 = r5.j
            if (r0 != 0) goto L_0x0074
            com.baidu.platform.comapi.map.af r0 = r5.k
            com.baidu.platform.comapi.map.e r0 = r0.b()
            if (r0 != 0) goto L_0x001c
        L_0x0018:
            r5.e()
        L_0x001b:
            return
        L_0x001c:
            com.baidu.platform.comapi.map.af r0 = r5.k
            com.baidu.platform.comapi.map.e r1 = r0.b()
            monitor-enter(r1)
            monitor-enter(r5)     // Catch:{ all -> 0x0059 }
            boolean r0 = r5.j     // Catch:{ all -> 0x005c }
            if (r0 != 0) goto L_0x0030
            com.baidu.platform.comapi.map.m$a r0 = r5.c     // Catch:{ all -> 0x005c }
            int r0 = r0.a()     // Catch:{ all -> 0x005c }
            r5.i = r0     // Catch:{ all -> 0x005c }
        L_0x0030:
            monitor-exit(r5)     // Catch:{ all -> 0x005c }
            com.baidu.platform.comapi.map.af r0 = r5.k     // Catch:{ all -> 0x0059 }
            com.baidu.platform.comapi.map.e r0 = r0.b()     // Catch:{ all -> 0x0059 }
            java.util.List<com.baidu.platform.comapi.map.l> r0 = r0.f     // Catch:{ all -> 0x0059 }
            java.util.Iterator r2 = r0.iterator()     // Catch:{ all -> 0x0059 }
        L_0x003d:
            boolean r0 = r2.hasNext()     // Catch:{ all -> 0x0059 }
            if (r0 == 0) goto L_0x0065
            java.lang.Object r0 = r2.next()     // Catch:{ all -> 0x0059 }
            com.baidu.platform.comapi.map.l r0 = (com.baidu.platform.comapi.map.l) r0     // Catch:{ all -> 0x0059 }
            com.baidu.platform.comapi.map.af r3 = r5.k     // Catch:{ all -> 0x0059 }
            com.baidu.platform.comapi.map.e r3 = r3.b()     // Catch:{ all -> 0x0059 }
            com.baidu.platform.comapi.map.ae r3 = r3.I()     // Catch:{ all -> 0x0059 }
            javax.microedition.khronos.opengles.GL10 r4 = r5.h     // Catch:{ all -> 0x0059 }
            if (r4 != 0) goto L_0x005f
            monitor-exit(r1)     // Catch:{ all -> 0x0059 }
            goto L_0x001b
        L_0x0059:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0059 }
            throw r0
        L_0x005c:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x005c }
            throw r0     // Catch:{ all -> 0x0059 }
        L_0x005f:
            javax.microedition.khronos.opengles.GL10 r4 = r5.h     // Catch:{ all -> 0x0059 }
            r0.a(r4, r3)     // Catch:{ all -> 0x0059 }
            goto L_0x003d
        L_0x0065:
            javax.microedition.khronos.egl.EGL10 r0 = r5.d     // Catch:{ all -> 0x0059 }
            javax.microedition.khronos.egl.EGLDisplay r2 = r5.e     // Catch:{ all -> 0x0059 }
            javax.microedition.khronos.egl.EGLSurface r3 = r5.g     // Catch:{ all -> 0x0059 }
            r0.eglSwapBuffers(r2, r3)     // Catch:{ all -> 0x0059 }
            monitor-exit(r1)     // Catch:{ all -> 0x0059 }
        L_0x006f:
            boolean r0 = r5.j
            if (r0 == 0) goto L_0x0003
            goto L_0x0018
        L_0x0074:
            monitor-enter(r5)     // Catch:{ InterruptedException -> 0x007d }
            r5.wait()     // Catch:{ all -> 0x007a }
            monitor-exit(r5)     // Catch:{ all -> 0x007a }
            goto L_0x006f
        L_0x007a:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x007a }
            throw r0     // Catch:{ InterruptedException -> 0x007d }
        L_0x007d:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x006f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.comapi.map.m.run():void");
    }
}
