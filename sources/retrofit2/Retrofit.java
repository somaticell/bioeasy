package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.BuiltInConverters;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.ServiceMethod;

public final class Retrofit {
    private final List<CallAdapter.Factory> adapterFactories;
    private final HttpUrl baseUrl;
    private final Call.Factory callFactory;
    private final Executor callbackExecutor;
    private final List<Converter.Factory> converterFactories;
    private final Map<Method, ServiceMethod> serviceMethodCache = new LinkedHashMap();
    private final boolean validateEagerly;

    Retrofit(Call.Factory callFactory2, HttpUrl baseUrl2, List<Converter.Factory> converterFactories2, List<CallAdapter.Factory> adapterFactories2, Executor callbackExecutor2, boolean validateEagerly2) {
        this.callFactory = callFactory2;
        this.baseUrl = baseUrl2;
        this.converterFactories = Collections.unmodifiableList(converterFactories2);
        this.adapterFactories = Collections.unmodifiableList(adapterFactories2);
        this.callbackExecutor = callbackExecutor2;
        this.validateEagerly = validateEagerly2;
    }

    public <T> T create(final Class<T> service) {
        Utils.validateServiceInterface(service);
        if (this.validateEagerly) {
            eagerlyValidateMethods(service);
        }
        return Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new InvocationHandler() {
            private final Platform platform = Platform.get();

            public Object invoke(Object proxy, Method method, Object... args) throws Throwable {
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, args);
                }
                if (this.platform.isDefaultMethod(method)) {
                    return this.platform.invokeDefaultMethod(method, service, proxy, args);
                }
                ServiceMethod serviceMethod = Retrofit.this.loadServiceMethod(method);
                return serviceMethod.callAdapter.adapt(new OkHttpCall(serviceMethod, args));
            }
        });
    }

    private void eagerlyValidateMethods(Class<?> service) {
        Platform platform = Platform.get();
        for (Method method : service.getDeclaredMethods()) {
            if (!platform.isDefaultMethod(method)) {
                loadServiceMethod(method);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public ServiceMethod loadServiceMethod(Method method) {
        ServiceMethod result;
        synchronized (this.serviceMethodCache) {
            result = this.serviceMethodCache.get(method);
            if (result == null) {
                result = new ServiceMethod.Builder(this, method).build();
                this.serviceMethodCache.put(method, result);
            }
        }
        return result;
    }

    public Call.Factory callFactory() {
        return this.callFactory;
    }

    public HttpUrl baseUrl() {
        return this.baseUrl;
    }

    public List<CallAdapter.Factory> callAdapterFactories() {
        return this.adapterFactories;
    }

    public CallAdapter<?> callAdapter(Type returnType, Annotation[] annotations) {
        return nextCallAdapter((CallAdapter.Factory) null, returnType, annotations);
    }

    public CallAdapter<?> nextCallAdapter(CallAdapter.Factory skipPast, Type returnType, Annotation[] annotations) {
        Utils.checkNotNull(returnType, "returnType == null");
        Utils.checkNotNull(annotations, "annotations == null");
        int start = this.adapterFactories.indexOf(skipPast) + 1;
        int count = this.adapterFactories.size();
        for (int i = start; i < count; i++) {
            CallAdapter<?> adapter = this.adapterFactories.get(i).get(returnType, annotations, this);
            if (adapter != null) {
                return adapter;
            }
        }
        StringBuilder builder = new StringBuilder("Could not locate call adapter for ").append(returnType).append(".\n");
        if (skipPast != null) {
            builder.append("  Skipped:");
            for (int i2 = 0; i2 < start; i2++) {
                builder.append("\n   * ").append(this.adapterFactories.get(i2).getClass().getName());
            }
            builder.append(10);
        }
        builder.append("  Tried:");
        int count2 = this.adapterFactories.size();
        for (int i3 = start; i3 < count2; i3++) {
            builder.append("\n   * ").append(this.adapterFactories.get(i3).getClass().getName());
        }
        throw new IllegalArgumentException(builder.toString());
    }

    public List<Converter.Factory> converterFactories() {
        return this.converterFactories;
    }

    public <T> Converter<T, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations) {
        return nextRequestBodyConverter((Converter.Factory) null, type, parameterAnnotations, methodAnnotations);
    }

    public <T> Converter<T, RequestBody> nextRequestBodyConverter(Converter.Factory skipPast, Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations) {
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(parameterAnnotations, "parameterAnnotations == null");
        Utils.checkNotNull(methodAnnotations, "methodAnnotations == null");
        int start = this.converterFactories.indexOf(skipPast) + 1;
        int count = this.converterFactories.size();
        for (int i = start; i < count; i++) {
            Converter<?, RequestBody> converter = this.converterFactories.get(i).requestBodyConverter(type, parameterAnnotations, methodAnnotations, this);
            if (converter != null) {
                return converter;
            }
        }
        StringBuilder builder = new StringBuilder("Could not locate RequestBody converter for ").append(type).append(".\n");
        if (skipPast != null) {
            builder.append("  Skipped:");
            for (int i2 = 0; i2 < start; i2++) {
                builder.append("\n   * ").append(this.converterFactories.get(i2).getClass().getName());
            }
            builder.append(10);
        }
        builder.append("  Tried:");
        int count2 = this.converterFactories.size();
        for (int i3 = start; i3 < count2; i3++) {
            builder.append("\n   * ").append(this.converterFactories.get(i3).getClass().getName());
        }
        throw new IllegalArgumentException(builder.toString());
    }

    public <T> Converter<ResponseBody, T> responseBodyConverter(Type type, Annotation[] annotations) {
        return nextResponseBodyConverter((Converter.Factory) null, type, annotations);
    }

    public <T> Converter<ResponseBody, T> nextResponseBodyConverter(Converter.Factory skipPast, Type type, Annotation[] annotations) {
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(annotations, "annotations == null");
        int start = this.converterFactories.indexOf(skipPast) + 1;
        int count = this.converterFactories.size();
        for (int i = start; i < count; i++) {
            Converter<ResponseBody, ?> converter = this.converterFactories.get(i).responseBodyConverter(type, annotations, this);
            if (converter != null) {
                return converter;
            }
        }
        StringBuilder builder = new StringBuilder("Could not locate ResponseBody converter for ").append(type).append(".\n");
        if (skipPast != null) {
            builder.append("  Skipped:");
            for (int i2 = 0; i2 < start; i2++) {
                builder.append("\n   * ").append(this.converterFactories.get(i2).getClass().getName());
            }
            builder.append(10);
        }
        builder.append("  Tried:");
        int count2 = this.converterFactories.size();
        for (int i3 = start; i3 < count2; i3++) {
            builder.append("\n   * ").append(this.converterFactories.get(i3).getClass().getName());
        }
        throw new IllegalArgumentException(builder.toString());
    }

    public <T> Converter<T, String> stringConverter(Type type, Annotation[] annotations) {
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(annotations, "annotations == null");
        int count = this.converterFactories.size();
        for (int i = 0; i < count; i++) {
            Converter<?, String> converter = this.converterFactories.get(i).stringConverter(type, annotations, this);
            if (converter != null) {
                return converter;
            }
        }
        return BuiltInConverters.ToStringConverter.INSTANCE;
    }

    public Executor callbackExecutor() {
        return this.callbackExecutor;
    }

    public static final class Builder {
        private List<CallAdapter.Factory> adapterFactories;
        private HttpUrl baseUrl;
        private Call.Factory callFactory;
        private Executor callbackExecutor;
        private List<Converter.Factory> converterFactories;
        private Platform platform;
        private boolean validateEagerly;

        Builder(Platform platform2) {
            this.converterFactories = new ArrayList();
            this.adapterFactories = new ArrayList();
            this.platform = platform2;
            this.converterFactories.add(new BuiltInConverters());
        }

        public Builder() {
            this(Platform.get());
        }

        public Builder client(OkHttpClient client) {
            return callFactory((Call.Factory) Utils.checkNotNull(client, "client == null"));
        }

        public Builder callFactory(Call.Factory factory) {
            this.callFactory = (Call.Factory) Utils.checkNotNull(factory, "factory == null");
            return this;
        }

        public Builder baseUrl(String baseUrl2) {
            Utils.checkNotNull(baseUrl2, "baseUrl == null");
            HttpUrl httpUrl = HttpUrl.parse(baseUrl2);
            if (httpUrl != null) {
                return baseUrl(httpUrl);
            }
            throw new IllegalArgumentException("Illegal URL: " + baseUrl2);
        }

        public Builder baseUrl(HttpUrl baseUrl2) {
            Utils.checkNotNull(baseUrl2, "baseUrl == null");
            List<String> pathSegments = baseUrl2.pathSegments();
            if (!"".equals(pathSegments.get(pathSegments.size() - 1))) {
                throw new IllegalArgumentException("baseUrl must end in /: " + baseUrl2);
            }
            this.baseUrl = baseUrl2;
            return this;
        }

        public Builder addConverterFactory(Converter.Factory factory) {
            this.converterFactories.add(Utils.checkNotNull(factory, "factory == null"));
            return this;
        }

        public Builder addCallAdapterFactory(CallAdapter.Factory factory) {
            this.adapterFactories.add(Utils.checkNotNull(factory, "factory == null"));
            return this;
        }

        public Builder callbackExecutor(Executor executor) {
            this.callbackExecutor = (Executor) Utils.checkNotNull(executor, "executor == null");
            return this;
        }

        public Builder validateEagerly(boolean validateEagerly2) {
            this.validateEagerly = validateEagerly2;
            return this;
        }

        public Retrofit build() {
            if (this.baseUrl == null) {
                throw new IllegalStateException("Base URL required.");
            }
            Call.Factory callFactory2 = this.callFactory;
            if (callFactory2 == null) {
                callFactory2 = new OkHttpClient();
            }
            Executor callbackExecutor2 = this.callbackExecutor;
            if (callbackExecutor2 == null) {
                callbackExecutor2 = this.platform.defaultCallbackExecutor();
            }
            List<CallAdapter.Factory> adapterFactories2 = new ArrayList<>(this.adapterFactories);
            adapterFactories2.add(this.platform.defaultCallAdapterFactory(callbackExecutor2));
            return new Retrofit(callFactory2, this.baseUrl, new ArrayList<>(this.converterFactories), adapterFactories2, callbackExecutor2, this.validateEagerly);
        }
    }
}
