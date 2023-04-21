package cn.com.bioeasy.healty.app.healthapp.modules.user.modal;

import android.content.Context;
import android.support.annotation.NonNull;
import cn.com.bioeasy.app.base.BaseRepository;
import cn.com.bioeasy.app.retrofit.HttpResult;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.AddressBean;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.LoginRequest;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.OpenAccount;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.User;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.UserInfo;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.UserIntegral;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.UserSms;
import java.util.List;
import javax.inject.Inject;
import retrofit2.Retrofit;
import rx.Observable;

public class UserRepository extends BaseRepository<UserApiService> {
    @Inject
    public UserRepository(Context context, Retrofit retrofit) {
        super(context, retrofit);
    }

    /* access modifiers changed from: protected */
    public Class<UserApiService> getServiceClass() {
        return UserApiService.class;
    }

    public Observable<HttpResult<UserInfo>> login(LoginRequest loginRequest) {
        startRequest();
        return ((UserApiService) this.service).login(loginRequest);
    }

    public Observable<HttpResult<OpenAccount>> wxLogin(OpenAccount openAccount) {
        startRequest();
        return ((UserApiService) this.service).wxLogin(openAccount);
    }

    public Observable<HttpResult<Integer>> logout(UserInfo user) {
        startRequest();
        return ((UserApiService) this.service).logout(user);
    }

    public Observable<HttpResult<Integer>> updateUser(User user) {
        startRequest();
        return ((UserApiService) this.service).updateUser(user);
    }

    public Observable<HttpResult<UserInfo>> register(User userBean) {
        startRequest();
        return ((UserApiService) this.service).register(userBean);
    }

    public Observable<HttpResult<String>> sms(UserSms sms) {
        startRequest();
        return ((UserApiService) this.service).sms(sms);
    }

    public Observable<HttpResult<String>> smsCheck(UserSms sms) {
        startRequest();
        return ((UserApiService) this.service).smcCheck(sms);
    }

    public Observable<HttpResult<UserInfo>> resetPwd(@NonNull User user) {
        startRequest();
        return ((UserApiService) this.service).resetPwd(user);
    }

    public Observable<HttpResult<UserInfo>> resetUserName(@NonNull User user) {
        startRequest();
        return ((UserApiService) this.service).resetUserName(user);
    }

    public Observable<HttpResult<UserInfo>> addOpenAccount(@NonNull OpenAccount openAccount) {
        startRequest();
        return ((UserApiService) this.service).addOpenAccount(openAccount);
    }

    public Observable<HttpResult<Integer>> deleteAddress(@NonNull AddressBean addressBean) {
        startRequest();
        return ((UserApiService) this.service).deleteAddress(addressBean);
    }

    public Observable<HttpResult<Integer>> addAddress(@NonNull AddressBean addressBean) {
        startRequest();
        return ((UserApiService) this.service).addAddress(addressBean);
    }

    public Observable<HttpResult<Integer>> updateAddress(@NonNull AddressBean addressBean) {
        startRequest();
        return ((UserApiService) this.service).updateAddress(addressBean);
    }

    public Observable<HttpResult<List<AddressBean>>> getAllAddress(@NonNull int userId) {
        startRequest();
        return ((UserApiService) this.service).getAllAddress(userId);
    }

    public Observable<HttpResult<Integer>> addUserIntegral(@NonNull UserIntegral integral) {
        startRequest();
        return ((UserApiService) this.service).addUserIntegral(integral);
    }
}
