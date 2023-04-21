package com.baidu.platform.base;

import android.util.Log;
import cn.com.bioeasy.app.utils.ListUtils;
import com.baidu.mapapi.http.HttpClient;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.Point;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.platform.comapi.util.PermissionCheck;
import com.baidu.platform.comjni.util.AppMD5;
import com.baidu.platform.domain.c;
import com.baidu.platform.domain.d;
import com.baidu.platform.util.a;

public abstract class e {
    protected a a = new a();
    private boolean b = true;
    private boolean c = true;

    public String a() {
        String a2 = a(d.a());
        String authToken = HttpClient.getAuthToken();
        if (authToken == null) {
            Log.e("SearchRequest", "toUrlString get authtoken failed");
            int permissionCheck = PermissionCheck.permissionCheck();
            if (permissionCheck != 0) {
                Log.e("SearchRequest", "try permissionCheck result is: " + permissionCheck);
            }
            return null;
        }
        if (this.b) {
            this.a.a("token", authToken);
        }
        String str = this.a.a() + HttpClient.getPhoneInfo();
        if (this.c) {
            str = str + "&sign=" + AppMD5.getSignMD5String(str);
        }
        return a2 + "?" + str;
    }

    /* access modifiers changed from: protected */
    public final String a(PlanNode planNode) {
        if (planNode == null) {
            return null;
        }
        String str = new String("{");
        LatLng location = planNode.getLocation();
        if (location != null) {
            String str2 = str + "\"type\":1,";
            Point ll2point = CoordUtil.ll2point(location);
            return str2 + "\"xy\":\"" + ll2point.x + ListUtils.DEFAULT_JOIN_SEPARATOR + ll2point.y + "\"}";
        } else if (planNode.getName() == null) {
            return str;
        } else {
            return (str + "\"type\":2,") + "\"keyword\":\"" + planNode.getName() + "\"}";
        }
    }

    public abstract String a(c cVar);

    public void a(boolean z) {
        this.c = z;
    }

    public void b(boolean z) {
        this.b = z;
    }
}
