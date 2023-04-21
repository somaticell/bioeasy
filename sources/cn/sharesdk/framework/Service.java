package cn.sharesdk.framework;

import android.content.Context;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.b.b.f;
import cn.sharesdk.framework.utils.d;
import com.alipay.sdk.sys.a;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import java.util.HashMap;

public abstract class Service {
    /* access modifiers changed from: private */
    public Context a;
    private String b;

    /* access modifiers changed from: protected */
    public abstract int getServiceVersionInt();

    public abstract String getServiceVersionName();

    /* access modifiers changed from: package-private */
    public void a(Context context) {
        this.a = context;
    }

    public Context getContext() {
        return this.a;
    }

    /* access modifiers changed from: package-private */
    public void a(String str) {
        this.b = str;
    }

    public String getAppKey() {
        return this.b;
    }

    public String getDeviceKey() {
        return DeviceHelper.getInstance(this.a).getDeviceKey();
    }

    public void onBind() {
    }

    public void onUnbind() {
    }

    public static abstract class ServiceEvent {
        private static final int PLATFORM = 1;
        protected Service service;

        public ServiceEvent(Service service2) {
            this.service = service2;
        }

        /* access modifiers changed from: protected */
        public HashMap<String, Object> toMap() {
            HashMap<String, Object> hashMap = new HashMap<>();
            DeviceHelper instance = DeviceHelper.getInstance(this.service.a);
            hashMap.put("deviceid", instance.getDeviceKey());
            hashMap.put(a.f, this.service.getAppKey());
            hashMap.put("apppkg", instance.getPackageName());
            hashMap.put("appver", Integer.valueOf(instance.getAppVersion()));
            hashMap.put("sdkver", Integer.valueOf(this.service.getServiceVersionInt()));
            hashMap.put("plat", 1);
            hashMap.put("networktype", instance.getDetailNetworkTypeForStatic());
            hashMap.put("deviceData", instance.getDeviceDataNotAES());
            return hashMap;
        }

        public final String toString() {
            return new Hashon().fromHashMap(toMap());
        }

        /* access modifiers changed from: protected */
        public HashMap<String, Object> filterShareContent(int platformId, Platform.ShareParams params, HashMap<String, Object> result) {
            f.a filterShareContent = ShareSDK.getPlatform(ShareSDK.platformIdToName(platformId)).filterShareContent(params, result);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("shareID", filterShareContent.a);
            hashMap.put("shareContent", new Hashon().fromJson(filterShareContent.toString()));
            d.a().i("filterShareContent ==>>%s", hashMap);
            return hashMap;
        }
    }
}
