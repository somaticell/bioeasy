package cn.com.bioeasy.healty.app.healthapp.modules.mall.modal;

import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.retrofit.HttpResult;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.Order;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.OrderEvaluation;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.OrderPay;
import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

public interface OrderService {
    @POST("app/order/alipay")
    Observable<HttpResult<String>> addAliOrderPay(@Body OrderPay orderPay);

    @POST("app/goods/evaluation/add")
    @Multipart
    Observable<HttpResult<Integer>> addGoodsEvaluation(@Part("evaluation") String str, @Part("fileCount") int i, @PartMap Map<String, RequestBody> map);

    @POST("app/order/add")
    Observable<HttpResult<Long>> addOrder(@Body Order order);

    @POST("app/order/evaluation/add")
    Observable<HttpResult<Integer>> addOrderEvaluation(@Body OrderEvaluation orderEvaluation);

    @POST("app/order/wepay")
    Observable<HttpResult<String>> addWxOrderPay(@Body OrderPay orderPay);

    @POST("app/order/cancel")
    Observable<HttpResult<Integer>> cancelOrder(@Body Order order);

    @POST("app/order/confirm")
    Observable<HttpResult<Integer>> confirmOrder(@Body Order order);

    @GET("app/order/get")
    Observable<HttpResult<Order>> getOrder(@Query("orderId") long j);

    @GET("http://ali-deliver.showapi.com/showapi_expInfo")
    @Headers({"Authorization:APPCODE 4ff75f45c62548748e3a0e6401d7eadc"})
    Observable<JSONObject> getOrderExpressProgress(@Query("com") String str, @Query("nu") String str2);

    @GET("app/order/list/get")
    Observable<HttpResult<PageResult<Order>>> getOrderList(@Query("userId") int i, @Query("status") int i2, @Query("pageSize") int i3, @Query("pageNumber") int i4);

    @POST("app/order/update")
    Observable<HttpResult<Integer>> updateOrder(@Body Order order);

    @POST("app/order/pay/update")
    Observable<HttpResult<Integer>> updateOrderPay(@Body OrderPay orderPay);
}
