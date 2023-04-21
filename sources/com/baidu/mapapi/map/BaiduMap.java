package com.baidu.mapapi.map;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.ActivityChooserView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import cn.com.bioeasy.app.utils.ListUtils;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.mapapi.map.MapBaseIndoorMapInfo;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.model.ParcelItem;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.ad;
import com.baidu.platform.comapi.map.ae;
import com.baidu.platform.comapi.map.af;
import com.baidu.platform.comapi.map.al;
import com.baidu.platform.comapi.map.e;
import com.baidu.platform.comapi.map.h;
import com.baidu.platform.comapi.map.j;
import com.baidu.platform.comapi.map.l;
import com.baidu.platform.comapi.map.q;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.microedition.khronos.opengles.GL10;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BaiduMap {
    public static final int MAP_TYPE_NONE = 3;
    public static final int MAP_TYPE_NORMAL = 1;
    public static final int MAP_TYPE_SATELLITE = 2;
    private static final String e = BaiduMap.class.getSimpleName();
    public static int mapStatusReason = 0;
    /* access modifiers changed from: private */
    public OnMapDrawFrameCallback A;
    /* access modifiers changed from: private */
    public OnBaseIndoorMapListener B;
    /* access modifiers changed from: private */
    public TileOverlay C;
    /* access modifiers changed from: private */
    public HeatMap D;
    /* access modifiers changed from: private */
    public Lock E = new ReentrantLock();
    /* access modifiers changed from: private */
    public Lock F = new ReentrantLock();
    /* access modifiers changed from: private */
    public InfoWindow G;
    /* access modifiers changed from: private */
    public Marker H;
    /* access modifiers changed from: private */
    public View I;
    /* access modifiers changed from: private */
    public Marker J;
    private MyLocationData K;
    private MyLocationConfiguration L;
    private boolean M;
    private boolean N;
    private boolean O;
    /* access modifiers changed from: private */
    public boolean P;
    private Point Q;
    MapView a;
    TextureMapView b;
    WearMapView c;
    ad d;
    /* access modifiers changed from: private */
    public Projection f;
    private UiSettings g;
    private j h;
    /* access modifiers changed from: private */
    public e i;
    private af j;
    /* access modifiers changed from: private */
    public List<Overlay> k;
    /* access modifiers changed from: private */
    public List<Marker> l;
    /* access modifiers changed from: private */
    public List<Marker> m;
    private Overlay.a n;
    /* access modifiers changed from: private */
    public OnMapStatusChangeListener o;
    /* access modifiers changed from: private */
    public OnMapTouchListener p;
    /* access modifiers changed from: private */
    public OnMapClickListener q;
    /* access modifiers changed from: private */
    public OnMapLoadedCallback r;
    /* access modifiers changed from: private */
    public OnMapRenderCallback s;
    /* access modifiers changed from: private */
    public OnMapDoubleClickListener t;
    /* access modifiers changed from: private */
    public OnMapLongClickListener u;
    /* access modifiers changed from: private */
    public CopyOnWriteArrayList<OnMarkerClickListener> v = new CopyOnWriteArrayList<>();
    /* access modifiers changed from: private */
    public CopyOnWriteArrayList<OnPolylineClickListener> w = new CopyOnWriteArrayList<>();
    /* access modifiers changed from: private */
    public OnMarkerDragListener x;
    /* access modifiers changed from: private */
    public OnMyLocationClickListener y;
    /* access modifiers changed from: private */
    public SnapshotReadyCallback z;

    public interface OnBaseIndoorMapListener {
        void onBaseIndoorMapMode(boolean z, MapBaseIndoorMapInfo mapBaseIndoorMapInfo);
    }

    public interface OnMapClickListener {
        void onMapClick(LatLng latLng);

        boolean onMapPoiClick(MapPoi mapPoi);
    }

    public interface OnMapDoubleClickListener {
        void onMapDoubleClick(LatLng latLng);
    }

    public interface OnMapDrawFrameCallback {
        void onMapDrawFrame(MapStatus mapStatus);

        @Deprecated
        void onMapDrawFrame(GL10 gl10, MapStatus mapStatus);
    }

    public interface OnMapLoadedCallback {
        void onMapLoaded();
    }

    public interface OnMapLongClickListener {
        void onMapLongClick(LatLng latLng);
    }

    public interface OnMapRenderCallback {
        void onMapRenderFinished();
    }

    public interface OnMapStatusChangeListener {
        public static final int REASON_API_ANIMATION = 2;
        public static final int REASON_DEVELOPER_ANIMATION = 3;
        public static final int REASON_GESTURE = 1;

        void onMapStatusChange(MapStatus mapStatus);

        void onMapStatusChangeFinish(MapStatus mapStatus);

        void onMapStatusChangeStart(MapStatus mapStatus);

        void onMapStatusChangeStart(MapStatus mapStatus, int i);
    }

    public interface OnMapTouchListener {
        void onTouch(MotionEvent motionEvent);
    }

    public interface OnMarkerClickListener {
        boolean onMarkerClick(Marker marker);
    }

    public interface OnMarkerDragListener {
        void onMarkerDrag(Marker marker);

        void onMarkerDragEnd(Marker marker);

        void onMarkerDragStart(Marker marker);
    }

    public interface OnMyLocationClickListener {
        boolean onMyLocationClick();
    }

    public interface OnPolylineClickListener {
        boolean onPolylineClick(Polyline polyline);
    }

    public interface SnapshotReadyCallback {
        void onSnapshotReady(Bitmap bitmap);
    }

    BaiduMap(af afVar) {
        this.j = afVar;
        this.i = this.j.b();
        this.d = ad.TextureView;
        c();
    }

    BaiduMap(j jVar) {
        this.h = jVar;
        this.i = this.h.a();
        this.d = ad.GLSurfaceView;
        c();
    }

    private Point a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int i2 = 0;
        int i3 = 0;
        for (String replaceAll : str.replaceAll("^\\{", "").replaceAll("\\}$", "").split(ListUtils.DEFAULT_JOIN_SEPARATOR)) {
            String[] split = replaceAll.replaceAll("\"", "").split(":");
            if ("x".equals(split[0])) {
                i3 = Integer.valueOf(split[1]).intValue();
            }
            if ("y".equals(split[0])) {
                i2 = Integer.valueOf(split[1]).intValue();
            }
        }
        return new Point(i3, i2);
    }

    private ae a(MapStatusUpdate mapStatusUpdate) {
        if (this.i == null) {
            return null;
        }
        ae E2 = this.i.E();
        MapStatus a2 = mapStatusUpdate.a(this.i, getMapStatus());
        if (a2 != null) {
            return a2.b(E2);
        }
        return null;
    }

    private final void a(MyLocationData myLocationData, MyLocationConfiguration myLocationConfiguration) {
        Bundle bundle;
        float f2;
        if (myLocationData != null && myLocationConfiguration != null && isMyLocationEnabled()) {
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObject3 = new JSONObject();
            LatLng latLng = new LatLng(myLocationData.latitude, myLocationData.longitude);
            GeoPoint ll2mc = CoordUtil.ll2mc(latLng);
            try {
                jSONObject.put("type", 0);
                jSONObject2.put("ptx", ll2mc.getLongitudeE6());
                jSONObject2.put("pty", ll2mc.getLatitudeE6());
                jSONObject2.put("radius", (double) ((float) CoordUtil.getMCDistanceByOneLatLngAndRadius(latLng, (int) myLocationData.accuracy)));
                float f3 = myLocationData.direction;
                if (myLocationConfiguration.enableDirection) {
                    f2 = myLocationData.direction % 360.0f;
                    if (f2 > 180.0f) {
                        f2 -= 360.0f;
                    } else if (f2 < -180.0f) {
                        f2 += 360.0f;
                    }
                } else {
                    f2 = -1.0f;
                }
                jSONObject2.put("direction", (double) f2);
                jSONObject2.put("iconarrownor", "NormalLocArrow");
                jSONObject2.put("iconarrownorid", 28);
                jSONObject2.put("iconarrowfoc", "FocusLocArrow");
                jSONObject2.put("iconarrowfocid", 29);
                jSONObject2.put("lineid", myLocationConfiguration.accuracyCircleStrokeColor);
                jSONObject2.put("areaid", myLocationConfiguration.accuracyCircleFillColor);
                jSONArray.put(jSONObject2);
                jSONObject.put("data", jSONArray);
                if (myLocationConfiguration.locationMode == MyLocationConfiguration.LocationMode.COMPASS) {
                    jSONObject3.put("ptx", ll2mc.getLongitudeE6());
                    jSONObject3.put("pty", ll2mc.getLatitudeE6());
                    jSONObject3.put("radius", 0);
                    jSONObject3.put("direction", 0);
                    jSONObject3.put("iconarrownor", "direction_wheel");
                    jSONObject3.put("iconarrownorid", 54);
                    jSONObject3.put("iconarrowfoc", "direction_wheel");
                    jSONObject3.put("iconarrowfocid", 54);
                    jSONArray.put(jSONObject3);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            if (myLocationConfiguration.customMarker == null) {
                bundle = null;
            } else {
                ArrayList<BitmapDescriptor> arrayList = new ArrayList<>();
                arrayList.add(myLocationConfiguration.customMarker);
                Bundle bundle2 = new Bundle();
                ArrayList arrayList2 = new ArrayList();
                for (BitmapDescriptor bitmapDescriptor : arrayList) {
                    ParcelItem parcelItem = new ParcelItem();
                    Bundle bundle3 = new Bundle();
                    Bitmap bitmap = bitmapDescriptor.a;
                    ByteBuffer allocate = ByteBuffer.allocate(bitmap.getWidth() * bitmap.getHeight() * 4);
                    bitmap.copyPixelsToBuffer(allocate);
                    bundle3.putByteArray("imgdata", allocate.array());
                    bundle3.putInt("imgindex", bitmapDescriptor.hashCode());
                    bundle3.putInt("imgH", bitmap.getHeight());
                    bundle3.putInt("imgW", bitmap.getWidth());
                    parcelItem.setBundle(bundle3);
                    arrayList2.add(parcelItem);
                }
                if (arrayList2.size() > 0) {
                    ParcelItem[] parcelItemArr = new ParcelItem[arrayList2.size()];
                    for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                        parcelItemArr[i2] = (ParcelItem) arrayList2.get(i2);
                    }
                    bundle2.putParcelableArray("icondata", parcelItemArr);
                }
                bundle = bundle2;
            }
            if (this.i != null) {
                this.i.a(jSONObject.toString(), bundle);
            }
            switch (myLocationConfiguration.locationMode) {
                case COMPASS:
                    animateMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().rotate(myLocationData.direction).overlook(-45.0f).target(new LatLng(myLocationData.latitude, myLocationData.longitude)).targetScreen(getMapStatus().targetScreen).zoom(getMapStatus().zoom).build()));
                    return;
                case FOLLOWING:
                    animateMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().target(new LatLng(myLocationData.latitude, myLocationData.longitude)).zoom(getMapStatus().zoom).rotate(getMapStatus().rotate).overlook(getMapStatus().overlook).targetScreen(getMapStatus().targetScreen).build()));
                    return;
                default:
                    return;
            }
        }
    }

    private void c() {
        this.k = new CopyOnWriteArrayList();
        this.l = new CopyOnWriteArrayList();
        this.m = new CopyOnWriteArrayList();
        this.Q = new Point((int) (SysOSUtil.getDensity() * 40.0f), (int) (SysOSUtil.getDensity() * 40.0f));
        this.g = new UiSettings(this.i);
        this.n = new a(this);
        this.i.a((l) new b(this));
        this.i.a((q) new c(this));
        this.i.a((al) new d(this));
        this.M = this.i.C();
        this.N = this.i.D();
    }

    /* access modifiers changed from: package-private */
    public void a() {
        if (this.i != null) {
            this.i.t();
        }
    }

    /* access modifiers changed from: package-private */
    public void a(HeatMap heatMap) {
        this.E.lock();
        try {
            if (!(this.D == null || this.i == null || heatMap != this.D)) {
                this.D.b();
                this.D.c();
                this.D.a = null;
                this.i.o();
                this.D = null;
                this.i.m(false);
            }
        } finally {
            this.E.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public void a(TileOverlay tileOverlay) {
        this.F.lock();
        if (tileOverlay != null) {
            try {
                if (this.C == tileOverlay) {
                    tileOverlay.b();
                    tileOverlay.a = null;
                    if (this.i != null) {
                        this.i.d(false);
                    }
                }
            } catch (Throwable th) {
                this.C = null;
                this.F.unlock();
                throw th;
            }
        }
        this.C = null;
        this.F.unlock();
    }

    public void addHeatMap(HeatMap heatMap) {
        if (heatMap != null) {
            this.E.lock();
            try {
                if (heatMap != this.D) {
                    if (this.D != null) {
                        this.D.b();
                        this.D.c();
                        this.D.a = null;
                        this.i.o();
                    }
                    this.D = heatMap;
                    this.D.a = this;
                    this.i.m(true);
                    this.E.unlock();
                }
            } finally {
                this.E.unlock();
            }
        }
    }

    public final Overlay addOverlay(OverlayOptions overlayOptions) {
        if (overlayOptions == null) {
            return null;
        }
        Overlay a2 = overlayOptions.a();
        a2.listener = this.n;
        if (a2 instanceof Marker) {
            Marker marker = (Marker) a2;
            if (!(marker.n == null || marker.n.size() == 0)) {
                this.l.add(marker);
                if (this.i != null) {
                    this.i.b(true);
                }
            }
            this.m.add(marker);
        }
        Bundle bundle = new Bundle();
        a2.a(bundle);
        if (this.i != null) {
            this.i.b(bundle);
        }
        this.k.add(a2);
        return a2;
    }

    public final List<Overlay> addOverlays(List<OverlayOptions> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Bundle[] bundleArr = new Bundle[list.size()];
        int i2 = 0;
        for (OverlayOptions next : list) {
            if (next != null) {
                Bundle bundle = new Bundle();
                Overlay a2 = next.a();
                a2.listener = this.n;
                if (a2 instanceof Marker) {
                    Marker marker = (Marker) a2;
                    if (!(marker.n == null || marker.n.size() == 0)) {
                        this.l.add(marker);
                        if (this.i != null) {
                            this.i.b(true);
                        }
                    }
                    this.m.add(marker);
                }
                this.k.add(a2);
                arrayList.add(a2);
                a2.a(bundle);
                bundleArr[i2] = bundle;
                i2++;
            }
        }
        int length = bundleArr.length / 400;
        int i3 = 0;
        while (i3 < length + 1) {
            ArrayList arrayList2 = new ArrayList();
            int i4 = 0;
            while (i4 < 400 && (i3 * 400) + i4 < bundleArr.length) {
                if (bundleArr[(i3 * 400) + i4] != null) {
                    arrayList2.add(bundleArr[(i3 * 400) + i4]);
                }
                i4++;
            }
            if (this.i != null) {
                this.i.a((List<Bundle>) arrayList2);
            }
            i3++;
        }
        return arrayList;
    }

    public TileOverlay addTileLayer(TileOverlayOptions tileOverlayOptions) {
        if (tileOverlayOptions == null) {
            return null;
        }
        if (this.C != null) {
            this.C.b();
            this.C.a = null;
        }
        if (this.i == null || !this.i.a(tileOverlayOptions.a())) {
            return null;
        }
        TileOverlay a2 = tileOverlayOptions.a(this);
        this.C = a2;
        return a2;
    }

    public final void animateMapStatus(MapStatusUpdate mapStatusUpdate) {
        animateMapStatus(mapStatusUpdate, 300);
    }

    public final void animateMapStatus(MapStatusUpdate mapStatusUpdate, int i2) {
        if (mapStatusUpdate != null && i2 > 0) {
            ae a2 = a(mapStatusUpdate);
            if (this.i != null) {
                mapStatusReason |= 256;
                if (!this.P) {
                    this.i.a(a2);
                } else {
                    this.i.a(a2, i2);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean b() {
        if (this.i == null) {
            return false;
        }
        return this.i.e();
    }

    public final void clear() {
        this.k.clear();
        this.l.clear();
        this.m.clear();
        if (this.i != null) {
            this.i.b(false);
            this.i.n();
        }
        hideInfoWindow();
    }

    public final Point getCompassPosition() {
        if (this.i != null) {
            return a(this.i.h());
        }
        return null;
    }

    public MapBaseIndoorMapInfo getFocusedBaseIndoorMapInfo() {
        return this.i.p();
    }

    public final MyLocationConfiguration getLocationConfigeration() {
        return getLocationConfiguration();
    }

    public final MyLocationConfiguration getLocationConfiguration() {
        return this.L;
    }

    public final MyLocationData getLocationData() {
        return this.K;
    }

    public final MapStatus getMapStatus() {
        if (this.i == null) {
            return null;
        }
        return MapStatus.a(this.i.E());
    }

    public final LatLngBounds getMapStatusLimit() {
        if (this.i == null) {
            return null;
        }
        return this.i.F();
    }

    public final int getMapType() {
        if (this.i == null) {
            return 1;
        }
        if (!this.i.l()) {
            return 3;
        }
        return this.i.k() ? 2 : 1;
    }

    public List<Marker> getMarkersInBounds(LatLngBounds latLngBounds) {
        if (getMapStatus() == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (this.m.size() == 0) {
            return null;
        }
        for (Marker next : this.m) {
            if (latLngBounds.contains(next.getPosition())) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    public final float getMaxZoomLevel() {
        if (this.i == null) {
            return 0.0f;
        }
        return this.i.a;
    }

    public final float getMinZoomLevel() {
        if (this.i == null) {
            return 0.0f;
        }
        return this.i.b;
    }

    public final Projection getProjection() {
        return this.f;
    }

    public final UiSettings getUiSettings() {
        return this.g;
    }

    public j getmGLMapView() {
        return this.h;
    }

    public void hideInfoWindow() {
        if (this.G != null) {
            if (this.G.b != null) {
                switch (this.d) {
                    case TextureView:
                        if (this.b != null) {
                            this.b.removeView(this.I);
                            break;
                        }
                        break;
                    case GLSurfaceView:
                        if (this.h != null) {
                            this.a.removeView(this.I);
                            break;
                        }
                        break;
                }
                this.I = null;
            }
            this.G = null;
            this.H.remove();
            this.H = null;
        }
    }

    public void hideSDKLayer() {
        this.i.c();
    }

    public final boolean isBaiduHeatMapEnabled() {
        if (this.i == null) {
            return false;
        }
        return this.i.i();
    }

    public boolean isBaseIndoorMapMode() {
        return this.i.q();
    }

    public final boolean isBuildingsEnabled() {
        if (this.i == null) {
            return false;
        }
        return this.i.m();
    }

    public final boolean isMyLocationEnabled() {
        if (this.i == null) {
            return false;
        }
        return this.i.s();
    }

    public final boolean isSupportBaiduHeatMap() {
        if (this.i == null) {
            return false;
        }
        return this.i.j();
    }

    public final boolean isTrafficEnabled() {
        if (this.i == null) {
            return false;
        }
        return this.i.g();
    }

    public final void removeMarkerClickListener(OnMarkerClickListener onMarkerClickListener) {
        if (this.v.contains(onMarkerClickListener)) {
            this.v.remove(onMarkerClickListener);
        }
    }

    public final void setBaiduHeatMapEnabled(boolean z2) {
        if (this.i != null) {
            this.i.f(z2);
        }
    }

    public final void setBuildingsEnabled(boolean z2) {
        if (this.i != null) {
            this.i.h(z2);
        }
    }

    public void setCompassEnable(boolean z2) {
        this.i.c(z2);
    }

    public void setCompassIcon(Bitmap bitmap) {
        if (bitmap == null) {
            throw new IllegalArgumentException("compass's icon can not be null");
        }
        this.i.a(bitmap);
    }

    public void setCompassPosition(Point point) {
        if (this.i.a(point)) {
            this.Q = point;
        }
    }

    public boolean setCustomTrafficColor(String str, String str2, String str3, String str4) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4)) {
            if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3) && TextUtils.isEmpty(str4)) {
                this.i.a((long) Color.parseColor("#ffffffff"), (long) Color.parseColor("#ffffffff"), (long) Color.parseColor("#ffffffff"), (long) Color.parseColor("#ffffffff"), false);
            }
        } else if (!str.matches("^#[0-9a-fA-F]{8}$") || !str2.matches("^#[0-9a-fA-F]{8}$") || !str3.matches("^#[0-9a-fA-F]{8}$") || !str4.matches("^#[0-9a-fA-F]{8}$")) {
            Log.e(e, "the string of the input customTrafficColor is error");
            return false;
        } else {
            this.i.a((long) Color.parseColor(str), (long) Color.parseColor(str2), (long) Color.parseColor(str3), (long) Color.parseColor(str4), true);
        }
        return true;
    }

    public final void setIndoorEnable(boolean z2) {
        if (this.i != null) {
            this.O = z2;
            this.i.j(z2);
        }
        if (this.B != null && !z2) {
            this.B.onBaseIndoorMapMode(false, (MapBaseIndoorMapInfo) null);
        }
    }

    public final void setMapStatus(MapStatusUpdate mapStatusUpdate) {
        if (mapStatusUpdate != null) {
            ae a2 = a(mapStatusUpdate);
            if (this.i != null) {
                this.i.a(a2);
                if (this.o != null) {
                    this.o.onMapStatusChange(getMapStatus());
                }
            }
        }
    }

    public final void setMapStatusLimits(LatLngBounds latLngBounds) {
        if (this.i != null) {
            this.i.a(latLngBounds);
            setMapStatus(MapStatusUpdateFactory.newLatLngBounds(latLngBounds));
        }
    }

    public final void setMapType(int i2) {
        if (this.i != null) {
            switch (i2) {
                case 1:
                    this.i.a(false);
                    this.i.r(this.M);
                    this.i.s(this.N);
                    this.i.e(true);
                    this.i.j(this.O);
                    break;
                case 2:
                    this.i.a(true);
                    this.i.r(this.M);
                    this.i.s(this.N);
                    this.i.e(true);
                    break;
                case 3:
                    if (this.i.C()) {
                        this.i.r(false);
                    }
                    if (this.i.D()) {
                        this.i.s(false);
                    }
                    this.i.e(false);
                    this.i.j(false);
                    break;
            }
            if (this.h != null) {
                this.h.a(i2);
            }
        }
    }

    public final void setMaxAndMinZoomLevel(float f2, float f3) {
        if (f2 <= 21.0f && f3 >= 3.0f && f2 >= f3 && this.i != null) {
            this.i.a(f2, f3);
        }
    }

    public final void setMyLocationConfigeration(MyLocationConfiguration myLocationConfiguration) {
        setMyLocationConfiguration(myLocationConfiguration);
    }

    public final void setMyLocationConfiguration(MyLocationConfiguration myLocationConfiguration) {
        this.L = myLocationConfiguration;
        a(this.K, this.L);
    }

    public final void setMyLocationData(MyLocationData myLocationData) {
        this.K = myLocationData;
        if (this.L == null) {
            this.L = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, false, (BitmapDescriptor) null);
        }
        a(myLocationData, this.L);
    }

    public final void setMyLocationEnabled(boolean z2) {
        if (this.i != null) {
            this.i.l(z2);
        }
    }

    public final void setOnBaseIndoorMapListener(OnBaseIndoorMapListener onBaseIndoorMapListener) {
        this.B = onBaseIndoorMapListener;
    }

    public final void setOnMapClickListener(OnMapClickListener onMapClickListener) {
        this.q = onMapClickListener;
    }

    public final void setOnMapDoubleClickListener(OnMapDoubleClickListener onMapDoubleClickListener) {
        this.t = onMapDoubleClickListener;
    }

    public final void setOnMapDrawFrameCallback(OnMapDrawFrameCallback onMapDrawFrameCallback) {
        this.A = onMapDrawFrameCallback;
    }

    public void setOnMapLoadedCallback(OnMapLoadedCallback onMapLoadedCallback) {
        this.r = onMapLoadedCallback;
    }

    public final void setOnMapLongClickListener(OnMapLongClickListener onMapLongClickListener) {
        this.u = onMapLongClickListener;
    }

    public void setOnMapRenderCallbadk(OnMapRenderCallback onMapRenderCallback) {
        this.s = onMapRenderCallback;
    }

    public final void setOnMapStatusChangeListener(OnMapStatusChangeListener onMapStatusChangeListener) {
        this.o = onMapStatusChangeListener;
    }

    public final void setOnMapTouchListener(OnMapTouchListener onMapTouchListener) {
        this.p = onMapTouchListener;
    }

    public final void setOnMarkerClickListener(OnMarkerClickListener onMarkerClickListener) {
        if (onMarkerClickListener != null && !this.v.contains(onMarkerClickListener)) {
            this.v.add(onMarkerClickListener);
        }
    }

    public final void setOnMarkerDragListener(OnMarkerDragListener onMarkerDragListener) {
        this.x = onMarkerDragListener;
    }

    public final void setOnMyLocationClickListener(OnMyLocationClickListener onMyLocationClickListener) {
        this.y = onMyLocationClickListener;
    }

    public final void setOnPolylineClickListener(OnPolylineClickListener onPolylineClickListener) {
        if (onPolylineClickListener != null) {
            this.w.add(onPolylineClickListener);
        }
    }

    @Deprecated
    public final void setPadding(int i2, int i3, int i4, int i5) {
        if (i2 >= 0 && i3 >= 0 && i4 >= 0 && i5 >= 0 && this.i != null) {
            this.i.E();
            switch (this.d) {
                case TextureView:
                    if (this.b != null) {
                        float width = ((float) ((this.b.getWidth() - i2) - i4)) / ((float) this.b.getWidth());
                        float height = ((float) ((this.b.getHeight() - i3) - i5)) / ((float) this.b.getHeight());
                        MapStatusUpdate newMapStatus = MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().targetScreen(new Point(((this.b.getWidth() + i2) - i4) / 2, ((this.b.getHeight() + i3) - i5) / 2)).build());
                        this.i.a(new Point((int) ((width * ((float) this.Q.x)) + ((float) i2)), (int) ((height * ((float) this.Q.y)) + ((float) i3))));
                        setMapStatus(newMapStatus);
                        this.b.setPadding(i2, i3, i4, i5);
                        this.b.invalidate();
                        return;
                    }
                    return;
                case GLSurfaceView:
                    if (this.a != null) {
                        float width2 = ((float) ((this.a.getWidth() - i2) - i4)) / ((float) this.a.getWidth());
                        float height2 = ((float) ((this.a.getHeight() - i3) - i5)) / ((float) this.a.getHeight());
                        MapStatusUpdate newMapStatus2 = MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().targetScreen(new Point(((this.a.getWidth() + i2) - i4) / 2, ((this.a.getHeight() + i3) - i5) / 2)).build());
                        this.i.a(new Point((int) ((width2 * ((float) this.Q.x)) + ((float) i2)), (int) ((height2 * ((float) this.Q.y)) + ((float) i3))));
                        setMapStatus(newMapStatus2);
                        this.a.setPadding(i2, i3, i4, i5);
                        this.a.invalidate();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public void setPixelFormatTransparent(boolean z2) {
        if (this.h != null) {
            if (z2) {
                this.h.d();
            } else {
                this.h.e();
            }
        }
    }

    public final void setTrafficEnabled(boolean z2) {
        if (this.i != null) {
            this.i.g(z2);
        }
    }

    public final void setViewPadding(int i2, int i3, int i4, int i5) {
        if (i2 >= 0 && i3 >= 0 && i4 >= 0 && i5 >= 0 && this.i != null) {
            switch (this.d) {
                case TextureView:
                    if (this.b != null) {
                        float width = ((float) ((this.b.getWidth() - i2) - i4)) / ((float) this.b.getWidth());
                        float height = ((float) ((this.b.getHeight() - i3) - i5)) / ((float) this.b.getHeight());
                        this.i.a(new Point((int) ((width * ((float) this.Q.x)) + ((float) i2)), (int) ((height * ((float) this.Q.y)) + ((float) i3))));
                        this.b.setPadding(i2, i3, i4, i5);
                        this.b.invalidate();
                        return;
                    }
                    return;
                case GLSurfaceView:
                    if (this.a != null) {
                        float width2 = ((float) ((this.a.getWidth() - i2) - i4)) / ((float) this.a.getWidth());
                        float height2 = ((float) ((this.a.getHeight() - i3) - i5)) / ((float) this.a.getHeight());
                        this.i.a(new Point((int) ((width2 * ((float) this.Q.x)) + ((float) i2)), (int) ((height2 * ((float) this.Q.y)) + ((float) i3))));
                        this.a.setPadding(i2, i3, i4, i5);
                        this.a.invalidate();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public void showInfoWindow(InfoWindow infoWindow) {
        if (infoWindow != null) {
            hideInfoWindow();
            if (infoWindow.b != null) {
                this.I = infoWindow.b;
                this.I.destroyDrawingCache();
                MapViewLayoutParams build = new MapViewLayoutParams.Builder().layoutMode(MapViewLayoutParams.ELayoutMode.mapMode).position(infoWindow.c).yOffset(infoWindow.e).build();
                switch (this.d) {
                    case TextureView:
                        if (this.b != null) {
                            this.b.addView(this.I, build);
                            break;
                        }
                        break;
                    case GLSurfaceView:
                        if (this.h != null) {
                            this.a.addView(this.I, build);
                            break;
                        }
                        break;
                }
            }
            this.G = infoWindow;
            Overlay a2 = new MarkerOptions().perspective(false).icon(infoWindow.b != null ? BitmapDescriptorFactory.fromView(infoWindow.b) : infoWindow.a).position(infoWindow.c).zIndex(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED).a(infoWindow.e).a();
            a2.listener = this.n;
            a2.type = h.popup;
            Bundle bundle = new Bundle();
            a2.a(bundle);
            if (this.i != null) {
                this.i.b(bundle);
            }
            this.k.add(a2);
            this.H = (Marker) a2;
        }
    }

    public final void showMapIndoorPoi(boolean z2) {
        if (this.i != null) {
            this.i.s(z2);
            this.N = z2;
        }
    }

    public final void showMapPoi(boolean z2) {
        if (this.i != null) {
            this.i.r(z2);
            this.M = z2;
        }
    }

    public void showSDKLayer() {
        this.i.d();
    }

    public final void snapshot(SnapshotReadyCallback snapshotReadyCallback) {
        this.z = snapshotReadyCallback;
        switch (this.d) {
            case TextureView:
                if (this.j != null) {
                    this.j.a("anything", (Rect) null);
                    return;
                }
                return;
            case GLSurfaceView:
                if (this.h != null) {
                    this.h.a("anything", (Rect) null);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public final void snapshotScope(Rect rect, SnapshotReadyCallback snapshotReadyCallback) {
        this.z = snapshotReadyCallback;
        switch (this.d) {
            case TextureView:
                if (this.j != null) {
                    this.j.a("anything", rect);
                    return;
                }
                return;
            case GLSurfaceView:
                if (this.h != null) {
                    this.h.a("anything", rect);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public MapBaseIndoorMapInfo.SwitchFloorError switchBaseIndoorMapFloor(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return MapBaseIndoorMapInfo.SwitchFloorError.FLOOR_INFO_ERROR;
        }
        MapBaseIndoorMapInfo focusedBaseIndoorMapInfo = getFocusedBaseIndoorMapInfo();
        if (focusedBaseIndoorMapInfo == null) {
            return MapBaseIndoorMapInfo.SwitchFloorError.SWITCH_ERROR;
        }
        if (!str2.equals(focusedBaseIndoorMapInfo.a)) {
            return MapBaseIndoorMapInfo.SwitchFloorError.FOCUSED_ID_ERROR;
        }
        ArrayList<String> floors = focusedBaseIndoorMapInfo.getFloors();
        return (floors == null || !floors.contains(str)) ? MapBaseIndoorMapInfo.SwitchFloorError.FLOOR_OVERLFLOW : this.i.a(str, str2) ? MapBaseIndoorMapInfo.SwitchFloorError.SWITCH_OK : MapBaseIndoorMapInfo.SwitchFloorError.SWITCH_ERROR;
    }
}
