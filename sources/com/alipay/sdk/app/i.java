package com.alipay.sdk.app;

import com.alipay.android.phone.mrpc.core.RpcException;
import com.baidu.mapapi.UIMsg;

public enum i {
    SUCCEEDED(9000, "处理成功"),
    FAILED(UIMsg.m_AppUI.MSG_APP_SAVESCREEN, "系统繁忙，请稍后再试"),
    CANCELED(RpcException.ErrorCode.SERVER_METHODNOTFOUND, "用户取消"),
    NETWORK_ERROR(RpcException.ErrorCode.SERVER_PARAMMISSING, "网络连接异常"),
    PARAMS_ERROR(RpcException.ErrorCode.SERVER_REQUESTTIMEOUT, "参数错误"),
    DOUBLE_REQUEST(5000, "重复请求"),
    PAY_WAITTING(8000, "支付结果确认中");
    
    public int h;
    public String i;

    private i(int i2, String str) {
        this.h = i2;
        this.i = str;
    }

    private void b(int i2) {
        this.h = i2;
    }

    private int a() {
        return this.h;
    }

    private void a(String str) {
        this.i = str;
    }

    private String b() {
        return this.i;
    }

    public static i a(int i2) {
        switch (i2) {
            case RpcException.ErrorCode.SERVER_REQUESTTIMEOUT:
                return PARAMS_ERROR;
            case 5000:
                return DOUBLE_REQUEST;
            case RpcException.ErrorCode.SERVER_METHODNOTFOUND:
                return CANCELED;
            case RpcException.ErrorCode.SERVER_PARAMMISSING:
                return NETWORK_ERROR;
            case 8000:
                return PAY_WAITTING;
            case 9000:
                return SUCCEEDED;
            default:
                return FAILED;
        }
    }
}
