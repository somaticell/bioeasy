package cn.com.bioeasy.app.retrofit;

import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

public interface RemoteApi {
    public static final String end_point = "http://m.gzgov.gov.cn/";

    @GET("{url}")
    Observable<ResponseBody> loadString(@Path(encoded = true, value = "url") String str);

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> postString(@Url String str, @FieldMap Map<String, String> map);
}
