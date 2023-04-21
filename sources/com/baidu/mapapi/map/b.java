package com.baidu.mapapi.map;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.MotionEvent;
import com.alipay.sdk.sys.a;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.ae;
import com.baidu.platform.comapi.map.l;
import java.util.Iterator;
import javax.microedition.khronos.opengles.GL10;
import org.json.JSONException;
import org.json.JSONObject;

class b implements l {
    final /* synthetic */ BaiduMap a;

    b(BaiduMap baiduMap) {
        this.a = baiduMap;
    }

    public void a() {
    }

    public void a(Bitmap bitmap) {
        if (this.a.z != null) {
            this.a.z.onSnapshotReady(bitmap);
        }
    }

    public void a(MotionEvent motionEvent) {
        if (this.a.p != null) {
            this.a.p.onTouch(motionEvent);
        }
    }

    public void a(GeoPoint geoPoint) {
        if (this.a.q != null) {
            this.a.q.onMapClick(CoordUtil.mc2ll(geoPoint));
        }
    }

    public void a(ae aeVar) {
        if (this.a.I != null) {
            this.a.I.setVisibility(4);
        }
        if (this.a.o != null) {
            MapStatus a2 = MapStatus.a(aeVar);
            this.a.o.onMapStatusChangeStart(a2);
            this.a.o.onMapStatusChangeStart(a2, (BaiduMap.mapStatusReason & 256) == 256 ? 3 : (BaiduMap.mapStatusReason & 16) == 16 ? 2 : 1);
        }
        BaiduMap.mapStatusReason = 0;
    }

    public void a(String str) {
        ae E;
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject optJSONObject = jSONObject.optJSONArray("dataset").optJSONObject(0);
            GeoPoint b = this.a.i.b(jSONObject.optInt("px"), jSONObject.optInt("py"));
            int optInt = optJSONObject.optInt(a.g);
            if (optInt == 17) {
                if (this.a.q != null) {
                    MapPoi mapPoi = new MapPoi();
                    mapPoi.a(optJSONObject);
                    this.a.q.onMapPoiClick(mapPoi);
                }
            } else if (optInt == 18) {
                if (this.a.y != null) {
                    this.a.y.onMyLocationClick();
                } else {
                    a(b);
                }
            } else if (optInt == 19) {
                if (this.a.i != null && (E = this.a.i.E()) != null) {
                    E.c = 0;
                    E.b = 0;
                    BaiduMap.mapStatusReason |= 16;
                    this.a.i.a(E, 300);
                }
            } else if (optInt == 90909) {
                String optString = optJSONObject.optString("marker_id");
                if (this.a.G == null || !optString.equals(this.a.H.p)) {
                    for (Overlay overlay : this.a.k) {
                        if ((overlay instanceof Marker) && overlay.p.equals(optString)) {
                            if (!this.a.v.isEmpty()) {
                                Iterator it = this.a.v.iterator();
                                while (it.hasNext()) {
                                    ((BaiduMap.OnMarkerClickListener) it.next()).onMarkerClick((Marker) overlay);
                                }
                                return;
                            }
                            a(b);
                        }
                    }
                    return;
                }
                InfoWindow.OnInfoWindowClickListener onInfoWindowClickListener = this.a.G.d;
                if (onInfoWindowClickListener != null) {
                    onInfoWindowClickListener.onInfoWindowClick();
                } else {
                    a(b);
                }
            } else if (optInt == 90910) {
                String optString2 = optJSONObject.optString("polyline_id");
                for (Overlay overlay2 : this.a.k) {
                    if ((overlay2 instanceof Polyline) && overlay2.p.equals(optString2)) {
                        if (!this.a.w.isEmpty()) {
                            Iterator it2 = this.a.w.iterator();
                            while (it2.hasNext()) {
                                ((BaiduMap.OnPolylineClickListener) it2.next()).onPolylineClick((Polyline) overlay2);
                            }
                        } else {
                            a(b);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void a(GL10 gl10, ae aeVar) {
        if (this.a.A != null) {
            this.a.A.onMapDrawFrame(MapStatus.a(aeVar));
        }
    }

    public void a(boolean z) {
        if (this.a.B != null) {
            this.a.B.onBaseIndoorMapMode(z, this.a.getFocusedBaseIndoorMapInfo());
        }
    }

    public void b() {
        Projection unused = this.a.f = new Projection(this.a.i);
        boolean unused2 = this.a.P = true;
        if (this.a.r != null) {
            this.a.r.onMapLoaded();
        }
    }

    public void b(GeoPoint geoPoint) {
        if (this.a.t != null) {
            this.a.t.onMapDoubleClick(CoordUtil.mc2ll(geoPoint));
        }
    }

    public void b(ae aeVar) {
        if (this.a.o != null) {
            this.a.o.onMapStatusChange(MapStatus.a(aeVar));
        }
    }

    public boolean b(String str) {
        try {
            JSONObject optJSONObject = new JSONObject(str).optJSONArray("dataset").optJSONObject(0);
            if (optJSONObject.optInt(a.g) == 90909) {
                String optString = optJSONObject.optString("marker_id");
                if (this.a.H == null || !optString.equals(this.a.H.p)) {
                    Iterator it = this.a.k.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Overlay overlay = (Overlay) it.next();
                        if ((overlay instanceof Marker) && overlay.p.equals(optString)) {
                            Marker marker = (Marker) overlay;
                            if (marker.f) {
                                Marker unused = this.a.J = marker;
                                Point screenLocation = this.a.f.toScreenLocation(this.a.J.a);
                                this.a.J.setPosition(this.a.f.fromScreenLocation(new Point(screenLocation.x, screenLocation.y - 60)));
                                if (this.a.x != null) {
                                    this.a.x.onMarkerDragStart(this.a.J);
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void c() {
        if (this.a.s != null) {
            this.a.s.onMapRenderFinished();
        }
    }

    public void c(GeoPoint geoPoint) {
        if (this.a.u != null) {
            this.a.u.onMapLongClick(CoordUtil.mc2ll(geoPoint));
        }
    }

    public void c(ae aeVar) {
        if (this.a.I != null) {
            this.a.I.setVisibility(0);
        }
        if (this.a.o != null) {
            this.a.o.onMapStatusChangeFinish(MapStatus.a(aeVar));
        }
    }

    public void d() {
        this.a.E.lock();
        try {
            if (this.a.D != null) {
                this.a.D.a();
            }
        } finally {
            this.a.E.unlock();
        }
    }

    public void d(GeoPoint geoPoint) {
        if (this.a.J != null && this.a.J.f) {
            Point screenLocation = this.a.f.toScreenLocation(CoordUtil.mc2ll(geoPoint));
            this.a.J.setPosition(this.a.f.fromScreenLocation(new Point(screenLocation.x, screenLocation.y - 60)));
            if (this.a.x != null && this.a.J.f) {
                this.a.x.onMarkerDrag(this.a.J);
            }
        }
    }

    public void e() {
        this.a.E.lock();
        try {
            if (this.a.D != null) {
                this.a.D.a();
                this.a.i.o();
            }
        } finally {
            this.a.E.unlock();
        }
    }

    public void e(GeoPoint geoPoint) {
        if (this.a.J != null && this.a.J.f) {
            Point screenLocation = this.a.f.toScreenLocation(CoordUtil.mc2ll(geoPoint));
            this.a.J.setPosition(this.a.f.fromScreenLocation(new Point(screenLocation.x, screenLocation.y - 60)));
            if (this.a.x != null && this.a.J.f) {
                this.a.x.onMarkerDragEnd(this.a.J);
            }
            Marker unused = this.a.J = null;
        }
    }

    public void f() {
        this.a.i.b(false);
        this.a.E.lock();
        try {
            if (this.a.D != null) {
                this.a.a(this.a.D);
            }
        } finally {
            this.a.E.unlock();
        }
    }
}
