package cn.com.bioeasy.healty.app.healthapp.modules.user;

import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.base.BaseView;
import cn.com.bioeasy.app.retrofit.HttpResult;
import cn.com.bioeasy.app.retrofit.HttpSubscriber;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.cache.CacheManager;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.UserInfo;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.UserIntegral;
import cn.com.bioeasy.healty.app.healthapp.modules.user.modal.UserRepository;
import java.util.Date;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<BaseView, UserRepository> {
    @Inject
    CacheManager cacheManager;

    @Inject
    public MainPresenter(UserRepository mRepository) {
        super(mRepository);
    }

    public void logout(final BasePresenter.ICallback<Integer> callback) {
        UserInfo userInfo = HealthApp.x.getDataManager().getUserInfo();
        if (userInfo != null) {
            ((UserRepository) this.mRepository).logout(userInfo).subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<Integer>>() {
                public void onNext(HttpResult<Integer> result) {
                    if (result.result == 0) {
                        HealthApp.x.getDataManager().setUserInfo((UserInfo) null);
                    }
                    if (callback != null) {
                        callback.onResult(Integer.valueOf(result.result));
                    }
                }
            });
        }
    }

    public void dailySign() {
        final UserInfo userInfo = HealthApp.x.getDataManager().getUserInfo();
        if (userInfo != null) {
            UserIntegral integral = new UserIntegral();
            integral.setType(0);
            integral.setIntegral(20);
            integral.setCtime(new Date().getTime());
            integral.setUserId(Integer.valueOf(userInfo.getUserId()));
            ((UserRepository) this.mRepository).addUserIntegral(integral).subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<Integer>>() {
                public void onNext(HttpResult<Integer> cb) {
                    if (cb.result == 0 && ((Integer) cb.data).intValue() > 0) {
                        userInfo.setIntegral(userInfo.getIntegral() + 20);
                        HealthApp.x.getDataManager().setUserInfo(userInfo);
                        MainPresenter.this.mUiView.showMessage((int) R.string.sign_in);
                    }
                }
            });
        }
    }
}
