package cn.com.bioeasy.app.base;

import android.content.Context;
import cn.com.bioeasy.app.log.LogUtil;
import com.anthony.citypicker.utils.ToastUtils;
import com.baidu.mapapi.UIMsg;

public class BaseResponse {
    public static final String TAG = "BaseResponse";
    protected int pageSize;
    protected int respCode;

    public int getRespCode() {
        return this.respCode;
    }

    public void setRespCode(int respCode2) {
        this.respCode = respCode2;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize2) {
        this.pageSize = pageSize2;
    }

    public boolean check(Context context) {
        LogUtil.i(TAG, toString());
        switch (getRespCode()) {
            case 1:
                ToastUtils.showToast(context, "服务器错误");
                LogUtil.w(TAG, "服务器错误");
                return false;
            case 1000:
                LogUtil.w(TAG, "数据正常返回");
                return true;
            case 1001:
                ToastUtils.showToast(context, "手机号码未注册");
                LogUtil.w(TAG, "手机号码未注册");
                return false;
            case 1002:
                ToastUtils.showToast(context, "密码错误");
                LogUtil.w(TAG, "密码错误");
                return false;
            case 1003:
                LogUtil.w(TAG, "返回数据为空");
                return true;
            case UIMsg.f_FUN.FUN_ID_VOICE_SCH /*2001*/:
                ToastUtils.showToast(context, "此手机号已注册");
                LogUtil.w(TAG, "此手机号已注册");
                return false;
            case 2002:
                ToastUtils.showToast(context, "短信验证码错误");
                LogUtil.w(TAG, "短信验证码错误");
                return false;
            case 2003:
                ToastUtils.showToast(context, "网络连接超时");
                LogUtil.w(TAG, "网络连接超时");
                return false;
            case UIMsg.m_AppUI.MSG_APP_VERSION /*2004*/:
                ToastUtils.showToast(context, "验证码已过期");
                LogUtil.w(TAG, "验证码已过期");
                return false;
            case 2013:
                ToastUtils.showToast(context, "超过改签时间");
                LogUtil.w(TAG, "超过改签时间");
                return false;
            case 3003:
                ToastUtils.showToast(context, "无此项目");
                LogUtil.w(TAG, "无此项目");
                return false;
            case 8000:
                LogUtil.w(TAG, "应用有新版本，请更新");
                return true;
            case 8001:
                ToastUtils.showToast(context, "当前已是最新版本");
                LogUtil.w(TAG, "应用已是最新版本");
                return false;
            case 9007:
                ToastUtils.showToast(context, "删除类容失败");
                LogUtil.w(TAG, "删除类容失败");
                return false;
            case 9008:
                ToastUtils.showToast(context, "更新类容失败");
                LogUtil.w(TAG, "更新类容失败");
                return false;
            case 9009:
                ToastUtils.showToast(context, "新增内容失败");
                LogUtil.w(TAG, "新增内容失败");
                return false;
            case 9999:
                ToastUtils.showToast(context, "获取内容失败");
                LogUtil.w(TAG, "获取内容失败");
                return false;
            default:
                ToastUtils.showToast(context, "网络请求出错");
                LogUtil.w(TAG, "网络请求出错");
                return false;
        }
    }
}
