package cn.com.bioeasy.healty.app.healthapp.injection.component;

import android.content.Context;
import android.view.LayoutInflater;
import cn.com.bioeasy.app.base.AppManager;
import cn.com.bioeasy.app.base.AppManager_Factory;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.HealthApp_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.Interceptor.LoggingInterceptor;
import cn.com.bioeasy.healty.app.healthapp.Interceptor.LoggingInterceptor_Factory;
import cn.com.bioeasy.healty.app.healthapp.Interceptor.ParamsInterceptor;
import cn.com.bioeasy.healty.app.healthapp.Interceptor.ParamsInterceptor_Factory;
import cn.com.bioeasy.healty.app.healthapp.ble.BLERepository;
import cn.com.bioeasy.healty.app.healthapp.ble.BLERepository_Factory;
import cn.com.bioeasy.healty.app.healthapp.ble.BLERepository_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.cache.CacheManager;
import cn.com.bioeasy.healty.app.healthapp.cache.CacheManager_Factory;
import cn.com.bioeasy.healty.app.healthapp.data.DataManager;
import cn.com.bioeasy.healty.app.healthapp.data.DataManager_Factory;
import cn.com.bioeasy.healty.app.healthapp.data.DataManager_MembersInjector;
import cn.com.bioeasy.healty.app.healthapp.injection.module.ApplicationModule;
import cn.com.bioeasy.healty.app.healthapp.injection.module.ApplicationModule_ProvideApplicationFactory;
import cn.com.bioeasy.healty.app.healthapp.injection.module.ApplicationModule_ProvideContextFactory;
import cn.com.bioeasy.healty.app.healthapp.injection.module.ApplicationModule_ProvideEventBusFactory;
import cn.com.bioeasy.healty.app.healthapp.injection.module.ApplicationModule_ProvideLayoutInflaterFactory;
import cn.com.bioeasy.healty.app.healthapp.injection.module.NetworkModule;
import cn.com.bioeasy.healty.app.healthapp.injection.module.NetworkModule_PrivodeOkHttpClientFactory;
import cn.com.bioeasy.healty.app.healthapp.injection.module.NetworkModule_PrivodeRetrofitFactory;
import cn.com.bioeasy.healty.app.healthapp.widgets.hellochart.HelloChartManager;
import cn.com.bioeasy.healty.app.healthapp.widgets.hellochart.HelloChartManager_Factory;
import com.squareup.otto.Bus;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public final class DaggerApplicationComponent implements ApplicationComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerApplicationComponent.class.desiredAssertionStatus());
    private MembersInjector<BLERepository> bLERepositoryMembersInjector;
    private Provider<BLERepository> bLERepositoryProvider;
    private Provider<CacheManager> cacheManagerProvider;
    private MembersInjector<DataManager> dataManagerMembersInjector;
    private Provider<DataManager> dataManagerProvider;
    private MembersInjector<HealthApp> healthAppMembersInjector;
    private Provider<HelloChartManager> helloChartManagerProvider;
    private Provider<LoggingInterceptor> loggingInterceptorProvider;
    private Provider<ParamsInterceptor> paramsInterceptorProvider;
    private Provider<OkHttpClient> privodeOkHttpClientProvider;
    private Provider<Retrofit> privodeRetrofitProvider;
    private Provider<HealthApp> provideApplicationProvider;
    private Provider<Context> provideContextProvider;
    private Provider<Bus> provideEventBusProvider;
    private Provider<LayoutInflater> provideLayoutInflaterProvider;

    private DaggerApplicationComponent(Builder builder) {
        if ($assertionsDisabled || builder != null) {
            initialize(builder);
            return;
        }
        throw new AssertionError();
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(Builder builder) {
        this.provideContextProvider = ScopedProvider.create(ApplicationModule_ProvideContextFactory.create(builder.applicationModule));
        this.cacheManagerProvider = ScopedProvider.create(CacheManager_Factory.create(this.provideContextProvider));
        this.dataManagerMembersInjector = DataManager_MembersInjector.create(this.cacheManagerProvider);
        this.dataManagerProvider = ScopedProvider.create(DataManager_Factory.create(this.dataManagerMembersInjector, this.provideContextProvider));
        this.healthAppMembersInjector = HealthApp_MembersInjector.create(MembersInjectors.noOp(), this.dataManagerProvider, this.cacheManagerProvider);
        this.provideApplicationProvider = ScopedProvider.create(ApplicationModule_ProvideApplicationFactory.create(builder.applicationModule));
        this.provideEventBusProvider = ScopedProvider.create(ApplicationModule_ProvideEventBusFactory.create(builder.applicationModule));
        this.provideLayoutInflaterProvider = ScopedProvider.create(ApplicationModule_ProvideLayoutInflaterFactory.create(builder.applicationModule));
        this.paramsInterceptorProvider = ScopedProvider.create(ParamsInterceptor_Factory.create(this.provideContextProvider));
        this.loggingInterceptorProvider = ScopedProvider.create(LoggingInterceptor_Factory.create());
        this.privodeOkHttpClientProvider = ScopedProvider.create(NetworkModule_PrivodeOkHttpClientFactory.create(builder.networkModule, this.paramsInterceptorProvider, this.loggingInterceptorProvider));
        this.privodeRetrofitProvider = ScopedProvider.create(NetworkModule_PrivodeRetrofitFactory.create(builder.networkModule, this.provideContextProvider, this.privodeOkHttpClientProvider));
        this.bLERepositoryMembersInjector = BLERepository_MembersInjector.create(this.cacheManagerProvider);
        this.bLERepositoryProvider = ScopedProvider.create(BLERepository_Factory.create(this.bLERepositoryMembersInjector));
        this.helloChartManagerProvider = ScopedProvider.create(HelloChartManager_Factory.create());
    }

    public void inject(HealthApp application) {
        this.healthAppMembersInjector.injectMembers(application);
    }

    public Context context() {
        return this.provideContextProvider.get();
    }

    public HealthApp application() {
        return this.provideApplicationProvider.get();
    }

    public Bus eventBus() {
        return this.provideEventBusProvider.get();
    }

    public LayoutInflater layoutInflater() {
        return this.provideLayoutInflaterProvider.get();
    }

    public AppManager pageManager() {
        return AppManager_Factory.create().get();
    }

    public CacheManager cacheManager() {
        return this.cacheManagerProvider.get();
    }

    public Retrofit retrofit() {
        return this.privodeRetrofitProvider.get();
    }

    public OkHttpClient okHttpClient() {
        return this.privodeOkHttpClientProvider.get();
    }

    public BLERepository bleRepository() {
        return this.bLERepositoryProvider.get();
    }

    public HelloChartManager helloChartManager() {
        return this.helloChartManagerProvider.get();
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public ApplicationModule applicationModule;
        /* access modifiers changed from: private */
        public NetworkModule networkModule;

        private Builder() {
        }

        public ApplicationComponent build() {
            if (this.applicationModule == null) {
                throw new IllegalStateException("applicationModule must be set");
            }
            if (this.networkModule == null) {
                this.networkModule = new NetworkModule();
            }
            return new DaggerApplicationComponent(this);
        }

        public Builder applicationModule(ApplicationModule applicationModule2) {
            if (applicationModule2 == null) {
                throw new NullPointerException("applicationModule");
            }
            this.applicationModule = applicationModule2;
            return this;
        }

        public Builder networkModule(NetworkModule networkModule2) {
            if (networkModule2 == null) {
                throw new NullPointerException("networkModule");
            }
            this.networkModule = networkModule2;
            return this;
        }
    }
}
