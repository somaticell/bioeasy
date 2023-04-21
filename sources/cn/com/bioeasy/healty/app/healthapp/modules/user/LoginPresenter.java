package cn.com.bioeasy.healty.app.healthapp.modules.user;

import android.text.TextUtils;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.exception.HttpServerError;
import cn.com.bioeasy.app.retrofit.HttpResult;
import cn.com.bioeasy.app.retrofit.HttpSubscriber;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.cache.CacheManager;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.LoginRequest;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.OpenAccount;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.User;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.UserInfo;
import cn.com.bioeasy.healty.app.healthapp.modules.user.modal.UserRepository;
import cn.com.bioeasy.healty.app.healthapp.modules.user.view.LoginView;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginView, UserRepository> {
    private CacheManager cacheManager;

    @Inject
    public LoginPresenter(CacheManager cacheManager2, UserRepository repository) {
        super(repository);
        this.cacheManager = cacheManager2;
    }

    public void login() {
        String phone = ((LoginView) this.mUiView).getPhoneNum();
        String securityPsw = ((LoginView) this.mUiView).getPassword();
        if (TextUtils.isEmpty(phone)) {
            ((LoginView) this.mUiView).showMessage((int) R.string.enter_username_mailbox);
        } else if (TextUtils.isEmpty(securityPsw)) {
            ((LoginView) this.mUiView).showMessage((int) R.string.input_password);
        } else {
            LoginRequest loginBean = new LoginRequest();
            loginBean.setUsername(phone);
            loginBean.setPassword(securityPsw);
            ((UserRepository) this.mRepository).login(loginBean).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
                public void call() {
                    ((LoginView) LoginPresenter.this.mUiView).showProgress("");
                }
            }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<UserInfo>>() {
                public void onNext(HttpResult<UserInfo> loginResult) {
                    ((LoginView) LoginPresenter.this.mUiView).hideProgress();
                    if (loginResult.result == 0) {
                        LoginPresenter.this.saveLoginInfo((UserInfo) loginResult.data);
                        ((LoginView) LoginPresenter.this.mUiView).toMainPage();
                        return;
                    }
                    ((LoginView) LoginPresenter.this.mUiView).showMessage(loginResult.errMsg.getMessage());
                }

                public void onError(Throwable e) {
                    super.onError(e);
                    ((LoginView) LoginPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
                }

                public void OnServerError(HttpServerError e) {
                    ((LoginView) LoginPresenter.this.mUiView).hideProgress();
                    ((LoginView) LoginPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
                }
            });
        }
    }

    public void wxLogin(OpenAccount account, final BasePresenter.ICallback<OpenAccount> callback) {
        ((UserRepository) this.mRepository).wxLogin(account).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<OpenAccount>>() {
            public void onNext(HttpResult<OpenAccount> loginResult) {
                if (!(loginResult.data == null || ((OpenAccount) loginResult.data).getUser() == null)) {
                    LoginPresenter.this.saveLoginInfo(((OpenAccount) loginResult.data).getUser());
                }
                callback.onResult(loginResult.data);
            }

            public void onError(Throwable e) {
                super.onError(e);
            }

            public void OnServerError(HttpServerError e) {
                ((LoginView) LoginPresenter.this.mUiView).hideProgress();
            }
        });
    }

    public void updateNickName(final String nickName, final BasePresenter.ICallback<Integer> callback) {
        final UserInfo userInfo = HealthApp.x.getUserInfo();
        if (userInfo != null) {
            User user = new User();
            user.setId(Integer.valueOf(userInfo.getUserId()));
            user.setNickName(nickName);
            ((UserRepository) this.mRepository).updateUser(user).subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<Integer>>() {
                public void onNext(HttpResult<Integer> result) {
                    if (result.result == 0) {
                        if (userInfo != null) {
                            userInfo.setNickName(nickName);
                        }
                        callback.onResult(result.data);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void saveLoginInfo(UserInfo userInfo) {
        HealthApp.x.getDataManager().setUserInfo(userInfo);
    }
}
