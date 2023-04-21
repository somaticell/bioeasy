package com.ble.ble.adaption;

public interface OnResultListener {
    void onError(String str, Error error);

    void onSuccess(String str);
}
