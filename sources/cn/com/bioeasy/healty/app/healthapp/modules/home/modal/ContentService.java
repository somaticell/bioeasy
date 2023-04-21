package cn.com.bioeasy.healty.app.healthapp.modules.home.modal;

import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.retrofit.HttpResult;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.Content;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestStripCategory;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

public interface ContentService {
    @GET("app/content/list/get?")
    Observable<HttpResult<PageResult<Content>>> getContentList(@Query("pageSize") int i, @Query("category") String str, @Query("pageNumber") int i2);

    @GET("app/search/suggest?")
    Observable<HttpResult<List<String>>> getContentSuggestList(@Query("keyword") String str, @Query("category") String str2);

    @GET
    Observable<HttpResult<List<TestStripCategory>>> getItemCategoryList(@Url String str);

    @GET("app/search/content?")
    Observable<HttpResult<PageResult<Content>>> getSearchContentList(@Query("keyword") String str, @Query("category") String str2, @Query("pageNumber") int i);
}
