package cn.com.bioeasy.healty.app.healthapp.Interceptor;

import dagger.internal.Factory;

public enum LoggingInterceptor_Factory implements Factory<LoggingInterceptor> {
    INSTANCE;

    public LoggingInterceptor get() {
        return new LoggingInterceptor();
    }

    public static Factory<LoggingInterceptor> create() {
        return INSTANCE;
    }
}
