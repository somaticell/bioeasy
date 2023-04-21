package cn.com.bioeasy.app.upload;

import java.util.Map;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Url;
import rx.Observable;

public interface UploadApi {
    public static final String end_point = "http://m.gzgov.gov.cn/";

    @POST
    @Multipart
    Observable<ResponseBody> uploadFile(@Url String str, @PartMap Map<String, RequestBody> map);
}
