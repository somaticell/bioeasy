package cn.com.bioeasy.healty.app.healthapp.modules.user.modal;

import cn.com.bioeasy.app.retrofit.HttpResult;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.AddressBean;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.LoginRequest;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.OpenAccount;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.User;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.UserInfo;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.UserIntegral;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.UserSms;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface UserApiService {
    @POST("app/users/address/add")
    Observable<HttpResult<Integer>> addAddress(@Body AddressBean addressBean);

    @POST("app/users/open/account/add")
    Observable<HttpResult<UserInfo>> addOpenAccount(@Body OpenAccount openAccount);

    @POST("app/users/integral/daily/add")
    Observable<HttpResult<Integer>> addUserIntegral(@Body UserIntegral userIntegral);

    @POST("app/users/address/delete")
    Observable<HttpResult<Integer>> deleteAddress(@Body AddressBean addressBean);

    @GET("app/users/address/list/get")
    Observable<HttpResult<List<AddressBean>>> getAllAddress(@Query("userId") int i);

    @POST("app/users/login")
    Observable<HttpResult<UserInfo>> login(@Body LoginRequest loginRequest);

    @POST("app/users/logout")
    Observable<HttpResult<Integer>> logout(@Body UserInfo userInfo);

    @POST("app/users/add")
    Observable<HttpResult<UserInfo>> register(@Body User user);

    @POST("app/users/resetPwd")
    Observable<HttpResult<UserInfo>> resetPwd(@Body User user);

    @POST("app/users/resetUserName")
    Observable<HttpResult<UserInfo>> resetUserName(@Body User user);

    @POST("app/sms/check")
    Observable<HttpResult<String>> smcCheck(@Body UserSms userSms);

    @POST("app/sms")
    Observable<HttpResult<String>> sms(@Body UserSms userSms);

    @POST("app/users/address/update")
    Observable<HttpResult<Integer>> updateAddress(@Body AddressBean addressBean);

    @POST("app/users/update")
    Observable<HttpResult<Integer>> updateUser(@Body User user);

    @POST("app/users/open/login/wx")
    Observable<HttpResult<OpenAccount>> wxLogin(@Body OpenAccount openAccount);
}
