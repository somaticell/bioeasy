package com.mob.commons;

import android.content.Context;
import cn.sharesdk.framework.ShareSDK;
import com.mob.tools.MobLog;
import com.mob.tools.utils.ReflectHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class MobProductCollector {
    private static final String[] a = {ShareSDK.SDK_TAG, "SMSSDK", "SHAREREC", "MOBAPI", "MOBLINK", "UMSSDK", "CMSSDK", "BBSSDK"};
    private static final HashMap<String, MobProduct> b = new HashMap<>();

    public static final synchronized void registerProduct(MobProduct mobProduct) {
        synchronized (MobProductCollector.class) {
            if (mobProduct != null) {
                if (!b.containsKey(mobProduct.getProductTag())) {
                    b.put(mobProduct.getProductTag(), mobProduct);
                }
            }
        }
    }

    public static final synchronized ArrayList<MobProduct> getProducts() {
        ArrayList<MobProduct> arrayList;
        synchronized (MobProductCollector.class) {
            try {
                ReflectHelper.importClass("com.mob.commons.*");
                for (String newInstance : a) {
                    try {
                        MobProduct mobProduct = (MobProduct) ReflectHelper.newInstance(newInstance, new Object[0]);
                        if (mobProduct != null) {
                            b.put(mobProduct.getProductTag(), mobProduct);
                        }
                    } catch (Throwable th) {
                    }
                }
                int i = 1;
                while (true) {
                    int i2 = i;
                    if (i2 > 128) {
                        break;
                    }
                    try {
                        MobProduct mobProduct2 = (MobProduct) ReflectHelper.newInstance("MobProduct" + i2, new Object[0]);
                        if (mobProduct2 != null) {
                            b.put(mobProduct2.getProductTag(), mobProduct2);
                        }
                    } catch (Throwable th2) {
                    }
                    i = i2 + 1;
                }
            } catch (Throwable th3) {
                MobLog.getInstance().w(th3);
            }
            arrayList = new ArrayList<>();
            for (Map.Entry<String, MobProduct> value : b.entrySet()) {
                arrayList.add(value.getValue());
            }
        }
        return arrayList;
    }

    public static final synchronized String getUserIdentity(Context context, ArrayList<MobProduct> arrayList) {
        String str;
        String str2;
        synchronized (MobProductCollector.class) {
            try {
                Object invokeStaticMethod = ReflectHelper.invokeStaticMethod("DeviceHelper", "getInstance", context);
                String str3 = ReflectHelper.invokeInstanceMethod(invokeStaticMethod, "getPackageName", new Object[0]) + "/" + ReflectHelper.invokeInstanceMethod(invokeStaticMethod, "getAppVersionName", new Object[0]);
                String str4 = "CLV/1";
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    try {
                        MobProduct mobProduct = arrayList.get(i);
                        str2 = str4 + " " + mobProduct.getProductTag() + "/" + mobProduct.getSdkver();
                    } catch (Throwable th) {
                        str2 = str4;
                    }
                    i++;
                    str4 = str2;
                }
                str = str3 + " " + str4 + " " + ("Android/" + ReflectHelper.invokeInstanceMethod(invokeStaticMethod, "getOSVersionInt", new Object[0])) + " " + TimeZone.getDefault().getID() + " " + ("Lang/" + Locale.getDefault().toString().replace("-r", "-"));
            } catch (Throwable th2) {
                MobLog.getInstance().w(th2);
                str = "";
            }
        }
        return str;
    }
}
