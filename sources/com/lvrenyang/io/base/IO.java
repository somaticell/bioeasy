package com.lvrenyang.io.base;

import java.util.concurrent.locks.ReentrantLock;

public class IO {
    public final ReentrantLock mCloseLocker = new ReentrantLock();
    public final ReentrantLock mMainLocker = new ReentrantLock();

    public int Write(byte[] buffer, int offset, int count) {
        return -1;
    }

    public int Read(byte[] buffer, int offset, int count, int timeout) {
        return -1;
    }

    public void SkipAvailable() {
    }

    public boolean IsOpened() {
        return false;
    }
}
