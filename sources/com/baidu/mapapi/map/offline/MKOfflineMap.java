package com.baidu.mapapi.map.offline;

import com.baidu.platform.comapi.map.i;
import com.baidu.platform.comapi.map.t;
import com.baidu.platform.comapi.map.u;
import com.baidu.platform.comapi.map.x;
import com.baidu.platform.comapi.map.y;
import java.util.ArrayList;
import java.util.Iterator;

public class MKOfflineMap {
    public static final int TYPE_DOWNLOAD_UPDATE = 0;
    public static final int TYPE_NETWORK_ERROR = 2;
    public static final int TYPE_NEW_OFFLINE = 6;
    public static final int TYPE_VER_UPDATE = 4;
    private static final String a = MKOfflineMap.class.getSimpleName();
    /* access modifiers changed from: private */
    public u b;
    /* access modifiers changed from: private */
    public MKOfflineMapListener c;

    public void destroy() {
        this.b.d(0);
        this.b.b((y) null);
        this.b.b();
        i.b();
    }

    public ArrayList<MKOLUpdateElement> getAllUpdateInfo() {
        ArrayList<x> e = this.b.e();
        if (e == null) {
            return null;
        }
        ArrayList<MKOLUpdateElement> arrayList = new ArrayList<>();
        Iterator<x> it = e.iterator();
        while (it.hasNext()) {
            arrayList.add(OfflineMapUtil.getUpdatElementFromLocalMapElement(it.next().a()));
        }
        return arrayList;
    }

    public ArrayList<MKOLSearchRecord> getHotCityList() {
        ArrayList<t> c2 = this.b.c();
        if (c2 == null) {
            return null;
        }
        ArrayList<MKOLSearchRecord> arrayList = new ArrayList<>();
        Iterator<t> it = c2.iterator();
        while (it.hasNext()) {
            arrayList.add(OfflineMapUtil.getSearchRecordFromLocalCityInfo(it.next()));
        }
        return arrayList;
    }

    public ArrayList<MKOLSearchRecord> getOfflineCityList() {
        ArrayList<t> d = this.b.d();
        if (d == null) {
            return null;
        }
        ArrayList<MKOLSearchRecord> arrayList = new ArrayList<>();
        Iterator<t> it = d.iterator();
        while (it.hasNext()) {
            arrayList.add(OfflineMapUtil.getSearchRecordFromLocalCityInfo(it.next()));
        }
        return arrayList;
    }

    public MKOLUpdateElement getUpdateInfo(int i) {
        x g = this.b.g(i);
        if (g == null) {
            return null;
        }
        return OfflineMapUtil.getUpdatElementFromLocalMapElement(g.a());
    }

    @Deprecated
    public int importOfflineData() {
        return importOfflineData(false);
    }

    @Deprecated
    public int importOfflineData(boolean z) {
        int i;
        int i2 = 0;
        ArrayList<x> e = this.b.e();
        if (e != null) {
            i2 = e.size();
            i = i2;
        } else {
            i = 0;
        }
        this.b.a(z, true);
        ArrayList<x> e2 = this.b.e();
        if (e2 != null) {
            i2 = e2.size();
        }
        return i2 - i;
    }

    public boolean init(MKOfflineMapListener mKOfflineMapListener) {
        i.a();
        this.b = u.a();
        if (this.b == null) {
            return false;
        }
        this.b.a((y) new a(this));
        this.c = mKOfflineMapListener;
        return true;
    }

    public boolean pause(int i) {
        return this.b.c(i);
    }

    public boolean remove(int i) {
        return this.b.e(i);
    }

    public ArrayList<MKOLSearchRecord> searchCity(String str) {
        ArrayList<t> a2 = this.b.a(str);
        if (a2 == null) {
            return null;
        }
        ArrayList<MKOLSearchRecord> arrayList = new ArrayList<>();
        Iterator<t> it = a2.iterator();
        while (it.hasNext()) {
            arrayList.add(OfflineMapUtil.getSearchRecordFromLocalCityInfo(it.next()));
        }
        return arrayList;
    }

    public boolean start(int i) {
        if (this.b == null) {
            return false;
        }
        if (this.b.e() != null) {
            Iterator<x> it = this.b.e().iterator();
            while (it.hasNext()) {
                x next = it.next();
                if (next.a.a == i) {
                    if (next.a.j || next.a.l == 2 || next.a.l == 3 || next.a.l == 6) {
                        return this.b.b(i);
                    }
                    return false;
                }
            }
        }
        return this.b.a(i);
    }

    public boolean update(int i) {
        if (this.b == null) {
            return false;
        }
        if (this.b.e() != null) {
            Iterator<x> it = this.b.e().iterator();
            while (it.hasNext()) {
                x next = it.next();
                if (next.a.a == i) {
                    if (next.a.j) {
                        return this.b.f(i);
                    }
                    return false;
                }
            }
        }
        return false;
    }
}
