package com.baidu.mapapi.map;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.util.LongSparseArray;
import android.util.SparseIntArray;
import com.baidu.mapapi.model.LatLng;
import com.facebook.imageutils.JfifUtil;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public class HeatMap {
    public static final Gradient DEFAULT_GRADIENT = new Gradient(d, e);
    public static final double DEFAULT_OPACITY = 0.6d;
    public static final int DEFAULT_RADIUS = 12;
    private static final String b = HeatMap.class.getSimpleName();
    private static final SparseIntArray c = new SparseIntArray();
    private static final int[] d = {Color.rgb(0, 0, 200), Color.rgb(0, JfifUtil.MARKER_APP1, 0), Color.rgb(255, 0, 0)};
    private static final float[] e = {0.08f, 0.4f, 1.0f};
    private static int r = 0;
    BaiduMap a;
    private l<WeightedLatLng> f;
    private Collection<WeightedLatLng> g;
    private int h;
    private Gradient i;
    private double j;
    private f k;
    private int[] l;
    private double[] m;
    private double[] n;
    private HashMap<String, Tile> o;
    private ExecutorService p;
    private HashSet<String> q;

    public static class Builder {
        /* access modifiers changed from: private */
        public Collection<WeightedLatLng> a;
        /* access modifiers changed from: private */
        public int b = 12;
        /* access modifiers changed from: private */
        public Gradient c = HeatMap.DEFAULT_GRADIENT;
        /* access modifiers changed from: private */
        public double d = 0.6d;

        public HeatMap build() {
            if (this.a != null) {
                return new HeatMap(this, (g) null);
            }
            throw new IllegalStateException("No input data: you must use either .data or .weightedData before building");
        }

        public Builder data(Collection<LatLng> collection) {
            if (collection == null || collection.isEmpty()) {
                throw new IllegalArgumentException("No input points.");
            } else if (!collection.contains((Object) null)) {
                return weightedData(HeatMap.c(collection));
            } else {
                throw new IllegalArgumentException("input points can not contain null.");
            }
        }

        public Builder gradient(Gradient gradient) {
            if (gradient == null) {
                throw new IllegalArgumentException("gradient can not be null");
            }
            this.c = gradient;
            return this;
        }

        public Builder opacity(double d2) {
            this.d = d2;
            if (this.d >= 0.0d && this.d <= 1.0d) {
                return this;
            }
            throw new IllegalArgumentException("Opacity must be in range [0, 1]");
        }

        public Builder radius(int i) {
            this.b = i;
            if (this.b >= 10 && this.b <= 50) {
                return this;
            }
            throw new IllegalArgumentException("Radius not within bounds.");
        }

        public Builder weightedData(Collection<WeightedLatLng> collection) {
            if (collection == null || collection.isEmpty()) {
                throw new IllegalArgumentException("No input points.");
            } else if (collection.contains((Object) null)) {
                throw new IllegalArgumentException("input points can not contain null.");
            } else {
                ArrayList arrayList = new ArrayList();
                for (WeightedLatLng next : collection) {
                    LatLng latLng = next.latLng;
                    if (latLng.latitude < 0.37532d || latLng.latitude > 54.562495d || latLng.longitude < 72.508319d || latLng.longitude > 135.942198d) {
                        arrayList.add(next);
                    }
                }
                collection.removeAll(arrayList);
                this.a = collection;
                return this;
            }
        }
    }

    static {
        c.put(3, 8388608);
        c.put(4, 4194304);
        c.put(5, 2097152);
        c.put(6, 1048576);
        c.put(7, 524288);
        c.put(8, 262144);
        c.put(9, 131072);
        c.put(10, 65536);
        c.put(11, 32768);
        c.put(12, 16384);
        c.put(13, 8192);
        c.put(14, 4096);
        c.put(15, 2048);
        c.put(16, 1024);
        c.put(17, 512);
        c.put(18, 256);
        c.put(19, 128);
        c.put(20, 64);
    }

    private HeatMap(Builder builder) {
        this.o = new HashMap<>();
        this.p = Executors.newFixedThreadPool(1);
        this.q = new HashSet<>();
        this.g = builder.a;
        this.h = builder.b;
        this.i = builder.c;
        this.j = builder.d;
        this.m = a(this.h, ((double) this.h) / 3.0d);
        a(this.i);
        b(this.g);
    }

    /* synthetic */ HeatMap(Builder builder, g gVar) {
        this(builder);
    }

    private static double a(Collection<WeightedLatLng> collection, f fVar, int i2, int i3) {
        LongSparseArray longSparseArray;
        double d2 = fVar.a;
        double d3 = fVar.c;
        double d4 = fVar.b;
        double d5 = fVar.d;
        double d6 = ((double) ((int) (((double) (i3 / (i2 * 2))) + 0.5d))) / (d3 - d2 > d5 - d4 ? d3 - d2 : d5 - d4);
        LongSparseArray longSparseArray2 = new LongSparseArray();
        double d7 = 0.0d;
        Iterator<WeightedLatLng> it = collection.iterator();
        while (true) {
            double d8 = d7;
            if (!it.hasNext()) {
                return d8;
            }
            WeightedLatLng next = it.next();
            int i4 = (int) ((((double) next.a().x) - d2) * d6);
            int i5 = (int) ((((double) next.a().y) - d4) * d6);
            LongSparseArray longSparseArray3 = (LongSparseArray) longSparseArray2.get((long) i4);
            if (longSparseArray3 == null) {
                LongSparseArray longSparseArray4 = new LongSparseArray();
                longSparseArray2.put((long) i4, longSparseArray4);
                longSparseArray = longSparseArray4;
            } else {
                longSparseArray = longSparseArray3;
            }
            Double d9 = (Double) longSparseArray.get((long) i5);
            if (d9 == null) {
                d9 = Double.valueOf(0.0d);
            }
            Double valueOf = Double.valueOf(next.intensity + d9.doubleValue());
            longSparseArray.put((long) i5, valueOf);
            d7 = valueOf.doubleValue() > d8 ? valueOf.doubleValue() : d8;
        }
    }

    private static Bitmap a(double[][] dArr, int[] iArr, double d2) {
        int i2 = iArr[iArr.length - 1];
        double length = ((double) (iArr.length - 1)) / d2;
        int length2 = dArr.length;
        int[] iArr2 = new int[(length2 * length2)];
        for (int i3 = 0; i3 < length2; i3++) {
            for (int i4 = 0; i4 < length2; i4++) {
                double d3 = dArr[i4][i3];
                int i5 = (i3 * length2) + i4;
                int i6 = (int) (d3 * length);
                if (d3 == 0.0d) {
                    iArr2[i5] = 0;
                } else if (i6 < iArr.length) {
                    iArr2[i5] = iArr[i6];
                } else {
                    iArr2[i5] = i2;
                }
            }
        }
        Bitmap createBitmap = Bitmap.createBitmap(length2, length2, Bitmap.Config.ARGB_8888);
        createBitmap.setPixels(iArr2, 0, length2, 0, 0, length2, length2);
        return createBitmap;
    }

    private static Tile a(Bitmap bitmap) {
        ByteBuffer allocate = ByteBuffer.allocate(bitmap.getWidth() * bitmap.getHeight() * 4);
        bitmap.copyPixelsToBuffer(allocate);
        return new Tile(256, 256, allocate.array());
    }

    private void a(Gradient gradient) {
        this.i = gradient;
        this.l = gradient.a(this.j);
    }

    private synchronized void a(String str, Tile tile) {
        this.o.put(str, tile);
    }

    private synchronized boolean a(String str) {
        return this.q.contains(str);
    }

    private double[] a(int i2) {
        double[] dArr = new double[20];
        for (int i3 = 5; i3 < 11; i3++) {
            dArr[i3] = a(this.g, this.k, i2, (int) (1280.0d * Math.pow(2.0d, (double) (i3 - 3))));
            if (i3 == 5) {
                for (int i4 = 0; i4 < i3; i4++) {
                    dArr[i4] = dArr[i3];
                }
            }
        }
        for (int i5 = 11; i5 < 20; i5++) {
            dArr[i5] = dArr[10];
        }
        return dArr;
    }

    private static double[] a(int i2, double d2) {
        double[] dArr = new double[((i2 * 2) + 1)];
        for (int i3 = -i2; i3 <= i2; i3++) {
            dArr[i3 + i2] = Math.exp(((double) ((-i3) * i3)) / ((2.0d * d2) * d2));
        }
        return dArr;
    }

    private static double[][] a(double[][] dArr, double[] dArr2) {
        int floor = (int) Math.floor(((double) dArr2.length) / 2.0d);
        int length = dArr.length;
        int i2 = length - (floor * 2);
        int i3 = (floor + i2) - 1;
        double[][] dArr3 = (double[][]) Array.newInstance(Double.TYPE, new int[]{length, length});
        for (int i4 = 0; i4 < length; i4++) {
            for (int i5 = 0; i5 < length; i5++) {
                double d2 = dArr[i4][i5];
                if (d2 != 0.0d) {
                    int i6 = (i3 < i4 + floor ? i3 : i4 + floor) + 1;
                    for (int i7 = floor > i4 - floor ? floor : i4 - floor; i7 < i6; i7++) {
                        double[] dArr4 = dArr3[i7];
                        dArr4[i5] = dArr4[i5] + (dArr2[i7 - (i4 - floor)] * d2);
                    }
                }
            }
        }
        double[][] dArr5 = (double[][]) Array.newInstance(Double.TYPE, new int[]{i2, i2});
        for (int i8 = floor; i8 < i3 + 1; i8++) {
            for (int i9 = 0; i9 < length; i9++) {
                double d3 = dArr3[i8][i9];
                if (d3 != 0.0d) {
                    int i10 = (i3 < i9 + floor ? i3 : i9 + floor) + 1;
                    for (int i11 = floor > i9 - floor ? floor : i9 - floor; i11 < i10; i11++) {
                        double[] dArr6 = dArr5[i8 - floor];
                        int i12 = i11 - floor;
                        dArr6[i12] = dArr6[i12] + (dArr2[i11 - (i9 - floor)] * d3);
                    }
                }
            }
        }
        return dArr5;
    }

    /* access modifiers changed from: private */
    public void b(int i2, int i3, int i4) {
        double d2 = (double) c.get(i4);
        double d3 = (((double) this.h) * d2) / 256.0d;
        double d4 = ((2.0d * d3) + d2) / ((double) ((this.h * 2) + 256));
        if (i2 >= 0 && i3 >= 0) {
            double d5 = (((double) i2) * d2) - d3;
            double d6 = (d2 * ((double) (i3 + 1))) + d3;
            f fVar = new f(d5, (((double) (i2 + 1)) * d2) + d3, (((double) i3) * d2) - d3, d6);
            if (fVar.a(new f(this.k.a - d3, this.k.c + d3, this.k.b - d3, d3 + this.k.d))) {
                Collection<WeightedLatLng> a2 = this.f.a(fVar);
                if (!a2.isEmpty()) {
                    double[][] dArr = (double[][]) Array.newInstance(Double.TYPE, new int[]{(this.h * 2) + 256, (this.h * 2) + 256});
                    for (WeightedLatLng next : a2) {
                        Point a3 = next.a();
                        int i5 = (int) ((((double) a3.x) - d5) / d4);
                        int i6 = (int) ((d6 - ((double) a3.y)) / d4);
                        if (i5 >= (this.h * 2) + 256) {
                            i5 = ((this.h * 2) + 256) - 1;
                        }
                        if (i6 >= (this.h * 2) + 256) {
                            i6 = ((this.h * 2) + 256) - 1;
                        }
                        double[] dArr2 = dArr[i5];
                        dArr2[i6] = dArr2[i6] + next.intensity;
                    }
                    Bitmap a4 = a(a(dArr, this.m), this.l, this.n[i4 - 1]);
                    Tile a5 = a(a4);
                    a4.recycle();
                    a(i2 + "_" + i3 + "_" + i4, a5);
                    if (this.o.size() > r) {
                        a();
                    }
                    if (this.a != null) {
                        this.a.a();
                    }
                }
            }
        }
    }

    private synchronized void b(String str) {
        this.q.add(str);
    }

    private void b(Collection<WeightedLatLng> collection) {
        this.g = collection;
        if (this.g.isEmpty()) {
            throw new IllegalArgumentException("No input points.");
        }
        this.k = d(this.g);
        this.f = new l<>(this.k);
        for (WeightedLatLng a2 : this.g) {
            this.f.a(a2);
        }
        this.n = a(this.h);
    }

    private synchronized Tile c(String str) {
        Tile tile;
        if (this.o.containsKey(str)) {
            tile = this.o.get(str);
            this.o.remove(str);
        } else {
            tile = null;
        }
        return tile;
    }

    /* access modifiers changed from: private */
    public static Collection<WeightedLatLng> c(Collection<LatLng> collection) {
        ArrayList arrayList = new ArrayList();
        for (LatLng weightedLatLng : collection) {
            arrayList.add(new WeightedLatLng(weightedLatLng));
        }
        return arrayList;
    }

    private static f d(Collection<WeightedLatLng> collection) {
        Iterator<WeightedLatLng> it = collection.iterator();
        WeightedLatLng next = it.next();
        double d2 = (double) next.a().x;
        double d3 = (double) next.a().x;
        double d4 = (double) next.a().y;
        double d5 = (double) next.a().y;
        while (it.hasNext()) {
            WeightedLatLng next2 = it.next();
            double d6 = (double) next2.a().x;
            double d7 = (double) next2.a().y;
            if (d6 < d2) {
                d2 = d6;
            }
            if (d6 > d3) {
                d3 = d6;
            }
            if (d7 < d4) {
                d4 = d7;
            }
            if (d7 > d5) {
                d5 = d7;
            }
        }
        return new f(d2, d3, d4, d5);
    }

    private synchronized void d() {
        this.o.clear();
    }

    /* access modifiers changed from: package-private */
    public Tile a(int i2, int i3, int i4) {
        String str = i2 + "_" + i3 + "_" + i4;
        Tile c2 = c(str);
        if (c2 != null) {
            return c2;
        }
        if (!a(str)) {
            if (this.a != null && r == 0) {
                MapStatus mapStatus = this.a.getMapStatus();
                r = (((mapStatus.a.j.bottom - mapStatus.a.j.top) / 256) + 2) * (((mapStatus.a.j.right - mapStatus.a.j.left) / 256) + 2) * 4;
            }
            if (this.o.size() > r) {
                a();
            }
            if (!this.p.isShutdown()) {
                try {
                    this.p.execute(new g(this, i2, i3, i4));
                    b(str);
                } catch (RejectedExecutionException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public synchronized void a() {
        this.q.clear();
        this.o.clear();
    }

    /* access modifiers changed from: package-private */
    public void b() {
        d();
    }

    /* access modifiers changed from: package-private */
    public void c() {
        this.p.shutdownNow();
    }

    public void removeHeatMap() {
        if (this.a != null) {
            this.a.a(this);
        }
    }
}
