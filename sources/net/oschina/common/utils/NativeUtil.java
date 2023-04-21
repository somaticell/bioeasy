package net.oschina.common.utils;

public final class NativeUtil {
    public static void doLoadNative() {
        try {
            System.loadLibrary("osc-common-debug-lib");
        } catch (UnsatisfiedLinkError e) {
            System.loadLibrary("osc-common-lib");
        }
    }
}
