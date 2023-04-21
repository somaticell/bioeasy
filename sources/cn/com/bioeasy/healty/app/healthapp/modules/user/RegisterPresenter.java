package cn.com.bioeasy.healty.app.healthapp.modules.user;

import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.exception.HttpServerError;
import cn.com.bioeasy.app.retrofit.HttpResult;
import cn.com.bioeasy.app.retrofit.HttpSubscriber;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.cache.CacheManager;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.OpenAccount;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.User;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.UserInfo;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.UserSms;
import cn.com.bioeasy.healty.app.healthapp.modules.user.modal.UserRepository;
import cn.com.bioeasy.healty.app.healthapp.modules.user.view.RegisterView;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class RegisterPresenter extends BasePresenter<RegisterView, UserRepository> {
    CacheManager cacheManager;

    @Inject
    public RegisterPresenter(CacheManager cacheManager2, UserRepository mRepository) {
        super(mRepository);
        this.cacheManager = cacheManager2;
    }

    public void sendVerifyCode(String mobile, int type) {
        UserSms sms = new UserSms();
        sms.setMobile(mobile);
        sms.setType(type);
        ((UserRepository) this.mRepository).sms(sms).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<String>>() {
            public void onNext(HttpResult<String> result) {
                if (result.result == 0) {
                }
            }
        });
    }

    public void checkVerifyCode(String mobile, String code, int type, final BasePresenter.ICallback<String> callback) {
        UserSms sms = new UserSms();
        sms.setMobile(mobile);
        sms.setCode(code);
        sms.setType(type);
        ((UserRepository) this.mRepository).smsCheck(sms).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<String>>() {
            public void onNext(HttpResult<String> result) {
                if (result.result == 0) {
                    callback.onResult(result.data);
                }
            }
        });
    }

    public void register(String phoneNum, String password) {
        User rUserBean = new User();
        rUserBean.setUsername(phoneNum);
        rUserBean.setPassword(password);
        ((UserRepository) this.mRepository).register(rUserBean).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
                ((RegisterView) RegisterPresenter.this.mUiView).showProgress("");
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<UserInfo>>() {
            public void onNext(HttpResult<UserInfo> result) {
                ((RegisterView) RegisterPresenter.this.mUiView).hideProgress();
                if (result.result == 0) {
                    RegisterPresenter.this.saveLoginInfo((UserInfo) result.data);
                    ((RegisterView) RegisterPresenter.this.mUiView).toMainHome();
                    return;
                }
                ((RegisterView) RegisterPresenter.this.mUiView).showMessage(result.errMsg.getMessage());
            }

            public void onError(Throwable e) {
                super.onError(e);
                ((RegisterView) RegisterPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }

            public void OnServerError(HttpServerError e) {
                ((RegisterView) RegisterPresenter.this.mUiView).hideProgress();
                ((RegisterView) RegisterPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }

    public void resetPwd(String phoneNum, String password, final BasePresenter.ICallback<UserInfo> callback) {
        User rUserBean = new User();
        rUserBean.setUsername(phoneNum);
        rUserBean.setPassword(password);
        ((UserRepository) this.mRepository).resetPwd(rUserBean).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<UserInfo>>() {
            public void onNext(HttpResult<UserInfo> result) {
                if (result.result == 0) {
                    RegisterPresenter.this.saveLoginInfo((UserInfo) result.data);
                    callback.onResult(result.data);
                    return;
                }
                ((RegisterView) RegisterPresenter.this.mUiView).showMessage(result.errMsg.getMessage());
            }

            public void onError(Throwable e) {
                super.onError(e);
                ((RegisterView) RegisterPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }

            public void OnServerError(HttpServerError e) {
                ((RegisterView) RegisterPresenter.this.mUiView).hideProgress();
                ((RegisterView) RegisterPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }

    public void bindPhone(String oldPhone, String newPhone, final BasePresenter.ICallback<UserInfo> callback) {
        User rUserBean = new User();
        rUserBean.setUsername(oldPhone);
        rUserBean.setMobile(newPhone);
        ((UserRepository) this.mRepository).resetUserName(rUserBean).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<UserInfo>>() {
            public void onNext(HttpResult<UserInfo> result) {
                if (result.result == 0) {
                    RegisterPresenter.this.saveLoginInfo((UserInfo) result.data);
                    callback.onResult(result.data);
                    return;
                }
                ((RegisterView) RegisterPresenter.this.mUiView).showMessage(result.errMsg.getMessage());
            }

            public void onError(Throwable e) {
                super.onError(e);
                ((RegisterView) RegisterPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }

            public void OnServerError(HttpServerError e) {
                ((RegisterView) RegisterPresenter.this.mUiView).hideProgress();
                ((RegisterView) RegisterPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }

    public void addOpenAccount(OpenAccount openAccount, final BasePresenter.ICallback<UserInfo> callback) {
        ((UserRepository) this.mRepository).addOpenAccount(openAccount).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<UserInfo>>() {
            public void onNext(HttpResult<UserInfo> result) {
                if (result.result == 0) {
                    RegisterPresenter.this.saveLoginInfo((UserInfo) result.data);
                    callback.onResult(result.data);
                    return;
                }
                ((RegisterView) RegisterPresenter.this.mUiView).showMessage(result.errMsg.getMessage());
            }

            public void onError(Throwable e) {
                super.onError(e);
                ((RegisterView) RegisterPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }

            public void OnServerError(HttpServerError e) {
                ((RegisterView) RegisterPresenter.this.mUiView).hideProgress();
                ((RegisterView) RegisterPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }

    /* access modifiers changed from: private */
    public void saveLoginInfo(UserInfo userInfo) {
        HealthApp.x.getDataManager().setUserInfo(userInfo);
    }
}
