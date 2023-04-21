package cn.com.bioeasy.healty.app.healthapp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import cn.com.bioeasy.healty.app.healthapp.cache.CacheManager;
import cn.com.bioeasy.healty.app.healthapp.constant.PayType;
import cn.com.bioeasy.healty.app.healthapp.data.DataManager;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ApplicationComponent;
import cn.com.bioeasy.healty.app.healthapp.injection.component.DaggerApplicationComponent;
import cn.com.bioeasy.healty.app.healthapp.injection.module.ApplicationModule;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.UserInfo;
import cn.sharesdk.framework.ShareSDK;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import java.util.Locale;
import javax.inject.Inject;
import org.litepal.LitePalApplication;

public class HealthApp extends LitePalApplication {
    public static HealthApp x;
    @Inject
    CacheManager cacheManager;
    @Inject
    DataManager dataManager;
    private boolean isConnected;
    private boolean isDebug;
    private boolean isFreeDebug;
    private ApplicationComponent mAppComponent;

    public void onCreate() {
        super.onCreate();
        x = this;
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                StrictMode.class.getMethod("disableDeathOnFileUriExposure", new Class[0]).invoke((Object) null, new Object[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        getAppComponent().inject(this);
        Logger.init(getClass().getName()).logLevel(LogLevel.NONE);
        new Thread(new Runnable() {
            public void run() {
                Fresco.initialize(HealthApp.this);
                HealthApp.this.dataManager.initialize();
                ShareSDK.initSDK(HealthApp.this);
                WXAPIFactory.createWXAPI(HealthApp.this.getApplicationContext(), (String) null).registerApp(PayType.WX_APP_ID);
            }
        }).start();
        SDKInitializer.initialize(getApplicationContext());
        setLanguage();
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public DataManager getDataManager() {
        return this.dataManager;
    }

    public ApplicationComponent getAppComponent() {
        if (this.mAppComponent == null) {
            this.mAppComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        }
        return this.mAppComponent;
    }

    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 16384);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean hasSystemFeature(Context context, String feature) {
        return context.getPackageManager().hasSystemFeature(feature);
    }

    public UserInfo getUserInfo() {
        return this.dataManager.getUserInfo();
    }

    public int getUserId() {
        if (getUserInfo() != null) {
            return getUserInfo().getUserId();
        }
        return 0;
    }

    public String getUserName() {
        return getUserInfo() != null ? getUserInfo().getUserName() : getString(R.string.anonymous);
    }

    public boolean isLogin() {
        return getUserInfo() != null;
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    public void setConnected(boolean connected) {
        this.isConnected = connected;
    }

    public boolean isDebug() {
        return this.isDebug;
    }

    public void setDebug(boolean debug) {
        this.isDebug = debug;
    }

    public boolean isFreeDebug() {
        return this.isFreeDebug;
    }

    public void setFreeDebug(boolean debug) {
        this.isDebug = debug;
    }

    private void setLanguage() {
        getResources().getConfiguration().setLocale(Locale.ENGLISH);
    }
}
