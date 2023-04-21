package com.alipay.android.phone.mrpc.core;

public abstract class Request {
    private boolean cancel = false;
    protected TransportCallback mCallback;

    public void cancel() {
        this.cancel = true;
    }

    public TransportCallback getCallback() {
        return this.mCallback;
    }

    public boolean isCanceled() {
        return this.cancel;
    }

    public void setTransportCallback(TransportCallback transportCallback) {
        this.mCallback = transportCallback;
    }
}
