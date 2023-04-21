package com.loc;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.amap.api.fence.Fence;
import com.amap.api.location.AMapLocation;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: FenceManager */
public class f {
    Context a;
    private Hashtable<PendingIntent, ArrayList<Fence>> b = new Hashtable<>();

    public f(Context context) {
        this.a = context;
    }

    private void a(PendingIntent pendingIntent, Fence fence, int i) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("fenceid", fence.b);
        bundle.putInt("event", i);
        intent.putExtras(bundle);
        try {
            pendingIntent.send(this.a, 0, intent);
        } catch (Exception e) {
        }
    }

    public boolean a(Fence fence, PendingIntent pendingIntent) {
        if (pendingIntent == null || fence == null) {
            return false;
        }
        if (TextUtils.isEmpty(fence.b)) {
            return false;
        }
        if (fence.e < 100.0f) {
            return false;
        }
        if (fence.e > 1000.0f) {
            return false;
        }
        if (!b() && !this.b.containsKey(pendingIntent)) {
            return false;
        }
        if (fence.a() == 0) {
            return false;
        }
        if (fence.a() > 7) {
            return false;
        }
        Iterator<Map.Entry<PendingIntent, ArrayList<Fence>>> it = this.b.entrySet().iterator();
        int i = 0;
        while (it != null && it.hasNext()) {
            i = ((ArrayList) it.next().getValue()).size() + i;
        }
        if (i > 20) {
            return false;
        }
        fence.g = -1;
        if (!b()) {
            ArrayList arrayList = this.b.get(pendingIntent);
            Fence fence2 = null;
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                Fence fence3 = (Fence) it2.next();
                if (!fence3.b.equals(fence.b)) {
                    fence3 = fence2;
                }
                fence2 = fence3;
            }
            if (fence2 != null) {
                arrayList.remove(fence2);
            }
            arrayList.add(fence);
            this.b.put(pendingIntent, arrayList);
        } else {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(fence);
            this.b.put(pendingIntent, arrayList2);
        }
        return true;
    }

    public boolean a(PendingIntent pendingIntent) {
        if (pendingIntent == null || !this.b.containsKey(pendingIntent)) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = this.b.get(pendingIntent).iterator();
        while (it.hasNext()) {
            arrayList.add(((Fence) it.next()).b);
        }
        return a((List<String>) arrayList);
    }

    public boolean a(PendingIntent pendingIntent, String str) {
        if (pendingIntent == null || !this.b.containsKey(pendingIntent) || TextUtils.isEmpty(str)) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        return a(pendingIntent, (List<String>) arrayList);
    }

    private boolean a(PendingIntent pendingIntent, List<String> list) {
        boolean z;
        boolean z2 = false;
        if (!b() && list != null && !list.isEmpty() && this.b.containsKey(pendingIntent)) {
            Iterator it = this.b.get(pendingIntent).iterator();
            while (it != null && it.hasNext()) {
                Fence fence = (Fence) it.next();
                if (list.contains(fence.b) || a(fence)) {
                    it.remove();
                    z = true;
                } else {
                    z = z2;
                }
                z2 = z;
            }
        }
        return z2;
    }

    private boolean a(List<String> list) {
        boolean z;
        if (b() || list == null || list.isEmpty()) {
            return false;
        }
        Iterator<Map.Entry<PendingIntent, ArrayList<Fence>>> it = this.b.entrySet().iterator();
        boolean z2 = false;
        while (it != null && it.hasNext()) {
            Map.Entry next = it.next();
            Iterator it2 = ((ArrayList) next.getValue()).iterator();
            while (it2 != null && it2.hasNext()) {
                Fence fence = (Fence) it2.next();
                if (list.contains(fence.b) || a(fence)) {
                    it2.remove();
                    z = true;
                } else {
                    z = z2;
                }
                z2 = z;
            }
            if (((ArrayList) next.getValue()).isEmpty()) {
                it.remove();
            }
        }
        return z2;
    }

    public void a(AMapLocation aMapLocation) {
        float f;
        if (!b()) {
            Iterator<Map.Entry<PendingIntent, ArrayList<Fence>>> it = this.b.entrySet().iterator();
            while (it != null && it.hasNext()) {
                Map.Entry next = it.next();
                Iterator it2 = ((ArrayList) next.getValue()).iterator();
                while (it2.hasNext()) {
                    Fence fence = (Fence) it2.next();
                    if (!a(fence)) {
                        float a2 = bw.a(new double[]{fence.d, fence.c, aMapLocation.getLatitude(), aMapLocation.getLongitude()});
                        float accuracy = aMapLocation.getAccuracy();
                        if (accuracy >= 500.0f) {
                            f = a2 - (fence.e + 500.0f);
                        } else {
                            f = a2 - (accuracy + fence.e);
                        }
                        boolean z = false;
                        if (f > 0.0f) {
                            if (fence.g != 0) {
                                z = true;
                            }
                            fence.g = 0;
                        } else {
                            if (fence.g != 1) {
                                z = true;
                            }
                            fence.g = 1;
                        }
                        if (z) {
                            switch (fence.g) {
                                case 0:
                                    fence.h = -1;
                                    if ((fence.a() & 2) != 2) {
                                        break;
                                    } else {
                                        a((PendingIntent) next.getKey(), fence, 2);
                                        break;
                                    }
                                case 1:
                                    fence.h = bw.b();
                                    if ((fence.a() & 1) != 1) {
                                        break;
                                    } else {
                                        a((PendingIntent) next.getKey(), fence, 1);
                                        break;
                                    }
                            }
                        } else if ((fence.a() & 4) == 4 && fence.h > 0 && bw.b() - fence.h > fence.c()) {
                            fence.h = bw.b();
                            a((PendingIntent) next.getKey(), fence, 4);
                        }
                    }
                }
            }
        }
    }

    private boolean b() {
        return this.b.isEmpty();
    }

    private boolean a(Fence fence) {
        return fence.b() != -1 && fence.b() <= bw.b();
    }

    public void a() {
        this.b.clear();
    }
}
