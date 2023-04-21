package com.ble.ble.oad;

import com.ble.ble.BleService;

public abstract class OADProxy {
    protected final byte[] a = new byte[262144];
    protected OADListener b = null;
    protected BleService c;
    protected State d = State.idle;
    protected OADType e;

    protected OADProxy(BleService bleService, OADListener oADListener) {
        this.c = bleService;
        this.b = oADListener;
        for (int i = 0; i < this.a.length; i++) {
            this.a[i] = -1;
        }
    }

    public State getState() {
        return this.d;
    }

    public OADType getType() {
        return this.e;
    }

    public boolean isProgramming() {
        return this.d == State.programming;
    }

    public abstract void prepare(String str, String str2, boolean z);

    public abstract void release();

    public abstract void startProgramming(int i);

    public abstract void stopProgramming();
}
