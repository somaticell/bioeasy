package cn.com.bioeasy.app.retrofit;

import android.util.Log;
import cn.com.bioeasy.app.exception.HttpServerError;
import cn.com.bioeasy.app.exception.RetrofitException;
import rx.Subscriber;

public abstract class HttpSubscriber<T> extends Subscriber<T> {
    public void onCompleted() {
        Log.v("HttpHelper", "Subscriber On Completed");
    }

    public void onError(Throwable e) {
        e.printStackTrace();
        Log.e("HttpHelper", "Subscriber On Error: " + e.toString());
        try {
            if (e instanceof RetrofitException) {
                RetrofitException exception = (RetrofitException) e;
                if (exception.getKind() == RetrofitException.Kind.HTTP) {
                    OnServerError((HttpServerError) exception.getErrorBodyAs(HttpServerError.class));
                    return;
                }
                if (exception.getKind() == RetrofitException.Kind.NETWORK || exception.getKind() == RetrofitException.Kind.UNEXPECTED) {
                }
            }
        } catch (Throwable e1) {
            e1.printStackTrace();
            Log.e("HttpHelper", ": " + e1.getMessage());
        }
    }

    public void OnServerError(HttpServerError e) {
        Log.e("HttpSubscriber", e.getMessage());
    }
}
