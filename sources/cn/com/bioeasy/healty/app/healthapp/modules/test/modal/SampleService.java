package cn.com.bioeasy.healty.app.healthapp.modules.test.modal;

import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.retrofit.HttpResult;
import cn.com.bioeasy.healty.app.healthapp.injection.module.NetworkModule;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.BusinessOperator;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.SampleItemResultData;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.SampleResultData;
import cn.com.bioeasy.healty.app.healthapp.modules.record.bean.ProductTypeItemData;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.UploadDataCallback;
import java.util.List;
import java.util.Map;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

public interface SampleService {
    public static final String MAKET_LIST = (NetworkModule.TEST_HTTP_URL + "/app/search/businessOperator");
    public static final String MARKET_SUGGEST = (NetworkModule.TEST_HTTP_URL + "/app/search/suggest?");
    public static final String SAMPLE_DATA = (NetworkModule.TEST_HTTP_URL + "/app/sample/data");
    public static final String SAMPLE_ITEM_DATA = (NetworkModule.TEST_HTTP_URL + "/app/sample/item/data");
    public static final String SAMPLE_ITEM_RESULT_GET = (NetworkModule.TEST_HTTP_URL + "/app/sample/product/type/item/data/list");
    public static final String SAMPLE_RESULT_ADD = (NetworkModule.TEST_HTTP_URL + "/app/sample/result/add");

    @POST
    @Multipart
    Observable<HttpResult<UploadDataCallback>> addSampleData(@Url String str, @Part("sampleData") String str2, @Part("fileCount") int i, @PartMap Map<String, RequestBody> map);

    @GET
    Observable<HttpResult<List<String>>> getMarketSuggestList(@Url String str, @Query("keyword") String str2);

    @GET
    Observable<HttpResult<List<ProductTypeItemData>>> getProductTypeItemDataList(@Url String str, @Query("days") int i, @Query("province") String str2, @Query("city") String str3, @Query("distinct") String str4);

    @GET
    Observable<HttpResult<PageResult<SampleItemResultData>>> getSampleItemResultData(@Url String str, @Query("operatorSn") String str2, @Query("pageNumber") int i, @Query("province") String str3, @Query("city") String str4, @Query("distinct") String str5);

    @GET
    Observable<HttpResult<PageResult<SampleResultData>>> getSampleResultData(@Url String str, @Query("operatorSn") String str2, @Query("pageNumber") int i, @Query("province") String str3, @Query("city") String str4, @Query("distinct") String str5, @Query("result") String str6);

    @GET
    Observable<HttpResult<PageResult<BusinessOperator>>> searchMarketList(@Url String str, @Query("keyword") String str2, @Query("pageNumber") int i, @Query("province") String str3, @Query("city") String str4, @Query("distinct") String str5);
}
