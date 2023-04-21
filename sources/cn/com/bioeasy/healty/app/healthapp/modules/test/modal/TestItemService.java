package cn.com.bioeasy.healty.app.healthapp.modules.test.modal;

import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.retrofit.HttpResult;
import cn.com.bioeasy.healty.app.healthapp.injection.module.NetworkModule;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestStrip;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestStripCategory;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

public interface TestItemService {
    public static final String TEST_STRIP_CATEGORY_URL = (NetworkModule.TEST_HTTP_URL + "/app/testStrip/category/list");
    public static final String TEST_STRIP_LIST_URL = (NetworkModule.TEST_HTTP_URL + "/app/testStrip/list");

    @GET
    Observable<HttpResult<List<TestStripCategory>>> getItemCategoryList(@Url String str);

    @GET
    Observable<HttpResult<PageResult<TestStrip>>> getItemList(@Url String str, @Query("categoryId") int i, @Query("pageNumber") int i2);
}
