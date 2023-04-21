package cn.com.bioeasy.app.exception;

import java.io.IOException;
import java.lang.annotation.Annotation;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitException extends RuntimeException {
    private final Kind kind;
    private final Response response;
    private final Retrofit retrofit;
    private final String url;

    public enum Kind {
        NETWORK,
        HTTP,
        UNEXPECTED
    }

    public static RetrofitException httpError(String url2, Response response2, Retrofit retrofit3) {
        return new RetrofitException(response2.code() + " " + response2.message(), url2, response2, Kind.HTTP, (Throwable) null, retrofit3);
    }

    public static RetrofitException networkError(IOException exception) {
        return new RetrofitException(exception.getMessage(), (String) null, (Response) null, Kind.NETWORK, exception, (Retrofit) null);
    }

    public static RetrofitException unexpectedError(Throwable exception) {
        return new RetrofitException(exception.getMessage(), (String) null, (Response) null, Kind.UNEXPECTED, exception, (Retrofit) null);
    }

    RetrofitException(String message, String url2, Response response2, Kind kind2, Throwable exception, Retrofit retrofit3) {
        super(message, exception);
        this.url = url2;
        this.response = response2;
        this.kind = kind2;
        this.retrofit = retrofit3;
    }

    public String getUrl() {
        return this.url;
    }

    public Response getResponse() {
        return this.response;
    }

    public Kind getKind() {
        return this.kind;
    }

    public Retrofit getRetrofit() {
        return this.retrofit;
    }

    public <T> T getErrorBodyAs(Class<T> type) throws IOException {
        if (this.response == null || this.response.errorBody() == null) {
            return null;
        }
        return this.retrofit.responseBodyConverter(type, new Annotation[0]).convert(this.response.errorBody());
    }
}
