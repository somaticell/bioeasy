package com.ble.ble.adaption;

public class Error {
    public static final int DISCONNECTED = 1;
    public static final int NOT_NEED_TO_ADAPT = 4;
    public static final int WRITE_FAILED = 2;
    public static final int WRITE_TIMEOUT = 3;
    private int a;
    private String b;

    Error(int i, String str) {
        this.a = i;
        this.b = str;
    }

    public int getErrorCode() {
        return this.a;
    }

    public String getMessage() {
        return this.b;
    }
}
