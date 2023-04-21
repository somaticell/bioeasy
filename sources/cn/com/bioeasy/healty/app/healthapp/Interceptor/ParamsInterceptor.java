package cn.com.bioeasy.healty.app.healthapp.Interceptor;

import android.content.Context;
import cn.com.bioeasy.app.log.LogUtil;
import cn.com.bioeasy.healty.app.healthapp.R;
import com.alipay.sdk.sys.a;
import com.orhanobut.logger.Logger;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Singleton
public class ParamsInterceptor implements Interceptor {
    private static final String TAG = "request params";
    private Context context;

    @Inject
    public ParamsInterceptor(Context context2) {
        this.context = context2;
    }

    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request orgRequest = chain.request().newBuilder().addHeader("Content-Type", "application/json; charset=UTF-8").addHeader("Accept-Encoding", "*").addHeader("Accept-Charset", "UTF-8").addHeader("Connection", "keep-alive").addHeader("Accept", "*/*").addHeader("Access-Control-Allow-Origin", "*").addHeader("Access-Control-Allow-Headers", "X-Requested-With").addHeader("Vary", "Accept-Encoding").build();
        RequestBody body = orgRequest.body();
        Logger.e("Login RequestBody>>>>>>>>" + body.toString(), new Object[0]);
        StringBuilder paramsBuilder = new StringBuilder();
        if (body != null) {
            RequestBody newBody = null;
            if (body instanceof FormBody) {
                newBody = addParamsToFormBody((FormBody) body, paramsBuilder);
            } else if (body instanceof MultipartBody) {
                newBody = addParamsToMultipartBody((MultipartBody) body, paramsBuilder);
            }
            if (newBody != null) {
                LogUtil.i(TAG, paramsBuilder.toString());
                return chain.proceed(orgRequest.newBuilder().url(orgRequest.url()).method(orgRequest.method(), newBody).build());
            }
        }
        return chain.proceed(orgRequest);
    }

    private MultipartBody addParamsToMultipartBody(MultipartBody body, StringBuilder paramsBuilder) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        String appcode = this.context.getString(R.string.appkey);
        builder.addFormDataPart("appcode", appcode);
        paramsBuilder.append("appcode=" + appcode);
        for (int i = 0; i < body.size(); i++) {
            builder.addPart(body.part(i));
        }
        return builder.build();
    }

    private FormBody addParamsToFormBody(FormBody body, StringBuilder paramsBuilder) {
        FormBody.Builder builder = new FormBody.Builder();
        String appcode = this.context.getString(R.string.appkey);
        builder.add("appcode", appcode);
        paramsBuilder.append("appcode=" + appcode);
        for (int i = 0; i < body.size(); i++) {
            builder.addEncoded(body.encodedName(i), body.encodedValue(i));
            paramsBuilder.append(a.b);
            paramsBuilder.append(body.name(i));
            paramsBuilder.append("=");
            paramsBuilder.append(body.value(i));
        }
        return builder.build();
    }
}
