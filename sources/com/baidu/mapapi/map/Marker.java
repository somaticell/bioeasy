package com.baidu.mapapi.map;

import android.graphics.Bitmap;
import android.os.Bundle;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import com.alipay.sdk.authjs.a;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.ParcelItem;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.h;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;

public final class Marker extends Overlay {
    LatLng a;
    BitmapDescriptor b;
    float c;
    float d;
    boolean e;
    boolean f;
    float g;
    String h;
    int i;
    boolean j = false;
    boolean k = false;
    float l;
    int m;
    ArrayList<BitmapDescriptor> n;
    int o = 20;

    Marker() {
        this.type = h.marker;
    }

    private void a(ArrayList<BitmapDescriptor> arrayList, Bundle bundle) {
        ArrayList arrayList2 = new ArrayList();
        Iterator<BitmapDescriptor> it = arrayList.iterator();
        while (it.hasNext()) {
            ParcelItem parcelItem = new ParcelItem();
            Bundle bundle2 = new Bundle();
            Bitmap bitmap = it.next().a;
            ByteBuffer allocate = ByteBuffer.allocate(bitmap.getWidth() * bitmap.getHeight() * 4);
            bitmap.copyPixelsToBuffer(allocate);
            byte[] array = allocate.array();
            bundle2.putByteArray("image_data", array);
            bundle2.putInt("image_width", bitmap.getWidth());
            bundle2.putInt("image_height", bitmap.getHeight());
            MessageDigest messageDigest = null;
            try {
                messageDigest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e2) {
                e2.printStackTrace();
            }
            messageDigest.update(array, 0, array.length);
            byte[] digest = messageDigest.digest();
            StringBuilder sb = new StringBuilder("");
            for (byte b2 : digest) {
                sb.append(Integer.toString((b2 & BLEServiceApi.CONNECTED_STATUS) + 256, 16).substring(1));
            }
            bundle2.putString("image_hashcode", sb.toString());
            parcelItem.setBundle(bundle2);
            arrayList2.add(parcelItem);
        }
        if (arrayList2.size() > 0) {
            ParcelItem[] parcelItemArr = new ParcelItem[arrayList2.size()];
            for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                parcelItemArr[i2] = (ParcelItem) arrayList2.get(i2);
            }
            bundle.putParcelableArray("icons", parcelItemArr);
        }
    }

    /* access modifiers changed from: package-private */
    public Bundle a(Bundle bundle) {
        int i2 = 1;
        super.a(bundle);
        Bundle bundle2 = new Bundle();
        if (this.b != null) {
            bundle.putBundle("image_info", this.b.b());
        }
        GeoPoint ll2mc = CoordUtil.ll2mc(this.a);
        bundle.putInt("animatetype", this.m);
        bundle.putDouble("location_x", ll2mc.getLongitudeE6());
        bundle.putDouble("location_y", ll2mc.getLatitudeE6());
        bundle.putInt("perspective", this.e ? 1 : 0);
        bundle.putFloat("anchor_x", this.c);
        bundle.putFloat("anchor_y", this.d);
        bundle.putFloat("rotate", this.g);
        bundle.putInt("y_offset", this.i);
        bundle.putInt("isflat", this.j ? 1 : 0);
        if (!this.k) {
            i2 = 0;
        }
        bundle.putInt("istop", i2);
        bundle.putInt("period", this.o);
        bundle.putFloat("alpha", this.l);
        if (this.n != null && this.n.size() > 0) {
            a(this.n, bundle);
        }
        bundle2.putBundle(a.f, bundle);
        return bundle;
    }

    public float getAlpha() {
        return this.l;
    }

    public float getAnchorX() {
        return this.c;
    }

    public float getAnchorY() {
        return this.d;
    }

    public BitmapDescriptor getIcon() {
        return this.b;
    }

    public ArrayList<BitmapDescriptor> getIcons() {
        return this.n;
    }

    public String getId() {
        return this.p;
    }

    public int getPeriod() {
        return this.o;
    }

    public LatLng getPosition() {
        return this.a;
    }

    public float getRotate() {
        return this.g;
    }

    public String getTitle() {
        return this.h;
    }

    public boolean isDraggable() {
        return this.f;
    }

    public boolean isFlat() {
        return this.j;
    }

    public boolean isPerspective() {
        return this.e;
    }

    public void setAlpha(float f2) {
        if (f2 < 0.0f || ((double) f2) > 1.0d) {
            this.l = 1.0f;
            return;
        }
        this.l = f2;
        this.listener.b(this);
    }

    public void setAnchor(float f2, float f3) {
        if (f2 >= 0.0f && f2 <= 1.0f && f3 >= 0.0f && f3 <= 1.0f) {
            this.c = f2;
            this.d = f3;
            this.listener.b(this);
        }
    }

    public void setAnimateType(int i2) {
        this.m = i2;
        this.listener.b(this);
    }

    public void setDraggable(boolean z) {
        this.f = z;
        this.listener.b(this);
    }

    public void setFlat(boolean z) {
        this.j = z;
        this.listener.b(this);
    }

    public void setIcon(BitmapDescriptor bitmapDescriptor) {
        if (bitmapDescriptor == null) {
            throw new IllegalArgumentException("marker's icon can not be null");
        }
        this.b = bitmapDescriptor;
        this.listener.b(this);
    }

    public void setIcons(ArrayList<BitmapDescriptor> arrayList) {
        int i2 = 0;
        if (arrayList == null) {
            throw new IllegalArgumentException("marker's icons can not be null");
        } else if (arrayList.size() != 0) {
            if (arrayList.size() != 1) {
                while (true) {
                    int i3 = i2;
                    if (i3 >= arrayList.size()) {
                        this.n = (ArrayList) arrayList.clone();
                        this.b = null;
                        break;
                    } else if (arrayList.get(i3) != null && arrayList.get(i3).a != null) {
                        i2 = i3 + 1;
                    } else {
                        return;
                    }
                }
            } else {
                this.b = arrayList.get(0);
            }
            this.listener.b(this);
        }
    }

    public void setPeriod(int i2) {
        if (i2 <= 0) {
            throw new IllegalArgumentException("marker's period must be greater than zero ");
        }
        this.o = i2;
        this.listener.b(this);
    }

    public void setPerspective(boolean z) {
        this.e = z;
        this.listener.b(this);
    }

    public void setPosition(LatLng latLng) {
        if (latLng == null) {
            throw new IllegalArgumentException("marker's position can not be null");
        }
        this.a = latLng;
        this.listener.b(this);
    }

    public void setRotate(float f2) {
        while (f2 < 0.0f) {
            f2 += 360.0f;
        }
        this.g = f2 % 360.0f;
        this.listener.b(this);
    }

    public void setTitle(String str) {
        this.h = str;
    }

    public void setToTop() {
        this.k = true;
        this.listener.b(this);
    }
}
