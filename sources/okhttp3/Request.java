package okhttp3;

import com.mob.tools.network.HttpPatch;
import java.net.URI;
import java.net.URL;
import java.util.List;
import okhttp3.Headers;
import okhttp3.internal.http.HttpMethod;

public final class Request {
    /* access modifiers changed from: private */
    public final RequestBody body;
    private volatile CacheControl cacheControl;
    /* access modifiers changed from: private */
    public final Headers headers;
    private volatile URI javaNetUri;
    /* access modifiers changed from: private */
    public final String method;
    /* access modifiers changed from: private */
    public final Object tag;
    /* access modifiers changed from: private */
    public final HttpUrl url;

    private Request(Builder builder) {
        Object obj;
        this.url = builder.url;
        this.method = builder.method;
        this.headers = builder.headers.build();
        this.body = builder.body;
        if (builder.tag != null) {
            obj = builder.tag;
        } else {
            obj = this;
        }
        this.tag = obj;
    }

    public HttpUrl url() {
        return this.url;
    }

    public String method() {
        return this.method;
    }

    public Headers headers() {
        return this.headers;
    }

    public String header(String name) {
        return this.headers.get(name);
    }

    public List<String> headers(String name) {
        return this.headers.values(name);
    }

    public RequestBody body() {
        return this.body;
    }

    public Object tag() {
        return this.tag;
    }

    public Builder newBuilder() {
        return new Builder();
    }

    public CacheControl cacheControl() {
        CacheControl result = this.cacheControl;
        if (result != null) {
            return result;
        }
        CacheControl result2 = CacheControl.parse(this.headers);
        this.cacheControl = result2;
        return result2;
    }

    public boolean isHttps() {
        return this.url.isHttps();
    }

    public String toString() {
        return "Request{method=" + this.method + ", url=" + this.url + ", tag=" + (this.tag != this ? this.tag : null) + '}';
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public RequestBody body;
        /* access modifiers changed from: private */
        public Headers.Builder headers;
        /* access modifiers changed from: private */
        public String method;
        /* access modifiers changed from: private */
        public Object tag;
        /* access modifiers changed from: private */
        public HttpUrl url;

        public Builder() {
            this.method = "GET";
            this.headers = new Headers.Builder();
        }

        private Builder(Request request) {
            this.url = request.url;
            this.method = request.method;
            this.body = request.body;
            this.tag = request.tag;
            this.headers = request.headers.newBuilder();
        }

        public Builder url(HttpUrl url2) {
            if (url2 == null) {
                throw new IllegalArgumentException("url == null");
            }
            this.url = url2;
            return this;
        }

        public Builder url(String url2) {
            if (url2 == null) {
                throw new IllegalArgumentException("url == null");
            }
            if (url2.regionMatches(true, 0, "ws:", 0, 3)) {
                url2 = "http:" + url2.substring(3);
            } else if (url2.regionMatches(true, 0, "wss:", 0, 4)) {
                url2 = "https:" + url2.substring(4);
            }
            HttpUrl parsed = HttpUrl.parse(url2);
            if (parsed != null) {
                return url(parsed);
            }
            throw new IllegalArgumentException("unexpected url: " + url2);
        }

        public Builder url(URL url2) {
            if (url2 == null) {
                throw new IllegalArgumentException("url == null");
            }
            HttpUrl parsed = HttpUrl.get(url2);
            if (parsed != null) {
                return url(parsed);
            }
            throw new IllegalArgumentException("unexpected url: " + url2);
        }

        public Builder header(String name, String value) {
            this.headers.set(name, value);
            return this;
        }

        public Builder addHeader(String name, String value) {
            this.headers.add(name, value);
            return this;
        }

        public Builder removeHeader(String name) {
            this.headers.removeAll(name);
            return this;
        }

        public Builder headers(Headers headers2) {
            this.headers = headers2.newBuilder();
            return this;
        }

        public Builder cacheControl(CacheControl cacheControl) {
            String value = cacheControl.toString();
            if (value.isEmpty()) {
                return removeHeader("Cache-Control");
            }
            return header("Cache-Control", value);
        }

        public Builder get() {
            return method("GET", (RequestBody) null);
        }

        public Builder head() {
            return method("HEAD", (RequestBody) null);
        }

        public Builder post(RequestBody body2) {
            return method("POST", body2);
        }

        public Builder delete(RequestBody body2) {
            return method("DELETE", body2);
        }

        public Builder delete() {
            return delete(RequestBody.create((MediaType) null, new byte[0]));
        }

        public Builder put(RequestBody body2) {
            return method("PUT", body2);
        }

        public Builder patch(RequestBody body2) {
            return method(HttpPatch.METHOD_NAME, body2);
        }

        public Builder method(String method2, RequestBody body2) {
            if (method2 == null || method2.length() == 0) {
                throw new IllegalArgumentException("method == null || method.length() == 0");
            } else if (body2 != null && !HttpMethod.permitsRequestBody(method2)) {
                throw new IllegalArgumentException("method " + method2 + " must not have a request body.");
            } else if (body2 != null || !HttpMethod.requiresRequestBody(method2)) {
                this.method = method2;
                this.body = body2;
                return this;
            } else {
                throw new IllegalArgumentException("method " + method2 + " must have a request body.");
            }
        }

        public Builder tag(Object tag2) {
            this.tag = tag2;
            return this;
        }

        public Request build() {
            if (this.url != null) {
                return new Request(this);
            }
            throw new IllegalStateException("url == null");
        }
    }
}
