package cn.com.bioeasy.app.download;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

public interface DownloadApi {
    public static final String end_point = "http://m.gzgov.gov.cn/";

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String str);
}
