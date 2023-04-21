package com.alipay.android.phone.mrpc.core;

import android.content.Context;

public class DefaultRpcClient extends RpcClient {
    /* access modifiers changed from: private */
    public Context mContext;

    public DefaultRpcClient(Context context) {
        this.mContext = context;
    }

    private Config createConfig(final RpcParams rpcParams) {
        return new Config() {
            public Context getApplicationContext() {
                return DefaultRpcClient.this.mContext.getApplicationContext();
            }

            public RpcParams getRpcParams() {
                return rpcParams;
            }

            public Transport getTransport() {
                return HttpManager.getInstance(getApplicationContext());
            }

            public String getUrl() {
                return rpcParams.getGwUrl();
            }

            public boolean isGzip() {
                return rpcParams.isGzip();
            }
        };
    }

    public <T> T getRpcProxy(Class<T> cls, RpcParams rpcParams) {
        return new RpcFactory(createConfig(rpcParams)).getRpcProxy(cls);
    }
}
