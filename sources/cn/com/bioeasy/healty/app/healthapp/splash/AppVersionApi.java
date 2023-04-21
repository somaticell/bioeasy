package cn.com.bioeasy.healty.app.healthapp.splash;

import cn.com.bioeasy.healty.app.healthapp.data.VersionInfo;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

public interface AppVersionApi {
    public static final String VERSION_URL = "http://www.bioeasy.com/dist/te_health_en/version.json";
    public static final String end_point = "http://www.bioeasy.com";

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String str);

    @GET("/dist/te_health_en/history.json")
    Observable<List<VersionInfo>> loadHistoryInfo();

    @GET
    Observable<VersionInfo> loadVersionInfo(@Url String str);
}
