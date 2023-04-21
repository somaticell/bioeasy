package cn.com.bioeasy.healty.app.healthapp.modules.mall.modal;

import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.retrofit.HttpResult;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.CategoryNode;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.Goods;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.GoodsEvaluation;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.GoodsWithBLOBs;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface GoodsService {
    @GET("app/goods/category/list/get")
    Observable<HttpResult<List<CategoryNode>>> getGoodsCategoryList();

    @GET("app/goods/get")
    Observable<HttpResult<GoodsWithBLOBs>> getGoodsDetail(@Query("goodsId") int i);

    @GET("app/goods/evaluation/list")
    Observable<HttpResult<PageResult<GoodsEvaluation>>> getGoodsEvaluationList(@Query("goodsId") int i, @Query("stars") int i2, @Query("pageSize") int i3, @Query("pageNumber") int i4);

    @GET("app/goods/list/get")
    Observable<HttpResult<PageResult<Goods>>> getGoodsList(@Query("category") int i, @Query("pageSize") int i2, @Query("pageNumber") int i3);

    @GET("app/goods/list/ids/get")
    Observable<HttpResult<List<GoodsWithBLOBs>>> getGoodsListByIds(@Query("ids") String str);

    @GET("app/search/suggest?")
    Observable<HttpResult<List<String>>> getGoodsSuggestList(@Query("keyword") String str, @Query("category") String str2);

    @GET("app/goods/recommend/list/get")
    Observable<HttpResult<List<Goods>>> getRecommendGoodsList();

    @GET("app/search/goods?")
    Observable<HttpResult<PageResult<Goods>>> getSearchGoodsList(@Query("keyword") String str, @Query("category") String str2, @Query("pageNumber") int i);
}
