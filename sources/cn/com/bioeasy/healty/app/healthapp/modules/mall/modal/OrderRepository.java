package cn.com.bioeasy.healty.app.healthapp.modules.mall.modal;

import android.content.Context;
import android.support.annotation.NonNull;
import cn.com.bioeasy.app.base.BaseRepository;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.retrofit.HttpResult;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.GoodsEvaluation;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.Order;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.OrderEvaluation;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.OrderPay;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.facebook.common.util.UriUtil;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Observable;

public class OrderRepository extends BaseRepository<OrderService> {
    @Inject
    public OrderRepository(Context context, Retrofit retrofit) {
        super(context, retrofit);
    }

    /* access modifiers changed from: protected */
    public Class<OrderService> getServiceClass() {
        return OrderService.class;
    }

    public Observable<HttpResult<Long>> addOrder(@NonNull Order order) {
        startRequest();
        return ((OrderService) this.service).addOrder(order);
    }

    public Observable<HttpResult<Order>> getOrder(@NonNull long orderId) {
        startRequest();
        return ((OrderService) this.service).getOrder(orderId);
    }

    public Observable<HttpResult<PageResult<Order>>> getOrderList(@NonNull int userId, int status, @NonNull int pageSize, @NonNull int pageNumber) {
        startRequest();
        return ((OrderService) this.service).getOrderList(userId, status, pageSize, pageNumber);
    }

    public Observable<HttpResult<Integer>> updateOrder(@NonNull Order order) {
        startRequest();
        return ((OrderService) this.service).updateOrder(order);
    }

    public Observable<HttpResult<Integer>> cancelOrder(@NonNull Order order) {
        startRequest();
        return ((OrderService) this.service).cancelOrder(order);
    }

    public Observable<HttpResult<Integer>> confirmOrder(@NonNull Order order) {
        startRequest();
        return ((OrderService) this.service).confirmOrder(order);
    }

    public Observable<HttpResult<String>> addWxOrderPay(@NonNull OrderPay order) {
        startRequest();
        return ((OrderService) this.service).addWxOrderPay(order);
    }

    public Observable<HttpResult<String>> addAliOrderPay(@NonNull OrderPay order) {
        startRequest();
        return ((OrderService) this.service).addAliOrderPay(order);
    }

    public Observable<HttpResult<Integer>> updateOrderPay(@NonNull OrderPay order) {
        startRequest();
        return ((OrderService) this.service).updateOrderPay(order);
    }

    public Observable<HttpResult<Integer>> addGoodsEvaluation(@NonNull GoodsEvaluation evaluation, String[] images) {
        String evaStr = JSON.toJSONString(evaluation);
        Map<String, RequestBody> fileMaps = new HashMap<>();
        if (images != null && images.length > 0) {
            for (int i = 0; i < images.length; i++) {
                File file = new File(images[i]);
                fileMaps.put(UriUtil.LOCAL_FILE_SCHEME + i + "\"; fileName=\"" + file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
            }
        }
        startRequest();
        return ((OrderService) this.service).addGoodsEvaluation(evaStr, fileMaps.size(), fileMaps);
    }

    public Observable<HttpResult<Integer>> addOrderEvaluation(@NonNull OrderEvaluation evaluation) {
        startRequest();
        return ((OrderService) this.service).addOrderEvaluation(evaluation);
    }

    public Observable<JSONObject> getOrderExpressProgress(String com2, String nu) {
        startRequest();
        return ((OrderService) this.service).getOrderExpressProgress(com2, nu);
    }
}
