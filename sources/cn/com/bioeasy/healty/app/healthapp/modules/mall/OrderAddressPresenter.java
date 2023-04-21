package cn.com.bioeasy.healty.app.healthapp.modules.mall;

import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.exception.HttpServerError;
import cn.com.bioeasy.app.retrofit.HttpResult;
import cn.com.bioeasy.app.retrofit.HttpSubscriber;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.AddressBean;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.AddressView;
import cn.com.bioeasy.healty.app.healthapp.modules.user.modal.UserRepository;
import java.util.List;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class OrderAddressPresenter extends BasePresenter<AddressView, UserRepository> {
    @Inject
    public OrderAddressPresenter(UserRepository mRepository) {
        super(mRepository);
    }

    public void addOrderAddress(AddressBean addressBean) {
        addressBean.setUserId(HealthApp.x.getUserInfo() != null ? HealthApp.x.getUserInfo().getUserId() : 0);
        ((UserRepository) this.mRepository).addAddress(addressBean).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
                ((AddressView) OrderAddressPresenter.this.mUiView).showProgress("");
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<Integer>>() {
            public void onNext(HttpResult<Integer> response) {
                ((AddressView) OrderAddressPresenter.this.mUiView).hideProgress();
                if (response.result == 0) {
                    ((AddressView) OrderAddressPresenter.this.mUiView).updateAddressStatus(((Integer) response.data).intValue(), 1);
                }
            }

            public void OnServerError(HttpServerError e) {
                ((AddressView) OrderAddressPresenter.this.mUiView).hideProgress();
                ((AddressView) OrderAddressPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }

    public void getAllAddress() {
        ((UserRepository) this.mRepository).getAllAddress(HealthApp.x.getUserInfo() != null ? HealthApp.x.getUserInfo().getUserId() : 0).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
                ((AddressView) OrderAddressPresenter.this.mUiView).showProgress((int) R.string.error_view_loading);
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<List<AddressBean>>>() {
            public void onNext(HttpResult<List<AddressBean>> response) {
                ((AddressView) OrderAddressPresenter.this.mUiView).hideProgress();
                if (response.result == 0) {
                    ((AddressView) OrderAddressPresenter.this.mUiView).updateUserAddressList((List) response.data);
                }
            }

            public void OnServerError(HttpServerError e) {
                ((AddressView) OrderAddressPresenter.this.mUiView).hideProgress();
                ((AddressView) OrderAddressPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }

    public void updateOrderAddress(final AddressBean addressBean) {
        addressBean.setUserId(HealthApp.x.getUserInfo() != null ? HealthApp.x.getUserInfo().getUserId() : 0);
        ((UserRepository) this.mRepository).updateAddress(addressBean).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
                ((AddressView) OrderAddressPresenter.this.mUiView).showProgress("");
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<Integer>>() {
            public void onNext(HttpResult<Integer> response) {
                ((AddressView) OrderAddressPresenter.this.mUiView).hideProgress();
                if (response.result == 0) {
                    ((AddressView) OrderAddressPresenter.this.mUiView).updateAddressStatus(addressBean.getId(), 2);
                }
            }

            public void OnServerError(HttpServerError e) {
                ((AddressView) OrderAddressPresenter.this.mUiView).hideProgress();
                ((AddressView) OrderAddressPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }

    public void deleteAddress(final int id) {
        AddressBean addressBean = new AddressBean();
        addressBean.setId(id);
        ((UserRepository) this.mRepository).deleteAddress(addressBean).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
                ((AddressView) OrderAddressPresenter.this.mUiView).showProgress("");
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<Integer>>() {
            public void onNext(HttpResult<Integer> response) {
                ((AddressView) OrderAddressPresenter.this.mUiView).hideProgress();
                if (response.result == 0) {
                    ((AddressView) OrderAddressPresenter.this.mUiView).updateAddressStatus(id, 3);
                }
            }

            public void OnServerError(HttpServerError e) {
                ((AddressView) OrderAddressPresenter.this.mUiView).hideProgress();
                ((AddressView) OrderAddressPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }
}
