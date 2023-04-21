package com.mob.commons.authorize;

import android.content.Context;
import com.mob.commons.MobProduct;
import com.mob.commons.MobProductCollector;
import com.mob.commons.a;

public final class DeviceAuthorizer {
    public static String authorize(Context context, final MobProduct mobProduct) {
        return authorize(context, (MobProduct) new MobProduct() {
            public int getSdkver() {
                return mobProduct.getSdkver();
            }

            public String getProductTag() {
                return mobProduct.getProductTag();
            }

            public String getProductAppkey() {
                return mobProduct.getProductAppkey();
            }
        });
    }

    public static synchronized String authorize(Context context, MobProduct mobProduct) {
        String a;
        synchronized (DeviceAuthorizer.class) {
            MobProductCollector.registerProduct(mobProduct);
            a aVar = new a();
            if (mobProduct == null || !a.h(context)) {
                a = aVar.a(context);
            } else {
                a = aVar.a(context, mobProduct);
            }
        }
        return a;
    }
}
