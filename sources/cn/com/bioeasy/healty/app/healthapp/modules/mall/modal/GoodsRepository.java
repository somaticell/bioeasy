package cn.com.bioeasy.healty.app.healthapp.modules.mall.modal;

import android.content.Context;
import android.support.annotation.NonNull;
import cn.com.bioeasy.app.base.BaseRepository;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.retrofit.HttpResult;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.CategoryNode;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.Goods;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.GoodsEvaluation;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.GoodsWithBLOBs;
import java.util.List;
import javax.inject.Inject;
import retrofit2.Retrofit;
import rx.Observable;

public class GoodsRepository extends BaseRepository<GoodsService> {
    @Inject
    public GoodsRepository(Context context, Retrofit retrofit) {
        super(context, retrofit);
    }

    /* access modifiers changed from: protected */
    public Class<GoodsService> getServiceClass() {
        return GoodsService.class;
    }

    public Observable<HttpResult<PageResult<Goods>>> getGoodsList(int category, @NonNull int pageSize, @NonNull int pageNumber) {
        startRequest();
        return ((GoodsService) this.service).getGoodsList(category, pageSize, pageNumber);
    }

    public Observable<HttpResult<List<Goods>>> getRecommendGoodsList() {
        startRequest();
        return ((GoodsService) this.service).getRecommendGoodsList();
    }

    public Observable<HttpResult<GoodsWithBLOBs>> getGoodsDetail(@NonNull int goodId) {
        startRequest();
        return ((GoodsService) this.service).getGoodsDetail(goodId);
    }

    public Observable<HttpResult<List<GoodsWithBLOBs>>> getGoodsListByIds(@NonNull String goodsId) {
        startRequest();
        return ((GoodsService) this.service).getGoodsListByIds(goodsId);
    }

    public Observable<HttpResult<PageResult<GoodsEvaluation>>> getGoodsEvaluationList(@NonNull int goodsId, int stars, int pageSize, int pageNumber) {
        startRequest();
        return ((GoodsService) this.service).getGoodsEvaluationList(goodsId, stars, pageSize, pageNumber);
    }

    public Observable<HttpResult<PageResult<Goods>>> getSearchGoodsList(@NonNull String keyword, @NonNull String category, @NonNull int pageNumber) {
        startRequest();
        return ((GoodsService) this.service).getSearchGoodsList(keyword, category, pageNumber);
    }

    public Observable<HttpResult<List<String>>> getGoodsSuggestList(@NonNull String keyword, @NonNull String category) {
        startRequest();
        return ((GoodsService) this.service).getGoodsSuggestList(keyword, category);
    }

    public Observable<HttpResult<List<CategoryNode>>> getGoodsCategoryList() {
        startRequest();
        return ((GoodsService) this.service).getGoodsCategoryList();
    }
}
