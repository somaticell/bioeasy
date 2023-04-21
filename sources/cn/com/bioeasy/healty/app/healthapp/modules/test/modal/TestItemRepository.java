package cn.com.bioeasy.healty.app.healthapp.modules.test.modal;

import android.content.Context;
import android.support.annotation.NonNull;
import cn.com.bioeasy.app.base.BaseRepository;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.retrofit.HttpResult;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestStrip;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestStripCategory;
import java.util.List;
import javax.inject.Inject;
import retrofit2.Retrofit;
import rx.Observable;

public class TestItemRepository extends BaseRepository<TestItemService> {
    @Inject
    public TestItemRepository(Context context, Retrofit retrofit) {
        super(context, retrofit);
    }

    /* access modifiers changed from: protected */
    public Class<TestItemService> getServiceClass() {
        return TestItemService.class;
    }

    public Observable<HttpResult<PageResult<TestStrip>>> getItemInfoList(@NonNull int category, @NonNull int page) {
        startRequest();
        return ((TestItemService) this.service).getItemList(TestItemService.TEST_STRIP_LIST_URL, category, page);
    }

    public Observable<HttpResult<List<TestStripCategory>>> getItemCategoryList() {
        startRequest();
        return ((TestItemService) this.service).getItemCategoryList(TestItemService.TEST_STRIP_CATEGORY_URL);
    }
}
