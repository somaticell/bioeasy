package cn.com.bioeasy.healty.app.healthapp.modules.mall;

import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.exception.HttpServerError;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.retrofit.HttpResult;
import cn.com.bioeasy.app.retrofit.HttpSubscriber;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.GoodsEvaluation;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.Order;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.OrderEvaluation;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.OrderPay;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.modal.OrderRepository;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.OrderView;
import com.alibaba.fastjson.JSONObject;
import java.util.Date;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class OrderPresenter extends BasePresenter<OrderView, OrderRepository> {
    @Inject
    public OrderPresenter(OrderRepository mRepository) {
        super(mRepository);
    }

    public void addOrder(final Order order) {
        ((OrderRepository) this.mRepository).addOrder(order).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
                ((OrderView) OrderPresenter.this.mUiView).showProgress((int) R.string.order_generation);
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<Long>>() {
            public void onNext(HttpResult<Long> cb) {
                ((OrderView) OrderPresenter.this.mUiView).hideProgress();
                if (cb.result == 0) {
                    order.setId((Long) cb.data);
                    JSONObject obj = new JSONObject();
                    obj.put("items", (Object) order.getItems());
                    order.setProps(obj.toJSONString());
                    ((OrderView) OrderPresenter.this.mUiView).updateOrderStatus(order, 1);
                }
            }

            public void OnServerError(HttpServerError e) {
                ((OrderView) OrderPresenter.this.mUiView).hideProgress();
                ((OrderView) OrderPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }

            public void onError(Throwable e) {
                ((OrderView) OrderPresenter.this.mUiView).hideProgress();
                ((OrderView) OrderPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }

    public void getOrder(long orderId, final BasePresenter.ICallback<Order> callback) {
        ((OrderRepository) this.mRepository).getOrder(orderId).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<Order>>() {
            public void onNext(HttpResult<Order> cb) {
                if (cb.result == 0) {
                    callback.onResult(cb.data);
                }
            }

            public void OnServerError(HttpServerError e) {
                ((OrderView) OrderPresenter.this.mUiView).hideProgress();
                ((OrderView) OrderPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }

    public void getOrderList(int status, int pageNumber, final boolean refresh) {
        ((OrderRepository) this.mRepository).getOrderList(HealthApp.x.getUserId(), status, 10, pageNumber).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<PageResult<Order>>>() {
            public void onNext(HttpResult<PageResult<Order>> cb) {
                if (cb.result == 0) {
                    ((OrderView) OrderPresenter.this.mUiView).updateOrderList((PageResult) cb.data, refresh);
                }
            }

            public void OnServerError(HttpServerError e) {
                ((OrderView) OrderPresenter.this.mUiView).hideProgress();
                ((OrderView) OrderPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }

    public void createWXOrderPay(Order order, int payType, final BasePresenter.ICallback<String> callback) {
        OrderPay orderPay = new OrderPay();
        orderPay.setPlatform("weixin");
        orderPay.setUserId(Integer.valueOf(HealthApp.x.getUserId()));
        orderPay.setCtime(Long.valueOf(new Date().getTime()));
        orderPay.setOrderId(order.getId());
        orderPay.setPrice(Double.valueOf(Double.parseDouble(order.getTotalPrice())));
        orderPay.setPayType(Integer.valueOf(payType));
        orderPay.setStatus(1);
        ((OrderRepository) this.mRepository).addWxOrderPay(orderPay).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
                ((OrderView) OrderPresenter.this.mUiView).showProgress("");
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<String>>() {
            public void onNext(HttpResult<String> cb) {
                ((OrderView) OrderPresenter.this.mUiView).hideProgress();
                if (cb.result == 0) {
                    callback.onResult(cb.data);
                }
            }

            public void OnServerError(HttpServerError e) {
                ((OrderView) OrderPresenter.this.mUiView).hideProgress();
                ((OrderView) OrderPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }

    public void createAliOrderPay(Order order, int payType, final BasePresenter.ICallback<String> callback) {
        OrderPay orderPay = new OrderPay();
        orderPay.setPlatform("ali");
        orderPay.setUserId(Integer.valueOf(HealthApp.x.getUserId()));
        orderPay.setCtime(Long.valueOf(new Date().getTime()));
        orderPay.setOrderId(order.getId());
        orderPay.setPrice(Double.valueOf(Double.parseDouble(order.getTotalPrice())));
        orderPay.setPayType(Integer.valueOf(payType));
        orderPay.setStatus(1);
        ((OrderRepository) this.mRepository).addAliOrderPay(orderPay).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
                ((OrderView) OrderPresenter.this.mUiView).showProgress("");
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<String>>() {
            public void onNext(HttpResult<String> cb) {
                ((OrderView) OrderPresenter.this.mUiView).hideProgress();
                if (cb.result == 0) {
                    callback.onResult(cb.data);
                }
            }

            public void OnServerError(HttpServerError e) {
                ((OrderView) OrderPresenter.this.mUiView).hideProgress();
                ((OrderView) OrderPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }

    public void updateOrderPay(final Order order, int payId) {
        OrderPay orderPay = new OrderPay();
        orderPay.setId(Integer.valueOf(payId));
        orderPay.setStatus(2);
        orderPay.setOrderId(order.getId());
        ((OrderRepository) this.mRepository).updateOrderPay(orderPay).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
                ((OrderView) OrderPresenter.this.mUiView).showProgress("");
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<Integer>>() {
            public void onNext(HttpResult<Integer> httpResult) {
                ((OrderView) OrderPresenter.this.mUiView).updateOrderStatus(order, 1);
            }

            public void OnServerError(HttpServerError e) {
                ((OrderView) OrderPresenter.this.mUiView).hideProgress();
                ((OrderView) OrderPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }

    public void cancelOrder(final Order order) {
        Order sendOrder = new Order();
        sendOrder.setId(order.getId());
        sendOrder.setUserId(order.getUserId());
        sendOrder.setStatus((byte) 5);
        ((OrderRepository) this.mRepository).cancelOrder(sendOrder).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<Integer>>() {
            public void onNext(HttpResult<Integer> cb) {
                if (cb.result == 0 && ((Integer) cb.data).intValue() > 0) {
                    ((OrderView) OrderPresenter.this.mUiView).updateOrderStatus(order, 5);
                }
            }

            public void OnServerError(HttpServerError e) {
                ((OrderView) OrderPresenter.this.mUiView).hideProgress();
                ((OrderView) OrderPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }

    public void confirmOrder(final Order order) {
        Order sendOrder = new Order();
        sendOrder.setId(order.getId());
        ((OrderRepository) this.mRepository).confirmOrder(sendOrder).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<Integer>>() {
            public void onNext(HttpResult<Integer> cb) {
                if (cb.result == 0 && ((Integer) cb.data).intValue() > 0) {
                    ((OrderView) OrderPresenter.this.mUiView).updateOrderStatus(order, 3);
                }
            }

            public void OnServerError(HttpServerError e) {
                ((OrderView) OrderPresenter.this.mUiView).hideProgress();
                ((OrderView) OrderPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }

    public void addGoodsEvaluation(final GoodsEvaluation evaluation, String[] images, final BasePresenter.ICallback<GoodsEvaluation> callback) {
        ((OrderRepository) this.mRepository).addGoodsEvaluation(evaluation, images).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
                ((OrderView) OrderPresenter.this.mUiView).showProgress((int) R.string.uploading_evaluation);
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<Integer>>() {
            public void onNext(HttpResult<Integer> cb) {
                ((OrderView) OrderPresenter.this.mUiView).hideProgress();
                if (cb.result == 0 && ((Integer) cb.data).intValue() > 0) {
                    ((OrderView) OrderPresenter.this.mUiView).showMessage((int) R.string.evaluation_success);
                    if (callback != null) {
                        callback.onResult(evaluation);
                    }
                }
            }

            public void OnServerError(HttpServerError e) {
                ((OrderView) OrderPresenter.this.mUiView).hideProgress();
                ((OrderView) OrderPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }

    public void addOrderEvaluation(final OrderEvaluation evaluation, final BasePresenter.ICallback<OrderEvaluation> callback) {
        ((OrderRepository) this.mRepository).addOrderEvaluation(evaluation).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<Integer>>() {
            public void onNext(HttpResult<Integer> cb) {
                if (cb.result == 0 && ((Integer) cb.data).intValue() > 0) {
                    ((OrderView) OrderPresenter.this.mUiView).showMessage((int) R.string.evaluation_success);
                    if (callback != null) {
                        callback.onResult(evaluation);
                    }
                }
            }

            public void OnServerError(HttpServerError e) {
                ((OrderView) OrderPresenter.this.mUiView).hideProgress();
                ((OrderView) OrderPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }

    public void getOrderExpressProgress(String com2, String nu, final BasePresenter.ICallback<JSONObject> callback) {
        ((OrderRepository) this.mRepository).getOrderExpressProgress(com2, nu).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<JSONObject>() {
            public void onNext(JSONObject result) {
                if (callback != null) {
                    callback.onResult(result);
                }
            }

            public void OnServerError(HttpServerError e) {
                ((OrderView) OrderPresenter.this.mUiView).hideProgress();
                ((OrderView) OrderPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }
}
