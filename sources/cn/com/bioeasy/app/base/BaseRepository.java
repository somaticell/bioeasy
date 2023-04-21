package cn.com.bioeasy.app.base;

import android.content.Context;
import cn.com.bioeasy.app.common.AppUtils;
import cn.com.bioeasy.app.log.LogUtil;
import com.anthony.citypicker.utils.ToastUtils;
import retrofit2.Retrofit;

public abstract class BaseRepository<S> {
    private static final String TAG = "repository";
    private boolean isCancle = false;
    protected Context mContext;
    protected Retrofit retrofit;
    protected S service;

    /* access modifiers changed from: protected */
    public abstract Class<S> getServiceClass();

    public BaseRepository(Context context, Retrofit retrofit3) {
        this.mContext = context;
        this.retrofit = retrofit3;
        this.service = retrofit3.create(getServiceClass());
    }

    public boolean isCanRequest() {
        if (AppUtils.isOnline(this.mContext)) {
            return true;
        }
        ToastUtils.showToast(this.mContext, "请检查你的网络连接");
        return false;
    }

    public boolean isCancled() {
        LogUtil.d(TAG, "请求是否取消：" + this.isCancle);
        return this.isCancle;
    }

    public void cancleRequest() {
        this.isCancle = true;
    }

    public void startRequest() {
        this.isCancle = false;
    }
}
