package cn.com.bioeasy.healty.app.healthapp.modules.home.modal;

import android.content.Context;
import android.support.annotation.NonNull;
import cn.com.bioeasy.app.base.BaseRepository;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.retrofit.HttpResult;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.Content;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestStripCategory;
import cn.com.bioeasy.healty.app.healthapp.modules.test.modal.TestItemService;
import java.util.List;
import javax.inject.Inject;
import retrofit2.Retrofit;
import rx.Observable;

public class ContentRepository extends BaseRepository<ContentService> {
    @Inject
    public ContentRepository(Context context, Retrofit retrofit) {
        super(context, retrofit);
    }

    /* access modifiers changed from: protected */
    public Class<ContentService> getServiceClass() {
        return ContentService.class;
    }

    public Observable<HttpResult<PageResult<Content>>> getContentList(@NonNull int pageSize, @NonNull String category, @NonNull int pageNumber) {
        startRequest();
        if (category.equals("")) {
            category = "-1";
        }
        return ((ContentService) this.service).getContentList(pageSize, category, pageNumber);
    }

    public Observable<HttpResult<PageResult<Content>>> getSearchContentList(@NonNull String keyword, @NonNull String category, @NonNull int pageNumber) {
        startRequest();
        return ((ContentService) this.service).getSearchContentList(keyword, category, pageNumber);
    }

    public Observable<HttpResult<List<String>>> getContentSuggestList(@NonNull String keyword, @NonNull String category) {
        startRequest();
        return ((ContentService) this.service).getContentSuggestList(keyword, category);
    }

    public Observable<HttpResult<List<TestStripCategory>>> getItemCategoryList() {
        startRequest();
        return ((ContentService) this.service).getItemCategoryList(TestItemService.TEST_STRIP_CATEGORY_URL);
    }
}
