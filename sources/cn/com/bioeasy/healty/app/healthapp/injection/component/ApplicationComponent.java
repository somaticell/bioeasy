package cn.com.bioeasy.healty.app.healthapp.injection.component;

import android.content.Context;
import android.view.LayoutInflater;
import cn.com.bioeasy.app.base.AppManager;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.ble.BLERepository;
import cn.com.bioeasy.healty.app.healthapp.cache.CacheManager;
import cn.com.bioeasy.healty.app.healthapp.injection.module.ApplicationModule;
import cn.com.bioeasy.healty.app.healthapp.injection.module.NetworkModule;
import cn.com.bioeasy.healty.app.healthapp.widgets.hellochart.HelloChartManager;
import com.squareup.otto.Bus;
import dagger.Component;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {
    HealthApp application();

    BLERepository bleRepository();

    CacheManager cacheManager();

    Context context();

    Bus eventBus();

    HelloChartManager helloChartManager();

    void inject(HealthApp healthApp);

    LayoutInflater layoutInflater();

    OkHttpClient okHttpClient();

    AppManager pageManager();

    Retrofit retrofit();
}
